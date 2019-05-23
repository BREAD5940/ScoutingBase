package base;

import java.util.HashMap;

import com.cpjd.models.matches.Match;

public class CustomMatch{

    public int teamNum;
    public String matchType;
    public int matchNum;
    public String alliancePosition;
    public boolean isBlueAlliance;

    public int HPShipGame;
    public int HPShipSand;
    public int HPRocketGame;
    public int HPRocketSand;
    public int HPDropGame;
    public int HPDropSand;

    public int CShipGame;
    public int CShipSand;
    public int CRocketGame;
    public int CRocketSand;
    public int CDropGame;
    public int CDropSand;

    public int scaleLevel;
    public boolean isHelp;

    public int startHab;
    public boolean crossedLine;

    public int fouls;
    public int techs;
    public boolean yellow;
    public boolean red;
    public boolean eStopped;
    public boolean borked;

    public int points;
    public int nonFoulPoints;
    public int rankingPoints;
    public boolean rRocket, lRocket, habRP, crRP;

    HashMap<String, Object> scoreBreakdown;
    public boolean tbaSynced=false;

    public String matchNotes;

    public CustomMatch(String[] csvRow){
        this.matchType = csvRow[0];
        this.matchNum = Integer.valueOf(csvRow[1]);
        this.teamNum = Integer.valueOf(csvRow[2]);
        this.alliancePosition = csvRow[3];
        this.isBlueAlliance = alliancePosition.charAt(0) == 'B';

        this.startHab = Integer.valueOf(csvRow[4]);

        this.CShipSand = Integer.valueOf(csvRow[5]);
        this.CRocketSand = Integer.valueOf(csvRow[6]);
        this.CDropSand = Integer.valueOf(csvRow[7]);
        this.HPShipSand = Integer.valueOf(csvRow[8]);
        this.HPRocketSand = Integer.valueOf(csvRow[9]);
        this.HPDropSand = Integer.valueOf(csvRow[10]);

        this.CShipGame = Integer.valueOf(csvRow[11]);
        this.CRocketGame = Integer.valueOf(csvRow[12]);
        this.CDropGame = Integer.valueOf(csvRow[13]);
        this.HPShipGame = Integer.valueOf(csvRow[14]);
        this.HPRocketGame = Integer.valueOf(csvRow[15]);
        this.HPDropGame = Integer.valueOf(csvRow[16]);

        this.scaleLevel = Integer.valueOf(csvRow[17]);

        this.techs = Integer.valueOf(csvRow[18]);
        this.fouls = Integer.valueOf(csvRow[19]);
        this.yellow = (csvRow[20].equalsIgnoreCase("true"));
        this.red = csvRow[21].equalsIgnoreCase("true");
        this.borked = csvRow[22].equalsIgnoreCase("true");
        this.points = Integer.valueOf(csvRow[23]);

        this.matchNotes = csvRow[24];
    }

    public int getSandPlaces(){
        return HPShipSand+HPRocketSand+CShipSand+CRocketSand;
    }

    public void syncTBA(){
        //grab all the matches
        String[] keys = Main.tbaApi.getMatchKeys(Main.tbaEventKey);
        Match foundMatch = new Match();

        //find the correct match
        // TODO this could probably be done with key string modification (to save loops), but that's jank
        for(String key : keys){
            if( this.matchNum == Main.tbaApi.getMatch(key).getMatchNumber()){
                foundMatch = Main.tbaApi.getMatch(key);
            }
        }

        //get the correct points TODO find a better way to do blue vs red
        if(this.points != foundMatch.getBlue().getScore() && this.isBlueAlliance) {
            Lib.report(String.format("Scouted points for match %d, Blue Alliance are incorrect. %nScouted points: %d%nTBA points: %d",this.matchNum, this.points, foundMatch.getBlue().getScore()));
            this.points = (int)(foundMatch.getBlue().getScore());
        }
        
        if (this.points != foundMatch.getRed().getScore() && !this.isBlueAlliance){
            Lib.report(String.format("Scouted points for match %d, Red Alliance are incorrect. %nScouted points: %d%nTBA points: %d", this.matchNum, this.points, foundMatch.getRed().getScore()));
            this.points = (int)(foundMatch.getRed().getScore());
        }

        //get the big fancy hashmap
        if(this.isBlueAlliance){
            this.scoreBreakdown = foundMatch.getBlueScoreBreakdown();
        }else{
            this.scoreBreakdown = foundMatch.getRedScoreBreakdown();
        }

        //sync fouls
        if(this.fouls > (int)scoreBreakdown.get("foulCount")){
            Lib.report(String.format("Fouls for match %d, alliance position %s are more than the total fouls. %nReported fouls: %d%nTotal fouls: %d",
                         this.matchNum, this.alliancePosition, this.fouls, this.scoreBreakdown.get("foulCount")));
            this.fouls = (int)scoreBreakdown.get("foulCount");
        }

        //sync tech fouls
        if(this.techs > (int)scoreBreakdown.get("techFoulCount")){
            Lib.report(String.format("Tech fouls for match %d, alliance position %s are more than the total tech fouls. %nReported tech fouls: %d%nTotal fouls: %d",
                        this.matchNum, this.alliancePosition, this.techs, this.scoreBreakdown.get("techFoulCount"))); 
            this.techs = (int)scoreBreakdown.get("techFoulCount");
        }

        //get the points excluding points from fouls
        this.nonFoulPoints = this.points - (int)this.scoreBreakdown.get("foulPoints");

        int alPos = this.alliancePosition.charAt(alliancePosition.length()-1);

        //the starting hab
        if(this.startHab != Integer.valueOf(this.scoreBreakdown.get("preMatchLevelRobot"+alPos).toString().charAt(8))){
            Lib.report(String.format("Start level for match %d, alliance position %s is incorrect. %nReported level: %d%nTBA level: %d",
                        this.matchNum, this.alliancePosition, this.startHab, (int)this.scoreBreakdown.get("preMatchLevelRobot"+alPos).toString().charAt(8)));
            this.startHab = Integer.valueOf(this.scoreBreakdown.get("preMatchLevelRobot"+alPos).toString().charAt(8));
        }

        //get the number of ranking points
        this.rankingPoints = (int)this.scoreBreakdown.get("rp");

        this.rRocket = (boolean)this.scoreBreakdown.get("completedRocketNear");
        this.lRocket = (boolean)this.scoreBreakdown.get("completedRocketFar");

        this.habRP = (boolean)this.scoreBreakdown.get("habDockingRankingPoint");
        this.crRP = (boolean)this.scoreBreakdown.get("completeRocketRankingPoint");

        //TODO add more?

        this.tbaSynced = true;
    }
}
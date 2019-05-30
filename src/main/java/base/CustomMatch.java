package base;

import java.util.HashMap;

import com.cpjd.models.matches.Match;
import com.cpjd.utils.exceptions.DataNotFoundException;

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
    public boolean isHelp = false;

    public int startHab;
    public boolean crossedLine = false;

    public Long fouls;
    public Long techs;
    public boolean yellow;
    public boolean red;
    public boolean eStopped;
    public boolean borked;

    public int points;
    public int nonFoulPoints = 0;
    public int rankingPoints = 0;
    public boolean rRocket=false, lRocket=false, habRP=false, crRP=false;

    HashMap<String, Object> scoreBreakdown;
    public boolean tbaSynced=false;

    public String matchNotes;

    public CustomMatch(String[] csvRow){
        this.matchType = csvRow[0];
        this.matchNum = Integer.valueOf(csvRow[1]);
        this.teamNum = Integer.valueOf(csvRow[2]);
        System.out.println(csvRow[3].charAt(0));
        this.alliancePosition = csvRow[3];
        this.isBlueAlliance = this.alliancePosition.charAt(0)=='B';

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

        try{
            this.scaleLevel = Integer.valueOf(csvRow[17]);
        }catch(NumberFormatException e){
            Lib.report("Scale Level is not a number. Setting to 0.");
            this.scaleLevel = 0;
        }

        this.techs = Long.valueOf(csvRow[18]);
        this.fouls = Long.valueOf(csvRow[19]);
        this.yellow = (csvRow[20].equalsIgnoreCase("true"));
        this.red = csvRow[21].equalsIgnoreCase("true");
        this.borked = csvRow[22].equalsIgnoreCase("true");
        this.points = Integer.valueOf(csvRow[23]);

        this.matchNotes = csvRow[24];

        this.matchNotes = this.matchNotes.replace("\n", ".  ");
    }

    public int getSandPlaces(){
        return HPShipSand+HPRocketSand+CShipSand+CRocketSand;
    }

    public void syncTBA(){
        Match foundMatch = new Match();
        try{
            foundMatch = Main.tbaApi.getMatch(Main.tbaEventKey+"_qm"+this.matchNum);
        }catch(DataNotFoundException e){
            System.out.println("Match not found. \n"+e+"\n\n");
            return;
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
        //TODO fix the 0 thing
        if(this.fouls > (Long)scoreBreakdown.get("foulCount")){
            Lib.report(String.format("Fouls for match %d, alliance position %s are more than the total fouls. %nReported fouls: %d%nTotal fouls: %d",
                         this.matchNum, this.alliancePosition, this.fouls, this.scoreBreakdown.get("foulCount")));
            // this.fouls = (Long)scoreBreakdown.get("foulCount");
        }

        //sync tech fouls
        if(this.techs > (Long)scoreBreakdown.get("techFoulCount")){
            Lib.report(String.format("Tech fouls for match %d, alliance position %s are more than the total tech fouls. %nReported tech fouls: %d%nTotal fouls: %d",
                        this.matchNum, this.alliancePosition, this.techs, this.scoreBreakdown.get("techFoulCount"))); 
            // this.techs = (Long)scoreBreakdown.get("techFoulCount");
        }

        //get the points excluding points from fouls
        this.nonFoulPoints = this.points - ((Long)this.scoreBreakdown.get("foulPoints")).intValue();

        int alPos = Character.getNumericValue(this.alliancePosition.charAt(this.alliancePosition.length()-1));
        System.out.println("alpos:"+alPos);
        //the starting hab
        if(this.startHab != Character.getNumericValue(this.scoreBreakdown.get("preMatchLevelRobot"+alPos).toString().charAt(8))){
            Lib.report(String.format("Start level for match %d, alliance position %s is incorrect. %nReported level: %d%nTBA level: %d",
                        this.matchNum, this.alliancePosition, this.startHab, Character.getNumericValue(this.scoreBreakdown.get("preMatchLevelRobot"+alPos).toString().charAt(8))));
            this.startHab = Character.getNumericValue(this.scoreBreakdown.get("preMatchLevelRobot"+alPos).toString().charAt(8));
        }

        //get the number of ranking points
        this.rankingPoints = ((Long)this.scoreBreakdown.get("rp")).intValue();

        this.rRocket = (boolean)this.scoreBreakdown.get("completedRocketNear");
        this.lRocket = (boolean)this.scoreBreakdown.get("completedRocketFar");

        this.habRP = (boolean)this.scoreBreakdown.get("habDockingRankingPoint");
        this.crRP = (boolean)this.scoreBreakdown.get("completeRocketRankingPoint");

        //TODO add more?

        this.tbaSynced = true;
    }

    public String toString(){
        return String.format("Team Number: %d, Match Type: %s, Match Number: %d, Position: %s, isBlue: %b, "
                                +"HPShipGame: %d, HPShipSand: %d, HPRocketGame: %d, HPRocketSand: %d, HPDropGame: %d, "
                                +"HPDropSand: %d, CShipGame: %d, CShipSand: %d, CRocketGame: %d, CRocketSand: %d, CDropGame: %d, "
                                +"CDropSand: %d, Scale Level: %d, Is a Helper: %b, Starting Level: %d, Crossed the Line: %b, Fouls: %d, Tech Fouls: %d, Yellow Card: %b, "
                                +"Red Card: %b, Emergency Stop: %b, Broken: %b, Total Points: %d, Points w/o Penalties: %d, "
                                +"Ranking Points: %d, Filled Right Rocket: %b, Filled Left Rocket: %b, Hab Docking RP: %b, "
                                +"Rocket RP: %b, Synced? %b, Notes: %s%n",
                                this.teamNum, this.matchType, this.matchNum, this.alliancePosition, this.isBlueAlliance,
                                this.HPShipGame, this.HPShipSand, this.HPRocketGame, this.HPRocketSand, this.HPDropGame,
                                this.HPDropSand, this.CShipGame, this.CShipSand, this.CRocketGame, this.CRocketSand, this.CDropGame,
                                this.CDropSand, this.scaleLevel, this.isHelp, this.startHab, this.crossedLine, this.fouls, this.techs, this.yellow,
                                this.red, this.eStopped, this.borked, this.points, this.nonFoulPoints,
                                this.rankingPoints, this.rRocket, this.lRocket, this.habRP,
                                this.crRP, this.tbaSynced, this.matchNotes);
    }
}
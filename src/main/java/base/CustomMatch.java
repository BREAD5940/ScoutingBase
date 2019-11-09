package base;

import java.util.HashMap;

import com.cpjd.models.matches.Match;
import com.cpjd.utils.exceptions.DataNotFoundException;

public class CustomMatch implements Comparable<CustomMatch>{

    private int teamNum;
    private String matchType;
    private int matchNum;
    private String alliancePosition;
    private boolean isBlueAlliance;

    private int HPShipGame;
    private int HPShipSand;
    private int HPRocketGame;
    private int HPRocketSand;
    private int HPDropGame;
    private int HPDropSand;

    private int CShipGame;
    private int CShipSand;
    private int CRocketGame;
    private int CRocketSand;
    private int CDropGame;
    private int CDropSand;

    private int scaleLevel;
    private boolean isHelp = false;

    private int startHab;
    private boolean crossedLine = false;

    private Long fouls;
    private Long techs;
    private int yellow;
    private int red;
    private boolean eStopped;
    private boolean borked;

    private int points;
    private int nonFoulPoints = 0;
    private int rankingPoints = 0;
    private boolean rRocket = false, lRocket = false, habRP = false, crRP = false;

    HashMap<String, Object> scoreBreakdown;
    private boolean tbaSynced = false;

    private int driverRank;
    
    private String scoutName;
    private boolean gotOff;
    private String climbType;
    private boolean isRamp;

    private String matchNotes;

    
    public CustomMatch(String[] csvRow){
//        this.matchType = csvRow[0];
        this.matchNum = Integer.valueOf(csvRow[31]);
        this.teamNum = Integer.valueOf(csvRow[2]);
        System.out.println(csvRow[30].charAt(0));
        this.alliancePosition = csvRow[30];
        this.isBlueAlliance = this.alliancePosition.charAt(0)=='B';

        this.startHab = Integer.valueOf(csvRow[3]);

        this.CShipSand = Integer.valueOf(csvRow[5]);
        this.CRocketSand = Integer.valueOf(csvRow[4]);
        this.CDropSand = Integer.valueOf(csvRow[6]);
        this.HPShipSand = Integer.valueOf(csvRow[8]);
        this.HPRocketSand = Integer.valueOf(csvRow[7]);
        this.HPDropSand = Integer.valueOf(csvRow[9]);

        this.CShipGame = Integer.valueOf(csvRow[11]);
        this.CRocketGame = Integer.valueOf(csvRow[10]);
        this.CDropGame = Integer.valueOf(csvRow[12]);
        this.HPShipGame = Integer.valueOf(csvRow[14]);
        this.HPRocketGame = Integer.valueOf(csvRow[13]);
        this.HPDropGame = Integer.valueOf(csvRow[15]);

        try{
            this.scaleLevel = Integer.valueOf(csvRow[19]);
        }catch(NumberFormatException e){
            Lib.report("Scale Level is not a number. Setting to 0.");
            this.scaleLevel = 0;
        }

        this.techs = Long.valueOf(csvRow[23]);
        this.fouls = Long.valueOf(csvRow[22]);
        try {
            this.yellow = Integer.valueOf(csvRow[20]);
        }catch(NumberFormatException e){
            Lib.report("Yellow cards aren't numbers. Setting to 0");
            this.yellow=0;
        }
        
        try {
            this.red = Integer.valueOf(csvRow[21]);
        }catch(NumberFormatException e){
            Lib.report("Red cards aren't numbers. Setting to 0");
            this.red=0;
        }
        switch(csvRow[24]){
            case "bork":
                this.borked=true;
                this.eStopped=false;
            case "eStop, bork":
                this.borked=this.eStopped=true;
            case "eStop":
                this.eStopped=true;
                this.borked=false;
            default:
                this.eStopped=this.borked=false;
        }
        this.points = Integer.valueOf(csvRow[26]);

        this.matchNotes = csvRow[27];
        this.matchNotes = this.matchNotes.replace("\n", ".  ");
        
        this.scoutName = csvRow[1];
        this.gotOff = csvRow[16].equalsIgnoreCase("gotOff");
        this.climbType = csvRow[17];
        this.isRamp = csvRow[18].equalsIgnoreCase("ramp");
        try {
            this.driverRank = Integer.valueOf(csvRow[29]);
        }catch(NumberFormatException e){
            Lib.report("Driver rank is not a number. Setting to 0.");
            this.driverRank=0;
        }

        Lib.saveMatch(this, Main.currentSession.eventDir);
    }
    
    public CustomMatch(){
        //dummy constructor
    }

    public int sandPlaces(){
        return HPShipSand+HPRocketSand+CShipSand+CRocketSand;
    }
    public int totalHatch() {return HPRocketGame+HPRocketSand+HPShipGame+HPShipSand;}
    public int totalCargo() { return CRocketGame+CRocketSand+CShipGame+CShipSand;}
    public int totalRocketHatch() { return HPRocketGame+HPRocketSand;}
    public int totalRocketCargo() { return CRocketSand+CRocketGame;}
    public int totalShipHatch() { return  HPShipSand+HPShipGame;}
    public int totalShipCargo() {return CShipSand+CShipGame;}
    public int totalDropHatch() {return HPDropGame+HPDropSand;}
    public int totalDropCargo() { return CDropGame+CDropSand;}
    
    @Override
    public int compareTo(CustomMatch match){
        int compareNum = match.getMatchNum();
        return this.getMatchNum() - compareNum;
    }

    public void syncTBA(){
        Match foundMatch = new Match();
        try{
            foundMatch = Main.tbaApi.getMatch(Main.currentSession.tbaEventKey+"_qm"+this.matchNum);
        }catch(DataNotFoundException e){
            System.out.println("Match not found. \n"+e);
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

        System.out.println(this.scoreBreakdown);

        //FIXME get hekd i guess

//        //sync fouls
//        System.out.println(this.fouls);
//        System.out.println(scoreBreakdown.get("foulCount"));
//        if(this.fouls > (Long)scoreBreakdown.get("foulCount")){
//            Lib.report(String.format("Fouls for match %d, alliance position %s are more than the total fouls. %nReported fouls: %d%nTotal fouls: %d",
//                         this.matchNum, this.alliancePosition, this.fouls, this.scoreBreakdown.get("foulCount")));
//            this.fouls = (Long)scoreBreakdown.get("foulCount");
//        }
//
//        //sync tech fouls
//        if(this.techs > (Long)scoreBreakdown.get("techFoulCount")){
//            Lib.report(String.format("Tech fouls for match %d, alliance position %s are more than the total tech fouls. %nReported tech fouls: %d%nTotal fouls: %d",
//                        this.matchNum, this.alliancePosition, this.techs, this.scoreBreakdown.get("techFoulCount")));
//            this.techs = (Long)scoreBreakdown.get("techFoulCount");
//        }

        //get the points excluding points from fouls
        this.nonFoulPoints = this.points - ((Long)this.scoreBreakdown.get("foulPoints")).intValue();

        int alPos = Character.getNumericValue(this.alliancePosition.charAt(this.alliancePosition.length()-1));
        //the starting hab
        if(this.startHab != Character.getNumericValue(this.scoreBreakdown.get("preMatchLevelRobot"+alPos).toString().charAt(8))){
            Lib.report(String.format("Start level for match %d, alliance position %s is incorrect. %nReported level: %d%nTBA level: %d",
                        this.matchNum, this.alliancePosition, this.startHab, Character.getNumericValue(this.scoreBreakdown.get("preMatchLevelRobot"+alPos).toString().charAt(8))));
            this.startHab = Character.getNumericValue(this.scoreBreakdown.get("preMatchLevelRobot"+alPos).toString().charAt(8));
        }

        //get the number of ranking points
        this.rankingPoints = ((Long)this.scoreBreakdown.get("rp")).intValue();

//        this.rRocket = (boolean)this.scoreBreakdown.get("completedRocketNear");
//        this.lRocket = (boolean)this.scoreBreakdown.get("completedRocketFar");

        this.habRP = (boolean)this.scoreBreakdown.get("habDockingRankingPoint");
        this.crRP = (boolean)this.scoreBreakdown.get("completeRocketRankingPoint");

        //TODO add more?

        this.tbaSynced = true;

        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public String toReadableString(){
        return String.format("Team Number: %d, Match Number: %d, Position: %s, isBlue: %b, "
                                +"HPShipGame: %d, HPShipSand: %d, HPRocketGame: %d, HPRocketSand: %d, HPDropGame: %d, "
                                +"HPDropSand: %d, CShipGame: %d, CShipSand: %d, CRocketGame: %d, CRocketSand: %d, CDropGame: %d, "
                                +"CDropSand: %d, Scale Level: %d, Is a Helper: %b, Starting Level: %d, Crossed the Line: %b, Fouls: %d, Tech Fouls: %d, Yellow Card: %b, "
                                +"Red Card: %b, Emergency Stop: %b, Broken: %b, Total Points: %d, Points w/o Penalties: %d, "
                                +"Ranking Points: %d, Filled Right Rocket: %b, Filled Left Rocket: %b, Hab Docking RP: %b, "
                                +"Rocket RP: %b, Synced? %b, Notes: %s%n",
                                this.teamNum, this.matchNum, this.alliancePosition, this.isBlueAlliance,
                                this.HPShipGame, this.HPShipSand, this.HPRocketGame, this.HPRocketSand, this.HPDropGame,
                                this.HPDropSand, this.CShipGame, this.CShipSand, this.CRocketGame, this.CRocketSand, this.CDropGame,
                                this.CDropSand, this.scaleLevel, this.isHelp, this.startHab, this.crossedLine, this.fouls, this.techs, this.yellow,
                                this.red, this.eStopped, this.borked, this.points, this.nonFoulPoints,
                                this.rankingPoints, this.rRocket, this.lRocket, this.habRP,
                                this.crRP, this.tbaSynced, this.matchNotes);
    }

    @Override
    public String toString(){
        return this.teamNum+","+this.matchNum+","+this.alliancePosition
                +","+this.isBlueAlliance+","+this.HPShipGame+","+this.HPShipSand+","+this.HPRocketGame
                +","+this.HPRocketSand+","+this.HPDropGame+","+this.HPDropSand+","+this.CShipGame
                +","+this.CShipSand+","+this.CRocketGame+","+this.CRocketSand+","+this.CRocketGame
                +","+this.CRocketSand+","+this.CDropGame+","+this.CDropSand+","+this.scaleLevel+","+this.isHelp
                +","+this.startHab+","+this.crossedLine+","+this.fouls+","+this.techs+","+this.yellow
                +","+this.red+","+this.eStopped+","+this.borked+","+this.points+","+this.nonFoulPoints
                +","+this.rankingPoints+","+this.rRocket+","+this.lRocket+","+this.habRP+","+this.crRP
                +","+this.tbaSynced+",|"+this.matchNotes+"|"; //TODO
    }







    public int getDriverRank(){ return this.driverRank; }

    public void setDriverRank(int rank) {
        this.driverRank = rank;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getTeamNum() {
        return this.teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    @Deprecated
    public String getMatchType() {
        return this.matchType;
    }

    @Deprecated
    public void setMatchType(String matchType) {
        this.matchType = matchType;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getMatchNum() {
        return this.matchNum;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public String getAlliancePosition() {
        return this.alliancePosition;
    }

    public void setAlliancePosition(String alliancePosition) {
        this.alliancePosition = alliancePosition;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public boolean getIsBlueAlliance() {
        return this.isBlueAlliance;
    }

    public void setIsBlueAlliance(boolean isBlueAlliance) {
        this.isBlueAlliance = isBlueAlliance;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getHPShipGame() {
        return this.HPShipGame;
    }

    public void setHPShipGame(int HPShipGame) {
        this.HPShipGame = HPShipGame;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getHPShipSand() {
        return this.HPShipSand;
    }

    public void setHPShipSand(int HPShipSand) {
        this.HPShipSand = HPShipSand;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getHPRocketGame() {
        return this.HPRocketGame;
    }

    public void setHPRocketGame(int HPRocketGame) {
        this.HPRocketGame = HPRocketGame;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getHPRocketSand() {
        return this.HPRocketSand;
    }

    public void setHPRocketSand(int HPRocketSand) {
        this.HPRocketSand = HPRocketSand;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getHPDropGame() {
        return this.HPDropGame;
    }

    public void setHPDropGame(int HPDropGame) {
        this.HPDropGame = HPDropGame;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getHPDropSand() {
        return this.HPDropSand;
    }

    public void setHPDropSand(int HPDropSand) {
        this.HPDropSand = HPDropSand;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getCShipGame() {
        return this.CShipGame;
    }

    public void setCShipGame(int CShipGame) {
        this.CShipGame = CShipGame;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getCShipSand() {
        return this.CShipSand;
    }

    public void setCShipSand(int CShipSand) {
        this.CShipSand = CShipSand;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getCRocketGame() {
        return this.CRocketGame;
    }

    public void setCRocketGame(int CRocketGame) {
        this.CRocketGame = CRocketGame;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getCRocketSand() {
        return this.CRocketSand;
    }

    public void setCRocketSand(int CRocketSand) {
        this.CRocketSand = CRocketSand;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getCDropGame() {
        return this.CDropGame;
    }

    public void setCDropGame(int CDropGame) {
        this.CDropGame = CDropGame;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getCDropSand() {
        return this.CDropSand;
    }

    public void setCDropSand(int CDropSand) {
        this.CDropSand = CDropSand;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getScaleLevel() {
        return this.scaleLevel;
    }

    public void setScaleLevel(int scaleLevel) {
        this.scaleLevel = scaleLevel;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public boolean getIsHelp() {
        return this.isHelp;
    }

    public void setIsHelp(boolean isHelp) {
        this.isHelp = isHelp;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getStartHab() {
        return this.startHab;
    }

    public void setStartHab(int startHab) {
        this.startHab = startHab;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public boolean getCrossedLine() {
        return this.crossedLine;
    }

    public void setCrossedLine(boolean crossedLine) {
        this.crossedLine = crossedLine;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public Long getFouls() {
        return this.fouls;
    }

    public void setFouls(Long fouls) {
        this.fouls = fouls;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public Long getTechs() {
        return this.techs;
    }

    public void setTechs(Long techs) {
        this.techs = techs;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getYellow() {
        return this.yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getRed() {
        return this.red;
    }

    public void setRed(int red) {
        this.red = red;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public boolean getEStopped() {
        return this.eStopped;
    }

    public void setEStopped(boolean eStopped) {
        this.eStopped = eStopped;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public boolean getBorked() {
        return this.borked;
    }

    public void setBorked(boolean borked) {
        this.borked = borked;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getNonFoulPoints() {
        return this.nonFoulPoints;
    }

    public void setNonFoulPoints(int nonFoulPoints) {
        this.nonFoulPoints = nonFoulPoints;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public int getRankingPoints() {
        return this.rankingPoints;
    }

    public void setRankingPoints(int rankingPoints) {
        this.rankingPoints = rankingPoints;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public boolean getRRocket(){
        return this.rRocket;
    }

    public void setRRocket(boolean rRocket){
        this.rRocket = rRocket;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public boolean getLRocket(){
        return this.lRocket;
    }

    public void setLRocket(boolean lRocket){
        this.lRocket = lRocket;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public boolean getHabRP(){
        return this.habRP;
    }

    public void setHabRP(boolean habRP){
        this.habRP = habRP;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public boolean getCrRP(){
        return this.crRP;
    }

    public void setCrRP(boolean crRP){
        this.crRP = crRP;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public HashMap<String, Object> getScoreBreakdown() {
        return this.scoreBreakdown;
    }

    public void setScoreBreakdown(HashMap<String,Object> scoreBreakdown) {
        this.scoreBreakdown = scoreBreakdown;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public boolean getTbaSynced(){
        return this.tbaSynced;
    }

    public void setTbaSynced(boolean tbaSynced){
        this.tbaSynced = tbaSynced;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }

    public String getMatchNotes() {
        return this.matchNotes;
    }

    public void setMatchNotes(String matchNotes) {
        this.matchNotes = matchNotes;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }
    
    public String getScoutName() {
        return scoutName;
    }
    
    public void setScoutName(String scoutName) {
        this.scoutName = scoutName;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }
    
    public boolean isGotOff() {
        return gotOff;
    }
    
    public void setGotOff(boolean gotOff) {
        this.gotOff = gotOff;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }
    
    public String getClimbType() {
        return climbType;
    }
    
    public void setClimbType(String climbType) {
        this.climbType = climbType;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }
    
    public boolean isRamp() {
        return isRamp;
    }
    
    public void setRamp(boolean ramp) {
        isRamp = ramp;
        Lib.saveMatch(this, Main.currentSession.eventDir);
    }
}

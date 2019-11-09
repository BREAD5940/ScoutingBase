package base;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import base.CustomMatch;
import base.Lib;

public class CustomTeam {

    public static enum Groups{
        Level3Climbers, Level2Climbers, Level2Starters, GotRed, 
        HasBorked, LowScorers, HighScorers, Completed3Rockets
    }

    private String scoutedName;
    private String tbaName;
    private String sponsors;
    private int number;
    private boolean isFullySync = true;

    private ArrayList<String> robotNicknames = new ArrayList<>();

    private ArrayList<CustomMatch> matches = new ArrayList<CustomMatch>();
    private ArrayList<Pit> pits = new ArrayList<>();

    private double avGPSand;
    private double avHPShip;
    private double avHPRocket;
    private double avHPDrop;
    private double avCShip;
    private double avCRocket;
    private double avCDrop;

    private ArrayList<Integer> scaleLevels = new ArrayList<Integer>();
    private double consistScaleLevel;
    private double maxScaleLevel;

    private boolean isRamp;

    private ArrayList<Integer> startHabs = new ArrayList<Integer>();
    private double consistStartHab;
    private double maxStartHab;
    private boolean consistOffHab;

    private double avFoul;
    private double avTech;
    private double totalYellow;
    private double totalRed;

    private double eStops;
    private double borks;

    private int totalRPs;
    private int totalHabRPs;
    private int totalRocketRPs;
    private int totalRockets;

    private ArrayList<Groups> groups = new ArrayList<Groups>();
    private HashMap<Integer, String> matchNotes = new HashMap<Integer, String>();

    

    public CustomTeam (String name_, int number_){
        this.scoutedName = name_;
        this.number = number_;

        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public CustomTeam (int number_){
        this.number = number_;

        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public CustomTeam(){
        //dummy constructor
    }


    public void addMatch(CustomMatch match){
        this.avGPSand = (this.avGPSand + match.sandPlaces())/2;

        this.avHPShip = (this.avHPShip + (match.getHPShipGame()+match.getHPShipSand()))/2;
        this.avHPRocket = (this.avHPRocket + (match.getHPRocketGame()+match.getHPRocketSand()))/2;
        this.avHPDrop = (this.avHPDrop + (match.getHPDropGame() + match.getHPDropSand()))/2;
        this.avCShip = (this.avCShip + (match.getCShipGame() + match.getCShipSand()))/2;
        this.avCRocket = (this.avCRocket + (match.getCRocketGame() +match.getCRocketSand()))/2;
        this.avCDrop = (this.avCDrop + (match.getCDropGame() + match.getCDropSand()))/2;

        this.scaleLevels.add(match.getScaleLevel());
        this.consistScaleLevel = Lib.mode(this.scaleLevels);
        this.maxScaleLevel = Lib.max(this.scaleLevels);
        this.isRamp = this.isRamp || match.getIsHelp();

        this.startHabs.add(match.getStartHab());

        this.consistStartHab = Lib.mode(this.startHabs);
        this.maxStartHab = Lib.max(this.startHabs);
        this.consistOffHab = consistStartHab != 0;

        this.avFoul = (this.avFoul + match.getFouls())/2;
        this.avTech = (this.avTech + match.getTechs())/2;
        this.totalYellow+=match.getYellow();

        this.totalRed+=match.getRed();

        if(match.getEStopped())
            this.eStops++;

        if(match.getBorked())
            this.borks++;

        this.totalRPs+=match.getRankingPoints();

        if(match.getHabRP())
            this.totalHabRPs++;
        
        if(match.getCrRP())
            this.totalRocketRPs++;

        if(match.getRRocket())
            this.totalRockets++;

        if(match.getLRocket())
            this.totalRockets++;

        this.matchNotes.put(match.getMatchNum(),match.getMatchNotes());
        this.matches.add(match);
        this.isFullySync = this.isFullySync && match.getTbaSynced();

        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public void addPit(Pit pitData){
        this.pits.add(pitData);


        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public void addGroups(){
        

        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public void syncTBA(){
        this.tbaName = Main.tbaApi.getTeam(this.number).getNickname();
        this.sponsors = Main.tbaApi.getTeam(this.number).getName(); //i stg this is the sponsors
        this.isFullySync = this.isFullySync && true;
        Lib.report("Team "+this.number+" synced.");

        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    // does NOT include matches, scale levels, start habs, or match notes
    public String toReadableString(){
        return String.format("Number: %d, Name: %s/%s, Robot Nicknames: %s, Sponsors: %s, Is Synced? %b, Average GPs in Sand: %f, "
        +"Average HPs on Cargo Ship: %f, Average HPs on Rocket: %f, Average Dropped HPs: %f, Average Cargo on Cargo Ship: %f, "
        +"Average Cargo on Rocket: %f, Average Dropped Cargo: %f, Consistant Scale Level: %f, Maximum Scale Level: %f, "
        +"Is ramp? %b, Consistant Starting Hab Level: %f, Maximum Starting Hab Level: %f, Consistantly Off Hab? %b, "
        +"Average Fouls: %f, Average Tech Fouls: %f, Total Yellow Cards: %f, Total Red Cards: %f, Total Emergency Stops: %f, "
        +"Total Breaks: %f, Total Ranking Points: %d, Total Hab RPs: %d, Total Rocket RPs: %d, Total Full Rockets: %d, "
        +"Groups: %s",

        this.number, 
        this.scoutedName, 
        this.tbaName, 
        Lib.arrayToString(this.robotNicknames.toArray()), 
        this.sponsors, 
        this.isFullySync, 
        this.avGPSand,
        
        this.avHPShip, this.avHPRocket, this.avHPDrop, this.avCShip,
        this.avCRocket, this.avCDrop, this.consistScaleLevel, this.maxScaleLevel,
        this.isRamp, this.consistStartHab, this.maxStartHab, this.consistOffHab,
        this.avFoul, this.avTech, this.totalYellow, this.totalRed, this.eStops, 
        this.borks, this.totalRPs, this.totalHabRPs, this.totalRocketRPs, this.totalRockets,
        Lib.arrayToString(this.groups.toArray()));
    }

    public void sendToTxt(String directory){
        FileWriter writer = null;
        try{
            writer = new FileWriter(directory+this.number+".txt", false);
        }catch(IOException e){
            Lib.report("File not found error");
            System.out.println(e);
        }

        try{
            writer.write(String.format(
                "IS FULLY SYNCED WITH TBA: %b\n"+
                "%-4d  (%s/%s) \nSponsors: %s\n"+
                "Nicknames: %s\n"+
                "-----------------------------------------------------------------------\n\n"+
                "Groups: %s\n"+
                "Consistantly Off the Hab? %b    Most Common Starting Hab: %.0f    Maximum Starting Hab: %.0f\n"+
                "Starting Hab Levels (By Match): %s\n\n"+
                "Is Ramp Bot? %b                 Most Common Scale Level: %.0f     Maximum Scale Level: %.0f\n"+
                "Scale Levels (By Match): %s\n\n"+
                "Total Ranking Points: %d    Hab-Related RPs: %d    Rocket-Realted RPs: %d    Filled Rockets: %d\n\n"+
                "Average Fouls Per Match: %.2f    Average Tech Fouls Per Match: %.2f\n"+
                "Total Yellow Cards: %.0f           Total Red Cards: %.0f\n"+
                "Total Emergency Stops: %.0f        Total Robot Breakages: %.0f\n\n"+
                "Average Game Pieces Placed (Sandstorm): %.2f\n"+
                "Average Hatch Panel Placement:    Average Cargo Placement:\n"+
                "    Cargo Ship: %.2f                  Cargo Ship: %.2f\n"+
                "    Rocket: %.2f                      Rocket: %.2f\n"+
                "    Dropped: %.2f                     Dropped: %.2f\n\n"+
                "Match Number | Note\n"
                ,this.isFullySync
                ,this.number, this.scoutedName, this.tbaName, this.sponsors
                ,Lib.arrayToString(this.robotNicknames.toArray())

                ,Lib.arrayToString(this.groups.toArray())
                ,this.consistOffHab, this.consistStartHab, this.maxStartHab
                ,Lib.arrayToString(this.startHabs.toArray())
                ,this.isRamp, this.consistScaleLevel, this.maxScaleLevel
                ,Lib.arrayToString(this.scaleLevels.toArray())
                ,this.totalRPs, this.totalHabRPs, this.totalRocketRPs, this.totalRockets
                ,this.avFoul, this.avTech
                ,this.totalYellow, this.totalRed
                ,this.eStops, this.borks
                ,this.avGPSand

                ,this.avHPShip, this.avCShip
                ,this.avHPRocket, this.avCRocket
                ,this.avHPDrop, this.avHPDrop
            ));
            for(Integer key : this.matchNotes.keySet()){
                writer.write(String.format("%12d | %s\n", key, this.matchNotes.get(key)));
            }
            writer.write("\n-----------------------------------------------------------------------\n\nMATCHES: \n");
            for(CustomMatch match : this.matches){
                Lib.report(this.number+", match "+match.getMatchNum());
                writer.write(match.toString()+"\n");
            }
            writer.close();
        }catch(IOException e){}
    }

    @Deprecated
    public String toCSVString(){
        return this.scoutedName+","+this.tbaName+","+this.sponsors+","+this.number+","+this.isFullySync+","+
                Lib.arrayToString(this.matches.toArray())+","+this.avGPSand+","+this.avHPShip+","+this.avHPRocket+","+
                    this.avHPDrop+","+this.avCShip+","+this.avCRocket+","+this.avCDrop+","+Lib.arrayToString(this.scaleLevels.toArray())+","+
                        this.consistScaleLevel+","+this.maxScaleLevel+","+this.isRamp+","+Lib.arrayToString(this.startHabs.toArray())+","+
                            this.consistStartHab+","+this.maxStartHab+","+this.consistOffHab+","+this.avFoul+","+this.avTech+","+this.totalYellow+","+
                                this.totalRed+","+eStops+","+borks+","+totalRPs+","+totalHabRPs+","+totalRocketRPs+","+totalRockets+","+Lib.arrayToString(this.groups.toArray())
                                    ;//does NOT inclue match notes or nicknames
    }







    public String getScoutedName() {
        return this.scoutedName;
    }

    public void setScoutedName(String scoutedName) {
        this.scoutedName = scoutedName;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public String getTbaName() {
        return this.tbaName;
    }

    public void setTbaName(String tbaName) {
        this.tbaName = tbaName;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public String getSponsors() {
        return this.sponsors;
    }

    public void setSponsors(String sponsors) {
        this.sponsors = sponsors;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public boolean getIsFullySync() {
        return this.isFullySync;
    }

    public void setIsFullySync(boolean isFullySync) {
        this.isFullySync = isFullySync;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public ArrayList<String> getRobotNicknames() {
        return this.robotNicknames;
    }

    public void setRobotNicknames(ArrayList<String> robotNicknames) {
        this.robotNicknames = robotNicknames;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public ArrayList<CustomMatch> getMatches() {
        return this.matches;
    }

    public void setMatches(ArrayList<CustomMatch> matches) {
        this.matches = matches;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getAvGPSand() {
        return this.avGPSand;
    }

    public void setAvGPSand(double avGPSand) {
        this.avGPSand = avGPSand;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getAvHPShip() {
        return this.avHPShip;
    }

    public void setAvHPShip(double avHPShip) {
        this.avHPShip = avHPShip;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getAvHPRocket() {
        return this.avHPRocket;
    }

    public void setAvHPRocket(double avHPRocket) {
        this.avHPRocket = avHPRocket;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getAvHPDrop() {
        return this.avHPDrop;
    }

    public void setAvHPDrop(double avHPDrop) {
        this.avHPDrop = avHPDrop;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getAvCShip() {
        return this.avCShip;
    }

    public void setAvCShip(double avCShip) {
        this.avCShip = avCShip;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getAvCRocket() {
        return this.avCRocket;
    }

    public void setAvCRocket(double avCRocket) {
        this.avCRocket = avCRocket;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getAvCDrop() {
        return this.avCDrop;
    }

    public void setAvCDrop(double avCDrop) {
        this.avCDrop = avCDrop;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public ArrayList<Integer> getScaleLevels() {
        return this.scaleLevels;
    }

    public void setScaleLevels(ArrayList<Integer> scaleLevels) {
        this.scaleLevels = scaleLevels;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getConsistScaleLevel() {
        return this.consistScaleLevel;
    }

    public void setConsistScaleLevel(double consistScaleLevel) {
        this.consistScaleLevel = consistScaleLevel;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getMaxScaleLevel() {
        return this.maxScaleLevel;
    }

    public void setMaxScaleLevel(double maxScaleLevel) {
        this.maxScaleLevel = maxScaleLevel;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public boolean getIsRamp() {
        return this.isRamp;
    }

    public void setIsRamp(boolean isRamp) {
        this.isRamp = isRamp;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public ArrayList<Integer> getStartHabs() {
        return this.startHabs;
    }

    public void setStartHabs(ArrayList<Integer> startHabs) {
        this.startHabs = startHabs;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getConsistStartHab() {
        return this.consistStartHab;
    }

    public void setConsistStartHab(double consistStartHab) {
        this.consistStartHab = consistStartHab;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getMaxStartHab() {
        return this.maxStartHab;
    }

    public void setMaxStartHab(double maxStartHab) {
        this.maxStartHab = maxStartHab;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public boolean getConsistOffHab() {
        return this.consistOffHab;
    }

    public void setConsistOffHab(boolean consistOffHab) {
        this.consistOffHab = consistOffHab;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getAvFoul() {
        return this.avFoul;
    }

    public void setAvFoul(double avFoul) {
        this.avFoul = avFoul;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getAvTech() {
        return this.avTech;
    }

    public void setAvTech(double avTech) {
        this.avTech = avTech;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getTotalYellow() {
        return this.totalYellow;
    }

    public void setTotalYellow(double totalYellow) {
        this.totalYellow = totalYellow;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getTotalRed() {
        return this.totalRed;
    }

    public void setTotalRed(double totalRed) {
        this.totalRed = totalRed;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getEStops() {
        return this.eStops;
    }

    public void setEStops(double eStops) {
        this.eStops = eStops;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public double getBorks() {
        return this.borks;
    }

    public void setBorks(double borks) {
        this.borks = borks;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public int getTotalRPs() {
        return this.totalRPs;
    }

    public void setTotalRPs(int totalRPs) {
        this.totalRPs = totalRPs;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public int getTotalHabRPs() {
        return this.totalHabRPs;
    }

    public void setTotalHabRPs(int totalHabRPs) {
        this.totalHabRPs = totalHabRPs;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public int getTotalRocketRPs() {
        return this.totalRocketRPs;
    }

    public void setTotalRocketRPs(int totalRocketRPs) {
        this.totalRocketRPs = totalRocketRPs;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public int getTotalRockets() {
        return this.totalRockets;
    }

    public void setTotalRockets(int totalRockets) {
        this.totalRockets = totalRockets;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public ArrayList<Groups> getGroups() {
        return this.groups;
    }

    public void setGroups(ArrayList<Groups> groups) {
        this.groups = groups;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }

    public HashMap<Integer, String> getMatchNotes() {
        return this.matchNotes;
    }

    public void setMatchNotes(HashMap<Integer, String> matchNotes) {
        this.matchNotes = matchNotes;
        Lib.saveTeam(this, Main.currentSession.eventDir);
    }
}
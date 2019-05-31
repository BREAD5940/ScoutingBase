package base;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import base.CustomMatch;
import base.Lib;

public class CustomTeam {

    public static enum Groups{
        Level3Climbers, Level2Climbers, Level2Starters, GotRed, 
        HasBorked, LowScorers, HighScorers, Completed3Rockets
    }

    String scoutedName;
    String tbaName;
    String sponsors;
    int number;
    boolean isFullySync = true;
    
    ArrayList<CustomMatch> matches = new ArrayList<CustomMatch>();

    double avGPSand;
    double avHPShip;
    double avHPRocket;
    double avHPDrop;
    double avCShip;
    double avCRocket;
    double avCDrop;

    ArrayList<Integer> scaleLevels = new ArrayList<Integer>();
    double consistScaleLevel;
    double maxScaleLevel;

    boolean isRamp;

    ArrayList<Integer> startHabs = new ArrayList<Integer>();
    double consistStartHab;
    double maxStartHab;
    boolean consistOffHab;

    double avFoul;
    double avTech;
    double totalYellow;
    double totalRed;

    double eStops;
    double borks;

    int totalRPs;
    int totalHabRPs;
    int totalRocketRPs;
    int totalRockets;

    ArrayList<Groups> groups = new ArrayList<Groups>();
    HashMap<Integer,String> matchNotes = new HashMap<Integer,String>();

    public CustomTeam (String name_, int number_){
        this.scoutedName = name_;
        this.number = number_;
    }


    public void addMatch(CustomMatch match){
        this.avGPSand = (this.avGPSand + match.getSandPlaces())/2;

        this.avHPShip = (this.avHPShip + (match.HPShipGame+match.HPShipSand))/2;
        this.avHPRocket = (this.avHPRocket + (match.HPRocketGame+match.HPRocketSand))/2;
        this.avHPDrop = (this.avHPDrop + (match.HPDropGame + match.HPDropSand))/2;
        this.avCShip = (this.avCShip + (match.CShipGame + match.CShipSand))/2;
        this.avCRocket = (this.avCRocket + (match.CRocketGame +match.CRocketSand))/2;
        this.avCDrop = (this.avCDrop + (match.CDropGame + match.CDropSand))/2;

        this.scaleLevels.add(match.scaleLevel);
        this.consistScaleLevel = Lib.mode(this.scaleLevels);
        this.maxScaleLevel = Lib.max(this.scaleLevels);
        this.isRamp = this.isRamp || match.isHelp;

        this.startHabs.add(match.startHab);

        this.consistStartHab = Lib.mode(this.startHabs);
        this.maxStartHab = Lib.max(this.startHabs);
        this.consistOffHab = consistStartHab != 0;

        this.avFoul = (this.avFoul + match.fouls)/2;
        this.avTech = (this.avTech + match.techs)/2;

        if(match.yellow)
            this.totalYellow++;

        if(match.red)
            this.totalRed++;

        if(match.eStopped)
            this.eStops++;

        if(match.borked)
            this.borks++;

        this.totalRPs+=match.rankingPoints;

        if(match.habRP)
            this.totalHabRPs++;
        
        if(match.crRP)
            this.totalRocketRPs++;

        if(match.rRocket)
            this.totalRockets++;

        if(match.lRocket)
            this.totalRockets++;

        this.matchNotes.put(match.matchNum,match.matchNotes);
        this.isFullySync = this.isFullySync && match.tbaSynced;

    }

    public void addPit(Pit pitData){

    }

    public void addGroups(){
        
    }

    public void syncTBA(){
        this.tbaName = Main.tbaApi.getTeam(this.number).getNickname();
        this.sponsors = Main.tbaApi.getTeam(this.number).getName(); //i stg this is the sponsors
        this.isFullySync = this.isFullySync && true;
        Lib.report("Team "+this.number+" synced.");
    }

    // does NOT include matches, scale levels, start habs, or match notes
    public String toReadableString(){
        return String.format("Number: %d, Name: %s/%s, Sponsors: %s, Is Synced? %b, Average GPs in Sand: %f, "
        +"Average HPs on Cargo Ship: %f, Average HPs on Rocket: %f, Average Dropped HPs: %f, Average Cargo on Cargo Ship: %f, "
        +"Average Cargo on Rocket: %f, Average Dropped Cargo: %f, Consistant Scale Level: %f, Maximum Scale Level: %f, "
        +"Is ramp? %b, Consistant Starting Hab Level: %f, Maximum Starting Hab Level: %f, Consistantly Off Hab? %b, "
        +"Average Fouls: %f, Average Tech Fouls: %f, Total Yellow Cards: %f, Total Red Cards: %f, Total Emergency Stops: %f, "
        +"Total Breaks: %f, Total Ranking Points: %d, Total Hab RPs: %d, Total Rocket RPs: %d, Total Full Rockets: %d, "
        +"Groups: %s",
        this.number, this.scoutedName, this.tbaName, this.sponsors, this.isFullySync, this.avGPSand,
        this.avHPShip, this.avHPRocket, this.avHPDrop, this.avCShip,
        this.avCRocket, this.avCDrop, this.consistScaleLevel, this.maxScaleLevel,
        this.isRamp, this.consistStartHab, this.maxStartHab, this.consistOffHab,
        this.avFoul, this.avTech, this.totalYellow, this.totalRed, this.eStops, 
        this.borks, this.totalRPs, this.totalHabRPs, this.totalRocketRPs, this.totalRockets,
        Lib.listToString(this.groups.toArray()));
    }

    public void sendToTxt(String directory){
        FileWriter writer = null;
        try{
            writer = new FileWriter(directory+this.number+".txt", true);
        }catch(IOException e){
            Lib.report("File not found error");
            System.out.println(e);
        }

        try{
            writer.write(String.format(
                "IS FULLY SYNCED WITH TBA: %b"+
                "%4d  (%s/%s) -- %s\n"+
                "-----------------------------------------------------------------------\n\n"+
                "Groups: %s\n"+
                "Consistantly Off the Hab? %b    Most Common Starting Hab: %1f    Maximum Starting Hab: %1f\n"+
                "Starting Hab Levels (By Match): %s\n\n"+
                "Is Ramp Bot? %b                 Most Common Scale Level: %1f     Maximum Scale Level: %1f\n"+
                "Scale Levels (By Match): %s\n\n"+
                "Total Ranking Points: %d    Hab-Related RPs: %d    Rocket-Realted RPs: %d    Filled Rockets: %d\n\n"+
                "Average Fouls Per Match: %3f    Average Tech Fouls Per Match: %3f\n"+
                "Total Yellow Cards: %1f           Total Red Cards: %1f\n"+
                "Total Emergency Stops: %1f        Total Robot Breakages: %1f\n\n"+
                "Average Game Pieces Placed (Sandstorm): %3f\n"+
                "Average Hatch Panel Placement:    Average Cargo Placement:\n"+
                "    Cargo Ship: %3f                  Cargo Ship: %3f\n"+
                "    Rocket: %3f                      Rocket: %3f\n"+
                "    Dropped: %3f                     Dropped: %3f\n\n"+
                "Match Number | Note\n"
                ,this.isFullySync
                ,this.number, this.scoutedName, this.tbaName, this.sponsors
                ,Lib.listToString(this.groups.toArray())
                ,this.consistOffHab, this.consistStartHab, this.maxStartHab
                ,Lib.listToString(this.startHabs.toArray())
                ,this.isRamp, this.consistScaleLevel, this.maxScaleLevel
                ,Lib.listToString(this.scaleLevels.toArray())
                ,this.totalRPs, this.totalHabRPs, this.totalRocketRPs, this.totalRockets
                ,this.avFoul, this.avTech
                ,this.totalYellow, this.totalRed
                ,this.avGPSand

                ,this.avHPShip, this.avCShip
                ,this.avHPRocket, this.avCRocket
                ,this.avHPDrop, this.avHPDrop
            ));
            for(Integer key : this.matchNotes.keySet()){
                writer.write(String.format("%-12f | %s\n", key, this.matchNotes.get(key)));
            }
            writer.write("\n-----------------------------------------------------------------------\n\nMATCHES: \n");
            for(CustomMatch match : this.matches){
                writer.write(match.toString()+"\n");
            }
            writer.close();
        }catch(IOException e){}
    }

    public String toCSVString(){
        return scoutedName+","+number+","+avGPSand+","+avHPShip+","+avHPRocket+","+
                avHPDrop+","+avCShip+","+avCRocket+","+avCDrop+","+scaleLevels+","+
                    consistScaleLevel+","+maxScaleLevel+","+isRamp+","+startHabs+","+
                        consistStartHab+","+maxStartHab+","+consistOffHab+","+avFoul+","+
                            avTech+","+totalYellow+","+totalRed+","+eStops+","+borks+","+
                                groups+","+matches;
    }
}
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

    public String scoutedName;
    public String tbaName;
    public List<String> robotNicknames = new ArrayList<String>();
    public String sponsors;
    public int number;
    public boolean isFullySync = true;
    
    public ArrayList<CustomMatch> matches = new ArrayList<CustomMatch>();

    public double avGPSand;
    public double avHPShip;
    public double avHPRocket;
    public double avHPDrop;
    public double avCShip;
    public double avCRocket;
    public double avCDrop;

    public ArrayList<Integer> scaleLevels = new ArrayList<Integer>();
    public double consistScaleLevel;
    public double maxScaleLevel;

    public boolean isRamp;

    public ArrayList<Integer> startHabs = new ArrayList<Integer>();
    public double consistStartHab;
    public double maxStartHab;
    public boolean consistOffHab;

    public double avFoul;
    public double avTech;
    public double totalYellow;
    public double totalRed;

    public double eStops;
    public double borks;

    public int totalRPs;
    public int totalHabRPs;
    public int totalRocketRPs;
    public int totalRockets;

    public ArrayList<Groups> groups = new ArrayList<Groups>();
    public HashMap<Integer,String> matchNotes = new HashMap<Integer,String>();

    public CustomTeam (String name_, int number_){
        this.scoutedName = name_;
        this.number = number_;
    }

    public CustomTeam(){
        //dummy constructor
    }


    public void addMatch(CustomMatch match){
        this.avGPSand = (this.avGPSand + match.sandPlaces())/2;

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
        this.matches.add(match);
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
        return String.format("Number: %d, Name: %s/%s, Robot Nicknames: %s, Sponsors: %s, Is Synced? %b, Average GPs in Sand: %f, "
        +"Average HPs on Cargo Ship: %f, Average HPs on Rocket: %f, Average Dropped HPs: %f, Average Cargo on Cargo Ship: %f, "
        +"Average Cargo on Rocket: %f, Average Dropped Cargo: %f, Consistant Scale Level: %f, Maximum Scale Level: %f, "
        +"Is ramp? %b, Consistant Starting Hab Level: %f, Maximum Starting Hab Level: %f, Consistantly Off Hab? %b, "
        +"Average Fouls: %f, Average Tech Fouls: %f, Total Yellow Cards: %f, Total Red Cards: %f, Total Emergency Stops: %f, "
        +"Total Breaks: %f, Total Ranking Points: %d, Total Hab RPs: %d, Total Rocket RPs: %d, Total Full Rockets: %d, "
        +"Groups: %s",
        this.number, this.scoutedName, this.tbaName, Lib.arrayToString(robotNicknames.toArray()), this.sponsors, this.isFullySync, this.avGPSand,
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
                Lib.report(this.number+", match "+match.matchNum);
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
}
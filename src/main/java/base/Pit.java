package base;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Pit {
 
    public int teamNumber;
    public String teamName;
    public String scoutName;

    public boolean level2Climb, level3Climb;

    public String intakeType; // Hatch Cargo Both

    public int rocketLevel; // 1 2 3

    public int mechIssues;

    public boolean cam, preset, sense, reach, ramp;

    public String[] nicknames;

    public int startHab; // 1 2

    public boolean driverControl, pathing, noControl; // auto

    public ArrayList<String> autoStrats;

    public String autoNotes;

    public boolean prefHatch, prefCargo;

    public int ppm; //pieces per match

    public ArrayList<String> teleStrats;

    public int cycleTime;

    public String teleNotes;

    public String hpPref; // Integral Ideal Unnecessary

    public String stratPref; // Strong Preferred Flexible

    public String notes;

    public Pit(String[] csvRow){
        //TODO match to vars

        Lib.savePit(this, Main.currentSession.eventDir);
    }

    public Pit(){}

    public String getName(){
        return this.teamNumber+"-"+this.scoutName;
    }

    public void s3ndToTxt(String directory){
        FileWriter writer = null;
        try{
            writer = new FileWriter(directory+this.teamNumber+"-"+this.scoutName+".txt", false);
        }catch(IOException e){
            Lib.report("File not found error");
            System.out.println(e);
        }

        try{
            writer.write(String.format(
                "Scout name: %s\n"+
                "Team Number: %-4d  Team Name: %s\n"+
                "Nicknames: %s\n"+
                "-----------------------------------------------------------------------\n\n"+
                "AUTO STRATEGY:\n"+
                "    Starts on: %d\n"+
                "    Controlled by...\n"+
                "        Driver? %b\n"+
                "        Paths? %b\n"+
                "        Nothing? %b\n"+
                "    Strategies:\n"+
                "%s\n"+
                "    Notes:\n"+
                "         %s\n"+                           
                "\n\nTELEOP STRATEGY:\n"+
                "    Prefers: %s\n"+
                "    Game Pieces Per Match: %d    Average Cycle Time (sec): %d\n"+
                "    Strategies:\n"+
                "%s\n"+
                "    Notes:\n"+
                "         %s\n"+
                "\n\nGENERAL:\n"+
                "    Climbs Level 2: %b  Climbs Level 3: %b\n"+
                "    Reaches Level: %d      Intake: %s\n"+
                "    Has...\n"+
                "        Camera? %b\n"+
                "        LimeLight/Sensor? %b\n"+
                "        Presets? %b\n"+
                "        Over-Cargo Reach? %b\n"+
                "        Ramp? %b\n"+
                "    Mechanical issues (1-10): %d\n"+
                "    Human Player Is: %s\n"+
                "    Strategy Is: %s\n"+
                "    Notes:\n"+
                "         %s\n", 
                    this.scoutName, 
                    this.teamNumber, this.teamName,
                    Lib.arrayToString(this.nicknames),//.substring(1,Lib.arrayToString(this.nicknames).length()-2),
                    this.startHab,
                    this.driverControl,
                    this.pathing,
                    this.noControl,
                    Lib.arrayToLinebreakString(this.autoStrats.toArray(), "         "),
                    this.autoNotes,

                    ((this.prefCargo&&this.prefHatch) ? "Both" : (this.prefCargo ? "Cargo" : (this.prefHatch ? "Hatch" : "None"))),
                    this.ppm, this.cycleTime,
                    Lib.arrayToLinebreakString(this.teleStrats.toArray(), "         "),
                    this.teleNotes,

                    this.level2Climb, this.level3Climb,
                    this.rocketLevel, this.intakeType,
                    this.cam,
                    this.sense,
                    this.preset,
                    this.reach,
                    this.ramp,
                    this.mechIssues,
                    this.hpPref,
                    this.stratPref,
                    this.notes     
            ));

            writer.close();
                        
        }catch(IOException e){}
    }

    
}
package base;

import java.util.ArrayList;
import base.CustomMatch;
import base.Lib;

public class CustomTeam {

    String name;
    int number;
    
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

    ArrayList<String> groups = new ArrayList<String>();

    public CustomTeam (String name_, int number_){
        this.name = name_;
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

    }

    public void addPit(Pit pitData){

    }

    public void addGroup(String group){
        this.groups.add(group);
    }

    public String toReadableString(){
        return "";
    }

    public String toCSVString(){
        return name+","+number+","+avGPSand+","+avHPShip+","+avHPRocket+","+
                avHPDrop+","+avCShip+","+avCRocket+","+avCDrop+","+scaleLevels+","+
                    consistScaleLevel+","+maxScaleLevel+","+isRamp+","+startHabs+","+
                        consistStartHab+","+maxStartHab+","+consistOffHab+","+avFoul+","+
                            avTech+","+totalYellow+","+totalRed+","+eStops+","+borks+","+
                                groups+","+matches;
    }
}
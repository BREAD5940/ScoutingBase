package base;


public class Match{

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

    public int fouls;
    public int techs;
    public boolean yellow;
    public boolean red;
    public boolean eStopped;
    public boolean borked;

    public Match(String[] csvRow){

    }

    public int getSandPlaces(){
        return HPShipSand+HPRocketSand+CShipSand+CRocketSand;
    }
}
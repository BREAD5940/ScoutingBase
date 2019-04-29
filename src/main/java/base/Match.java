package base;


public class Match{

    public int teamNum;
    public String matchType;
    public int matchNum;
    public String alliancePosition;

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

    public int scoutedPoints;
    public int tbaPoints;

    public String matchNotes;

    public Match(String[] csvRow){
        this.matchType = csvRow[0];
        this.matchNum = Integer.valueOf(csvRow[1]);
        this.teamNum = Integer.valueOf(csvRow[2]);
        this.alliancePosition = csvRow[3];

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
        this.scoutedPoints = Integer.valueOf(csvRow[23]);

        this.matchNotes = csvRow[24];
    }

    public int getSandPlaces(){
        return HPShipSand+HPRocketSand+CShipSand+CRocketSand;
    }

    private void getTBAVals(int matchNum){

    }
}
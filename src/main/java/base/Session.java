package base;

import javafx.scene.paint.Color;

public class Session {
    public int season;
    public String event;
    public String tbaEventKey;
    public String eventDir;
    public String dataDir;
    public String sortsDir;
    public String teamsDir;
    public String backupsDir;
    public Color backgroundColor;

    public Session(int season, String event, String eventKey, String eventDir, Color backColor){
        this.season = season;
        this.event = event;
        this.tbaEventKey = eventKey;
        this.eventDir = eventDir;
        this.dataDir = eventDir+"data/";
        this.sortsDir = eventDir+"sorts/";
        this.teamsDir = eventDir+"teams/";
        this.backupsDir = eventDir+"backups/";
        this.backgroundColor = backColor;
    }

    public Session(){};

    @Override
    public String toString(){
        return "Season: "+season+", Event Name: "+event+", Event Key: "+tbaEventKey+", Directory: "+eventDir+", Color: "+backgroundColor;
    }

}
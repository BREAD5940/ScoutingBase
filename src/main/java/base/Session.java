package base;

import java.awt.Color;

public class Session{
    public final int season;
    public final String event;
    public final String tbaEventKey;
    public final String eventDir;
    public final String dataDir;
    public final String sortsDir;
    public final String teamsDir;
    public final String backupsDir;
    public final Color backgroundColor;

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
}
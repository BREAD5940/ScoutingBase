package base;


import java.util.ArrayList;

import com.cpjd.main.TBA;

public class Main{
    public static TBA tbaApi;
    static ArrayList<Team> teams = new ArrayList<Team>();
    static ArrayList<Match> matches = new ArrayList<Match>();

    //TODO make some sort of session file to store these
    static final int season = 2019;
    static final String event = "Sacramento";
    static final String tbaEventKey = "2019cada"; //FIXME i hope
    static final String eventDir = "sac_2019/";
    static final String dataDir = eventDir+"data/";
    static final String sortsDir = eventDir+"sorts/";
    static final String teamsDir = eventDir+"teams/";

    public static void main(String[] args){
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        tbaApi = new TBA();
        System.out.println(tbaApi.getEventAwards(tbaEventKey)[0].getName());
    }


}
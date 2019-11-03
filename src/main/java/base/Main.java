package base;

import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import base.controllers.*;
import com.cpjd.main.TBA;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main {
    public static TBA tbaApi;
    // public static Session sesh = new Session(2019, "Sac 2019", "2019cada",
    // "sac_2019/", 0xffffff);

    public static DataRecoveryThread matchRecoveryThread;
    public static DataRecoveryThread teamRecoveryThread;

//    public static ArrayList<CustomTeam> teamSearchResults = new ArrayList<>();
    public static CustomTeam teamResult;

    public enum Windows {
        adminSignIn("/layouts/adminSignIn.fxml"), newSession("/layouts/newSession.fxml"),
        pitAssignEdit("/layouts/pitAssignEdit.fxml"), pitAssignView("/layouts/pitAssignView.fxml"),
        pitDataEntry("/layouts/pitDataEntry.fxml"), pitLaunch("/layouts/pitLaunch.fxml"),
        pitScoutSignIn("/layouts/pitScoutSignIn.fxml"), sessionLaunch("/layouts/sessionLaunch.fxml"),
        standEntry("/layouts/standEntry.fxml"), standLaunch("/layouts/standLaunch.fxml"),
        standRotEdit("/layouts/standRotEdit.fxml"), standRotView("/layouts/standRotView.fxml"),
        standRotViewAll("/layouts/standRotViewAll.fxml"), startup("/layouts/startup.fxml"),
        statsOffline("/layouts/statsOffline.fxml"), teamCompare("/layouts/teamCompare.fxml"),
        teamSearch("/layouts/teamSearch.fxml"), statsOnline("/layouts/statsOnline.fxml"),
        teamSearchResults("/layouts/teamSearchResults.fxml");
        public String filePath;

        private Windows(String filePath) {
            this.filePath = filePath;
        }

        @Override
        public String toString() {
            return filePath;
        }

    }

    //EVERYTHING IN THIS MAP MUST BE AN APPLICATION THAT EXTENDS ControlInterface
    //OTHERWISE STUFF WILL BREAK
    public static  HashMap<Windows, Application> controllersMap = new HashMap<Windows, Application>() {
        {
            put(Windows.startup, StartupControl.getInstance());
            put(Windows.newSession, NewSessionControl.getInstance());
            put(Windows.adminSignIn, AdminSignInControl.getInstance());
            put(Windows.sessionLaunch, SessionLaunchControl.getInstance());
            put(Windows.pitLaunch, new PitLaunchControl());
            put(Windows.pitDataEntry, new PitDataEntryControl());
            put(Windows.teamSearchResults, new TeamSearchResultsControl());
            put(Windows.statsOffline, new StatsOfflineControl());
            put(Windows.statsOnline, new StatsOnlineControl());
            put(Windows.standEntry, new StandEntryControl());
            put(Windows.standLaunch, new StandLaunchControl());
            put(Windows.teamSearch, new TeamSearchControl());
        }
    };

    //FIXME i know, i know, i'm just too lazy to encrypt it
    public static String adminPw = "croissant";

    public static HashMap<String, Session> activeSessions = new HashMap<String,Session>();
    public static ArrayList<CustomMatch> openMatches = new ArrayList<>();
    public static ArrayList<CustomTeam> openTeams = new ArrayList<>();
    public static ArrayList<Pit> openPits = new ArrayList<>();
    public static ArrayList<CustomTeam> searchResults = new ArrayList<>();

    public static synchronized void addToOpenMatches(List<CustomMatch> matches){
        openMatches.addAll(matches);
    }

    public static synchronized void addToOpenTeams(List<CustomTeam> teams){
        openTeams.addAll(teams);
    }

    public static Session currentSession = null;
    // public static Windows currentWindow=Windows.startup;
    // public static boolean switchWindow = false;

    public static void main(String[] args) throws Exception {

        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        tbaApi = new TBA();
        List<Session> tempActiveSessions= Lib.recoverAllSessions();

        for(Session sesh : tempActiveSessions){
            activeSessions.put(sesh.toString(), sesh);
//            Lib.report
        }

        currentSession = activeSessions.get("CCC");
        openMatches.addAll(Lib.convertMatches(currentSession.dataDir+"stand/geran.csv"));
        openMatches.addAll(Lib.convertMatches(currentSession.dataDir+"stand/matt.csv"));
        openMatches.addAll(Lib.convertMatches(currentSession.dataDir+"stand/nick.csv"));
        openMatches.addAll(Lib.convertMatches(currentSession.dataDir+"stand/thomas.csv"));
        openMatches.addAll(Lib.convertMatches(currentSession.dataDir+"stand/claire.csv"));
        openMatches.addAll(Lib.convertMatches(currentSession.dataDir+"stand/max.csv"));

        openTeams.addAll(Lib.generateTeams(currentSession.eventDir+"teams.csv"));

        for(CustomMatch match : openMatches){
            match.syncTBA();
        }

        for(CustomTeam team : openTeams){
            for(CustomMatch match : openMatches){
                if(match.getTeamNum()==team.getNumber()){
                    team.addMatch(match);
                }
            }
            team.syncTBA();
        }

        Lib.saveTeams(openTeams, currentSession.eventDir);

        for(CustomTeam team : openTeams){
            team.sendToTxt(currentSession.eventDir+"teams/");
        }

        openTeams.clear();

        for(Session sesh : tempActiveSessions){
            activeSessions.put(sesh.toString(), sesh);
//            Lib.report
        }

//        currentSession = activeSessions.get("Sac 2019");
//        openTeams.addAll(Lib.generateTeams(eventDir+"teams.csv"));

        

        Application.launch(StartupControl.class, args);

    }


}
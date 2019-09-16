package base;

import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cpjd.main.TBA;

import base.controllers.NewSessionControl;
import base.controllers.PitDataEntryControl;
import base.controllers.PitLaunchControl;
import base.controllers.SessionLaunchControl;
import base.controllers.StartupControl;
import javafx.application.Application;
import javafx.stage.Stage;
import base.controllers.AdminSignInControl;
import base.controllers.ControlInterface;

public class Main {
    public static TBA tbaApi;
    // public static Session sesh = new Session(2019, "Sac 2019", "2019cada",
    // "sac_2019/", 0xffffff);

    public enum Windows {
        adminSignIn("/layouts/adminSignIn.fxml"), newSession("/layouts/newSession.fxml"),
        pitAssignEdit("/layouts/pitAssignEdit.fxml"), pitAssignView("/layouts/pitAssignView.fxml"),
        pitDataEntry("/layouts/pitDataEntry.fxml"), pitLaunch("/layouts/pitLaunch.fxml"),
        pitScoutSignIn("/layouts/pitScoutSignIn.fxml"), sessionLaunch("/layouts/sessionLaunch.fxml"),
        standEntry("/layouts/standEntry.fxml"), standLaunch("/layouts/standLaunch.fxml"),
        standRotEdit("/layouts/standRotEdit.fxml"), standRotView("/layouts/standRotView.fxml"),
        standRotViewAll("/layouts/standRotViewAll.fxml"), startup("/layouts/startup.fxml"),
        statsOffline("/layouts/statsOffline.fxml"), teamCompare("/layouts/teamCompare.fxml"),
        teamSearch("/layouts/teamSearch.fxml"), teamStatsOnline("/layouts/teamStatsOnline.fxml");

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
        }
    };

    //FIXME i know, i know, i'm just too lazy to encrypt it
    public static String adminPw = "croissant";

    public static HashMap<String, Session> activeSessions = new HashMap<String,Session>();
    public static ArrayList<CustomMatch> openMatches = new ArrayList<>();
    public static ArrayList<CustomTeam> openTeams = new ArrayList<>();
    public static ArrayList<Pit> openPits = new ArrayList<>();

    public static Session currentSession = null;
    // public static Windows currentWindow=Windows.startup;
    // public static boolean switchWindow = false;

    public static void main(String[] args) throws Exception {
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        tbaApi = new TBA();

        System.out.println("APP HASH: "+controllersMap.get(Windows.adminSignIn));

        List<Session> tempActiveSessions= Lib.recoverAllSessions();

        for(Session sesh : tempActiveSessions){
            activeSessions.put(sesh.toString(), sesh);
        }

        currentSession = activeSessions.get("Sac 2019");
        openMatches.addAll(Lib.convertMatches(currentSession.dataDir+"claire.csv"));

        openTeams.addAll(Lib.generateTeams(currentSession.eventDir+"teams.csv"));

        for(CustomTeam team : openTeams){
            for(CustomMatch match : openMatches){
                if(match.getTeamNum()==team.getNumber()){
                    team.addMatch(match);
                }
            }
        }

        Lib.saveTeams(openTeams, currentSession.eventDir);

        openTeams.clear();

        

        Application.launch(StartupControl.class, args);

    }


}
package base;

import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cpjd.main.TBA;

import base.controllers.StartupControl;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main{
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

    public static HashMap<Windows, Application> controllersMap = new HashMap<Windows, Application>() {
        {
            put(Windows.startup, new StartupControl());
        }
    };

    public static ArrayList<Windows> backButtonList = new ArrayList<Windows>();
    public static int backIndex = 0; // the PREVIOUS page
    public static boolean isBack = false;

    public static List<Session> activeSessions = new ArrayList<Session>();

    public static Session currentSession = null;
    // public static Windows currentWindow=Windows.startup;
    // public static boolean switchWindow = false;

    public static void main(String[] args) throws Exception {
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        tbaApi = new TBA();

        // controllersMap.get(Windows.startup).start(new Stage());
        Application.launch(StartupControl.class, args);
        backButtonList.add(Windows.startup);

    }


}
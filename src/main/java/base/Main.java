package base;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.cpjd.main.TBA;
import com.cpjd.models.events.Award;
import com.cpjd.models.teams.Team;

import base.control.StartupControl;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.fxml.*;

public class Main{
    public static TBA tbaApi;
    public static Session sesh = new Session(2019, "Sac 2019", "2019cada", "sac_2019/", new Color(100, 199, 254));


    public enum Windows{
        adminSignIn("/layouts/adminSignIn.fxml"), newSession("/layouts/newSession.fxml"), pitAssignEdit("/layouts/pitAssignEdit.fxml"), 
        pitAssignView("/layouts/pitAssignView.fxml"), pitDataEntry("/layouts/pitDataEntry.fxml"), pitLaunch("/layouts/pitLaunch.fxml"),
        pitScoutSignIn("/layouts/pitScoutSignIn.fxml"), sessionLaunch("/layouts/sessionLaunch.fxml"), standEntry("/layouts/standEntry.fxml"),
        standLaunch("/layouts/standLaunch.fxml"), standRotEdit("/layouts/standRotEdit.fxml"), standRotView("/layouts/standRotView.fxml"), 
        standRotViewAll("/layouts/standRotViewAll.fxml"), startup("/layouts/startup.fxml"), statsOffline("/layouts/statsOffline.fxml"), 
        teamCompare("/layouts/teamCompare.fxml"), teamSearch("/layouts/teamSearch.fxml"), teamStatsOnline("/layouts/teamStatsOnline.fxml");

        public String filePath;
        private Windows(String filePath){
            this.filePath = filePath;
        }

    }
    public static Windows currentWindow=Windows.startup;
    public static boolean switchWindow = false;

    public static void main(String[] args) throws Exception{
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        tbaApi = new TBA();

        StartupControl.launch(args);
    }


}
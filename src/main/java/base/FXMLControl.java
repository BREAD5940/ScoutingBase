package base;

import javafx.event.ActionEvent;

import javafx.fxml.*;

public class FXMLControl{
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


    @FXML public void handleStartupGoButton(ActionEvent event){
        System.out.println("Go button pressed");
        switchWindow=true;
        currentWindow = Windows.sessionLaunch;

    }
}
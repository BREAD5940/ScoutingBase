package base.controllers;

import base.Lib;
import base.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StatsOnlineControl extends Application implements ControlInterface {
    Main.Windows previousPage = Main.Windows.sessionLaunch;
    @FXML
    AnchorPane basePane;
    @FXML
    Label sessionTitle;

    @Override
    public Main.Windows getPreviousPage() {
        return this.previousPage;
    }

    @Override
    public void setPreviousPage(Main.Windows prev) {
        this.previousPage = prev;
    }

    @Override
    public AnchorPane getBasePane() {
        return this.basePane;
    }

    @Override
    public Main.Windows getName() {
        return Main.Windows.statsOnline;
    }

    @Override
    public <T extends Application & ControlInterface> T getThis() {
        return (T) this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource(this.getName().toString())));
    }

    @Override
    public void handleBack(ActionEvent event){
        ControlInterface.super.handleBack(event);
    }

    public void handleTeamSearch(ActionEvent event) {
        Lib.pageChangeRequest(Main.Windows.teamSearch, false,this.getThis());
    }
}

package base.controllers;

import base.Lib;
import base.PrankToast;
import base.Main;
import base.Main.Windows;
import base.Session;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class SessionLaunchControl extends Application implements ControlInterface{

    Windows previousPage = Windows.startup;
    @FXML AnchorPane basePane;
    static SessionLaunchControl inst;

    public static <T extends Application & ControlInterface> T getInstance(){
        if(inst==null){
            inst = new SessionLaunchControl();
        }

        return (T) inst;
    }

    @Override
    public Windows getPreviousPage() {
        return this.previousPage;
    }

    @Override
    public void setPreviousPage(Windows prev) {
        this.previousPage = prev;
    }

    @Override
    public AnchorPane getBasePane() {
        return this.basePane;
    }

    @Override
    public Windows getName() {
        return Windows.sessionLaunch;
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

    @FXML
    public void handlePitScout(ActionEvent event){
        Lib.pageChangeRequest(Windows.pitLaunch, false, this.getThis());
    }

    @FXML
    public void handleStandScout(ActionEvent event){
        Lib.pageChangeRequest(Windows.standLaunch, false, this.getThis());
    }

    @FXML
    public void handleOnlineData(ActionEvent event){
        Lib.pageChangeRequest(Windows.teamStatsOnline, false, this.getThis());
    }

    @FXML
    public void handleOfflineData(ActionEvent event){
        Lib.pageChangeRequest(Windows.statsOffline, false, this.getThis());
    }

}
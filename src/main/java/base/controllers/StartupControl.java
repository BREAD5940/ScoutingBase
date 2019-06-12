package base.controllers;

import base.Lib;
import base.Main;
import base.Main.Windows;
import base.Session;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class StartupControl extends Application{

    @FXML private ComboBox<Session> sessionSelect;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource("/layouts/startup.fxml")));
        sessionSelect.getItems().addAll(Main.activeSessions);
    }


    @FXML public void handleGoButton(ActionEvent event){
        System.out.println("Go button pressed");
        Main.switchWindow=true;
        Main.currentWindow = Windows.sessionLaunch;
    }
}
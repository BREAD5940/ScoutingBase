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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class StartupControl extends Application{

    @FXML private ComboBox<Session> sessionSelect;
    @FXML AnchorPane basePane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource("/layouts/startup.fxml")));
        basePane.setBorder(new Border(new BorderStroke(Color.web(Main.currentSession.backgroundColor), BorderStrokeStyle.SOLID, new CornerRadii(1.0), BorderStroke.THICK)));
        sessionSelect.getItems().addAll(Main.activeSessions);
    }


    @FXML public void handleGoButton(ActionEvent event){
        System.out.println("Go button pressed");
        Main.currentSession = sessionSelect.getValue();
        Main.currentWindow = Windows.sessionLaunch;
        Main.switchWindow = true;
    }

    @FXML public void handleBakeNew(ActionEvent event){
        System.out.println("Bake new pressed");
        Main.currentWindow = Windows.newSession;
        Main.switchWindow = true;
    }
}
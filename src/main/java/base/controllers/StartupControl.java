package base.controllers;

import javafx.event.ActionEvent;

import javafx.fxml.*;
import base.Lib;
import base.Main;
import base.Session;
import base.Main.Windows;
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
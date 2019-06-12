package base.control;

import javafx.event.ActionEvent;

import javafx.fxml.*;
import base.Main;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/startup.fxml"));

        Scene scene = new Scene(root, 600, 400);
    
        primaryStage.setTitle("BREAD 5940 Scouting Base");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }


    @FXML public void handleStartupGoButton(ActionEvent event){
        System.out.println("Go button pressed");
        Main.switchWindow=true;
        Main.currentWindow = Windows.sessionLaunch;

    }
}
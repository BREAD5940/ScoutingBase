package base.controllers;

import java.util.Optional;

import base.*;
import base.Main.Windows;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class StartupControl extends Application implements ControlInterface{

    @FXML
    private ComboBox<String> sessionSelect;
    @FXML
    AnchorPane basePane;
    Stage stage;
    Windows previousPage = Windows.startup;
    private static StartupControl inst;


    public static <T extends Application & ControlInterface> T getInstance(){
        if(inst==null){
            inst = new StartupControl();
        }

        return (T) inst;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // sessionSelect = new ComboBox<String>();
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource("/layouts/startup.fxml")));
//         basePane.setBorder(new Border(new BorderStroke(Color.web(Main.currentSession.backgroundColor.getHexVal()), BorderStrokeStyle.SOLID, new CornerRadii(1.0), BorderStroke.THICK)));

        stage = primaryStage;
    }

    @Override
    public void initialize() {
ControlInterface.super.initialize();
        ControlInterface.super.initialize();
        System.out.println("isempty: "+Main.activeSessions.isEmpty());
        System.out.println("sessSel: "+ sessionSelect);
        if(!Main.activeSessions.isEmpty()){
            System.out.println(Main.activeSessions.isEmpty());
            sessionSelect.getItems().clear();
            sessionSelect.getItems().setAll(Main.activeSessions.keySet());
            System.out.println("Session select:"+sessionSelect.getItems().toString());
            System.out.println("select size "+sessionSelect.getItems().size());
        }

    }


    @FXML public void handleGoButton(ActionEvent event){
        System.out.println("Go button pressed");
        Main.currentSession = Main.activeSessions.get(sessionSelect.getValue());
        Main.teamRecoveryThread = new DataRecoveryThread(true);
        Main.matchRecoveryThread = new DataRecoveryThread(false);
//        Main.teamRecoveryThread.start(); FIXME uncomment this
//        Main.matchRecoveryThread.start();
        Lib.pageChangeRequest(Windows.sessionLaunch, false, this);
    }

    @FXML public void handleBakeNew(ActionEvent event){
        System.out.println("Bake new pressed");
        Lib.pwProteccPageChangeRequest(Windows.newSession, false, this);
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
        return Windows.startup;
    }


    @Override
    public <T extends Application & ControlInterface> T getThis() {
        return (T)this;
    }
}
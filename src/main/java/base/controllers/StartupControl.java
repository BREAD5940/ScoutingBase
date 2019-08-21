package base.controllers;

import java.util.Optional;

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

public class StartupControl extends Application implements ControlInterface{

    @FXML private ComboBox<Session> sessionSelect;
    @FXML AnchorPane basePane;
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
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource("/layouts/startup.fxml")));
        // basePane.setBorder(new Border(new BorderStroke(Color.web(Main.currentSession.backgroundColor), BorderStrokeStyle.SOLID, new CornerRadii(1.0), BorderStroke.THICK)));
        if(!Main.activeSessions.isEmpty()){
            sessionSelect.getItems().addAll(Main.activeSessions);
        }

        stage = primaryStage;
    }


    @FXML public void handleGoButton(ActionEvent event){
        System.out.println("Go button pressed");
        Main.currentSession = sessionSelect.getValue();
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
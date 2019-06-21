package base.controllers;

import java.io.File;
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
import javafx.stage.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


public class NewSessionControl extends Application{

    @FXML AnchorPane basePane;
    @FXML TextField year;
    @FXML TextField name;
    @FXML TextField tbaKey;
    @FXML ColorPicker colorPicker;
    File dir;
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource(Main.Windows.newSession.toString())));
        // basePane.setBorder(new Border(new BorderStroke(Color.web(Main.currentSession.backgroundColor), BorderStrokeStyle.SOLID, new CornerRadii(1.0), BorderStroke.THICK)));
        stage = primaryStage;
    }

    @FXML public void handleBackButton(ActionEvent event){
        Lib.report("Back button pressed");
        Lib.pageChangeRequest(null, true);
    }

    @FXML public void handleDirSelect(ActionEvent event){
        FileChooser chooser = new FileChooser();
        dir = chooser.showOpenDialog(stage);
    }

    @FXML public void handleSubmit(ActionEvent event){
        Main.currentSession = new Session(Integer.valueOf(year.getText()), name.getText(), tbaKey.getText(), dir.getAbsolutePath(), colorPicker.getValue());
        Main.activeSessions.add(Main.currentSession);
        Lib.pageChangeRequest(Optional.of(Main.Windows.sessionLaunch), false);
    }

}
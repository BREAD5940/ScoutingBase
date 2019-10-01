package base.controllers;

import base.Lib;
import base.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StandEntryControl extends Application implements ControlInterface{
    File file;
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
        return Main.Windows.standEntry;
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
    public void handleDataEntry(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        file = chooser.showOpenDialog(getStage());
        // TODO: Copy the file to the stand section of active session data directory (sac_2019/data/stand)
        try {
            System.out.println(file.getName());
//            file.renameTo(new File(Main.currentSession.eventDir+file.getName()));
            Files.copy(Paths.get(file.getPath()), Paths.get(Main.currentSession.dataDir + "stand/" + file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        handleBack(event);
    }
}

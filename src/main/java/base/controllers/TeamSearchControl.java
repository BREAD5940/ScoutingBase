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

public class TeamSearchControl extends Application implements ControlInterface {
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
        return Main.Windows.teamSearch;
    }

    @Override
    public <T extends Application & ControlInterface> T getThis() {
        return (T) this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource(this.getName().toString())));
    }

    // handleBack() isn't strictly necessary as there is no Back button in the FXML file,
    // but I've implemented it here for the future.
    @Override
    public void handleBack(ActionEvent event){
        ControlInterface.super.handleBack(event);
    }

}

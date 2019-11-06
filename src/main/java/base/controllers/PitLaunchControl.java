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
import javafx.scene.text.TextAlignment;

public class PitLaunchControl extends Application implements ControlInterface{

    Windows previousPage = Windows.sessionLaunch;
    @FXML AnchorPane basePane;
    @FXML Label sessionTitle;
    static PitLaunchControl inst;

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
        return Windows.pitLaunch;
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
    public void handleEnterData(ActionEvent event){
        Lib.pageChangeRequest(Main.Windows.pitDataEntry, false, this.getThis());
    }

    @FXML
    public void handleViewAssign(ActionEvent event){
        Lib.pageChangeRequest(Main.Windows.pitAssignView, false, this.getThis());
    }

    @FXML
    public void handleEditAssign(ActionEvent event){
        Lib.pageChangeRequest(Main.Windows.pitAssignEdit, false, this.getThis());
    }
    
    @Override
    public void initialize() {
        ControlInterface.super.initialize();
    }

}
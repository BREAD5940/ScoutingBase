package base.controllers;

import java.awt.Checkbox;
import java.util.ResourceBundle.Control;

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

public class PitDataEntryControl extends Application implements ControlInterface{

    Windows previousPage = Windows.pitLaunch;
    @FXML AnchorPane basePane;
    @FXML Label sessionTitle;
    static PitLaunchControl inst;


    @FXML TextField teamNumber;
    @FXML TextField teamName;
    @FXML TextField scoutName;
    @FXML Button submit;

    @FXML CheckBox climbs;
    @FXML RadioButton level2;
    @FXML RadioButton level3;

    @FXML CheckBox cargoIntake;
    @FXML CheckBox hatchIntake;

    @FXML RadioButton rocketLevel3;
    @FXML RadioButton rocketLevel2;

    @FXML Slider mechanicalIssues;

    @FXML CheckBox hasCamera;
    @FXML CheckBox usesPresets;
    @FXML CheckBox hasSensor;
    @FXML CheckBox reachCargo;
    @FXML CheckBox rampbot;

    @FXML TextArea nicknames;

    @FXML RadioButton startHab1;
    @FXML RadioButton startHab2;

    @FXML CheckBox driverControl;
    @FXML CheckBox pathing;
    @FXML CheckBox noControl;

    @FXML CheckBox autoCloseRocketHatch;
    @FXML CheckBox autoFArRocketHatch;
    @FXML CheckBox autoFrontShipHatch;
    @FXML CheckBox autoOtherShipHatch;
    @FXML CheckBox autoShipCargo;
    @FXML CheckBox autoline;
    @FXML CheckBox autoMultiPiece;
    @FXML CheckBox autoNoStrat;
    @FXML CheckBox autoOtherStrat;

    @FXML TextArea autoStratNotes;

    @FXML CheckBox prefHatch;
    @FXML CheckBox prefCargo;

    @FXML Slider piecesPerMatch;

    @FXML CheckBox teleopShipCargo;
    @FXML CheckBox teleopShipHatch;
    @FXML CheckBox teleopRocketCargo;
    @FXML CheckBox teleopRocketHatch;
    @FXML CheckBox teleopDefense;
    @FXML CheckBox teleopMixed;
    @FXML CheckBox teleopFlex;
    @FXML CheckBox teleopStratOther;

    @FXML Slider cycleTime;

    @FXML TextArea teleopStratNotes;

    @FXML RadioButton hpIntegral;
    @FXML RadioButton hpIdeal;
    @FXML RadioButton hpNoPref;

    @FXML RadioButton sticksStrat;
    @FXML RadioButton prefStrat;
    @FXML RadioButton flexStrat;

    @FXML TextArea notes;

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
        return basePane;
    }

    @Override
    public Windows getName() {
        return Windows.pitDataEntry;
    }

    @Override
    public <T extends Application & ControlInterface> T getThis() {
        return (T) this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource(this.getName().toString())));
        primaryStage.setHeight(600);
        primaryStage.setWidth(850);
    }

    @Override
    public void handleBack(ActionEvent event){
        ControlInterface.super.handleBack(event);
    }

    @FXML
    public void handleSubmit(ActionEvent event){
        //TODO add The Thingy to make it part of the object
    }

}
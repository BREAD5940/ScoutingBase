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


public class NewSessionControl extends Application implements ControlInterface{

    @FXML AnchorPane basePane;
    @FXML TextField year;
    @FXML TextField name;
    @FXML TextField tbaKey;
    @FXML ColorPicker colorPicker;
    @FXML Button dirSelect;
    File dir;
    Windows previousPage = Windows.startup;
    private static NewSessionControl inst;

    public static <T extends Application & ControlInterface> T getInstance(){
        if(inst==null){
            inst = new NewSessionControl();
        }

        return (T) inst;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource(Main.Windows.newSession.toString())));
        // basePane.setBorder(new Border(new BorderStroke(Color.web(Main.currentSession.backgroundColor), BorderStrokeStyle.SOLID, new CornerRadii(1.0), BorderStroke.THICK)));
        Lib.report(primaryStage.toString());
    }

    // @FXML public void handleBackButton(ActionEvent event){
    //     Lib.report("Back button pressed");
    //     Lib.pageChangeRequest(null, true, this);
    // }

    @FXML public void handleDirSelect(ActionEvent event){
        DirectoryChooser chooser = new DirectoryChooser();
        dir = chooser.showDialog(this.getStage());
        dirSelect.setText(dir.getName());
    }

    @FXML public void handleSubmit(ActionEvent event){
        Session tempSesh = new Session(Integer.valueOf(year.getText()), name.getText(), tbaKey.getText(), dir.getAbsolutePath()+"/", colorPicker.getValue());
        Main.activeSessions.put(name.getText(),tempSesh);
//        Lib.report(Main.activeSessions.get(0).toString());
        Lib.saveSession(tempSesh);
        Lib.pageChangeRequest(Main.Windows.startup, false, this);
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
        return Windows.newSession;
    }

    @Override
    public <T extends Application & ControlInterface> T getThis() {
        return (T)this;
    }

    @Override
    public void handleBack(ActionEvent event){
        ControlInterface.super.handleBack(event);
    }

    @Override
    public void initialize() {

    }
}
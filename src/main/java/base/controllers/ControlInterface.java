package base.controllers;

import java.util.Optional;
import java.util.ResourceBundle.Control;

import base.Lib;
import base.Main.Windows;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

public interface ControlInterface{

    public Windows getPreviousPage();
    void setPreviousPage(Windows prev);

    public AnchorPane getBasePane();

    public Windows getName();
    public default String getResourceLocation(){
        return this.getName().toString();
    }

    public default Stage getStage(){
        return (Stage)this.getBasePane().getScene().getWindow();
    }

    public <T extends Application & ControlInterface> T getThis();

    public default void handleBack(ActionEvent event){
        Lib.report("back button pressed from "+this.getName().toString()+". Reversing to "+this.getPreviousPage().toString());
        Lib.pageChangeRequest(this.getPreviousPage(), true, this.getThis());
    }
}
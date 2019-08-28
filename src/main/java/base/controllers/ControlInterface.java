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

public interface ControlInterface{

    
    
    public default void initialize(){
        if(!(Main.currentSession==null)){
            this.getBasePane().setBorder(new Border(new BorderStroke(Color.web(Main.currentSession.backgroundColor.getHexVal()), BorderStrokeStyle.SOLID, new CornerRadii(1.0), BorderStroke.THICK)));
    
        }
    }

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
package base.controllers;

import java.util.Optional;

import base.Lib;
import base.Main.Windows;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;





abstract class ControlBase extends Application{

    public Windows previousPage;
    @FXML AnchorPane basePane;
    Stage stage;
    String resourceLoc;
    Windows name;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource(resourceLoc)));
        //TODO border color
        stage = primaryStage;
    }

    @FXML public void handleBack(ActionEvent event){
        Lib.report("back button pressed from "+this.name.toString()+". Reversing to "+this.previousPage.toString());
        Lib.pageChangeRequest(Optional.of(this.previousPage), false, this);
    }


}
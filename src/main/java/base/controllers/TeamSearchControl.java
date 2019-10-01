package base.controllers;

import base.Lib;
import base.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.HashMap;

public class TeamSearchControl extends Application implements ControlInterface {
    Main.Windows previousPage = Main.Windows.sessionLaunch;
    @FXML
    AnchorPane basePane;
    @FXML
    Label sessionTitle;

    @FXML
    TextField numberBox;

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

    @FXML
    public void handleGoNumber(ActionEvent event){
        //FIXME this is a gud meme
        try {
            Main.teamResult = Lib.searchForTeamNumber((int) Integer.valueOf(this.numberBox.getText()), Main.openTeams);
        }catch(Exception e){

        }


    }

//    private <T extends Application & ControlInterface> void prankPageChange(){
//        System.out.println("APP HASH: "+this);
//        T newApp = (T)(Main.controllersMap.get(reqPage));
//        Lib.report(newApp.toString());
//        try{
//            Lib.report(newApp.getName().toString());
//            newApp.start(new Stage());
//        }catch(Exception e){
//            Lib.report(e.toString());
//        }
//        Lib.report(this.getName().toString());
//        newApp.setPreviousPage(this.getName());
//        Lib.report("Page changed to "+reqPage.toString()+". This "+((isBack) ? "was" : "was not")+" a back button change.");
//        //
//    }

    // handleBack() isn't strictly necessary as there is no Back button in the FXML file,
    // but I've implemented it here for the future.
    @Override
    public void handleBack(ActionEvent event){
        ControlInterface.super.handleBack(event);
    }

}

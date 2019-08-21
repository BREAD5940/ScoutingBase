package base.controllers;

import java.io.File;
import java.util.Optional;

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


public class AdminSignInControl extends Application implements ControlInterface{

    @FXML AnchorPane basePane;
    @FXML PasswordField passwordField;
    @FXML Button submitButton;
    Windows previousPage = Windows.startup;
    Windows followThroughPage;
    private static AdminSignInControl inst;

    public static <T extends Application & ControlInterface> T getInstance(){
        return (T) getSpecInstance();
    }

    protected static AdminSignInControl getSpecInstance(){
        if(inst==null){
            inst = new AdminSignInControl();
        }

        return inst;
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
        return Windows.adminSignIn;
    }

    @Override
    public <T extends Application & ControlInterface> T getThis() {
        return (T)this;
    }

    public void setFollowThroughPage(Windows page){
        Lib.report("Follow page set to :"+page);
        this.followThroughPage = page;
        Lib.report("Follow page is: "+this.followThroughPage);
    }

    public Windows getFollowThroughPage(){
        // Lib.report("Follow page"+this.followThroughPage.toString());
        return this.followThroughPage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource(this.getResourceLocation())));
        // basePane.setBorder(new Border(new BorderStroke(Color.web(Main.currentSession.backgroundColor), BorderStrokeStyle.SOLID, new CornerRadii(1.0), BorderStroke.THICK)));
        Lib.report(primaryStage.toString());
    }

    @Override
    public void handleBack(ActionEvent event){
        ControlInterface.super.handleBack(event);
    }

    @FXML
    public void handleSubmit(ActionEvent event){
        if(passwordField.getText().equals(Main.adminPw)){
            Lib.report("Admin successfully signed in");
            // Lib.report("Follow through page: "+this.getFollowThroughPage().toString());
            Lib.report("This: "+this.getThis().toString());
            Lib.pageChangeRequest(AdminSignInControl.getSpecInstance().getFollowThroughPage(), false, this.getThis());
        }else{
            Lib.report("Admin sign in failed");
            PrankToast.makeText(this.getStage(),"Password Incorrect",4000,500,500);
        }
    }

}
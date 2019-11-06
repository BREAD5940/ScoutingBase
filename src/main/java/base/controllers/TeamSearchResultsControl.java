package base.controllers;

import base.Lib;
import base.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TeamSearchResultsControl extends Application implements ControlInterface {
    Main.Windows previousPage = Main.Windows.teamSearch;
    @FXML AnchorPane basePane;
    @FXML TableView resultsTable;

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
        return Main.Windows.teamSearchResults;
    }

    @Override
    public <T extends Application & ControlInterface> T getThis() {
        return (T) this;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Lib.memeStart(stage, FXMLLoader.load(getClass().getResource(this.getName().toString())));
    }

    @Override
    public void handleBack(ActionEvent event){
        ControlInterface.super.handleBack(event);
    }

    @Override
    public void initialize() {
        ControlInterface.super.initialize();
        // Set the columns width auto size
        /*resultsTable.getColumns().get(0).prefWidthProperty().bind(resultsTable.widthProperty().multiply(0.33));    // 33% for id column size
        resultsTable.getColumns().get(1).prefWidthProperty().bind(resultsTable.widthProperty().multiply(0.33));   // 33% for dt column size
        resultsTable.getColumns().get(2).prefWidthProperty().bind(resultsTable.widthProperty().multiply(0.33));    // 33% for cv column size*/
        resultsTable.getItems().setAll(Main.searchResults); //TODO: fix this when we have team pages implemented.
    }
}

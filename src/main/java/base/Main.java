package base;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


import com.cpjd.main.TBA;
import com.cpjd.models.events.Award;
import com.cpjd.models.teams.Team;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Main extends Application{
    public static TBA tbaApi;
    public static Session sesh = new Session(2019, "Sac 2019", "2019cada", "sac_2019/", new Color(100, 199, 254));

    @Override
    public void start(Stage primaryStage) throws Exception {
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        tbaApi = new TBA();

        primaryStage.setTitle("Team 5940 Scouting Base");
        Button submit = new Button();
        Menu selector = new Menu();
        VBox box = new VBox();
        String selected;
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioMenuItem sacChoice = new RadioMenuItem("Choice 1");
        toggleGroup.getToggles().add(sacChoice);
        selector.getItems().add(sacChoice);
        submit.setText("Select");
        submit.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Current Session: "+toggleGroup.getSelectedToggle().toString());
            }
        });
        
        box.getChildren().add(selector);
        primaryStage.setScene(new Scene(box, 300, 250));
        primaryStage.show();
    }


}
package base.controllers;

import base.CustomMatch;
import base.CustomTeam;
import base.Lib;
import base.Main;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.NotActiveException;
import java.util.ArrayList;
import java.util.Arrays;


public class TeamStatsPageControl extends Application implements ControlInterface {

    Main.Windows previousPage = Main.Windows.startup;
    CustomTeam passedTeam = Main.teamResult;
    @FXML AnchorPane basePane;

    //General
    @FXML Label teamNum;
    @FXML Label teamName;
    @FXML Button pitButton;

    //Charts
    @FXML BarChart<String, Integer> driverRankChart;
    @FXML CategoryAxis driverRankMatchAxis;
    @FXML BarChart<String, Integer> sandstormPlacementChart;
    @FXML CategoryAxis sandstormPlacementMatchAxis;
    @FXML StackedBarChart<String, Integer> totalPlacementChart;
    @FXML CategoryAxis totalPlacementMatchAxis;
    @FXML StackedBarChart<String, Integer> placementLocationChart;
    @FXML CategoryAxis placementLocationLocationAxis;

    // Start Dots
    @FXML Circle startOne;
    @FXML Circle startTwo;
    @FXML Circle startThree;
    @FXML Circle startFour;
    @FXML Circle startFive;
    @FXML Circle startSix;
    @FXML Circle startSeven;
    @FXML Circle startEight;
    @FXML Circle startNine;
    @FXML Circle startTen;
    @FXML Circle startEleven;
    @FXML Circle startTwelve;
    @FXML Circle startThirteen;
    @FXML Circle startFourteen;
    @FXML Circle startFifteen;
    
    @FXML ArrayList<Circle> startCircles;

//    ObservableList<Circle> startCircles = FXCollections.observableArrayList(Arrays.asList(startOne, startTwo, startThree, startFour, startFive, startSix, startSeven, startEight, startNine, startTen, startEleven, startTwelve, startThirteen, startFourteen, startFifteen));

    //Climb Dots
    @FXML Circle climbOne;
    @FXML Circle climbTwo;
    @FXML Circle climbThree;
    @FXML Circle climbFour;
    @FXML Circle climbFive;
    @FXML Circle climbSix;
    @FXML Circle climbSeven;
    @FXML Circle climbEight;
    @FXML Circle climbNine;
    @FXML Circle climbTen;
    @FXML Circle climbEleven;
    @FXML Circle climbTwelve;
    @FXML Circle climbThirteen;
    @FXML Circle climbFourteen;
    @FXML Circle climbFifteen;
    
    @FXML ArrayList<Circle> climbCircles;
    
//    ObservableList<Circle> climbCircles = FXCollections.observableArrayList(Arrays.asList(climbOne, climbTwo, climbThree, climbFour, climbFive, climbSix, climbSeven, climbEight, climbNine, climbTen, climbEleven, climbTwelve, climbThirteen, climbFourteen, climbFifteen));
    //Consist Dots
    @FXML Circle consistOne;
    @FXML Circle consistTwo;
    @FXML Circle consistThree;
    @FXML Circle consistFour;
    @FXML Circle consistFive;
    @FXML Circle consistSix;
    @FXML Circle consistSeven;
    @FXML Circle consistEight;
    @FXML Circle consistNine;
    @FXML Circle consistTen;
    @FXML Circle consistEleven;
    @FXML Circle consistTwelve;
    @FXML Circle consistThirteen;
    @FXML Circle consistFourteen;
    @FXML Circle consistFifteen;
    
    @FXML ArrayList<Circle> consistCircles;
    
//    ObservableList<Circle> consistCircles = FXCollections.observableArrayList(Arrays.asList(consistOne, consistTwo, consistThree, consistFour, consistFive, consistSix, consistSeven, consistEight, consistNine, consistTen, consistEleven, consistTwelve, consistThirteen, consistFourteen, consistFifteen));
    //Notes
    @FXML TableView<NotesModel> noteTable;
    @FXML TableColumn<NotesModel, String> qColumn;
    @FXML TableColumn<NotesModel, Integer> matchNumColumn;
    @FXML TableColumn<NotesModel, String> stationColumn;
    @FXML TableColumn<NotesModel, String> noteColumn;



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
        return Main.Windows.teamStatsPage;
    }

    @Override
    public <T extends Application & ControlInterface> T getThis() {
        return (T)this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource(this.getResourceLocation())));
        Lib.report(primaryStage.toString());
    }

    @Override
    public void initialize(){
        this.teamNum.setText("Team "+this.passedTeam.getNumber());
        this.teamName.setText(this.passedTeam.getTbaName());
        
        this.driverRankMatchAxis.getCategories().add("Rank");
        XYChart.Series<String, Integer> driverRankDataSeries = new XYChart.Series<>();
        driverRankDataSeries.setName("Rank");
        
        this.sandstormPlacementMatchAxis.getCategories().add("Placed");
        XYChart.Series<String, Integer> sandStormPlaceDataSeries = new XYChart.Series<>();
        sandStormPlaceDataSeries.setName("Placed");

        this.totalPlacementMatchAxis.getCategories().addAll("Hatch", "Cargo");
        XYChart.Series<String, Integer> totalHatchDataSeries = new XYChart.Series<>();
        totalHatchDataSeries.setName("Hatch");
        XYChart.Series<String, Integer> totalCargoDataSeries = new XYChart.Series<>();
        totalCargoDataSeries.setName("Cargo");

        this.placementLocationLocationAxis.getCategories().addAll("Hatch", "Cargo");
        XYChart.Series<String, Integer> locHatchDataSeries = new XYChart.Series<>();
        locHatchDataSeries.setName("Hatch");
        XYChart.Series<String, Integer> locCargoDataSeries = new XYChart.Series<>();
        locCargoDataSeries.setName("Cargo");
        
        this.qColumn.setCellValueFactory(new PropertyValueFactory<>("qNum"));
        this.matchNumColumn.setCellValueFactory(new PropertyValueFactory<>("matchNum"));
        this.stationColumn.setCellValueFactory(new PropertyValueFactory<>("station"));
        this.noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

        int rocketHatch=0, rocketCargo=0, shipHatch=0, shipCargo=0, dropHatch=0, dropCargo=0;

        for(int i=1; i<this.passedTeam.getMatches().size()-1; i++){
            CustomMatch openMatch = this.passedTeam.getMatches().get(i-1);
            driverRankDataSeries.getData().add(new XYChart.Data<>("Q"+i, openMatch.getDriverRank()));
            sandStormPlaceDataSeries.getData().add(new XYChart.Data<>("Q"+i, openMatch.sandPlaces()));
            totalHatchDataSeries.getData().add(new XYChart.Data<>("Q"+i, openMatch.totalHatch()));
            totalCargoDataSeries.getData().add(new XYChart.Data<>("Q"+i, openMatch.totalCargo()));
            rocketHatch += openMatch.totalRocketHatch();
            rocketCargo += openMatch.totalRocketCargo();
            shipHatch += openMatch.totalShipHatch();
            shipCargo += openMatch.totalShipCargo();
            dropHatch += openMatch.totalDropHatch();
            dropCargo += openMatch.totalDropCargo();

            switch (openMatch.getStartHab()){
                case 1:
//                    this.startCircles.get(i-1).setFill(Color.web("#ff9f00"));
                    System.out.println(this.startCircles.get(i-1));
                case 2:
                    this.startCircles.get(i-1).setFill(Color.web("#0cba00"));
            }

            switch (openMatch.getScaleLevel()){
                case 0:
                    this.climbCircles.get(i-1).setFill(Color.web("#ff0000"));
                case 1:
                    this.climbCircles.get(i-1).setFill(Color.web("#ff9f00"));
                case 2:
                    this.climbCircles.get(i-1).setFill(Color.web("#0cba00"));
                case 3:
                    this.climbCircles.get(i-1).setFill(Color.web("#00a9cf"));
            }

//            if(openMatch.getEStopped()){ fixme uncomment
//                this.consistCircles.get(i-1).setFill(Color.web("#ff7300"));
//            }else if(openMatch.getBorked()){
//                this.consistCircles.get(i-1).setFill(Color.web("#ff0000"));
//            }else{
//                this.consistCircles.get(i-1).setFill(Color.web("#0cba00"));
//            }
            this.noteTable.getItems().add(new NotesModel("Q"+i, openMatch.getMatchNum(), openMatch.getAlliancePosition(), openMatch.getMatchNotes()));
            
        }

        locHatchDataSeries.getData().add(new XYChart.Data<>("Ship", shipHatch));
        locHatchDataSeries.getData().add(new XYChart.Data<>("Rocket", rocketHatch));
        locHatchDataSeries.getData().add(new XYChart.Data<>("Drop", dropHatch));

        locCargoDataSeries.getData().add(new XYChart.Data<>("Ship", shipCargo));
        locCargoDataSeries.getData().add(new XYChart.Data<>("Rocket", rocketCargo));
        locCargoDataSeries.getData().add(new XYChart.Data<>("Drop", dropCargo));
        
        this.driverRankChart.getData().add(driverRankDataSeries);
        this.sandstormPlacementChart.getData().add(sandStormPlaceDataSeries);

        this.placementLocationChart.getData().add(locHatchDataSeries);
        this.placementLocationChart.getData().add(locCargoDataSeries);

        this.totalPlacementChart.getData().add(totalHatchDataSeries);
        this.totalPlacementChart.getData().add(totalCargoDataSeries);
    }

    @Override
    public void handleBack(ActionEvent event) { ControlInterface.super.handleBack(event);}

    class NotesModel{
        private String qNum, station, note;
        private Integer matchNum;
        public NotesModel(String qNum, int matchNum, String station, String note){
            this.qNum = qNum;
            this.matchNum = matchNum;
            this.station = station;
            this.note = note;
        }

        public String getqNum(){return this.qNum;}
        public void setqNum(String num){this.qNum = num;}
        
        public String getStation(){return this.station;}
        public void setStation(String station){this.station = station;}
        
        public String getNote(){return this.note;}
        public void setNote(String note){this.note=note;}
        
        public Integer getMatchNum(){return this.matchNum;}
        public void setMatchNum(int matchNum){this.matchNum=matchNum;}
    }
}

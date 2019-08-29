// package base.controllers;

// import java.util.Optional;
// import java.util.ResourceBundle.Control;

// import base.Lib;
// import base.Main.Windows;
// import javafx.application.Application;
// import javafx.stage.Stage;
// import javafx.scene.layout.AnchorPane;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.event.ActionEvent;




// @Deprecated
// class ControlBase extends Application{

//     public Windows previousPage;
//     @FXML AnchorPane basePane;
//     String resourceLoc;
//     Windows name;
//     Stage stage;

//     protected ControlBase(Windows name){
//         resourceLoc = name.toString();
//         this.name = name;
//     }


//     @Override
//     public void start(Stage primaryStage) throws Exception{
//         Lib.memeStart(primaryStage, FXMLLoader.load(getClass().getResource(resourceLoc)));
//         //TODO border color
//         stage = primaryStage;
//     }

//     @FXML public void handleBack(ActionEvent event){
//         Lib.report("back button pressed from "+this.name.toString()+". Reversing to "+this.previousPage.toString());
//         // Lib.pageChangeRequest(this.previousPage, false, this);
//     }


// }
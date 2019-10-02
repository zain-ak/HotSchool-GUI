package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;

public class Main extends Application {

    private static Connection con = DBConnector.getConnection();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("HotSchool");
        primaryStage.setScene(new Scene(root, 1000, 550));
        primaryStage.show();
        //primaryStage.setOnCloseRequest(e -> handleExit());

        if (con != null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Connected to database!");
            a.show();
        }
        else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Error connecting to database :(");
            a.show();
        }
    }

    @Override
    public void stop() throws Exception {
        con.close();
        super.stop();
    }


//    private void handleExit() {
////handle disposing of the timestamp thread
//        controller.Dispose();
//        Platform.exit();
//        System.exit(0);
//    }



    public static void main(String[] args) {
        launch(args);
    }
}

package sample;/**
 * Created by harshit on 10-09-2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage signup_stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene signup_scene = new Scene(root, 700, 400);
        signup_stage.setTitle("FindDoc");
        signup_stage.setScene(signup_scene);
        signup_stage.show();
    }
}
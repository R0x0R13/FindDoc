package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashboardProfileController {
    private UserProfile userProfile;
    @FXML
    JFXButton signout;
    @FXML
    JFXTextField name;
    @FXML
    JFXDatePicker dob;
    @FXML JFXTextField email;
    @FXML JFXTextField mob;
    @FXML
    ComboBox<String> bg;
    @FXML ComboBox<String> gender;
    public void initData(UserProfile userProfile){
        this.userProfile = userProfile;
        System.out.println("user_id Obtained" + userProfile.getUser_id());
        System.out.println("first name = " + userProfile.getFirst_name());

        name.setPromptText(userProfile.getFirst_name() + " " + userProfile.getLast_name());
        dob.setPromptText(userProfile.getDob());
        email.setPromptText(userProfile.getEmail());
        mob.setPromptText(userProfile.getMob_no());
    }
    public void showSearchDoctor() throws IOException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        DashboardController controller = root.<DashboardController>getController();
        controller.initData(userProfile);
        stage.show();
    }
    public void showSearchClinic() throws IOException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("dashboardSearchClinic.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        DashboardSearchClinicController controller = root.getController();
        controller.initData(userProfile);
        stage.show();
    }

    public void signout() throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage=(Stage) signout.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root, 700,400);
        stage.setScene(scene);
        stage.show();
        userProfile = null;
    }
}

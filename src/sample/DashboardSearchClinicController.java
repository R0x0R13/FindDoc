package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardSearchClinicController {
    @FXML
    JFXButton signout;
    private UserProfile userProfile;
    public void initData(UserProfile userProfile){
        this.userProfile = userProfile;
    }

    public void showProfile() throws IOException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/Dashboard_profile.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        DashboardProfileController controller = root.getController();
        controller.initData(userProfile);
        stage.show();
    }

    public void showSearchDoctor() throws IOException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/dashboard.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        DashboardController controller = root.<DashboardController>getController();
        controller.initData(userProfile);
        stage.show();
    }

    public void signout() throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage=(Stage) signout.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root, 700,400);
        stage.setScene(scene);
        stage.show();
        userProfile = null;
    }
}

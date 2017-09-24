package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardAppointmentDoctor {
    private DoctorDetail doctorDetail;
    @FXML
    JFXButton signout;
    public  void initData(DoctorDetail doctorDetail){
        this.doctorDetail = doctorDetail;
    }

    public void showSearchDoctor() throws IOException{
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/dashboard.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        DashboardController controller = root.getController();
        controller.initData(doctorDetail.userProfile);
        stage.show();
    }

    public void showProfile() throws IOException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/Dashboard_profile.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        DashboardProfileController controller = root.getController();
        controller.initData(doctorDetail.userProfile);
        stage.show();
    }

    public void showSearchClinic() throws IOException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/DashboardSearchClinic.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        DashboardSearchClinicController controller = root.getController();
        controller.initData(doctorDetail.userProfile);
        stage.show();
    }

    public void signout() throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) signout.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        Scene scene = new Scene(root, 700,400);
        stage.setScene(scene);
        stage.show();
        doctorDetail.userProfile = null;
        doctorDetail = null;
    }
}

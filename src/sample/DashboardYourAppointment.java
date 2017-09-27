package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashboardYourAppointment {
    public ListView<AppointmentDetailUser> listView;

    @FXML
    JFXButton signout;
    ObservableList<AppointmentDetailUser> appointmentDetailObservableList;
    private UserProfile userProfile;
    public void initData(UserProfile userProfile) throws SQLException {
        this.userProfile = userProfile;
        Connection connection = new ConnectDatabase().connectToDatabase();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from appointment_tbl where user_id = " + userProfile.getUser_id());
        while(rs.next()){
            ClinicDetail clinicDetail = new ClinicDetail(rs.getInt(2));
            DoctorDetail doctorDetail = new DoctorDetail(rs.getInt(3));
            AppointmentDetail appointmentDetail = new AppointmentDetail(doctorDetail.getName(),userProfile.getFirst_name() + userProfile.getLast_name(),
            rs.getDate(7).toLocalDate(), rs.getTime(5).toLocalTime(),doctorDetail.getFee(),rs.getString(4));
            appointmentDetailObservableList.add(new AppointmentDetailUser(clinicDetail,appointmentDetail,doctorDetail,rs.getInt(1)));
        }
        listView.setItems(appointmentDetailObservableList);
        listView.setCellFactory(appointmentDetailUserListView-> new AppointmentDetailUserListcell());
    }

    public DashboardYourAppointment(){
        appointmentDetailObservableList = FXCollections.observableArrayList();
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

    public void showSearchDoctor() throws IOException, SQLException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/dashboard.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        DashboardController controller = root.getController();
        controller.initData(userProfile);
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
        userProfile = null;
    }
}

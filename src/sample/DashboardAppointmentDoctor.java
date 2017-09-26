package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class DashboardAppointmentDoctor{
    public JFXButton profile;
    public JFXButton searchClinic;
    public JFXButton signout;
    public Label fee;
    public Label doc_name;
    public JFXComboBox<String> time;
    public JFXTextArea symptoms;
    public JFXDatePicker date;
    private DoctorDetail doctorDetail;
    public JFXComboBox<String> clinics;
    ObservableList<ClinicDetail> clinicList;
    ObservableList<String> clinicComboBox;
    ObservableList<String> timeslotList;
    Connection con;
    int hoc_id;
    Time from;
    Time to;
    LocalTime f;
    LocalTime t;
    int index;
    int clinic_id;
    public void initData(DoctorDetail doctorDetail) throws SQLException {
        this.doctorDetail = doctorDetail;
        this.doctorDetail.userProfile = new UserProfile(doctorDetail.getUser_id());
        int clinicListIndex = 0;
        fee.setText(String.valueOf(doctorDetail.getFee()));
        doc_name.setText(doctorDetail.getName());
        con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from works_tbl where doc_id = " + doctorDetail.getDoc_id());
        while(rs.next()){

            hoc_id = rs.getInt(2);
            clinicList.add(new ClinicDetail(hoc_id));
            clinicComboBox.add(clinicList.get(clinicListIndex).getAddress());
            clinicListIndex++;
        }
        clinics.setItems(clinicComboBox);
    }

    public DashboardAppointmentDoctor(){
        clinicList = FXCollections.observableArrayList();
        clinicComboBox = FXCollections.observableArrayList();
        timeslotList = FXCollections.observableArrayList();
    }

    public void showSearchDoctor() throws IOException{
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/dashboard.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        DashboardController controller = root.getController();
        try {
            controller.initData(doctorDetail.userProfile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stage.show();
    }

    public void showProfile() throws IOException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/Dashboard_profile.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
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

    public void getTimeSlots(ActionEvent actionEvent) throws SQLException {
        index = clinics.getSelectionModel().getSelectedIndex();
        clinic_id = clinicList.get(index).getId();
        con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from works_tbl where doc_id = " + doctorDetail.getDoc_id() + " and hoc_id = " +clinic_id);
        while(rs.next())
        {
            from = rs.getTime(3);
            to = rs.getTime(4);
        }
        f = from.toLocalTime();
        t = to.toLocalTime();

        while(f.isBefore(t)){
            timeslotList.add(f.toString() + " - " + f.plusMinutes(15).toString());
            f = f.plusMinutes(15);
        }
        time.setItems(timeslotList);
    }

    public void bookAppointment() throws SQLException {
        int timeIndex = time.getSelectionModel().getSelectedIndex();
        f = f.plusMinutes(15 * timeIndex);
        con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        stmt.executeUpdate("insert into appointment_tbl(hoc_id, doc_id, category,timeslot,user_id,date_app) values" +
                "("+ clinic_id + ", " + doctorDetail.getDoc_id() + ", '" + symptoms.getText() + "', '" + f.toString() + "'," + doctorDetail.getCaller_id() + ",'" + date.getValue().toString() + "')");

    }
}

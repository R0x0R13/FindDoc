package sample;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashboardYourAppointment {
    public ListView<AppointmentDetailUser> listView;
    public JFXTextField doc_name;
    public JFXTextField doc_type;
    public JFXTextField doc_edu;
    public JFXTextField doc_spec;
    public JFXTextField fee;
    public GridPane gridPane;
    public JFXTimePicker time_from;
    public JFXTimePicker time_to;
    public StackPane stackPane;

    @FXML
    JFXButton signout;
    ObservableList<AppointmentDetailUser> appointmentDetailObservableList;
    private UserProfile userProfile;
    public void initData(UserProfile userProfile) throws SQLException {
        this.userProfile = userProfile;
        setChanges();
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
    void setChanges() throws SQLException {
        if(userProfile.loginProfile.getAcc_type().equals("P")){
            gridPane.setVisible(false);
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
        else if(userProfile.loginProfile.getAcc_type().equals("C")){
            listView.setVisible(false);
        }
    }

    public void addDoctor(ActionEvent actionEvent) throws SQLException{
        int doc_id = -1;
        int hoc_id = -1;
        Connection con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        stmt.executeUpdate("insert into doctor_tbl(user_id, specialisation,education,d_type,fee,doc_name) values ("+
                userProfile.getUser_id() + ", '" + doc_spec.getText() + "','" + doc_edu.getText() + "','" + doc_type.getText() + "', " +
                fee.getText() + ", '" + doc_name.getText() + "')");
        ResultSet rs = stmt.getGeneratedKeys();
        while(rs.next()){
            doc_id = rs.getInt(1);
        }
        Statement getHocID = con.createStatement();
        rs = getHocID.executeQuery("select hoc_id from hoc_tbl where user_id = " + userProfile.getUser_id());
        while(rs.next()){
            hoc_id = rs.getInt(1);
        }
        Statement stmt1 = con.createStatement();
        stmt1.executeUpdate("insert into works_tbl values("+doc_id+","+hoc_id+ ",'" +time_from.getValue().toString() + "','" + time_to.getValue().toString() + "')");
        System.out.println("Inserted successfully");
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Done"));
        content.setBody(new Text("Doctor has been added to your clinic!"));
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMaxWidth(250);
        dialog.show();
    }

}

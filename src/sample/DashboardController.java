package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class DashboardController implements Initializable{
    public ListView<AppointmentDetail> appList;
    public TableView<AppointmentDetail> app_tbl;
    public TableColumn t_date;
    public TableColumn t_time;
    public TableColumn t_doc;
    public TableColumn t_patient;
    public TableColumn t_symptom;
    public TableColumn t_fee;
    @FXML JFXButton signout;
    @FXML ListView<DoctorDetail> listView;
    @FXML JFXButton profile;
    ClinicDetail clinicDetail;
    private UserProfile userProfile;
    private ObservableList<DoctorDetail> doctorDetailObservableList;
    private ObservableList<AppointmentDetail> appointmentDetailObservableList;
    void initData(LoginProfile loginProfile) throws SQLException {
        Connection con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select user_id from login_tbl where user_name = '" + loginProfile.getUser_name() + "'");
        rs.next();
        userProfile = new UserProfile(rs.getInt(1));
        this.userProfile.loginProfile = loginProfile;
        System.out.println("username passed: " + userProfile.loginProfile.getUser_name());
        System.out.println("user_id Obtained" + userProfile.getUser_id());
        setChanges();
    }

    void initData(UserProfile userProfile) throws SQLException {
        this.userProfile = userProfile;
        setChanges();
    }

    public DashboardController() throws SQLException {
        doctorDetailObservableList = FXCollections.observableArrayList();
        appointmentDetailObservableList = FXCollections.observableArrayList();
    }

    public void showProfile() throws IOException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) profile.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/Dashboard_profile.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        DashboardProfileController controller = root.getController();
        controller.initData(userProfile);
        stage.show();
    }

    public void showSearchClinic() throws IOException, SQLException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) profile.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/DashboardSearchClinic.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        DashboardYourAppointment controller = root.getController();
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    void setChanges() throws SQLException {
        if(userProfile.loginProfile.getAcc_type().equals("P"))
        {
            //appList.setVisible(false);
            app_tbl.setVisible(false);
            Connection connection = new ConnectDatabase().connectToDatabase();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("select d.user_id, doc_name, fname, d_type, fee, education, doc_id " +
                    "from doctor_tbl as d, user_profile as u where d.user_id = u.user_id");
            while(resultSet.next()){
                doctorDetailObservableList.add(new DoctorDetail(userProfile,resultSet.getString(2),
                        resultSet.getString(4), resultSet.getString(6), resultSet.getInt(5), resultSet.getInt(7),
                        resultSet.getInt(1), userProfile.getUser_id()));
            }
            listView.setItems(doctorDetailObservableList);
            listView.setCellFactory(doctorDetailListView -> new DoctorListViewCellController());
        }
        else if(userProfile.loginProfile.getAcc_type().equals("C")){
            listView.setVisible(false);

            Connection con = new ConnectDatabase().connectToDatabase();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from appointment_tbl where hoc_id in (select hoc_id from hoc_tbl where user_id = " + userProfile.getUser_id() + ")");
            while(rs.next()){
                String doc_name = null;
                String patient_name = null;
                String symptom;
                LocalTime t = rs.getTime(5).toLocalTime();
                LocalDate d = rs.getDate(7).toLocalDate();
                symptom = rs.getString(4);
                Statement stmt1 = con.createStatement();
                ResultSet rs_p = stmt1.executeQuery("select fname, lname from user_profile where user_id = " + rs.getInt(6));
                rs_p.next();
                patient_name = rs_p.getString(1) + " " + rs_p.getString(2);

                Statement stmt2 = con.createStatement();
                ResultSet rs_d = stmt2.executeQuery("select doc_name, fee from doctor_tbl where doc_id = " + rs.getInt(3));
                rs_d.next();
                doc_name = rs_d.getString(1);

                appointmentDetailObservableList.add(new AppointmentDetail(doc_name, patient_name, d,t , rs_d.getInt(2),symptom));
                System.out.println("1st Trace");
            }
            //appList.setItems(appointmentDetailObservableList);
            //appList.setCellFactory(appointmentDetailListView -> new AppointmentDetailListCell());
            app_tbl.setItems(appointmentDetailObservableList);
        }
    }
}
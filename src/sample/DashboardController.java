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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DashboardController implements Initializable{
    @FXML JFXButton signout;
    @FXML ListView<DoctorDetail> listView;
    private UserProfile userProfile;
    private ObservableList<DoctorDetail> doctorDetailObservableList;

    void initData(String user_name) throws SQLException {
        Connection con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select user_id from login_tbl where user_name = '" + user_name + "'");
        rs.next();
        userProfile = new UserProfile(rs.getInt(1));
        System.out.println("username passed: " + user_name);
        System.out.println("user_id Obtained" + userProfile.getUser_id());
    }

    public DashboardController() throws SQLException {
        doctorDetailObservableList = FXCollections.observableArrayList();
        Connection connection = new ConnectDatabase().connectToDatabase();
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("select d.user_id, fname, lname, d_type, fee, education, doc_id " +
                "from doctor_tbl as d, user_profile as u where d.user_id = u.user_id");
        while(resultSet.next()){
            doctorDetailObservableList.add(new DoctorDetail(resultSet.getString(2) + " " + resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(6), resultSet.getInt(5), resultSet.getInt(7),
                    resultSet.getInt(1)));
        }
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

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(doctorDetailObservableList);
        listView.setCellFactory(doctorDetailListView -> new DoctorListViewCellController());
    }
}

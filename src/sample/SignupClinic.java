package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignupClinic implements Initializable {
    @FXML public JFXTextField clinic_area;
    @FXML public JFXComboBox<String> state_cb;
    @FXML private JFXTextField clinic_pin;
    @FXML private JFXTextField clinic_city;
    private UserProfile userProfile;
    private LoginProfile loginProfile;
    private ObservableList<String> stateList;
    @FXML JFXTextField clinic_name;
    @FXML JFXTextField clinic_line;
    @FXML JFXTextField emailid;
    @FXML JFXTextField mob_no;

    public void initData(UserProfile userProfile, LoginProfile loginProfile){
        this.loginProfile = loginProfile;
        this.userProfile = userProfile;
    }

    public SignupClinic(){
        stateList = FXCollections.observableArrayList();
        stateList.addAll("Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir"
        , "Jharkhand", "Karnataka", "Kerela", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland",
                "Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand", "Uttar Pradesh",
                "West Bengal");

    }

    public void signup(ActionEvent actionEvent) throws SQLException {
        Connection con = new ConnectDatabase().connectToDatabase();
        Statement stmt = con.createStatement();
        stmt.executeUpdate("insert into login_tbl(user_name, password, acc_type) values('" + loginProfile.getUser_name() + "', '" + loginProfile.getPassword() + "', 'C')");
        ResultSet rs = stmt.executeQuery("select user_id from login_tbl where user_name = '" + loginProfile.getUser_name() + "'");
        rs.next();
        userProfile.setUser_id(rs.getInt(1));
        stmt.executeUpdate("insert into user_profile values(" + userProfile.getUser_id() + ", '" + userProfile.getFirst_name() + "','" + userProfile.getLast_name() +
                "','" + userProfile.getDob() + "','" + userProfile.getEmail() + "','" + userProfile.getMob_no() +
                "', null , null)");
        stmt.executeUpdate("insert into hoc_tbl(hoc_name, addr_state, addr_city, addr_pin, addr_line, ph_no, email, user_id) values('" +
        clinic_name.getText() + "', '" + state_cb.getSelectionModel().getSelectedItem() + "', '" + clinic_city.getText() + "'," + clinic_pin.getText() +
        ", '" + clinic_line.getText() + "', '" + mob_no.getText() + "', '" + emailid.getText() + "', " + userProfile.getUser_id() + ")");
        System.out.println("Clinic Account Created");

    }

    public void reset(ActionEvent actionEvent) {
        clinic_line.clear();
        clinic_pin.clear();
        clinic_city.clear();
        clinic_name.clear();
        clinic_area.clear();
        emailid.clear();
        mob_no.clear();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) clinic_name.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        Scene scene = new Scene(root, 700,400);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        state_cb.setItems(stateList);
        state_cb.setMaxSize(295, 120);
    }
}

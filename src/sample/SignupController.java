package sample;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by harshit on 10-09-2017.
 */
public class SignupController implements Initializable {
    @FXML
    private JFXTextField user_name;
    @FXML
    private JFXTextField last_name;
    @FXML
    private JFXPasswordField password2;
    @FXML
    private JFXTextField emailid;
    @FXML
    private JFXTextField mob_no;
    @FXML
    private JFXPasswordField password1;
    @FXML
    private JFXDatePicker dob_date_picker;
    @FXML
    private JFXTextField first_name;
    @FXML
    private ImageView backbtn;
    @FXML
    private JFXRadioButton patient;

    @FXML
    void signup(ActionEvent event) {
        if (patient.isSelected()) {
            Connection con = new ConnectDatabase().connectToDatabase();
            String usr_name = user_name.getText();
            String fname = first_name.getText();
            String lname = last_name.getText();
            String pass1 = password1.getText();
            String pass2 = password2.getText();
            String mob = mob_no.getText();
            String dob = dob_date_picker.getValue().toString();
            System.out.println(dob);
            String email = emailid.getText();
            int user_id;
            try {
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("select user_name from login_tbl where user_name = '" + usr_name + "'");
                if (!rs.next() && pass1.equals(pass2)) {
                    statement.executeUpdate("insert into login_tbl(user_name, password, acc_type)values('" + usr_name + "','" + pass1 + "','P')");
                    System.out.println("Inserted into login_tbl");
                    rs = statement.executeQuery("select user_id from login_tbl where user_name = '" + usr_name + "'");
                    rs.next();
                    user_id = rs.getInt(1);
                    statement.executeUpdate("insert into user_profile(user_id, fname, lname, dob, email, mob_no) values(" + user_id + ",'" + fname + "','" + lname + "','" + dob + "','" + email + "','" + mob + "')");
                    System.out.println("Inserted into user_profile");
                } else {
                    System.out.printf("Couldn't insert user!");
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void back() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) backbtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        Scene scene = new Scene(root, 700, 400);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void reset() {
        user_name.clear();
        first_name.clear();
        last_name.clear();
        mob_no.clear();
        emailid.clear();
        password1.clear();
        password2.clear();
        dob_date_picker.getEditor().clear();
    }

    @FXML
    void getClinicDetails() throws IOException {
        if (password1.getText().equals(password2.getText())) {
            UserProfile userProfile;
            userProfile = new UserProfile(0, first_name.getText(), last_name.getText(), dob_date_picker.getValue().toString(),
                    emailid.getText(), mob_no.getText(), null, null);
            LoginProfile loginProfile = new LoginProfile(user_name.getText(), password1.getText(), "P");
            Stage stage;
            FXMLLoader root;
            stage = (Stage) user_name.getScene().getWindow();
            root = new FXMLLoader(getClass().getResource("fxml/signup_clinic.fxml"));
            Scene scene = new Scene(root.load(), 870, 550);
            stage.setScene(scene);
            SignupClinic controller = root.getController();
            controller.initData(userProfile, loginProfile);
            stage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dob_date_picker.getEditor().setStyle("-fx-text-fill:#e5e5e5; -fx-prompt-text-fill:#e5e5e5");
        patient.setSelected(true);
        {
            user_name.focusedProperty().addListener(((observable, oldValue, newValue) -> {
                if (!newValue.toString().matches("[a-zA-Z]+[0-9]*"))
                    System.out.println("it does not match");
            }));
        }
    }
}

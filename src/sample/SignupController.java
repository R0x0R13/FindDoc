package sample;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by harshit on 10-09-2017.
 */
public class SignupController {
    @FXML private JFXTextField user_name;
    @FXML private JFXTextField last_name;
    @FXML private JFXPasswordField password2;
    @FXML private JFXTextField emailid;
    @FXML private JFXTextField mob_no;
    @FXML private JFXPasswordField password1;
    @FXML private JFXDatePicker dob_date_picker;
    @FXML private JFXTextField first_name;
    @FXML private ImageView backbtn;
    @FXML void signup(ActionEvent event) {
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
        try{
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select user_name from login_tbl where user_name = '" + usr_name + "'");
            if(!rs.next() && pass1.equals(pass2)){
                statement.executeUpdate("insert into login_tbl(user_name, password, acc_type)values('" + usr_name + "','" + pass1 + "','C')");
                System.out.println("Inserted into login_tbl");
                rs = statement.executeQuery("select user_id from login_tbl where user_name = '" + usr_name + "'");
                rs.next();
                user_id = rs.getInt(1);
                statement.executeUpdate("insert into user_profile(user_id, fname, lname, dob, email, mob_no) values(" + user_id + ",'" + fname + "','" + lname + "','" + dob + "','" + email + "','" + mob + "')");
                System.out.println("Inserted into user_profile");
            }
            else{
                System.out.printf("Couldn't insert user!");
            }
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML void back() throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) backbtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        Scene scene = new Scene(root, 700,400);
        stage.setScene(scene);
        stage.show();
    }
    @FXML void reset() {
        user_name.clear();
        first_name.clear();
        last_name.clear();
        mob_no.clear();
        emailid.clear();
        password1.clear();
        password2.clear();
        dob_date_picker.getEditor().clear();
    }
}

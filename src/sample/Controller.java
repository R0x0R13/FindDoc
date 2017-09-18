package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Controller {
    @FXML JFXTextField username;
    @FXML JFXPasswordField password;
    @FXML JFXButton signup;
    @FXML Label error;
    boolean isUserName(String username){
        if(username.matches("[a-zA-z0-9]+"))
            return true;
        return false;
    }
    @FXML void signin() throws SQLException, IOException {
        String usrname = username.getText();
        String pass = password.getText();

        Connection con = new ConnectDatabase().connectToDatabase();
        System.out.println("Connected");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select user_name, password from login_tbl where user_name = '" + usrname +"'");
        if(!rs.next())
        {
            error.setText("User does not exist!");
        }
        else if(pass.matches(rs.getString(2))){
            Stage stage;
            Parent root;
            stage=(Stage) signup.getScene().getWindow();
            //load up OTHER FXML document
            root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            //create a new scene with root and set the stage
            Scene scene = new Scene(root, 1000,550);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML void signup() throws IOException{
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage=(Stage) signup.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("signup_fxml.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root, 400,700);
        stage.setScene(scene);
        stage.show();
    }
}

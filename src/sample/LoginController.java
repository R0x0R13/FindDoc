package sample;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    public StackPane stackPane;
    @FXML JFXTextField username;
    @FXML JFXPasswordField password;
    @FXML JFXButton signup;
    @FXML Label error;
    @FXML void signin() throws SQLException, IOException {
        String usrname = username.getText();
        String pass = password.getText();

        Connection con = new ConnectDatabase().connectToDatabase();
        System.out.println("Connected");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select user_name, password, acc_type from login_tbl where user_name = '" + usrname +"'");
        if(!rs.next())
        {
            error.setText("User does not exist!");
        }
        else if(pass.matches(rs.getString(2))){
            Stage stage;
            FXMLLoader root;
            stage=(Stage) signup.getScene().getWindow();
            root = new FXMLLoader(getClass().getResource("fxml/dashboard.fxml"));
            Scene scene = new Scene(root.load(), 870,550);
            stage.setScene(scene);
            DashboardController controller = root.getController();
            controller.initData(new LoginProfile(username.getText(), password.getText(), rs.getString(3)));
            stage.show();
        }
        else{
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Error"));
            content.setBody(new Text("Please check your password!"));
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
            dialog.setMaxWidth(250);
            dialog.show();
        }
    }
    @FXML void signup() throws IOException{
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signup.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/signup_fxml.fxml"));
        Scene scene = new Scene(root.load(), 430,687);
        stage.setScene(scene);
        stage.show();
    }
}

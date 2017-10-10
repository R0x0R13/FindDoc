package sample;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by harshit on 10-09-2017.
 */
public class SignupController implements Initializable {
    public StackPane stackPane;
    @FXML private JFXTextField user_name;
    @FXML private JFXTextField last_name;
    @FXML private JFXPasswordField password2;
    @FXML private JFXTextField emailid;
    @FXML private JFXTextField mob_no;
    @FXML private JFXPasswordField password1;
    @FXML private JFXDatePicker dob_date_picker;
    @FXML private JFXTextField first_name;
    @FXML private ImageView backbtn;
    @FXML private JFXRadioButton patient;


    private boolean validateEmail(String email){
        Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher m = p.matcher(email);
        if(m.find() && m.group().equalsIgnoreCase(email))
            return true;
        else{
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Invalid E-mail"));
            content.setBody(new Text("Please enter a valid E-mail"));
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
            dialog.show();
        }
        return false;
    }
    private boolean validateFirstName(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(first_name.getText());
        if(m.find() && m.group().equalsIgnoreCase(first_name.getText()))
            return true;
        else{
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Invalid Name"));
            content.setBody(new Text("Name can only contain text!"));
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
            dialog.show();
        }
        return false;
    }
    private boolean validateLastName(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(last_name.getText());
        if(m.find() && m.group().equalsIgnoreCase(last_name.getText()))
            return true;
        else{
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Invalid Name"));
            content.setBody(new Text("Name can only contain text!"));
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
            dialog.show();
        }
        return false;
    }

    private boolean validateUserName(){
        Pattern p = Pattern.compile("[a-zA-Z]+[a-zA-Z0-9_]*");
        Matcher m = p.matcher(last_name.getText());
        if(m.find() && m.group().equalsIgnoreCase(last_name.getText()) && user_name.getText().length() > 6 && user_name.getText().length() < 13)
            return true;
        else{
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Invalid username"));
            content.setBody(new Text("Username must start with a letter\nIt must be between 8 to 13 characters\nIt can contain number and '_'"));
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
            dialog.show();
        }
        return false;
    }


    @FXML
    void signup(ActionEvent event) {
        if (patient.isSelected() && validateFirstName() && validateLastName()
                && validateUserName() && validateEmail(emailid.getText())) {
            Connection con = new ConnectDatabase().connectToDatabase();
            int user_id;
            try {
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("select user_name from login_tbl where user_name = '" + user_name.getText() + "'");
                if (!rs.next() && password1.getText().equals(password2.getText())) {
                    CallableStatement cb = con.prepareCall("{call new_user(?,?,?)}");
                    cb.setString(1, user_name.getText());
                    cb.setString(2, password1.getText());
                    cb.setString(3, "P");
                    cb.execute();
                    //statement.executeUpdate("insert into login_tbl(user_name, password, acc_type)values('" + user_name.getText() + "','" + password1.getText() + "','P')");
                    System.out.println("Inserted into login_tbl");
                    rs = statement.executeQuery("select user_id from login_tbl where user_name = '" + user_name.getText() + "'");
                    rs.next();
                    user_id = rs.getInt(1);
                    statement.executeUpdate("insert into user_profile(user_id, fname, lname, dob, email, mob_no) values(" + user_id + ",'" + first_name.getText() + "','" + last_name.getText() + "','" + dob_date_picker.getValue().toString() + "','" + emailid.getText() + "','" + mob_no.getText() + "')");
                    System.out.println("Inserted into user_profile");
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Done"));
                    content.setBody(new Text("User Created"));
                    JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                    dialog.show();
                } else {
                    JFXDialogLayout content = new JFXDialogLayout();
                    content.setHeading(new Text("Error"));
                    content.setBody(new Text("Passwords don't match or Username is already in use"));
                    JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
                    dialog.show();
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
            Scene scene = new Scene(root.load(), 424, 680);
            stage.setScene(scene);
            SignupClinic controller = root.getController();
            controller.initData(userProfile, loginProfile);
            stage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dob_date_picker.getEditor().setStyle("-fx-text-fill:#e5e5e5; -fx-prompt-text-fill:#e5e5e5");
        mob_no.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                mob_no.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        patient.setSelected(true);
    }
}

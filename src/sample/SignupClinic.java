package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupClinic implements Initializable {
    @FXML public JFXTextField clinic_area;
    @FXML public JFXComboBox<String> state_cb;
    public StackPane stackPane;
    @FXML private JFXTextField clinic_pin;
    @FXML private JFXTextField clinic_city;
    private UserProfile userProfile;
    private LoginProfile loginProfile;
    private ObservableList<String> stateList;
    @FXML JFXTextField clinic_name;
    @FXML JFXTextField clinic_line;
    @FXML JFXTextField emailid;
    @FXML JFXTextField mob_no;

    private boolean validateEmail(String email){
        Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)+])");
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

    private boolean validateClinicName(){
        Pattern p = Pattern.compile("[a-zA-Z0-9\\s]+");
        Matcher m = p.matcher(clinic_name.getText());
        if(m.find() && m.group().equalsIgnoreCase(clinic_name.getText()))
            return true;
        else{
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Invalid clinic Name"));
            content.setBody(new Text("Name can only contain text and digits!"));
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
            dialog.show();
        }
        return false;
    }

    private boolean validatePin(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(clinic_pin.getText());
        if(m.find() && m.group().equalsIgnoreCase(clinic_pin.getText()) && clinic_pin.getText().length() == 6)
            return true;
        else{
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("Invalid PIN"));
            content.setBody(new Text("PIN has to be 6 digits only!"));
            JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.TOP);
            dialog.show();
        }
        return false;
    }

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
        if(validateClinicName() && validatePin() && validateEmail(emailid.getText())) {
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
        mob_no.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                mob_no.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        clinic_pin.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                mob_no.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}

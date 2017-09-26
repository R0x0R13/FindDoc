package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import java.sql.Connection;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DashboardProfileController {
    private UserProfile userProfile;
    @FXML JFXButton signout;
    @FXML JFXTextField name;
    @FXML JFXDatePicker dob;
    @FXML JFXTextField email;
    @FXML JFXTextField mob;
    @FXML
    ComboBox<String> bg;
    @FXML JFXTextField lastname;
    @FXML ComboBox<String> gender;
    private ObservableList<String> genderList;
    private ObservableList<String> bgList;
    public void initData(UserProfile userProfile){
        this.userProfile = userProfile;
        genderList = FXCollections.observableArrayList();
        bgList = FXCollections.observableArrayList();
        System.out.println("user_id Obtained" + userProfile.getUser_id());
        System.out.println("first name = " + userProfile.getFirst_name());
        genderList.addAll(
                "Male",
                "Female"
        );
        gender.setItems(genderList);
        bgList.addAll("A+", "A-",
                "B+", "B-",
                "O+", "O-",
                "AB+", "AB-");
        bg.setItems(bgList);
        load();
    }
    public void load(){
        name.setPromptText(userProfile.getFirst_name());
        lastname.setPromptText(userProfile.getLast_name());
        dob.setPromptText(userProfile.getDob());
        email.setPromptText(userProfile.getEmail());
        mob.setPromptText(userProfile.getMob_no());
        bg.setPromptText(userProfile.getBlood_group());
        gender.setPromptText(userProfile.getGender());
    }
    public void showSearchDoctor() throws IOException, SQLException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/dashboard.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        DashboardController controller = root.<DashboardController>getController();
        controller.initData(userProfile);
        stage.show();
    }
    public void showSearchClinic() throws IOException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) signout.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/DashboardSearchClinic.fxml"));
        Scene scene = new Scene(root.load(), 870,550);
        stage.setScene(scene);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        DashboardSearchClinicController controller = root.getController();
        controller.initData(userProfile);
        stage.show();
    }

    public void signout() throws IOException {
        Stage stage;
        Parent root;
        //get reference to the button's stage
        stage=(Stage) signout.getScene().getWindow();
        //load up OTHER FXML document
        root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        //create a new scene with root and set the stage
        Scene scene = new Scene(root, 700,400);
        stage.setScene(scene);
        stage.show();
        userProfile = null;
    }

    public void modify() throws SQLException {
        Connection con = new ConnectDatabase().connectToDatabase();
        StringBuilder modification = new StringBuilder("");
        int flag = 0;

        if(!name.getText().equals("")){
            flag = 1;
            modification.append("fname = '" + name.getText() + "'");
        }
        if(!lastname.getText().equals("")) {
            if (flag == 1)
                modification.append(",lname = '").append(lastname.getText()).append("'");
            else {
                modification.append("lname = '").append(lastname.getText()).append("'");
                flag = 1;
            }
        }
        if(!dob.getEditor().getText().equals("")) {
            if (flag == 1)
                modification.append(", dob = '").append(dob.getValue().toString()).append("'");
            else {
                modification.append("dob = '").append(dob.getValue().toString()).append("'");
                flag = 1;
            }
        }
        if(!email.getText().equals("")) {
            if (flag == 1)
                modification.append(", email = '").append(email.getText()).append("'");

            else {
                modification.append("email = '").append(email.getText()).append("'");
                flag = 1;
            }
        }
        if(!mob.getText().equals("")){
            if (flag==1)
                modification.append(", mob_no = '").append(mob.getText()).append("'");
            else{
                modification.append("mob_no = '").append(mob.getText()).append("'");
                flag = 1;
            }
        }
        if(!(bg.getSelectionModel().getSelectedItem() == null)){
            if(flag == 1)
                modification.append(", bg = '").append(bg.getSelectionModel().getSelectedItem()).append("'");
            else{
                flag = 1;
                modification.append(" bg = '").append(bg.getSelectionModel().getSelectedItem()).append("'");
            }
        }
        if(!(gender.getSelectionModel().getSelectedItem() == null)){
            if(flag ==1)
                if(gender.getSelectionModel().getSelectedItem().equals("Male")){
                    modification.append(", gender = 'M'");
                }
                else{
                    modification.append(", gender = 'F'");
                }
            else{
                if(gender.getSelectionModel().getSelectedItem().equals("Male")){
                    modification.append(" gender = 'M'");
                }
                else{
                    modification.append(" gender = 'F'");
                }
            }
        }
        Statement stmt = con.createStatement();
        stmt.executeUpdate("update user_profile set " + modification + "where user_id = " + userProfile.getUser_id());
        con.close();
    }

    public void reset(){
        name.clear();;
        lastname.clear();
        email.clear();
        dob.getEditor().clear();
        mob.clear();
        gender.getSelectionModel().clearSelection();
        bg.getSelectionModel().clearSelection();
    }
}

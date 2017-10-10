package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DoctorListViewCellController extends JFXListCell<DoctorDetail> {
    @FXML Label name;
    @FXML Label type;
    @FXML Label spec;
    @FXML
    GridPane gridPane;
    @FXML Label clinic_name;
    @FXML Label fee;
    @FXML JFXButton appointment;
    private FXMLLoader mLLoader;
    DoctorDetail doctorDetail;
    int currentIndex = -1;
    @Override
    public void updateItem(DoctorDetail doctorDetail, boolean empty) {
        super.updateItem(doctorDetail, empty);
        this.doctorDetail = doctorDetail;
        if(empty || doctorDetail == null) {

            setText(null);
            setGraphic(null);

        } else {
            int index = getIndex();
            if(currentIndex == index) return;
            currentIndex = index;
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("fxml/doctor_listcell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            name.setText(doctorDetail.getName());
            type.setText(doctorDetail.getType());
            spec.setText(doctorDetail.getSpec());
            try {
                ClinicDetail clinicDetail = new ClinicDetail(doctorDetail.getUser_id());
                clinic_name.setText(clinicDetail.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            fee.setText(String.valueOf(doctorDetail.getFee()));
            appointment.setOnAction(event -> bookAppointment());
            setText(null);
            setGraphic(gridPane);
        }

    }
    @FXML public void bookAppointment()  {
        doctorDetail = getItem();
        Stage stage;
        FXMLLoader root;
        System.out.println(appointment.getScene().getWindow().getWidth());
        stage=(Stage) appointment.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/book_appointment_doctor.fxml"));
        Scene scene;
        try {
            scene = new Scene(root.load(), 870,550);
            stage.setScene(scene);
            DashboardAppointmentDoctor controller = root.getController();
            controller.initData(doctorDetail);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

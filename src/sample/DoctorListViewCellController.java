package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DoctorListViewCellController extends ListCell<DoctorDetail> {
    @FXML Label name;
    @FXML Label type;
    @FXML Label spec;
    @FXML
    GridPane gridPane;
    @FXML Label fee;
    @FXML JFXButton appointment;
    private FXMLLoader mLLoader;
    DoctorDetail doctorDetail;
    @Override
    public void updateItem(DoctorDetail doctorDetail, boolean empty) {
        super.updateItem(doctorDetail, empty);
        this.doctorDetail = doctorDetail;
        if(empty || doctorDetail == null) {

            setText(null);
            setGraphic(null);

        } else {
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
            fee.setText(String.valueOf(doctorDetail.getFee()));
            setText(null);
            setGraphic(gridPane);
        }

    }
    public void bookAppointment() throws IOException {
        Stage stage;
        FXMLLoader root;
        stage=(Stage) appointment.getScene().getWindow();
        root = new FXMLLoader(getClass().getResource("fxml/book_appointment_doctor.fxml"));
        Scene scene = new Scene((AnchorPane)root.load(), 870,550);
        stage.setScene(scene);
        DashboardAppointmentDoctor controller = root.getController();
        controller.initData(doctorDetail);
        stage.show();
    }
}

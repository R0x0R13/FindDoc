package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class AppointmentDetailListCell extends ListCell<AppointmentDetail> {

    public Label d_name;
    public Label p_name;
    public Label date;
    public Label time;
    public Label symptom;
    public Label fee;
    public GridPane gridPane;
    FXMLLoader mLLoader;
    @Override
    protected void updateItem(AppointmentDetail appointmentDetail, boolean empty){
        super.updateItem(appointmentDetail,empty);

        if(appointmentDetail == null || empty){
            setText(null);
            setGraphic(null);
        }
        else{
            if(mLLoader == null){
                mLLoader = new FXMLLoader(getClass().getResource("fxml/appointment_list_cell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            d_name.setText(appointmentDetail.getDoc_name());
            p_name.setText(appointmentDetail.getPatient_name());
            date.setText(appointmentDetail.getDate().toString());
            time.setText(appointmentDetail.getTime().toString());
            symptom.setText(appointmentDetail.getSymptom());
            fee.setText(String.valueOf(appointmentDetail.getFee()));
            setText(null);
            setGraphic(gridPane);
        }
    }
}

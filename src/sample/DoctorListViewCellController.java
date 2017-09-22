package sample;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class DoctorListViewCellController extends ListCell<DoctorDetail> {
    @FXML
    Label name;
    @FXML Label type;
    @FXML Label spec;
    @FXML
    GridPane gridPane;
    @FXML Label fee;
    private FXMLLoader mLLoader;

    @Override
    public void updateItem(DoctorDetail doctorDetail, boolean empty) {
        super.updateItem(doctorDetail, empty);

        if(empty || doctorDetail == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("doctor_listcell.fxml"));
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
}

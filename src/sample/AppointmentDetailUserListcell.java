package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AppointmentDetailUserListcell extends javafx.scene.control.ListCell<AppointmentDetailUser> {
        int currentIndex = -1;
        private FXMLLoader mLLoader;
        private AppointmentDetailUser appointmentDetailUser;
        public AnchorPane anchorPane;
        public Label doc_name;
        public Label clinic_name;
        public Label clinic_address;
        public Label clinic_ph;
        public Label app_date;
        public Label app_time;
        public Label fee;
        public JFXButton cancel;
        @Override
        public void updateItem(AppointmentDetailUser doctorDetail, boolean empty) {
            super.updateItem(doctorDetail, empty);
            this.appointmentDetailUser = doctorDetail;
            if(empty || doctorDetail == null) {

                setText(null);
                setGraphic(null);

            } else {
                int index = getIndex();
                if(currentIndex == index) return;
                currentIndex = index;
                if (mLLoader == null) {
                    mLLoader = new FXMLLoader(getClass().getResource("fxml/appointment_detail_user_listcell.fxml"));
                    mLLoader.setController(this);

                    try {
                        mLLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                doc_name.setText(doctorDetail.appointmentDetail.getDoc_name());
                clinic_name.setText(doctorDetail.clinic_name);
                clinic_address.setText(doctorDetail.clinic_address);
                fee.setText(String.valueOf(doctorDetail.appointmentDetail.getFee()));
                clinic_ph.setText(doctorDetail.clinic_phno);
                cancel.setOnAction(event -> {
                    try {
                        cancelAppointment();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                app_date.setText(doctorDetail.appointmentDetail.getDate().toString());
                app_time.setText(doctorDetail.appointmentDetail.getTime().toString());
                setText(null);
                setGraphic(anchorPane);
            }

        }

        private void cancelAppointment() throws SQLException {
            appointmentDetailUser = getItem();
            Connection con = new ConnectDatabase().connectToDatabase();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("Delete from appointment_tbl where hoc_id = " + appointmentDetailUser.hoc_id + " and doc_id = " + appointmentDetailUser.doc_id + " and app_id = " + appointmentDetailUser.app_id);
        }
    }


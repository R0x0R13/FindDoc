package sample;

public class AppointmentDetailUser {
    String clinic_name;
    String clinic_address;
    String clinic_phno;
    int hoc_id;
    int doc_id;
    int app_id;
    AppointmentDetail appointmentDetail;
    public AppointmentDetailUser(ClinicDetail clinicDetail, AppointmentDetail appointmentDetail, DoctorDetail doctorDetail, int app_id){
        this.clinic_name = clinicDetail.getName();
        this.clinic_address = clinicDetail.getAddress();
        this.clinic_phno = clinicDetail.getPh_no();
        this.appointmentDetail = appointmentDetail;
        this.hoc_id =clinicDetail.getId();
        this.doc_id = doctorDetail.getDoc_id();
        this.app_id = app_id;
    }
}

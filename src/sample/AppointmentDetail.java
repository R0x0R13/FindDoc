package sample;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class AppointmentDetail {
    String doc_name;
    String patient_name;
    LocalDate date;
    LocalTime time;
    int fee;
    String symptom;

    public AppointmentDetail(String doc_name, String patient_name, LocalDate date, LocalTime time, int fee, String symptom){
        this.doc_name = doc_name;
        this.symptom = symptom;
        this.patient_name = patient_name;
        this.date = date;
        this.time = time;
        this.fee = fee;
    }

    public int getFee() {
        return fee;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}

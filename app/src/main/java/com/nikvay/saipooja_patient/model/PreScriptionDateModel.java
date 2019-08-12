package com.nikvay.saipooja_patient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PreScriptionDateModel implements Serializable {

    String prescription_num;
    String diagnosis;
    String symptoms;
    String photo;
    String date;
    String s_name;
    String name ;


    public PreScriptionDateModel(String prescription_num, String diagnosis, String symptoms, String photo,
                                 String date, String s_name,
                                 String name, ArrayList<PrescriptionListDateModel> prescriptionListDateModelArrayList) {
        this.prescription_num = prescription_num;
        this.diagnosis = diagnosis;
        this.symptoms = symptoms;
        this.photo = photo;
        this.date = date;
        this.s_name = s_name;
        this.name = name;
        this.prescriptionListDateModelArrayList = prescriptionListDateModelArrayList;
    }


    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    @SerializedName("prescription_list_date")
    ArrayList<PrescriptionListDateModel> prescriptionListDateModelArrayList;

    public String getPrescription_num() {
        return prescription_num;
    }

    public void setPrescription_num(String prescription_num) {
        this.prescription_num = prescription_num;
    }

    public ArrayList<PrescriptionListDateModel> getPrescriptionListDateModelArrayList() {
        return prescriptionListDateModelArrayList;
    }

    public void setPrescriptionListDateModelArrayList(ArrayList<PrescriptionListDateModel> prescriptionListDateModelArrayList) {
        this.prescriptionListDateModelArrayList = prescriptionListDateModelArrayList;
    }

}

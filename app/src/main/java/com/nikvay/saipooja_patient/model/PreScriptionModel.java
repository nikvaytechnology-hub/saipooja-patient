package com.nikvay.saipooja_patient.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PreScriptionModel implements Serializable {


    public PreScriptionModel(String prescription_num, String diagnosis, String symptoms,
                             String photo, String s_name, String name,
                             ArrayList<PrescriptionListModel> prescriptionListModelArrayList) {
        this.prescription_num = prescription_num;
        this.diagnosis = diagnosis;
        this.symptoms = symptoms;
        this.photo = photo;
        this.s_name = s_name;
        this.name = name;
        this.prescriptionListModelArrayList = prescriptionListModelArrayList;
    }

    String  prescription_num;
    String diagnosis;
    String symptoms;
    String photo;

    String date;
    String s_name;
    String name ;



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getPrescription_num() {
        return prescription_num;
    }

    public void setPrescription_num(String prescription_num) {
        this.prescription_num = prescription_num;
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

    @SerializedName("prescription_list")
    ArrayList<PrescriptionListModel>prescriptionListModelArrayList;



    public ArrayList<PrescriptionListModel> getPrescriptionListModelArrayList() {
        return prescriptionListModelArrayList;
    }

    public void setPrescriptionListModelArrayList(ArrayList<PrescriptionListModel> prescriptionListModelArrayList) {
        this.prescriptionListModelArrayList = prescriptionListModelArrayList;
    }


}

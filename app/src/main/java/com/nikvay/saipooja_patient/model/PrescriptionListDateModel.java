package com.nikvay.saipooja_patient.model;

import java.io.Serializable;

public class PrescriptionListDateModel implements Serializable {

    String prescription_id;
    String medication_name;
    String medication_note;
    String test_name;
    String test_note;



    public PrescriptionListDateModel(String prescription_id,
                                     String medication_name, String medication_note,
                                     String test_name, String test_note)
    {
        this.prescription_id = prescription_id;
        this.medication_name = medication_name;
        this.medication_note = medication_note;
        this.test_name = test_name;
        this.test_note = test_note;
    }


    public String getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(String prescription_id) {
        this.prescription_id = prescription_id;
    }


    public String getMedication_name() {
        return medication_name;
    }

    public void setMedication_name(String medication_name) {
        this.medication_name = medication_name;
    }

    public String getMedication_note() {
        return medication_note;
    }

    public void setMedication_note(String medication_note) {
        this.medication_note = medication_note;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_note() {
        return test_note;
    }

    public void setTest_note(String test_note) {
        this.test_note = test_note;
    }



}

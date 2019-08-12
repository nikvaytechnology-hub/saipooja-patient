package com.nikvay.saipooja_patient.model;

public class DoctorTypeModel {

    int id;
    String Doctortype_name;
    String isSelected;

    public DoctorTypeModel(int i, String s, String s1) {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }




    public String getDoctortype_name() {
        return Doctortype_name;
    }

    public void setDoctortype_name(String doctortype_name) {
        Doctortype_name = doctortype_name;
    }



}

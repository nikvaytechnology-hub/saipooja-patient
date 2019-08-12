package com.nikvay.saipooja_patient.model;

import java.io.Serializable;

public class DoctorModel implements Serializable {
    String service_id;
    String doctor_id;
    String name;
    String email;
    String phone_no;
    String user_id;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
    String profile;
    public DoctorModel(String service_id, String doctor_id, String name, String email, String phone_no, String user_id) {
        this.service_id = service_id;
        this.doctor_id = doctor_id;
        this.name = name;
        this.email = email;
        this.phone_no = phone_no;
        this.user_id = user_id;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }


    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

package com.nikvay.saipooja_patient.model;

public class AppoinmentDateFilterModel {


    String patient_id;
    String appointment_id;
    String service_id;
    String label;
    String comment;
    String time;
    String date;
    String s_name;
    String service_cost;
    String service_time;
    String description;
    String name;

    public AppoinmentDateFilterModel(String patient_id, String appointment_id, String service_id, String label, String comment,
                                     String time, String date, String s_name, String service_cost,
                                     String service_time, String description, String name) {
        this.patient_id = patient_id;
        this.appointment_id = appointment_id;
        this.service_id = service_id;
        this.label = label;
        this.comment = comment;
        this.time = time;
        this.date = date;
        this.s_name = s_name;
        this.service_cost = service_cost;
        this.service_time = service_time;
        this.description = description;
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getService_cost() {
        return service_cost;
    }

    public void setService_cost(String service_cost) {
        this.service_cost = service_cost;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

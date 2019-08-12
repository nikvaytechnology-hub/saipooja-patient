package com.nikvay.saipooja_patient.model;

import java.io.Serializable;

public class PaymentDetailModel implements Serializable {

    String name;
    String s_name;
    String date;
    String hospital_charges;
    String comment_payment;
    String service_cost;
    String total_amount;
    String description;

    String patient_id;
    String payment_id;

    String service_id;
    String label;

    public PaymentDetailModel(String patient_id, String payment_id, String name, String hospital_charges, String comment_payment, String total_amount,
                              String date, String service_id, String s_name, String service_cost, String description, String label) {
        this.patient_id = patient_id;
        this.payment_id = payment_id;
        this.name = name;
        this.hospital_charges = hospital_charges;
        this.comment_payment = comment_payment;
        this.total_amount = total_amount;
        this.date = date;
        this.service_id = service_id;
        this.s_name = s_name;
        this.service_cost = service_cost;
        this.description = description;
        this.label = label;
    }



    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospital_charges() {
        return hospital_charges;
    }

    public void setHospital_charges(String hospital_charges) {
        this.hospital_charges = hospital_charges;
    }

    public String getComment_payment() {
        return comment_payment;
    }

    public void setComment_payment(String comment_payment) {
        this.comment_payment = comment_payment;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}

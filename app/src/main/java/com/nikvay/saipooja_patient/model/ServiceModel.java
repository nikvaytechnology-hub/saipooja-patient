package com.nikvay.saipooja_patient.model;

import java.io.Serializable;

public class ServiceModel implements Serializable {

    String service_id;
    String s_name;
    String service_cost;
    String service_time;
    String description;

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




    public ServiceModel(String service_id, String s_name, String service_cost, String service_time, String description) {
        this.service_id = service_id;
        this.s_name = s_name;
        this.service_cost = service_cost;
        this.service_time = service_time;
        this.description = description;
    }



}

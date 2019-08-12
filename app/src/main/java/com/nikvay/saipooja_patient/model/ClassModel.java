package com.nikvay.saipooja_patient.model;

import java.io.Serializable;

public class ClassModel implements Serializable {

    String class_id;
    String name;
    String cost;
    String duration;
    String description;
    String seats;
    String date;


    public ClassModel(String class_id, String name, String cost, String duration, String description,
                      String seats, String date) {
        this.class_id = class_id;
        this.name = name;
        this.cost = cost;
        this.duration = duration;
        this.description = description;
        this.seats = seats;
        this.date = date;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

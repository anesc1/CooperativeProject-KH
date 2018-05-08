package com.example.gogoooma.cooperativeproject;

public class Place {
    String admin;
    String place;
    String day;
    String startHour;
    String startMin;
    String endHour;
    String endMin;

    public Place(String admin, String place, String day, String startHour, String startMin, String endHour, String endMin) {
        this.admin = admin;
        this.place = place;
        this.day = day;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getStartMin() {
        return startMin;
    }

    public void setStartMin(String startMin) {
        this.startMin = startMin;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndMin() {
        return endMin;
    }

    public void setEndMin(String endMin) {
        this.endMin = endMin;
    }
}

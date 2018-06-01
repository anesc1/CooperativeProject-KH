package com.example.gogoooma.cooperativeproject;

import java.util.List;

public class Place {
    Member admin;
    String place;
    String day;
    int startHour;
    int startMin;
    int endHour;
    int endMin;
    double posX;
    double posY;
    List<Member> teams;

    public Place(Member admin, String place, String day, int startHour, int startMin, int endHour, int endMin, double posX, double posY, List<Member> teams) {
        this.admin = admin;
        this.place = place;
        this.day = day;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.posX = posX;
        this.posY = posY;
        this.teams = teams;
    }

    public Member getAdmin() {
        return admin;
    }

    public void setAdmin(Member admin) {
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

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public List<Member> getTeams() {
        return teams;
    }

    public void setTeams(List<Member> teams) {
        this.teams = teams;
    }
}

package com.example.gogoooma.cooperativeproject;

import java.io.Serializable;

public class Project implements Serializable{
    String projectName;
    int projectNum;
    int teamNum;
    int agenda;

    public Project(String projectName, int projectNum, int agenda, int teamNum) {
        this.projectName = projectName;
        this.projectNum = projectNum;
        this.agenda = agenda;
        this.teamNum = teamNum;
    }

    public int getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(int projectNum) {
        this.projectNum = projectNum;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getAgenda() {
        return agenda;
    }

    public void setAgenda(int agenda) {
        this.agenda = agenda;
    }
}

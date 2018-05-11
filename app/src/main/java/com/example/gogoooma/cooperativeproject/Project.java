package com.example.gogoooma.cooperativeproject;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable{
    String projectName;
    String projectNum;
    String teamNum;

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    List<String> agenda;

    public Project(String projectName, String projectNum, List<String> agenda, String teamNum) {
        this.projectName = projectName;
        this.projectNum = projectNum;
        this.agenda = agenda;
        this.teamNum = teamNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public List<String> getAgenda() {
        return agenda;
    }

    public void setAgenda(List<String> agenda) {
        this.agenda = agenda;
    }
}

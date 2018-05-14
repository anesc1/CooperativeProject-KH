package com.example.gogoooma.cooperativeproject;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable {
    List<Member> members;
    List<Place> places;
    String teamName;
    int teamNum;
    Member leader;
    int agendaNum;
    List<String> agenda;

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public int getAgendaNum() {
        return agendaNum;
    }

    public void setAgendaNum(int agendaNum) {
        this.agendaNum = agendaNum;
    }

    public List<String> getAgenda() {
        return agenda;
    }

    public void setAgenda(List<String> agenda) {
        this.agenda = agenda;
    }

    String projectName;



    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Team(List<String> agenda, String projectName, List<Member> members, List<Place> places, String teamName, int teamNum, Member leader) {
        this.members = members;
        this.places = places;
        this.teamName = teamName;
        this.teamNum = teamNum;
        this.leader = leader;
        this.agenda = agenda;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Member getLeader() {
        return leader;
    }

    public void setLeader(Member leader) {
        this.leader = leader;
    }
}

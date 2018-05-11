package com.example.gogoooma.cooperativeproject;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable {
    List<Member> members;
    List<Place> places;
    String teamName;
    String teamNum;
    Member leader;
    String agenda;
    String projectName;

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Team(String agenda, String projectName, List<Member> members, List<Place> places, String teamName, String teamNum, Member leader) {
        this.members = members;
        this.places = places;
        this.teamName = teamName;
        this.teamNum = teamNum;
        this.leader = leader;
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

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public Member getLeader() {
        return leader;
    }

    public void setLeader(Member leader) {
        this.leader = leader;
    }
}

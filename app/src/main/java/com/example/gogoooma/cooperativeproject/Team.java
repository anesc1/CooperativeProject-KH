package com.example.gogoooma.cooperativeproject;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable {
    List<Member> members;
    List<Place> places;
    String teamName;
    int teamNum;
    Member leader;
    Member admin;

    public Team(List<Member> members, List<Place> places, String teamName, int teamNum, Member leader, Member admin) {
        this.members = members;
        this.places = places;
        this.teamName = teamName;
        this.teamNum = teamNum;
        this.leader = leader;
        this.admin = admin;
    }

    public Member getAdmin() {
        return admin;
    }

    public void setAdmin(Member admin) {
        this.admin = admin;
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

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public Member getLeader() {
        return leader;
    }

    public void setLeader(Member leader) {
        this.leader = leader;
    }
}

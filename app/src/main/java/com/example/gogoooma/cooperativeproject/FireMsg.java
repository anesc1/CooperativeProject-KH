package com.example.gogoooma.cooperativeproject;

import java.util.ArrayList;

public class FireMsg {
    String teamNum;
    ArrayList<String> title;
    ArrayList<String> content;
    ArrayList<String> members;

    public FireMsg(String teamNum, ArrayList<String> title, ArrayList<String> content, ArrayList<String> members) {
        this.teamNum = teamNum;
        this.title = title;
        this.content = content;
        this.members = members;
    }

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
}

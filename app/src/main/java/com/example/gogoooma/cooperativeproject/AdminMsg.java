package com.example.gogoooma.cooperativeproject;

import java.util.ArrayList;

public class AdminMsg {
    String admin;
    String check;
    String teamNum;
    ArrayList<String> pNames;
    ArrayList<String> pAgendas;
    ArrayList<String> checking;

    public AdminMsg(String admin, String check, String teamNum, ArrayList<String> pNames, ArrayList<String> pAgendas, ArrayList<String> checking) {
        this.admin = admin;
        this.check = check;
        this.teamNum = teamNum;
        this.pNames = pNames;
        this.pAgendas = pAgendas;
        this.checking = checking;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
    }

    public ArrayList<String> getpNames() {
        return pNames;
    }

    public void setpNames(ArrayList<String> pNames) {
        this.pNames = pNames;
    }

    public ArrayList<String> getpAgendas() {
        return pAgendas;
    }

    public void setpAgendas(ArrayList<String> pAgendas) {
        this.pAgendas = pAgendas;
    }

    public ArrayList<String> getChecking() {
        return checking;
    }

    public void setChecking(ArrayList<String> checking) {
        this.checking = checking;
    }
}

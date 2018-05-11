package com.example.gogoooma.cooperativeproject;

import android.renderscript.Byte4;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Member implements Serializable {
    String name;
    int age;
    String phoneNum;
    String password;
    Boolean admin;
    List<String> team;

    public Member(String name, int age, String phoneNum, String password, Boolean admin, List<String> team) {
        this.name = name;

        this.age = age;
        this.phoneNum = phoneNum;
        this.password = password;
        this.admin = admin;
        this.team = team;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getTeam() {
        return team;
    }

    public void addTeam(String teamName){
        this.team.add(teamName);
    }
    public void setTeam(List<String> team) {
        this.team = team;
    }
}

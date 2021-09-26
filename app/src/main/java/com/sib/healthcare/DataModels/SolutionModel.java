package com.sib.healthcare.DataModels;

import com.google.firebase.Timestamp;

public class SolutionModel {
   private String id, name , profile , solution,type ;
   private   Timestamp timestamp;

    public SolutionModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SolutionModel(String id, String name, String profile, String solution, Timestamp timestamp) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.solution = solution;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

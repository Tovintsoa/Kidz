package com.m1p9.kidz.model;

public class Video {
    private String id, vName,vUrl;

    public Video(String id, String vName , String vUrl){
        this.id = id;
        this.vName = vName;
        this.vUrl = vUrl;
    }

    public String getId() {
        return id;
    }

    public String getvName() {
        return vName;
    }

    public String getvUrl() {
        return vUrl;
    }
}

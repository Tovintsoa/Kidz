package com.m1p9.kidz.model;

public class Category {
    private String id, cName,cDescription,cImage;

    public Category(String id, String cName, String cDescription, String cImage){
        this.id = id;
        this.cName = cName;
        this.cDescription = cDescription;
        this.cImage = cImage;
    }

    public String getId() {
        return id;
    }

    public String getcName() {
        return cName;
    }

    public String getcDescription() {
        return cDescription;
    }

    public String getcImage() {
        return cImage;
    }
}

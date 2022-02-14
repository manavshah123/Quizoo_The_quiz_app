package com.app.quizoofinal.model;

public class Categorymodel {
    private String name,nameimage;

    public Categorymodel( String name,String nameimage) {
        this.nameimage = nameimage;
        this.name = name;
    }

    public Categorymodel(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameimage() {
        return nameimage;
    }

    public void setImageurl(String imageurl) {
        this.nameimage = imageurl;
    }
}

package com.app.quizoofinal.model;

public class SubCategorymodel {
    String name,nameimage;

    public SubCategorymodel(String name, String image) {
        this.name = name;
        this.nameimage = image;
    }

    public SubCategorymodel(){

    }

    public String getNameimage() {
        return nameimage;
    }

    public void setNameimage(String nameimage) {
        this.nameimage = nameimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

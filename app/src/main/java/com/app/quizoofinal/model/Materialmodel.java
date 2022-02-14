package com.app.quizoofinal.model;

public class Materialmodel {
    private String name,nameimage,pdfurl;

    public Materialmodel(String name, String nameimage, String pdfurl) {
        this.nameimage = nameimage;
        this.name = name;
        this.pdfurl = pdfurl;
    }

    public Materialmodel(){
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

    public void setNameimage(String nameimage) {
        this.nameimage = nameimage;
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }
}



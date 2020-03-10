package com.example.mobilecomputing1;

import java.util.Date;

public class Data {
    int id;
    String about;

    public Data(int id, String about) {
        this.id = id;
        this.about = about;
    }
    public String toString(){
        return ""+id+"-"+about;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}

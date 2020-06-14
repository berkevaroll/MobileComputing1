package com.example.mobilecomputing1;

public class MyMarker {
    private int id,user_id;
    private String lng, lat, title, description, img_url;

    public MyMarker(int id, String lng, String lat, String title, String description, String img_url, int user_id) {
        this.id = id;
        this.lng = lng;
        this.lat = lat;
        this.title = title;
        this.description = description;
        if(img_url == null){
            this.img_url = "resim";
        }
        else{
            this.img_url = img_url;
        }

        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImg_url() {
        return img_url;
    }

    public int getUser_id() {
        return user_id;
    }
}

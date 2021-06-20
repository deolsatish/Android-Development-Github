package com.example.restaurantmapapp2.model;

public class Place {
    private int place_id;
    private String place_name;
    private String place_lat;
    private String place_long;

    public Place(String place_name, String place_lat, String place_long) {
        this.place_name = place_name;
        this.place_lat = place_lat;
        this.place_long = place_long;
    }

    public Place() {
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPlace_lat() {
        return place_lat;
    }

    public void setPlace_lat(String place_lat) {
        this.place_lat = place_lat;
    }

    public String getPlace_long() {
        return place_long;
    }

    public void setPlace_long(String place_long) {
        this.place_long = place_long;
    }
}

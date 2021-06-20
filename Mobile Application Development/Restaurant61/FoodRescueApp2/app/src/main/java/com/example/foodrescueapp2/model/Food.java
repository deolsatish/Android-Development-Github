package com.example.foodrescueapp2.model;

public class Food {
    private int food_id;
    private String user_name;
    private String title;
    private String description;
    private String date;
    private String pickuptime;
    private String quantity;
    private String location;
    private byte[] image;

    public Food(String user_name,String title, String description, String date, String pickuptime, String quantity, String location, byte[] image) {
        this.user_name=user_name;
        this.title = title;
        this.description = description;
        this.date = date;
        this.pickuptime = pickuptime;
        this.quantity = quantity;
        this.location = location;
        this.image = image;
    }

    public Food() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

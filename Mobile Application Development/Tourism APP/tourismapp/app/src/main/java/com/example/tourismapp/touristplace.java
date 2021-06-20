package com.example.tourismapp;

public class touristplace {
    private int id,image;
    private String title,ppa,description,address;
    //ppa=people visting per annum

    public touristplace(int id, int image, String title, String ppa, String description, String address) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.ppa = ppa;
        this.description = description;
        this.address=address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPpa() {
        return ppa;
    }

    public void setPpa(String ppa) {
        this.ppa = ppa;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

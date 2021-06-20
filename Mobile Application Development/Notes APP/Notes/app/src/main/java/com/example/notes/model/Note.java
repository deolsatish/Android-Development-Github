package com.example.notes.model;

public class Note {
    private int user_id;
    private String description;

    public Note(int user_id, String description) {
        this.user_id = user_id;
        this.description = description;
    }

    public Note() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.example.foodrescueapp2.model;

public class Cart {
    private int cart_id;
    private int food_id;

    public Cart( int food_id) {
        this.food_id = food_id;
    }

    public Cart() {
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }
}

package com.example.b2crentacar;

public class Dates {
    private String pickUp,drop;

    public Dates() {
    }

    public Dates(String pickUp, String drop) {
        this.pickUp = pickUp;
        this.drop = drop;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }
}

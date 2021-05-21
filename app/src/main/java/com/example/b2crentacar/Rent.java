package com.example.b2crentacar;

public class Rent {

    private String plateNumber,userId,price,startDate,endDate;

    public Rent(){

    }

    public Rent(String plateNumber, String userId, String price, String startDate, String endDate) {
        this.plateNumber = plateNumber;
        this.userId = userId;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

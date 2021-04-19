package com.example.b2crentacar;

import java.util.ArrayList;
import java.util.Date;

public class Car {

    private String PlateNumber,Brand,Model,Price,Year,type,fueltype,gear,photo;
    private ArrayList<RentDate> dates;

    public Car(String plateNumber, String brand, String model, String price, String year, String type, String fueltype, String gear,String photo) {
        PlateNumber = plateNumber;
        Brand = brand;
        Model = model;
        Price = price;
        Year = year;
        this.type = type;
        this.fueltype = fueltype;
        this.gear = gear;
        this.photo=photo;
        dates=new ArrayList<>();
    }

    public ArrayList<RentDate> getDates() {
        return dates;
    }

    public void setDates(ArrayList<RentDate> dates) {
        this.dates = dates;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPlateNumber() {
        return PlateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFueltype() {
        return fueltype;
    }

    public void setFueltype(String fueltype) {
        this.fueltype = fueltype;
    }

    public String getGear() {
        return gear;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }
}

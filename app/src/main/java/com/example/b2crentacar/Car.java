package com.example.b2crentacar;

import java.util.ArrayList;
import java.util.Date;

public class Car {

    private String PlateNumber,Brand,Model,Price,Year,Type,Fueltype,Gear,Photo;
    private ArrayList<RentDate> dates;

    public Car() {
    }

    public Car(String plateNumber, String brand, String model, String price, String year, String type, String fueltype, String gear, String photo) {
        PlateNumber = plateNumber;
        Brand = brand;
        Model = model;
        Price = price;
        Year = year;
        Type = type;
        Fueltype = fueltype;
        Gear = gear;
        Photo=photo;
        dates=new ArrayList<>();
    }

    public ArrayList<RentDate> getDates() {
        return dates;
    }

    public void setDates(ArrayList<RentDate> dates) {
        this.dates = dates;
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
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getFueltype() {
        return Fueltype;
    }

    public void setFueltype(String fueltype) {
        Fueltype = fueltype;
    }

    public String getGear() {
        return Gear;
    }

    public void setGear(String gear) {
        Gear = gear;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}

package com.example.b2crentacar;

import java.util.ArrayList;
import java.util.Comparator;
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

    public static Comparator<Car> compareToYear = new Comparator<Car>() {

        public int compare(Car c1, Car c2)
        {

            int car1
                    =Integer.parseInt(c1.getYear());
            int car2
                    = Integer.parseInt(c2.getYear());
            return car2-car1;

        }

    };

    public static Comparator<Car> compareToPrice = new Comparator<Car>() {

        public int compare(Car c1, Car c2)
        {

            int car1
                =Integer.parseInt(c1.getPrice());
            int car2
                    = Integer.parseInt(c2.getPrice());
            return car2-car1;
        }

    };

    public static Comparator<Car> compareToBrand = new Comparator<Car>() {

        public int compare(Car c1, Car c2)
        {

            String car1
                    =c1.getBrand().toUpperCase();
            String car2
                    = c2.getBrand().toUpperCase();

            return car1.compareTo(
                    car2);


        }

    };


}

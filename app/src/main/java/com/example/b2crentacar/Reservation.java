package com.example.b2crentacar;

public class Reservation {
    private String DropoffDate,userId,PickupDate,PlateNumber;

    public Reservation() {
    }

    public Reservation(String dropoffDate, String userId, String pickupDate, String plateNumber) {
        DropoffDate = dropoffDate;
        this.userId = userId;
        PickupDate = pickupDate;
        PlateNumber = plateNumber;
    }

    public String getDropoffDate() {
        return DropoffDate;
    }

    public void setDropoffDate(String dropoffDate) {
        DropoffDate = dropoffDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPickupDate() {
        return PickupDate;
    }

    public void setPickupDate(String pickupDate) {
        PickupDate = pickupDate;
    }

    public String getPlateNumber() {
        return PlateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "DropoffDate='" + DropoffDate + '\'' +
                ", userId='" + userId + '\'' +
                ", PickupDate='" + PickupDate + '\'' +
                ", PlateNumber='" + PlateNumber + '\'' +
                '}';
    }
}

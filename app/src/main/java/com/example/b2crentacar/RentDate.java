package com.example.b2crentacar;

public class RentDate {

    private String pickupDate,dropoffDate;

    public RentDate(String pickupDate, String dropoffDate) {
        this.pickupDate = pickupDate;
        this.dropoffDate = dropoffDate;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDropoffDate() {
        return dropoffDate;
    }

    public void setDropoffDate(String dropoffDate) {
        this.dropoffDate = dropoffDate;
    }
}

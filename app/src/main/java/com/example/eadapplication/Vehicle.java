package com.example.eadapplication;

import java.util.ArrayList;
import java.util.Date;

public class Vehicle {

    private String name;

    private String vehicleNumber;

    private String email;

    private String fuelType;

    private String balance;

    private ArrayList History;

    private Number quantity;

    private String station;

    public Number getQuantity() {
        return quantity;
    }

    public String getStation() {
        return station;
    }

    public Date getDate() {
        return date;
    }

    private Date date;

    public String getName() {
        return name;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getBalance() {
        return balance;
    }
}

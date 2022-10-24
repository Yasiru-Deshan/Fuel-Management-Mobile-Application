package com.example.eadapplication;

import java.util.Date;

public class Station {

    private String stationCode;

    private String name;

    private String location;

    private boolean available;

    private Date arrivalDate;

    private Date finishTime;

    private String arrivalType;

    public String getStationCode() {
        return stationCode;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public boolean isAvailable() {
        return available;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public String getArrivalType() {
        return arrivalType;
    }
}

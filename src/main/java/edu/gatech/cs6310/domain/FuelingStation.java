package edu.gatech.cs6310.domain;

public class FuelingStation {
    private String stationID;
    private int xCoordinate;
    private int yCoordinate;

    public FuelingStation(String stationID, String xCoordinate,String yCoordinate) {
        this.stationID = stationID;
        this.xCoordinate = Integer.parseInt(xCoordinate);
        this.yCoordinate = Integer.parseInt(yCoordinate);
    }

    public String getStationID() {
        return stationID;
    }
    public int getXCoordinate() {
        return xCoordinate;
    }
    public int getYCoordinate() {
        return yCoordinate;
    }
}
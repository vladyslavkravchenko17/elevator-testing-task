package com.elevatortask.model;

public class Passenger {
    private final int destinationFloorNumber;

    public Passenger(int destinationFloorNumber) {
        this.destinationFloorNumber = destinationFloorNumber;
    }

    public int getDestinationFloorNumber() {
        return destinationFloorNumber;
    }
}

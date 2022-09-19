package com.elevatortask.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Floor {
    private final int floorNumber;
    private List<Passenger> passengers;
    private List<Passenger> passengersArrivedOnFloor = new ArrayList<>();

    public Floor(int floorNumber, List<Passenger> passengers) {
        this.floorNumber = floorNumber;
        this.passengers = passengers;
    }

    public List<Passenger> getPassengersWithDestinationUp() {
        return passengers.stream()
                .filter(passenger -> passenger.getDestinationFloorNumber() > floorNumber)
                .collect(Collectors.toList());
    }

    public List<Passenger> getPassengersWithDestinationDown() {
        return passengers.stream()
                .filter(passenger -> passenger.getDestinationFloorNumber() < floorNumber)
                .collect(Collectors.toList());
    }


    public int getFloorNumber() {
        return floorNumber;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public List<Passenger> getPassengersArrivedOnFloor() {
        return passengersArrivedOnFloor;
    }

    public void setPassengersArrivedOnFloor(List<Passenger> passengersArrivedOnFloor) {
        this.passengersArrivedOnFloor = passengersArrivedOnFloor;
    }
}

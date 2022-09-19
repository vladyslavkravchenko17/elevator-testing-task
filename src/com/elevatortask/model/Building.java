package com.elevatortask.model;

import java.util.Arrays;

public class Building {
    private Floor[] floors;
    private Elevator elevator;

    public Building(Elevator elevator, Floor[] floors) {
        this.elevator = elevator;
        this.floors = floors;
    }

    public boolean hasPassengers() {
        return Arrays.stream(floors)
                .mapToLong(floor -> floor.getPassengers().size())
                .sum() != 0;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public Floor[] getFloors() {
        return floors;
    }

    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }
}

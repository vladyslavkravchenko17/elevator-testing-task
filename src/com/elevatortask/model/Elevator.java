package com.elevatortask.model;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private final static int CAPACITY = 5;
    private List<Passenger> passengers;
    private int currentFloorNumber;
    private boolean directionUp;

    public Elevator() {
        this.passengers = new ArrayList<>();
        this.currentFloorNumber = 1;
        this.directionUp = true;
    }

    public int emptyPlacesAmount() {
        return CAPACITY - passengers.size();
    }

    public void move() {
        if (directionUp) {
            currentFloorNumber++;
        } else {
            currentFloorNumber--;
        }
    }

    public void changeDirectionToUp() {
        directionUp = true;
    }

    public void changeDirectionToDown() {
        directionUp = false;
    }

    public String getMoveDirection() {
        if (directionUp) {
            return "^";
        } else {
            return "v";
        }
    }

    public int getCurrentFloorNumber() {
        return currentFloorNumber;
    }

    public void setCurrentFloorNumber(int currentFloorNumber) {
        this.currentFloorNumber = currentFloorNumber;
    }

    public int getCapacity() {
        return CAPACITY;
    }

    public boolean isDirectionUp() {
        return directionUp;
    }

    public void setDirectionUp(boolean directionUp) {
        this.directionUp = directionUp;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
}

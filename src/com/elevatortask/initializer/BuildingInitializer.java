package com.elevatortask.initializer;

import com.elevatortask.model.Building;
import com.elevatortask.model.Elevator;
import com.elevatortask.model.Floor;
import com.elevatortask.model.Passenger;

import java.util.ArrayList;
import java.util.List;

public class BuildingInitializer {

    private BuildingInitializer(){}

    public static Building initializeBuilding() {
        Elevator elevator = new Elevator();
        Floor[] floors = initializeFloors();
        return new Building(elevator, floors);
    }

    private static int randomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private static Floor[] initializeFloors() {
        int amountOfFloors = randomInt(20, 5);
        Floor[] floors = new Floor[amountOfFloors];
        for (int currentFloor = 0; currentFloor < amountOfFloors; currentFloor++) {
            floors[currentFloor] = initializeFloor(amountOfFloors, currentFloor + 1);
        }
        return floors;
    }

    private static Floor initializeFloor(int amountOfFloors, int currentFloorNumber){
        int amountOfPassengers = randomInt(10, 0);
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < amountOfPassengers; i++) {
            passengers.add(initializePassenger(amountOfFloors, currentFloorNumber));
        }

        return new Floor(currentFloorNumber, passengers);
    }

    private static Passenger initializePassenger(int amountOfFloors, int currentFloorNumber){
        int destinationFloorNumber = randomInt(1, amountOfFloors - 1);
        if (destinationFloorNumber == currentFloorNumber) {
            destinationFloorNumber++;
        }

        return new Passenger(destinationFloorNumber);
    }
}

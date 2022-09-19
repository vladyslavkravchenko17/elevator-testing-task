package com.elevatortask.service;

import com.elevatortask.model.Building;
import com.elevatortask.model.Floor;
import com.elevatortask.model.Passenger;

import java.util.ArrayList;
import java.util.List;

public class ProcessService {

    private static final String STEP_TEMPLATE = "*** step %d ***";
    private static final String REPORT_TEMPLATE = "%-2s |%s %-18s %s| %s";
    private final Building building;

    public ProcessService(Building building) {
        this.building = building;
    }

    public void run() {
        int step = 0;

        while (building.hasPassengers() || !building.getElevator().getPassengers().isEmpty()) {
            step++;
            System.out.printf((STEP_TEMPLATE) + "%n", step);

            if (building.getElevator().getPassengers().isEmpty()) {
                changeDirectionToFloorWithPassengers();
            }

            passengersEnterElevator();
            moveElevator();
            passengersLeaveElevator();
            printReport();
        }

        System.out.println("All passengers reached their destinations");
    }


    private void moveElevator() {
        building.getElevator().move();

        if (building.getElevator().getCurrentFloorNumber() == building.getFloors().length) {
            building.getElevator().changeDirectionToDown();
        } else if (building.getElevator().getCurrentFloorNumber() == 1) {
            building.getElevator().changeDirectionToUp();
        }
    }

    private void changeDirectionToFloorWithPassengers() {
        int neededFloor = findFloorWithPassengers();
        int currentFloorNumber = building.getElevator().getCurrentFloorNumber();

        if (neededFloor != 0 && currentFloorNumber != 1
                && currentFloorNumber != building.getFloors().length) {
            if (neededFloor > building.getElevator().getCurrentFloorNumber()) {
                building.getElevator().changeDirectionToUp();
            } else {
                building.getElevator().changeDirectionToDown();
            }
        }
    }

    private int findFloorWithPassengers() {
        for (int i = building.getFloors().length - 1; i >= 0; i--) {
            if (!building.getFloors()[i].getPassengers().isEmpty()) {
                return i + 1;
            }
        }
        return 0;
    }

    private void passengersLeaveElevator() {
        int floorNumberIndex = building.getElevator().getCurrentFloorNumber() - 1;
        List<Passenger> passengersEnteredElevator = new ArrayList<>();
        List<Passenger> passengersArrivedToCurrentFloor =
                building.getFloors()[floorNumberIndex].getPassengersArrivedOnFloor();

        building.getElevator().getPassengers().forEach(passenger -> {
            if (passenger.getDestinationFloorNumber() == building.getElevator().getCurrentFloorNumber() - 1) {
                passengersArrivedToCurrentFloor.add(passenger);
            } else {
                passengersEnteredElevator.add(passenger);
            }
        });

        building.getFloors()[floorNumberIndex].setPassengersArrivedOnFloor(passengersArrivedToCurrentFloor);
        building.getElevator().setPassengers(passengersEnteredElevator);
    }

    private void passengersEnterElevator() {
        int floorNumberIndex = building.getElevator().getCurrentFloorNumber() - 1;
        Floor[] floors = building.getFloors();

        if (!floors[floorNumberIndex].getPassengers().isEmpty()) {
            List<Passenger> passengersWithDestinationUp =
                    floors[floorNumberIndex].getPassengersWithDestinationUp();

            List<Passenger> passengersWithDestinationDown =
                    floors[floorNumberIndex].getPassengersWithDestinationDown();

            List<Passenger> passengersToEnterElevator =
                    getPassengersToEnterElevator(passengersWithDestinationUp, passengersWithDestinationDown);

            if (!passengersToEnterElevator.isEmpty()) {
                boardPassengersOnElevator(passengersToEnterElevator);
            }
        }
    }

    private List<Passenger> getPassengersToEnterElevator(List<Passenger> passengersWithDestinationUp,
                                                      List<Passenger> passengersWithDestinationDown) {
        List<Passenger> passengersToEnterElevator = new ArrayList<>();

        if (building.getElevator().getPassengers().isEmpty()) {
            if (passengersWithDestinationUp.size() < passengersWithDestinationDown.size()) {
                passengersToEnterElevator = passengersWithDestinationDown;
                building.getElevator().changeDirectionToDown();
            } else if (passengersWithDestinationUp.size() > passengersWithDestinationDown.size()) {
                passengersToEnterElevator = passengersWithDestinationUp;
                building.getElevator().changeDirectionToUp();
            } else {
                passengersToEnterElevator = passengersWithDestinationUp;
            }
        } else if (building.getElevator().emptyPlacesAmount() > 0) {
            if (building.getElevator().isDirectionUp()) {
                passengersToEnterElevator = passengersWithDestinationUp;
            } else {
                passengersToEnterElevator = passengersWithDestinationDown;
            }
        }

        return passengersToEnterElevator;
    }

    private void boardPassengersOnElevator(List<Passenger> passengersToEnterElevator) {
        int floorNumber = building.getElevator().getCurrentFloorNumber() - 1;
        List<Passenger> passengersInElevator = building.getElevator().getPassengers();
        List<Passenger> passengersOnFloor = building.getFloors()[floorNumber].getPassengers();

        int maxPassengersIn = Math.min(building.getElevator().emptyPlacesAmount(), passengersToEnterElevator.size());
        for (int i = 0; i < maxPassengersIn; i++) {
            passengersInElevator.add(passengersToEnterElevator.get(i));
            passengersOnFloor.remove(passengersToEnterElevator.get(i));
        }

        building.getElevator().setPassengers(passengersInElevator);
        building.getFloors()[floorNumber].setPassengers(passengersOnFloor);
    }

    private void printReport() {
        for (int i = building.getFloors().length - 1; i >= 0; i--) {
            int amountPassengersArrivedOnFloor = building.getFloors()[i].getPassengersArrivedOnFloor().size();
            String elevatorDirection = building.getElevator().getMoveDirection();
            StringBuilder passengersInElevator = new StringBuilder();
            StringBuilder passengersOnFloor = new StringBuilder();

            if (i + 1 == building.getElevator().getCurrentFloorNumber()) {
                building.getElevator().getPassengers().forEach(passenger ->
                        passengersInElevator.append(passenger.getDestinationFloorNumber()).append(" "));
            }

            building.getFloors()[i].getPassengers().forEach(passenger ->
                    passengersOnFloor.append(passenger.getDestinationFloorNumber()).append(" "));

            System.out.printf((REPORT_TEMPLATE) + "%n", amountPassengersArrivedOnFloor, elevatorDirection,
                    passengersInElevator, elevatorDirection, passengersOnFloor);

        }
    }

}
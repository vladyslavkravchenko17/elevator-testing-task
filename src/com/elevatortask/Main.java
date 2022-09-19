package com.elevatortask;

import com.elevatortask.model.Building;
import com.elevatortask.initializer.BuildingInitializer;
import com.elevatortask.service.ProcessService;

public class Main {
    public static void main(String[] args) {
        Building building = BuildingInitializer.initializeBuilding();

        ProcessService processService = new ProcessService(building);

        processService.run();
    }
}

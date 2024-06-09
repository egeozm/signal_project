package com.main;

import com.cardio_generator.HealthDataSimulator;
import com.data_management.DataStorage;


public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("DataStorage")) {
            DataStorage dataStorage = DataStorage.getInstance();
            dataStorage.main(new String[]{});
        } else {
            HealthDataSimulator healthDataSimulator = HealthDataSimulator.getInstance();
            healthDataSimulator.main(new String[]{});
        }
    }
}

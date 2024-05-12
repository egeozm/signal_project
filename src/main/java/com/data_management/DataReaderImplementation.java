package com.data_management;

import com.alerts.Alert;
import com.alerts.AlertStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataReaderImplementation implements DataReader {

    private final String pathToData;

    public DataReaderImplementation(String pathToData) {
        this.pathToData = pathToData;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        String[] fileNames = {"Cholesterol.txt", "DiastolicPressure.txt", "ECG.txt", "RedBloodCells.txt", "Saturation.txt", "SystolicPressure.txt", "WhiteBloodCells.txt"};

        for (String fileName : fileNames) {
            BufferedReader reader = Files.newBufferedReader(Paths.get(pathToData + "/" + fileName));

            String line = reader.readLine();

            while (line != null) {
                String[] lineData = line.split(",");
                int patientId = Integer.parseInt(lineData[0].split(":")[1].trim());
                long timestamp = Long.parseLong(lineData[1].split(":")[1].trim());
                String label = lineData[2].split(":")[1].trim();
                double data = Double.parseDouble(lineData[3].split(":")[1].trim().replace("%", ""));

                dataStorage.addPatientData(patientId, data, label, timestamp);
                line = reader.readLine();
            }
        }

        BufferedReader reader = Files.newBufferedReader(Paths.get(pathToData + "/Alert.txt"));
        String line = reader.readLine();
        AlertStorage alertStorage = AlertStorage.getAlertStorageInstance();

        while (line != null) {
            String[] lineData = line.split(",");
            String patientId = lineData[0].split(":")[1].trim();
            long timestamp = Long.parseLong(lineData[1].split(":")[1].trim());
            String condition = lineData[3].split(":")[1].trim();

            Alert alert = new Alert(patientId, condition, timestamp, "Nurses or patients triggering the alert button");
            alertStorage.alertStore(alert);

            line = reader.readLine();
        }

    }
}

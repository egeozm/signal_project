package com.data_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.alerts.AlertGenerator;

/**
 * Manages storage and retrieval of patient data within a healthcare monitoring
 * system.
 * This class serves as a repository for all patient records, organized by
 * patient IDs.
 */
public class DataStorage {
    private final Map<Integer, Patient> patientMap; // Stores patient objects indexed by their unique patient ID.
    private static ScheduledExecutorService scheduler;
    private static DataStorage instance;

    /**
     * Constructs a new instance of DataStorage, initializing the underlying storage
     * structure.
     */
    public DataStorage() {
        this.patientMap = new HashMap<>();

    }

    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    /**
     * Adds or updates patient data in the storage.
     * If the patient does not exist, a new Patient object is created and added to
     * the storage.
     * Otherwise, the new data is added to the existing patient's records.
     *
     * @param patientId        the unique identifier of the patient
     * @param measurementValue the value of the health metric being recorded
     * @param recordType       the type of record, e.g., "HeartRate",
     *                         "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in
     *                         milliseconds since the Unix epoch
     */
    public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        Patient patient = patientMap.computeIfAbsent(patientId, Patient::new);
        patient.addRecord(measurementValue, recordType, timestamp);
    }

    /**
     * Retrieves a list of PatientRecord objects for a specific patient, filtered by
     * a time range.
     *
     * @param patientId the unique identifier of the patient whose records are to be
     *                  retrieved
     * @param startTime the start of the time range, in milliseconds since the Unix
     *                  epoch
     * @param endTime   the end of the time range, in milliseconds since the Unix
     *                  epoch
     * @return a list of PatientRecord objects that fall within the specified time range
     */
    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>(); // return an empty list if no patient is found
    }

    /**
     * Retrieves a collection of all patients stored in the data storage.
     *
     * @return a list of all patients
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    /**
     * The main method for the DataStorage class.
     * Initializes the system, reads data into storage, and continuously monitors
     * and evaluates patient data.
     *
     * @param args command line arguments
     * @throws Exception if an error occurs during data reading or processing
     */
    public static void main(String[] args) throws Exception {

        // For File
        DataReader reader = new DataReaderImplementation("src/main/java/com/data_management/patient_data");
        DataStorage storage = new DataStorage();
        reader.readData(storage);

        // For WebSocket
        try {
            DataReader dataReader = new WebSocketReaderImplementation();
            dataReader.readData(storage);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize the AlertGenerator with the storage
        AlertGenerator alertGenerator = new AlertGenerator();

        scheduler = Executors.newScheduledThreadPool(1);
        scheduleTask(() -> alertSchedulerTask(storage, alertGenerator));

    }

    /**
     * Task to evaluate data and generate alerts for all patients.
     *
     * @param storage        the DataStorage instance containing patient data
     * @param alertGenerator the AlertGenerator instance used to evaluate and generate alerts
     */
    public static void alertSchedulerTask(DataStorage storage, AlertGenerator alertGenerator) {
        for (Patient patient : storage.getAllPatients()) {
            alertGenerator.evaluateData(patient);
        }
    }

    /**
     * Schedules a recurring task to evaluate patient data and generate alerts.
     *
     * @param task the task to be scheduled
     */
    private static void scheduleTask(Runnable task) {
        scheduler.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
    }
}
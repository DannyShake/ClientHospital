package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.openjfx.Client;
import org.openjfx.MainApp;
import org.openjfx.entity.Doctor;
import org.openjfx.entity.Patient;
import org.openjfx.entity.Procedure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminPatientsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Patient> tableview;

    @FXML
    private TableView<Doctor> tableview2;

    @FXML
    private TableView<Procedure> tableview3;

    @FXML
    private TableColumn<Patient, Integer> patientsId;

    @FXML
    private TableColumn<Patient, String> patientsName;

    @FXML
    private TableColumn<Patient, String> patientsAge;

    @FXML
    private TableColumn<Patient, String> patientsGender;

    @FXML
    private TableColumn<Doctor, Integer> doctorsId;

    @FXML
    private TableColumn<Doctor, String> doctorsName;

    @FXML
    private TableColumn<Doctor, String> doctorsGender;

    @FXML
    private TableColumn<Procedure, Integer> procedursId;

    @FXML
    private TableColumn<Procedure, String> procedursName;

    @FXML
    private TableColumn<Procedure, Integer> procedursCost;

    @FXML
    private Button goBackButton;

    @FXML
    void initialize() {
        viewPatient();
        viewDoctors();
        viewProcedurrs();
    }

    public void savePatients() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранение списка поставок");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                BufferedWriter outWriter = new BufferedWriter(new FileWriter(file));
                for (Patient patient : tableview.getItems()) {
                    outWriter.write(patient.toString());
                    outWriter.newLine();
                }
                System.out.println(tableview.getItems().toString());
                outWriter.close();
            } catch (IOException e) {
                System.out.println("Ошибка записи файла!");
            }
        }
    }

    public void saveDoctors() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранение списка поставок");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                BufferedWriter outWriter = new BufferedWriter(new FileWriter(file));
                for (Doctor doctor : tableview2.getItems()) {
                    outWriter.write(doctor.toString());
                    outWriter.newLine();
                }
                System.out.println(tableview2.getItems().toString());
                outWriter.close();
            } catch (IOException e) {
                System.out.println("Ошибка записи файла!");
            }
        }
    }

    public void saveProcedures() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранение списка поставок");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                BufferedWriter outWriter = new BufferedWriter(new FileWriter(file));
                for (Procedure procedure : tableview3.getItems()) {
                    outWriter.write(procedure.toString());
                    outWriter.newLine();
                }
                System.out.println(tableview3.getItems().toString());
                outWriter.close();
            } catch (IOException e) {
                System.out.println("Ошибка записи файла!");
            }
        }
    }

    private void viewDoctors() {
        Client client = new Client();
        client.connect();
        final Map<String, String> command = new HashMap<>();
        command.put("type", "DoctorList");
        client.send(command);

        Map<String, Object> temp = client.accept();
        ObservableList<Doctor> doctors = FXCollections.observableArrayList();


        ArrayList<Doctor> doctorsFromServer = (ArrayList<Doctor>) temp.get("doctors");
        doctors.addAll(doctorsFromServer);


        doctorsId.setCellValueFactory(new PropertyValueFactory<>("idDoctor"));
        doctorsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        doctorsGender.setCellValueFactory(new PropertyValueFactory<>("specialization"));

        tableview2.setItems(doctors);
    }

    private void viewProcedurrs() {
        Client client = new Client();
        client.connect();
        final Map<String, String> command = new HashMap<>();
        command.put("type", "ProcedurList");
        client.send(command);

        Map<String, Object> temp = client.accept();
        ObservableList<Procedure> procedures = FXCollections.observableArrayList();


        ArrayList<Procedure> proceduresFromServer = (ArrayList<Procedure>) temp.get("procedures");
        procedures.addAll(proceduresFromServer);


        procedursId.setCellValueFactory(new PropertyValueFactory<>("idProcedur" +
                ""));
        procedursCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        procedursName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableview3.setItems(procedures);
    }

    public void viewPatient() {
        Client client = new Client();
        client.connect();
        final Map<String, String> command = new HashMap<>();
        command.put("type", "PatientList");
        client.send(command);

        Map<String, Object> temp = client.accept();
        ObservableList<Patient> patients = FXCollections.observableArrayList();


        ArrayList<Patient> patientsFromServer = (ArrayList<Patient>) temp.get("patients");
        patients.addAll(patientsFromServer);


        patientsId.setCellValueFactory(new PropertyValueFactory<>("idPatient"));
        patientsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientsAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        patientsGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        tableview.setItems(patients);
    }

    public void openNewScene(String window) {
        goBackButton.getScene().getWindow().hide();

        Parent root = null;
        try {
            root = MainApp.loadFXML(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void goBack() {
        openNewScene("adminSite");
    }
}
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
import javafx.stage.Stage;
import org.openjfx.Client;
import org.openjfx.MainApp;
import org.openjfx.entity.Doctor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminDoctorsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Doctor> tableview;

    @FXML
    private TableColumn<Doctor, Integer> doctorsId;

    @FXML
    private TableColumn<Doctor, String> doctorsName;

    @FXML
    private TableColumn<Doctor, String> doctorsGender;

    @FXML
    private Button delDoctorsAdmin;

    @FXML
    private Button addDoctorsAdmin;

    @FXML
    private Button goBackButton;

    @FXML
    void initialize() {
        viewDoctors();
    }

    public void viewDoctors() {

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

        tableview.setItems(doctors);
    }

    public void addDoctorAdmin() {
        openNewScene("adminAddDoctor");
    }

    public void delDoctor() {
        if (tableview.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Ошибка! Выберите строку!");
        } else {
            Client client = new Client();
            client.connect();
            final Map<String, String> command = new HashMap<>();
            command.put("type", "delDoctor");
            command.put("idDoctor", String.valueOf(tableview.getSelectionModel().getSelectedItem().getIdDoctor()));
            client.send(command);

            Map<String, Object> temp = client.accept();

            if ((Boolean) temp.get("result")) viewDoctors();
            else System.out.println("Ошибка!");
        }
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

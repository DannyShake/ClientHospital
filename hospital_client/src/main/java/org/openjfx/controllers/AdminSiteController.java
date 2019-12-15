package org.openjfx.controllers;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.openjfx.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSiteController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button patientsButton;

    @FXML
    private Button proceduresButton;

    @FXML
    private Button doctorButton;

    public void showProcedures() {
        openNewScene("adminProcedures");
    }

    public void showDoctors() {
        openNewScene("adminDoctors");
    }

    public void showPatients() {

        openNewScene("adminPatients");
    }

    public void showPribil() {
        openNewScene("pribil");
    }

    public void openNewScene(String window) {
        patientsButton.getScene().getWindow().hide();

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
        openNewScene("sample");
    }

}

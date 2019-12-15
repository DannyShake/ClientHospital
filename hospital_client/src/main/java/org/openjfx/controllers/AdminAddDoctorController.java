package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.Client;
import org.openjfx.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminAddDoctorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddDoctorButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private Button back;

    @FXML
    private ChoiceBox<String> choiceBox;


    @FXML
    void initialize() {
        choiceBox.getItems().addAll("Лор", "Невролог", "Стоматолог", "Офтальмолог");
        choiceBox.getSelectionModel().select("Лор");
    }

    public void openNewScene(String window) {
        back.getScene().getWindow().hide();

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
        openNewScene("adminDoctors");
    }

    public void addDoctor() {
        Client client = new Client();
        client.connect();
        String name = nameField.getText().trim();
        String lastname = lastnameField.getText().trim();
        String specialization = choiceBox.getValue();
        if (!name.isEmpty() || !lastname.isEmpty()) {
            final Map<String, String> command = new HashMap<>();
            command.put("type", "AddDoctor");
            command.put("name", name);
            command.put("lastname", lastname);
            command.put("specialization", specialization);

            client.send(command);

            Map<String, Object> temp = client.accept();
            Boolean addOK = (Boolean) temp.get("result");
            if (addOK) {
                openNewScene("adminDoctors");
            } else {
                System.out.println("Ошибка!");
            }
        } else System.out.println("Поля пусты!");
    }
}

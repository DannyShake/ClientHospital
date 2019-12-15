package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.Client;
import org.openjfx.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminAddProceduresController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddProcedureButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField costField;

    @FXML
    private Button back;

    public void addProcedure() {
        Client client = new Client();
        client.connect();
        String name = nameField.getText().trim();
        String cost = costField.getText().trim();
        if (!name.isEmpty() || !cost.isEmpty()) {
            final Map<String, String> command = new HashMap<>();
            command.put("type", "AddProcedures");
            command.put("name", name);
            command.put("cost", cost);

            client.send(command);

            Map<String, Object> temp = client.accept();
            Boolean addOK = (Boolean) temp.get("result");
            if (addOK) {
                openNewScene("adminProcedures");
            } else {
                System.out.println("Ошибка!");
            }
        } else System.out.println("Поля пусты!");
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
        openNewScene("adminProcedures");
    }

    @FXML
    void initialize() {

    }
}

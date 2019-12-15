package org.openjfx.controllers;

import javafx.event.ActionEvent;
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

public class UserAddRecordController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddRecordButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private Button back;

    @FXML
    private TextField numberField;

    @FXML
    void addRecord(ActionEvent event) {
        Client client = new Client();
        client.connect();
        String name = nameField.getText().trim() + " " + lastnameField.getText().trim();
        String number = numberField.getText().trim();
        if (!name.isEmpty() && !number.isEmpty()) {
            final Map<String, String> command = new HashMap<>();
            command.put("type", "AddRecord");
            command.put("name", name);
            command.put("number", number);

            client.send(command);

            Map<String, Object> temp = client.accept();
            Boolean addOK = (Boolean) temp.get("result");
            if (addOK) {
                openNewScene("userProcedure");
            } else {
                System.out.println("Ошибка!");
            }
        } else System.out.println("Поля пусты!");
    }

    @FXML
    void goBack(ActionEvent event) {
        openNewScene("userProcedure");
    }

    @FXML
    void initialize() {

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

}

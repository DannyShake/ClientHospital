package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.Client;
import org.openjfx.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class WindowSingUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonSingUp;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField ageField;

    @FXML
    void initialize() {
        initializeTextField();
        Client client = new Client();
        client.connect();

        buttonSingUp.setOnAction(event -> {
            try {
                int age = getAgeFromForm();
                String gender = genderField.getText();
                String login = loginField.getText().trim();
                String password = passwordField.getText().trim();
                String name = nameField.getText().trim();
                String lastname = lastnameField.getText().trim();

                if (!(gender.contains("муж") || gender.contains("жен"))) throw new Exception();

                if (!(login.isEmpty() || password.isEmpty() || name.isEmpty() || lastname.isEmpty())) {
                    final Map<String, String> command = new HashMap<>();
                    command.put("type", "SignUpUser");
                    command.put("login", login);
                    command.put("password", password);
                    command.put("name", name);
                    if (gender.contains("муж")) command.put("gender", "male");
                    if (gender.contains("жен")) command.put("gender", "female");
                    command.put("lastname", lastname);
                    //command.put("gender", gender);
                    command.put("age", ageField.getText());

                    client.send(command);

                    Map<String, Object> temp = client.accept();
                    Boolean signUpOK = (Boolean) temp.get("result");
                    if (signUpOK) {
                        openNewScene("sample");
                    } else {
                        System.out.println("Такой логин уже существует!");
                    }
                } else System.out.println("Поля не заполнены!");

            } catch (NumberFormatException ex) {
                ageField.setStyle("-fx-background-color: red;");
                return;
            } catch (Exception ex) {
                genderField.setStyle("-fx-background-color: red;");
                return;
            }
        });
    }

    private void initializeTextField() {
        ageField.focusedProperty().addListener((observable, oldValue, newValue) -> ageField.setStyle("-fx-background-color: white;"));
        genderField.focusedProperty().addListener((observable, oldValue, newValue) -> genderField.setStyle("-fx-background-color: white;"));
    }

    private int getAgeFromForm() {
        String ageString = ageField.getText();
        return Integer.valueOf(ageString);
    }

    public void openNewScene(String window) {
        buttonSingUp.getScene().getWindow().hide();

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

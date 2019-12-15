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

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonSignUp;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button buttonSignIn;

    @FXML
    void initialize() {

        Client client = new Client();
        client.connect();

        buttonSignUp.setOnAction(event -> {
            openNewScene("windowSingUp");
        });

        buttonSignIn.setOnAction(event -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();

            if (!login.isEmpty() && !password.isEmpty()) {
                final Map<String, String> command = new HashMap<>();
                command.put("type", "loginUser");
                command.put("login", login);
                command.put("password", password);

                client.send(command);


                Map<String, Object> temp = client.accept();
                Boolean loginOK = (Boolean) temp.get("result");
                String admin = (String) temp.get("login");
                System.out.println(admin);

                if (loginOK) {
                    //openNewScene("/sample/GUI/adminSite.fxml");
                    if (admin.contains("admin")) openNewScene("adminSite");
                    else openNewScene("userSite");
                } else {
                    System.out.println("Неправильный логин или пароль!");
                }
            } else System.out.println("Поля не заполнены!");
        });
    }

    public void openNewScene(String window) {
        buttonSignUp.getScene().getWindow().hide();


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

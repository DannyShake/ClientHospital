package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.openjfx.Client;
import org.openjfx.MainApp;
import org.openjfx.entity.Procedure;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PribilController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label pibilText;

    @FXML
    private Button back;

    @FXML
    void initialize() {
        Client client = new Client();
        client.connect();
        final Map<String, String> command = new HashMap<>();
        command.put("type", "ProcedurList");
        client.send(command);

        Map<String, Object> temp = client.accept();
        ArrayList<Procedure> proceduresFromServer = (ArrayList<Procedure>) temp.get("procedures");

        int counter = 0;

        for (int i = 0; i < proceduresFromServer.size(); i++) {
            counter += proceduresFromServer.get(i).getCost();
        }
        String counterString = String.valueOf(counter);
        pibilText.setText(counterString + " рупий");

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
        openNewScene("adminSite");
    }
}

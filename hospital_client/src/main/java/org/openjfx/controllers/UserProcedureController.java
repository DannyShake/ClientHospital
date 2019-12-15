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
import org.openjfx.entity.Procedure;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UserProcedureController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Procedure> tableview;

    @FXML
    private TableColumn<Procedure, Integer> procedureId;

    @FXML
    private TableColumn<Procedure, String> procedureName;

    @FXML
    private TableColumn<Procedure, Integer> procedureCost;

    @FXML
    private Button goBackButton;

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

    public void addRecord() {
        openNewScene("userAddRecord");
    }

    public void goBack() {
        openNewScene("userSite");
    }


    private void viewProcedurs() {
        Client client = new Client();
        client.connect();
        final Map<String, String> command = new HashMap<>();
        command.put("type", "ProcedurList");
        client.send(command);

        Map<String, Object> temp = client.accept();
        ObservableList<Procedure> procedures = FXCollections.observableArrayList();


        ArrayList<Procedure> proceduresFromServer = (ArrayList<Procedure>) temp.get("procedures");
        procedures.addAll(proceduresFromServer);


        procedureId.setCellValueFactory(new PropertyValueFactory<>("idProcedur"));
        procedureCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        procedureName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableview.setItems(procedures);
    }

    @FXML
    void initialize() {
        viewProcedurs();
    }
}

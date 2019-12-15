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

public class AdminProceduresController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Procedure> tableview;

    @FXML
    private TableColumn<Procedure, Integer> procedursId;

    @FXML
    private TableColumn<Procedure, String> procedursName;

    @FXML
    private TableColumn<Procedure, Integer> procedursCost;

    @FXML
    private Button delProcedursAdmin;

    @FXML
    private Button addProcedursAdmin;

    @FXML
    private Button goBackButton;


    public void addProcedure() {
        openNewScene("adminAddProcedures");
    }

    public void delProcedure() {
        if (tableview.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Ошибка! Выберите строку!");
        } else {
            Client client = new Client();
            client.connect();
            final Map<String, String> command = new HashMap<>();
            command.put("type", "delProcedure");
            command.put("idProcedure", String.valueOf(tableview.getSelectionModel().getSelectedItem().getIdProcedur()));
            client.send(command);

            Map<String, Object> temp = client.accept();

            if ((Boolean) temp.get("result")) viewProcedurs();
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


        procedursId.setCellValueFactory(new PropertyValueFactory<>("idProcedur"));
        procedursCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        procedursName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableview.setItems(procedures);
    }

    @FXML
    void initialize() {
        viewProcedurs();
    }
}

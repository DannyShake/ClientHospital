package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.openjfx.entity.Room;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class UserRecordController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Room> tableview;

    @FXML
    private TableColumn<Room, Integer> roomNumber;

    @FXML
    private TableColumn<Room, String> procedureName;

    @FXML
    private TableColumn<Room, String> patientName;

    @FXML
    private Button goBackButton;

    @FXML
    void goBack(ActionEvent event) {
        openNewScene("userSite");
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

    public void viewProcedurs() {
        Client client = new Client();
        client.connect();
        final Map<String, String> command = new HashMap<>();
        command.put("type", "RoomList");
        client.send(command);

        Map<String, Object> temp = client.accept();
        ObservableList<Room> rooms = FXCollections.observableArrayList();

        ArrayList<Room> roomsFromServer = (ArrayList<Room>) temp.get("rooms");
        rooms.addAll(roomsFromServer);


        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        procedureName.setCellValueFactory(new PropertyValueFactory<>("procedureName"));
        patientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        tableview.setItems(rooms);
    }

    @FXML
    void initialize() {
        viewProcedurs();
    }
}

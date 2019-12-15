package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import org.openjfx.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DiagramController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart diagram;

    @FXML
    void initialize() {
        ObservableList<PieChart.Data> piechartData = FXCollections.observableArrayList(
                new PieChart.Data("Болезни органов дыхания", 30),
                new PieChart.Data("Последствия внещних причин", 13),
                new PieChart.Data("Болезни мочеполовой системы", 10),
                new PieChart.Data("Болезни системы кровообращения", 8),
                new PieChart.Data("Болезни кожи", 7),
                new PieChart.Data("Прочие", 32));
        diagram.setData(piechartData);
    }

    public void openNewScene(String window) {
        diagram.getScene().getWindow().hide();

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
        openNewScene("userSite");
    }

}

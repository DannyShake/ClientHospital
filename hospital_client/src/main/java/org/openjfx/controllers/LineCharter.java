package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LineCharter {
    @FXML
    private LineChart lineChart;

    @FXML
    void initialize() {

        XYChart.Series dataSeries1 = new XYChart.Series();


//        dataSeries1.getData().add(new XYChart<Integer, Integer>.Data(1970, 15));
//        dataSeries1.getData().add(new XYChart<>.Data(1980, 30));
//        dataSeries1.getData().add(new XYChart<>.Data(1990, 60));
//        dataSeries1.getData().add(new XYChart<>.Data(2000, 120));
//        dataSeries1.getData().add(new XYChart<>.Data(2013, 240));
//       dataSeries1.getData().add(new XYChart<>.Data(2014, 300));

        lineChart.getData().add(dataSeries1);

    }
}

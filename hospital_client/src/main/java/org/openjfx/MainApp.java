package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static Scene scene;

    public static Parent loadFXML(String fxml) throws IOException {
        return FXMLLoader.load(MainApp.class.getResource(fxml + ".fxml"));
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Регистратура");
        Scene scene = new Scene(loadFXML("sample"), 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

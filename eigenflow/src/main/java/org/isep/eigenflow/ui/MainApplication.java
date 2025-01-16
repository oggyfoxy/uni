package org.isep.eigenflow.ui;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;  // add this import

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/org/isep/eigenflow/Menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            String cssPath = "/org/isep/eigenflow/light-mode.css";
            URL cssUrl = (URL) MainApplication.class.getResource(cssPath);  
            if (cssUrl == null) {
                System.err.println("Could not find stylesheet: " + cssPath);
            } else {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            stage.setTitle("Eigenflow Task Manager");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
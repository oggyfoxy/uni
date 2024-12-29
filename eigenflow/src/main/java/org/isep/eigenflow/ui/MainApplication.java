package org.isep.eigenflow.ui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        var label = new Label("eigenflow task manager");
        var root = new VBox(label);
        var scene = new Scene(root, 800, 600);

        stage.setTitle("eigenflow");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
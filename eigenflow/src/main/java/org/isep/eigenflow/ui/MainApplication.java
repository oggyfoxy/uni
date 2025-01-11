package org.isep.eigenflow.ui;



import org.isep.eigenflow.domain.Personel;
import org.isep.eigenflow.domain.Project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/org/isep/eigenflow/Menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Eigenflow Task Manager");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

         
      
            
    public static void main(String[] args) {
        launch(args);
        Personel personel = new Personel(1, "Jane Smith", "Project Manager", "jane.smith@example.com", "+1234567890");

        personel.addProjectToHistory("Project X");

        personel.editPersonel("Jane Smith", "Senior Project Manager", "jane.smith@updatedemail.com", "+0987654321");

        System.out.println(personel);
        System.out.println();

        Project project = new Project(1, "Project Alpha", "2025-12-31");

        project.addTask("Design the UI");
        project.addTask("Develop the backend");
        project.addMember("Jane Smith - Project Manager");

        System.out.println(project);
    }
}
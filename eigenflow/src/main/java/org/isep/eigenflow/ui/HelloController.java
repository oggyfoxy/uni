package org.isep.eigenflow.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileWriter;


public class HelloController {

    @FXML
    private void handleNewTask() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/task-window.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Task Management");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleOpen() {
        // open file chooser to load a file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            System.out.println("File selected: " + file.getAbsolutePath());
            // add logic to read the file
        }
    }

    @FXML
    private void handleClose() {
        // close current window
        Stage stage = getStage();
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    private void handleSave() {
        // save data to a file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showSaveDialog(getStage());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Sample data to save...");
                System.out.println("Data saved to: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleQuit() {
        // quit the entire application
        System.out.println("Application is exiting...");
        System.exit(0);
    }

    // utility method to get the current stage
    private Stage getStage() {
        return (Stage) javafx.stage.Window.getWindows()
                .stream()
                .filter(window -> window instanceof Stage)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("no active stage found"));
    }


    @FXML
    private void handleProfiles() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/creationPersonnel.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("profiles window");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleTeamManagement() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/creationPersonnel.fxml"));
        Scene scene1 = new Scene((Parent)loader1.load(), (double)800.0F, (double)600.0F);
        Stage stage1 = new Stage();
        stage1.setTitle("Employees management");
        stage1.setScene(scene1);
        stage1.show();
        System.out.println("Opening Team Management...");
    }


    @FXML
    private void handleHistoryAndRoles() {
        System.out.println("Opening History and Roles...");
    }

    @FXML
    private void handleKanbanView() {
        System.out.println("Opening Kanban View...");
    }

    @FXML
    private void handleCalendarView() {
        System.out.println("Opening Calendar View...");
    }

    @FXML
    private void handleProgressCharts() {
        System.out.println("Opening Progress Charts...");
    }


    @FXML
    private void handlePerformance() {
        System.out.println("Opening Performance Report...");
    }

    @FXML
    private void handleBudgetAnalysis() {
        System.out.println("Opening Budget Analysis Report...");
    }

    @FXML
    private void handleMyTasks() {
        System.out.println("Opening My Tasks...");
    }

    @FXML
    private void handleProjectTasks() {
        System.out.println("Opening Project Tasks...");
    }

    @FXML
    private void handleNewProject() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/new_project.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("new project form");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleActiveProjects() {
        System.out.println("Opening Active Projects...");
    }

    @FXML
    private void handleArchivedProjects() {
        System.out.println("Opening Archived Projects...");
    }

    @FXML
    private void handleSettings() {
        System.out.println("Opening Settings...");
    }

    @FXML
    private void handleHelp() {
        System.out.println("Opening Help...");

    }
}

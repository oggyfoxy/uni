package org.isep.eigenflow.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private ListView<String> todoList;
    @FXML
    private ListView<String> inProgressList;
    @FXML
    private ListView<String> doneList;

    @FXML
    private Button newTaskButton;

    // Observable lists to hold tasks for each column
    private ObservableList<String> todoItems = FXCollections.observableArrayList();
    private ObservableList<String> inProgressItems = FXCollections.observableArrayList();
    private ObservableList<String> doneItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Bind the ListView to the observable lists
        todoList.setItems(todoItems);
        inProgressList.setItems(inProgressItems);
        doneList.setItems(doneItems);

        // Enable drag-and-drop functionality for moving tasks
        enableDragAndDrop();
    }

    // Add a new task to the "To Do" list
    @FXML
    private void handleNewTask() {
        todoItems.add("New Task " + (todoItems.size() + 1));
    }

    // Enable drag-and-drop between columns (ListViews)
    private void enableDragAndDrop() {
        todoList.setOnMouseClicked(event -> handleDragAndDrop(event, todoList));
        inProgressList.setOnMouseClicked(event -> handleDragAndDrop(event, inProgressList));
        doneList.setOnMouseClicked(event -> handleDragAndDrop(event, doneList));
    }

    private void handleDragAndDrop(MouseEvent event, ListView<String> sourceList) {
        String selectedTask = sourceList.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            if (sourceList == todoList) {
                // Move task from "To Do" to "In Progress"
                inProgressItems.add(selectedTask);
                todoItems.remove(selectedTask);
            } else if (sourceList == inProgressList) {
                // Move task from "In Progress" to "Done"
                doneItems.add(selectedTask);
                inProgressItems.remove(selectedTask);
            }
        }
    }

    // Other methods to handle file operations
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            System.out.println("File selected: " + file.getAbsolutePath());
        }
    }

    @FXML
    private void handleSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

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
    private void handleClose() {
        Stage stage = getStage();
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    private void handleQuit() {
        System.out.println("Application is exiting...");
        System.exit(0);
    }

    // Utility method to open FXML windows
    private void openWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stage getStage() {
        return (Stage) javafx.stage.Window.getWindows()
                .stream()
                .filter(window -> window instanceof Stage)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No active stage found"));
    }

    // Window opening methods for different views
    @FXML
    private void handleProfiles() {
        openWindow("/org/isep/eigenflow/profiles.fxml", "Profiles Window");
    }

    @FXML
    private void handleTeamManagement() {
        openWindow("/org/isep/eigenflow/team_management.fxml", "Team Management");
    }

    @FXML
    private void handleCalendarView() {
        openWindow("/org/isep/eigenflow/calendar.fxml", "Calendar View");
    }

    @FXML
    private void handleHistoryAndRoles() {
        System.out.println("Opening History and Roles...");
    }
/* PARTIE EN COURS DE REALISATION
    @FXML
    private void handleKanbanView() {
        System.out.println("Opening Kanban View...");
    }
*/
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
    private void handleKanbanView() {
        openWindow("/org/isep/eigenflow/main-window.fxml", "Kanban View");
    }

    @FXML
    private void handleNewProject() {
        openWindow("/org/isep/eigenflow/new_project.fxml", "New Project Form");
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

package org.isep.eigenflow.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.isep.eigenflow.domain.Task;

import java.io.*;
import java.util.Optional;

public class TaskController {

    @FXML
    private TextField taskTitle;
    @FXML
    private TextArea taskDescription;
    @FXML
    private ListView<String> todoList;
    @FXML
    private ListView<String> memberList;

    private ObservableList<String> todoTasks = FXCollections.observableArrayList();
    private ObservableList<String> availableMembers = FXCollections.observableArrayList("Alice", "Bob", "Charlie");

    @FXML
    public void initialize() {
        todoList.setItems(todoTasks);
        memberList.setItems(availableMembers);
    }

    // ✅ CREATE A NEW TASK WITH MEMBER ASSIGNMENT
    @FXML
    private void handleNewTask() {
        String title = taskTitle.getText();
        String description = taskDescription.getText();

        if (title.isEmpty()) {
            showAlert("Validation Error", "Task title cannot be empty.");
            return;
        }

        Optional<String> selectedMember = showMemberSelectionDialog();
        if (selectedMember.isEmpty()) {
            showAlert("Member Selection", "No member selected. Task creation canceled.");
            return;
        }

        Task task = new Task(title);
        task.setDescription(description);
        task.assignMember(selectedMember.get());

        saveTaskToFile(task);

        todoTasks.add(task.getTitle());
        clearTaskForm();

        System.out.println("Task created: " + task);
    }

    // ✅ SELECT MEMBER DIALOG
    private Optional<String> showMemberSelectionDialog() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(availableMembers.get(0), availableMembers);
        dialog.setTitle("Assign Member");
        dialog.setHeaderText("Assign a member to the task");
        dialog.setContentText("Choose a member:");

        return dialog.showAndWait();
    }

    // ✅ SAVE TASK TO FILE
    private void saveTaskToFile(Task task) {
        File file = new File("tasks.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(task.toString());
            writer.newLine();
            System.out.println("Task saved to file: " + task.getTitle());
        } catch (IOException e) {
            showAlert("Save Error", "Could not save the task to file.");
            e.printStackTrace();
        }
    }

    // ✅ CLEAR TASK FORM
    private void clearTaskForm() {
        taskTitle.clear();
        taskDescription.clear();
    }

    // ✅ SHOW ALERT
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCloseTaskWindow() {
        Stage stage = (Stage) taskTitle.getScene().getWindow();
        stage.close();
    }
}

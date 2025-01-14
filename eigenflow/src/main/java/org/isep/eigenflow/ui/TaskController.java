package org.isep.eigenflow.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.isep.eigenflow.domain.Personel;
import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.domain.Task;
import org.isep.eigenflow.repo.PersonnelRepository;
import org.isep.eigenflow.repo.ProjectRepository;
import org.isep.eigenflow.service.TaskService;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskController {
    @FXML
    private TextField taskTitle;
    @FXML
    private TextArea taskDescription;
    @FXML
    private ListView<String> todoList;
    @FXML
    private ListView<String> memberList;
    @FXML
    private ComboBox<String> statusFilter;

    private ObservableList<String> todoTasks = FXCollections.observableArrayList();
    private ObservableList<String> availableMembers = FXCollections.observableArrayList();
    private PersonnelRepository personnelRepo = new PersonnelRepository();
    private TaskService taskService;

    @FXML private ComboBox<Project> projectSelector;
    private Project selectedProject;

    private ProjectRepository projectRepo = new ProjectRepository();

    @FXML
    public void initialize() {
        taskService = new TaskService();
        loadPersonnelFromDatabase();
        todoList.setItems(todoTasks);
        memberList.setItems(availableMembers);
        statusFilter.setOnAction(event -> handleFilterTasks());
        // Populate the status filter options
        statusFilter.setItems(FXCollections.observableArrayList("TODO", "IN_PROGRESS", "COMPLETED"));

        // Set default selection
        statusFilter.getSelectionModel().selectFirst();

        // Set up event handling for filter changes
        statusFilter.setOnAction(event -> handleFilterTasks());

        List<Project> projects = projectRepo.getAllProjects();
        projectSelector.setItems(FXCollections.observableArrayList(projects));
        projectSelector.getItems().add(0, null); // option for no project
        projectSelector.setConverter(new StringConverter<Project>() {
            @Override
            public String toString(Project project) {
                return project == null ? "No Project" : project.getProjectName();
            }

            @Override
            public Project fromString(String s) {
                return null;
            }
            // ... implement fromString if needed
        });
    }





    private void loadPersonnelFromDatabase() {
        List<Personel> personnel = personnelRepo.getAllPersonnel();
        availableMembers.clear();
        for (Personel p : personnel) {
            availableMembers.add(p.getName());
        }
        if (availableMembers.isEmpty()) {
            // fallback to default members if db is empty
            availableMembers.addAll("Alice", "Bob", "Charlie");
        }
    }

    @FXML
    private void handleNewTask() {
        String title = taskTitle.getText();
        if (title.isEmpty()) {
            showAlert("Error", "title required");
            return;
        }

        // show dialog asking if task should be added to project
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Task Creation");
        alert.setHeaderText("Add to Project?");
        alert.setContentText("Do you want to add this task to a project?");

        Optional<ButtonType> result = alert.showAndWait();
        Integer projectId = null;

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // show project selection dialog
            ChoiceDialog<Project> projectDialog = new ChoiceDialog<>();
            projectDialog.getItems().addAll(projectRepo.getAllProjects());
            projectDialog.setTitle("Select Project");
            projectDialog.setHeaderText("Choose a project for this task");

            Optional<Project> selectedProject = projectDialog.showAndWait();
            if (selectedProject.isPresent()) {
                projectId = selectedProject.get().getId();
            }
        }

        try {
            Optional<String> assignee = showMemberSelectionDialog();
            if (assignee.isPresent()) {
                taskService.createTask(title, taskDescription.getText(), assignee.get(), projectId);
                todoTasks.add(title);
                clearTaskForm();
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    // your existing methods
    private Optional<String> showMemberSelectionDialog() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(availableMembers.get(0), availableMembers);
        dialog.setTitle("Assign Member");
        dialog.setHeaderText("Assign a member to the task");
        dialog.setContentText("Choose a member:");
        return dialog.showAndWait();
    }

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

    private void clearTaskForm() {
        taskTitle.clear();
        taskDescription.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) taskTitle.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void handleCompleteTask() {
        String selectedTask = todoList.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            showAlert("No Task Selected", "Please select a task to complete.");
            return;
        }
        // Mark the task as completed in the database or list
        System.out.println("Task completed: " + selectedTask);
        // Refresh the list after updating the task's status
    }

    @FXML
    private void handleDeleteTask() {
        String selectedTask = todoList.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            showAlert("No Task Selected", "Please select a task to delete.");
            return;
        }
        // Delete the task from the database or list
        System.out.println("Task deleted: " + selectedTask);
        todoTasks.remove(selectedTask);
    }

    @FXML
    private void handleFilterTasks() {
        String status = statusFilter.getValue();
        if (status == null) {
            return; // No filter applied
        }
        // Apply the filter and refresh the todoList with tasks of the selected status
        System.out.println("Filtering tasks by status: " + status);
    }

}
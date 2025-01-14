package org.isep.eigenflow.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.domain.Task;
import org.isep.eigenflow.repo.TaskRepository;

public class AddTaskController {
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private ComboBox<String> assigneeCombo;
    @FXML private DatePicker deadlinePicker;

    private Project project;
    private final TaskRepository taskRepo = new TaskRepository();

    public void setProject(Project project) {
        this.project = project;
        // populate assignee combo with project members
        assigneeCombo.getItems().addAll(project.getMembers());
    }

    @FXML
    private void handleSave() {
        Task task = new Task(titleField.getText());
        task.setDescription(descriptionField.getText());
        task.assignMember(assigneeCombo.getValue());

        taskRepo.save(task);
        taskRepo.addTaskToProject(task.getUuid(), project.getId());

        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) titleField.getScene().getWindow()).close();
    }
}
package org.isep.eigenflow.domain;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class TaskDialog extends Dialog<Task> {
    public TaskDialog(Project project) {
        setTitle("Add Task to Project");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField titleField = new TextField();
        DatePicker deadlinePicker = new DatePicker();
        ComboBox<String> assigneeCombo = new ComboBox<>();
        assigneeCombo.getItems().addAll(project.getMembers());

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Deadline:"), 0, 1);
        grid.add(deadlinePicker, 1, 1);
        grid.add(new Label("Assignee:"), 0, 2);
        grid.add(assigneeCombo, 1, 2);

        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setResultConverter(button -> {
            if (button == ButtonType.OK) {
                Task task = new Task(titleField.getText());
                task.setDeadline(deadlinePicker.getValue().toString());
                task.assignMember(assigneeCombo.getValue());
                return task;
            }
            return null;
        });
    }
}
package org.isep.eigenflow.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.isep.eigenflow.domain.Project;

public class NewProjectController {

    @FXML
    private TextField projectNameField;

    @FXML
    private TextField deadlineField;

    @FXML
    private void handleCreate() {
        String name = projectNameField.getText();
        String deadline = deadlineField.getText();

        if (!name.isEmpty() && !deadline.isEmpty()) {
            // create the project
            Project project = new Project(0, name, deadline);
            System.out.println("created project: " + project);
        } else {
            System.out.println("please fill out all fields.");
        }
    }
}

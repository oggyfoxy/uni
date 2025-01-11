package org.isep.eigenflow.ui;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class NewProjectController {

    @FXML
    private TextField projectNameField;
    
    @FXML
    private TextField deadlineField;

    @FXML
    private CalendarController calendarController;

    @FXML
    private void handleCreateProject() {
        String projectName = projectNameField.getText().trim();
        String deadlineText = deadlineField.getText().trim();

        if (projectName.isEmpty() || deadlineText.isEmpty()) {
            showAlert("Missing Fields", "Please fill out all fields.");
            return;
        }

        if (!deadlineText.matches("\\d{4}-\\d{2}-\\d{2}")) {
            showAlert("Invalid Date", "Please use the date format YYYY-MM-DD.");
            return;
        }

        try {
            LocalDate deadlineDate = LocalDate.parse(deadlineText);
            ZonedDateTime deadline = deadlineDate.atStartOfDay().atZone(java.time.ZoneId.systemDefault());

            if (calendarController != null) {
                calendarController.addDeadline(deadline, "Project: " + projectName);
            }

            System.out.println("Project created: " + projectName + " with deadline: " + deadlineDate);
        } catch (Exception e) {
            showAlert("Error", "An error occurred while processing the deadline.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

package org.isep.tp7;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class Ex3Controller {
    @FXML
    private TextField taskField;
    @FXML
    private ListView<String> taskList;

    @FXML
    protected void onAddClick() {
        String task = taskField.getText().trim();
        if (!task.isEmpty()) {
            taskList.getItems().add(task);
            taskField.clear();
        } else {
            showAlert("Please add a task");
        }
    }
    @FXML
    protected void onDeleteClick() {
        if (!taskList.getItems().isEmpty()) {
            taskList.getItems().remove(0);
        } else {
            showAlert("list is empty");
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
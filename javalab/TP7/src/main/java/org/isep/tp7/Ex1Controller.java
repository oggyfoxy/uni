package org.isep.tp7;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class Ex1Controller {
    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    protected void onSubmitClick() {
        String name = nameField.getText();
        String age = ageField.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Name: " + name + "\nAge: " + age);
        alert.showAndWait();
    }
    @FXML
    protected void onClearClick() {
        nameField.clear();
        ageField.clear();
    }
}
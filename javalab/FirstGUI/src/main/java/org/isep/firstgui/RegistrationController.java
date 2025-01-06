package org.isep.firstgui;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.FileWriter;
import java.io.IOException;

public class RegistrationController

{

    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML
    private void handleRegisterButton() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Please fill all the mandatory fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("The passwords do not match");
            return;
        }
        try (FileWriter writer = new FileWriter("users.csv", true)) { // save users to csv
            writer.write(String.format("%s,%s,%s,%s\n", name, phone, email, password));
            showAlert("Authentification Successful");
            clearFields();
        } catch (IOException e) {
            showAlert("Error writing to file");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClearButton() {
        clearFields();
    }
    private void clearFields()
    {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }


}

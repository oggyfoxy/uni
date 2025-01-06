package org.isep.firstgui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginPageController
{
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML
    private void handleLogin()
    {

        String email = emailField.getText();
        String password = passwordField.getText();

        if (isValidUser(email, password)) {
            openMailboxScene();
        } else {
            showAlert("Wrong identifiants. Please try again.");
        }
    }

    @FXML
    private void handleOpenRegistration() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/isep/firstgui/registration.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Create an account");
            stage.show();
            emailField.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidUser(String email, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                if (user.length == 3 && user[1].equals(email) && user[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void openMailboxScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/isep/firstgui/mailbox.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Boîte de réception");
            stage.show();
            emailField.getScene().getWindow().hide(); // to close the current window
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String message) {


        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }



}

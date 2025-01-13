package org.isep.eigenflow.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.*;

public class CreationPersonnelController {

    @FXML
    private TextField employeeIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button findEmployeeButton;

    @FXML
    private void handleAddEmployee() throws IOException {
        System.out.println("Employee added:");
        System.out.println("ID: " + employeeIdField.getText());
        System.out.println("Name: " + nameField.getText());
        System.out.println("Position: " + positionField.getText());
        System.out.println("Email: " + emailField.getText());
        System.out.println("Phone: " + phoneNumberField.getText());

        String Id = employeeIdField.getText();
        String name = nameField.getText();
        String position = positionField.getText();
        String email = emailField.getText();
        String phone = phoneNumberField.getText();

        File file = new File("user.csv");
        FileWriter writer = new FileWriter(file, true);
        writer.write(Id + "," + name + "," + position + "," + email + "," + phone + "\n");
        writer.close();

        employeeIdField.clear();
        nameField.clear();
        positionField.clear();
        emailField.clear();
        phoneNumberField.clear();
    }

    @FXML
    private void handleClear() {
        employeeIdField.clear();
        nameField.clear();
        positionField.clear();
        emailField.clear();
        phoneNumberField.clear();
    }

    @FXML
    private void handleFindEmployee() throws IOException {
        String Id = employeeIdField.getText();
        String name = nameField.getText();
        String position = positionField.getText();
        String email = emailField.getText();
        String phone = phoneNumberField.getText();

        try (BufferedReader reader = new BufferedReader(new FileReader("user.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(Id) && parts[1].equals(name)) {
                    String Id1 = parts[0];
                    String name1 = parts[1];
                    String pos1 = parts[2];
                    String email1 = parts[3];
                    String phone1 = parts[4];
                    System.out.println(Id1 + " | " +  name1 + " is a personnel member, all other infos : Position : " + pos1 + " | Email : " + email1 + " | Phone Number : "+ phone1 );
                    return;
                }
            }
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "This personnel member does not exist, please try again. \n You need to enter Id and Name", ButtonType.OK);
            alert1.showAndWait();
        }
    }
}

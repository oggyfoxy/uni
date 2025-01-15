package org.isep.eigenflow.ui;


import org.isep.eigenflow.domain.Personel;
import org.isep.eigenflow.repo.PersonnelRepository;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProfilesController {
    @FXML
    private TextField nameField;
    @FXML private TextField positionField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TableView<Personel> personnelTable;

    @FXML
    private TableColumn<Personel, String> nameColumn;
    @FXML
    private TableColumn<Personel, String> positionColumn;
    @FXML
    private TableColumn<Personel, String> emailColumn;
    @FXML
    private TableColumn<Personel, String> phoneColumn;

    private PersonnelRepository repository = new PersonnelRepository();

    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        refreshTable();
    }

    private void clearFields() {
        nameField.clear();
        positionField.clear();
        emailField.clear();
        phoneField.clear();
    }


    @FXML
    private void handleAddPersonnel() {
        try {
            String name = nameField.getText();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty");
                return;
            }
            String position = positionField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            int newId = repository.getNextId();
            System.out.println("Creating personnel with ID: " + newId); 

            Personel newPerson = new Personel(newId, name, position, email, phone);
            repository.addPersonnel(newPerson);

            refreshTable();
            clearFields();
        } catch (Exception e) {
            System.err.println("Error in handleAddPersonnel: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        personnelTable.getItems().setAll(repository.getAllPersonnel());
    }
}
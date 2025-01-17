package org.isep.eigenflow.ui;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.isep.eigenflow.domain.Personel;
import org.isep.eigenflow.repo.PersonnelRepository;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ProfilesController {
    @FXML
    private TableColumn<Personel, Void> actionsColumn;
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

    private final PersonnelRepository personnelRepo = new PersonnelRepository();

    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));


        actionsColumn.setCellFactory(col -> new TableCell<Personel, Void>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox buttons = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setOnAction(e -> {
                    Personel personnel = getTableRow().getItem();
                    handleEditPersonnel(personnel);
                });

                deleteBtn.setOnAction(e -> {
                    Personel personnel = getTableRow().getItem();
                    handleDeletePersonnel(personnel);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : buttons);
            }
        });
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
    private void handleEditPersonnel(Personel personnel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/personel-edit.fxml"));
            Parent root = loader.load();

            PersonelEditController controller = loader.getController();
            controller.loadPersonnel(personnel);

            Stage stage = new Stage();
            stage.setTitle("Edit Personnel");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // refresh table after edit
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeletePersonnel(Personel personnel) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Personnel");
        confirm.setHeaderText("Delete " + personnel.getName() + "?");
        confirm.setContentText("This action cannot be undone.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                personnelRepo.deletePersonnel(personnel.getEmployeeId());
                refreshTable();
            }
        });
    }


    private void refreshTable() {
        personnelTable.getItems().setAll(repository.getAllPersonnel());
    }
}
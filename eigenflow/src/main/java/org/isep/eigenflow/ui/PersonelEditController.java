package org.isep.eigenflow.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.isep.eigenflow.domain.Personel;
import org.isep.eigenflow.repo.PersonnelRepository;

public class PersonelEditController {
    @FXML
    private TextField nameField;
    @FXML private TextField positionField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;

    private Personel personnel;
    private final PersonnelRepository personnelRepo = new PersonnelRepository();

    public void loadPersonnel(Personel personnel) {
        this.personnel = personnel;
        nameField.setText(personnel.getName());
        positionField.setText(personnel.getPosition());
        emailField.setText(personnel.getEmail());
        phoneField.setText(personnel.getPhoneNumber());
    }

    @FXML
    private void handleSave() {
        personnel.setName(nameField.getText());
        personnel.setPosition(positionField.getText());
        personnel.setEmail(emailField.getText());
        personnel.setPhoneNumber(phoneField.getText());

        personnelRepo.updatePersonnel(personnel);
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) nameField.getScene().getWindow()).close();
    }
}
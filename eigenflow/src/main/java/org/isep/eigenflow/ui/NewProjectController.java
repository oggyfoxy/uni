package org.isep.eigenflow.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.isep.eigenflow.domain.Personel;
import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.repo.PersonnelRepository;
import org.isep.eigenflow.repo.ProjectRepository;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;


import java.util.List;

public class NewProjectController {
    @FXML private TextField projectNameField;
    @FXML private DatePicker deadlinePicker;
    @FXML private ListView<String> memberListView;
    @FXML private ComboBox<String> memberComboBox;

    @FXML
    private Project currentProject;
    private DatePicker projectDeadlinePicker;

    private ProjectRepository projectRepo = new ProjectRepository();
    private PersonnelRepository personnelRepo = new PersonnelRepository();

    @FXML
    public void initialize() {
        // Load available members
        List<Personel> personnel = personnelRepo.getAllPersonnel();
        ObservableList<String> memberNames = FXCollections.observableArrayList();
        for (Personel p : personnel) {
            memberNames.add(p.getName());
        }
        memberComboBox.setItems(memberNames);

        memberListView.setItems(FXCollections.observableArrayList());
    }

    @FXML
    private void handleAddMember() {
        String selected = memberComboBox.getValue();
        if (selected != null && !memberListView.getItems().contains(selected)) {
            memberListView.getItems().add(selected);
        }
    }

    @FXML
    private void handleCreateProject() {
        String name = projectNameField.getText();
        LocalDate deadline = deadlinePicker.getValue(); // Use LocalDate directly
        ObservableList<String> members = memberListView.getItems();

        if (name.isEmpty()) {
            showAlert("Error", "Project name cannot be empty");
            return;
        }

        // Create a new Project using LocalDate for deadline
        Project project = new Project(0, name, deadline); // id will be set by the database
        for (String member : members) {
            project.addMember(member);
        }
        projectRepo.save(project);
        Stage stage = (Stage) projectNameField.getScene().getWindow();
        HelloController controller = (HelloController) stage.getUserData(); // Pass controller reference when opening the dialog
        controller.refreshProjectsTable();

        stage.close();
    }




    @FXML
    private void handleCancel() {
        Stage stage = (Stage) projectNameField.getScene().getWindow();
        stage.close();
    }

    public void loadProject(Project project) {
        this.currentProject = project;
        projectNameField.setText(project.getProjectName());
        if (project.getDeadline() != null) {
            projectDeadlinePicker.setValue(project.getDeadline()); // No need to parse
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
package org.isep.eigenflow.ui;

import java.util.List;

import org.isep.eigenflow.domain.Personel;
import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.repo.PersonnelRepository;
import org.isep.eigenflow.repo.ProjectRepository;  // Ensure correct naming

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;  // Ensure correct naming
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class TeamManagementController {

    @FXML
    private ComboBox<String> memberSelector;

    @FXML
    private Button selectMemberButton;

    @FXML
    private Button viewHistoryButton;

    @FXML
    private ListView<Project> projectHistoryListView;

    private Personel selectedMember;  // Corrected naming

    private final PersonnelRepository personnelRepo = new PersonnelRepository();  // Corrected naming
    private final ProjectRepository projectRepo = new ProjectRepository();  // Assuming ProjectRepository exists

    @FXML
    public void initialize() {
        loadMembers();
        setupMemberSelection();
    }

    // Load members from the Personnel repository
    private void loadMembers() {
        List<Personel> personnel = personnelRepo.getAllPersonnel();  // Corrected naming
        ObservableList<String> memberNames = FXCollections.observableArrayList();
        for (Personel p : personnel) {  // Corrected naming
            memberNames.add(p.getName());
        }
        memberSelector.setItems(memberNames);
    }

    // Setup for member selection when button is clicked
    private void setupMemberSelection() {
        selectMemberButton.setOnAction(event -> handleMemberSelection());
    }

    // Handle the member selection
    private void handleMemberSelection() {
        String selectedMemberName = memberSelector.getValue();

        
        System.out.println(selectedMemberName);
        if (selectedMemberName != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selected Member");
            alert.setHeaderText(null);
            alert.setContentText("Selected Member: " + selectedMemberName);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Member Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a member first.");
            alert.showAndWait();
        }
    }

    // Handle the "View History" button click
    @FXML
    private void handleViewHistoryClick() {
        String selectedMember = memberSelector.getValue();

        if (selectedMember != null) {

            // Fetch archived projects for the selected member
            List<Project> projects = projectRepo.getArchivedProjects();  // Fetch the projects

            // Display the list of projects in the ListView
            ObservableList<Project> projectList = FXCollections.observableArrayList(projects);
            projectHistoryListView.setItems(projectList);

            // Optionally, open a new window or show the list in a dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Member Project History");
            alert.setHeaderText(selectedMember + "'s Archived Projects");
            alert.setContentText("Projects will be displayed below.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Member Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a member to view their project history.");
            alert.showAndWait();
        }
    }
}

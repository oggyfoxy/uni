package org.isep.eigenflow.ui;

import java.util.List;

import org.isep.eigenflow.domain.Personel;
import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.repo.PersonnelRepository;
import org.isep.eigenflow.repo.ProjectRepository;  

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;  
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

    private final PersonnelRepository personnelRepo = new PersonnelRepository();
    private final ProjectRepository projectRepo = new ProjectRepository();  

    @FXML
    public void initialize() {
        loadMembers();
        setupMemberSelection();
    }

    private void loadMembers() {
        List<Personel> personnel = personnelRepo.getAllPersonnel(); 
        ObservableList<String> memberNames = FXCollections.observableArrayList();
        for (Personel p : personnel) {  
            memberNames.add(p.getName());
        }
        memberSelector.setItems(memberNames);
    }

    private void setupMemberSelection() {
        selectMemberButton.setOnAction(event -> handleMemberSelection());
    }

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

    @FXML
    private void handleViewHistoryClick() {
        String selectedMember = memberSelector.getValue();

        if (selectedMember != null) {

            List<Project> projects = projectRepo.getArchivedProjects();  

            ObservableList<Project> projectList = FXCollections.observableArrayList(projects);
            projectHistoryListView.setItems(projectList);

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

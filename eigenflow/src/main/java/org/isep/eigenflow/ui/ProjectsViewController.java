package org.isep.eigenflow.ui;

import java.util.List;

import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.repo.ProjectRepository;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProjectsViewController {
    @FXML private Label titleLabel;
    @FXML private TableView<Project> projectTable;
    @FXML private TableColumn<Project, String> nameColumn;
    @FXML private TableColumn<Project, String> deadlineColumn;
    @FXML private TableColumn<Project, String> membersColumn;
    @FXML private TableColumn<Project, Double> progressColumn;
    @FXML private TableColumn<Project, Void> actionsColumn;

    public void initialize() {
        setupColumns();
    }


    private final ProjectRepository projectRepo = new ProjectRepository();

    private HelloController mainController;

    public void setMainController(HelloController controller) {
        this.mainController = controller;
    }

    private void archiveProject(Project project) {
        try {
            projectRepo.archiveProject(project.getId());

            projectTable.getItems().remove(project);
            if (mainController != null) {
                mainController.refreshProjectsTable();
                mainController.loadTasks();  
                mainController.refreshDashboard();  
            }

            showAlert("Success", "Project archived successfully", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to archive project: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void unarchiveProject(Project project) {
        try {
            projectRepo.unarchiveProject(project.getId());
           
            showAlert("Success", "Project unarchived successfully", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to unarchive project: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setProjects(List<Project> projects) {
        projectTable.setItems(FXCollections.observableArrayList(projects));
    }

    private void setupColumns() {
       
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button actionButton = new Button();

            {
                actionButton.setOnAction(e -> {
                    Project project = getTableRow().getItem();
                    if (project != null) {
                        if (project.isActive()) {
                            archiveProject(project);
                        } else {
                            unarchiveProject(project);
                        }
                        projectTable.getItems().remove(project);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Project project = getTableRow().getItem();
                    if (project != null) {
                        actionButton.setText(project.isActive() ? "Archive" : "Unarchive");
                        setGraphic(actionButton);
                    }
                }
            }
        });
    }
}
package org.isep.eigenflow.ui;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.repo.ProjectRepository;


import java.util.List;

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

            // refresh both views
            projectTable.getItems().remove(project);
            if (mainController != null) {
                mainController.refreshProjectsTable();
                mainController.loadTasks();  // refresh tasks
                mainController.refreshDashboard();  // refresh stats/graphs
            }

            showAlert("Success", "Project archived successfully", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Failed to archive project: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void unarchiveProject(Project project) {
        try {
            projectRepo.unarchiveProject(project.getId());
            // optional: show confirmation
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
        // setup like in your main view
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        // etc...

        // add archive/unarchive button based on status
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
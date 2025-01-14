package org.isep.eigenflow.ui;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.*;
import org.isep.eigenflow.domain.Task;
import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.repo.TaskRepository;
import org.isep.eigenflow.repo.ProjectRepository;

import java.util.UUID;

public class KanbanController {
    @FXML private ComboBox<Project> projectSelector;
    @FXML private ListView<Task> todoList;
    @FXML private ListView<Task> inProgressList;
    @FXML private ListView<Task> doneList;

    private HelloController mainController;
    private Project currentProject;
    private final TaskRepository taskRepo = new TaskRepository();
    private final ProjectRepository projectRepo = new ProjectRepository();

    public void setMainController(HelloController controller) {
        this.mainController = controller;
    }

    @FXML
    public void initialize() {
        setupProjectSelector();
        setupDragAndDrop();

        // debug initial state
        System.out.println("Initializing KanbanController");
        System.out.println("Available projects: " + projectRepo.getAllProjects());
    }

    private void setProject(Project project) {
        System.out.println("Setting current project: " + project.getProjectName());
        this.currentProject = project;
        loadTasks();
    }

    private void setupProjectSelector() {
        var projects = projectRepo.getAllProjects();
        projectSelector.setItems(FXCollections.observableArrayList(projects));

        // setup display format
        projectSelector.setCellFactory(p -> new ListCell<Project>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getProjectName());
            }
        });
        projectSelector.setButtonCell(projectSelector.getCellFactory().call(null));

        // handle selection
        projectSelector.getSelectionModel().selectedItemProperty().addListener((obs, old, project) -> {
            if (project != null) {
                setProject(project);
            }
        });
    }

    private void loadTasks() {
        if (currentProject == null) return;

        System.out.println("Loading tasks for project: " + currentProject.getProjectName());

        // clear existing
        todoList.getItems().clear();
        inProgressList.getItems().clear();
        doneList.getItems().clear();

        // load new tasks
        var tasks = taskRepo.getTasksByProject(currentProject.getId());
        for (Task task : tasks) {
            System.out.println("Found task: " + task.getTitle() + " [" + task.getUuid() + "] Status: " + task.getStatus());
            switch (task.getStatus()) {
                case Task.STATUS_TODO -> todoList.getItems().add(task);
                case Task.STATUS_IN_PROGRESS -> inProgressList.getItems().add(task);
                case Task.STATUS_DONE -> doneList.getItems().add(task);
            }
        }
    }

    private void setupDragAndDrop() {
        setupDragAndDropForList(todoList, Task.STATUS_TODO);
        setupDragAndDropForList(inProgressList, Task.STATUS_IN_PROGRESS);
        setupDragAndDropForList(doneList, Task.STATUS_DONE);
    }

    private void setupDragAndDropForList(ListView<Task> list, String targetStatus) {
        list.setOnDragDetected(e -> {
            Task task = list.getSelectionModel().getSelectedItem();
            if (task != null) {
                System.out.println("Starting drag for task: " + task.getTitle() + " [" + task.getUuid() + "]");

                Dragboard db = list.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(task.getUuid().toString());
                db.setContent(content);
                e.consume();
            }
        });

        list.setOnDragOver(e -> {
            if (e.getGestureSource() != list && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        list.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard(); // Get the dragboard from the event
            boolean success = false; // Initialize the success flag

            if (db.hasString()) {
                String uuid = db.getString();
                try {
                    // Update the task status in the repository
                    taskRepo.updateTaskStatus(UUID.fromString(uuid), targetStatus);

                    // Reload the tasks in the Kanban board
                    loadTasks();

                    // Refresh the dashboard and graphs, if applicable
                    if (mainController != null) {
                        mainController.refreshDashboardAndGraphs();
                    }

                    success = true;
                } catch (Exception ex) {
                    System.err.println("Failed to update task: " + ex.getMessage());
                }
            }

            e.setDropCompleted(success); // Notify the system about the result of the drop
            e.consume(); // Mark the event as consumed
        });

    }
}
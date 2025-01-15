package org.isep.eigenflow.ui;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.domain.Task;
import org.isep.eigenflow.domain.TeamMember;
import org.isep.eigenflow.repo.PersonnelRepository;
import org.isep.eigenflow.repo.ProjectRepository;
import org.isep.eigenflow.repo.TaskRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class HelloController {

    public MenuItem New;
    public MenuItem Open;
    public MenuItem Close;
    public MenuItem Save;
    public MenuItem Quit;
    public MenuItem Profiles;
    public MenuItem TeamManagement;
    public MenuItem HistoryAndRoles;
    public MenuItem KanbanView;
    public MenuItem CalendarView;
    public MenuItem ProgressCharts;
    public MenuItem Performance;
    public MenuItem BudgetAnalysis;
    public MenuItem MyTasks;
    public MenuItem ProjectTasks;
    public MenuItem NewProject;
    public MenuItem ActiveProject;
    public MenuItem ArchivedProjects;
    public MenuItem Settings;
    public MenuItem Help;



    @FXML private PieChart taskStatusChart;
    @FXML private BarChart<String, Number> projectProgressChart;
    @FXML private Label totalTasksLabel;
    @FXML private Label inProgressLabel;
    @FXML private Label completedLabel;
    @FXML private ListView<String> activityListView;

    @FXML private VBox todoColumn;
    @FXML private VBox inProgressColumn;
    @FXML private VBox doneColumn;

    @FXML
    private ListView<Task> todoList;
    @FXML
    private ListView<Task> inProgressList;
    @FXML
    private ListView<Task> doneList;

    @FXML private TableView<Project> projectsTableView;
    @FXML private TableView<Task> projectTasksTable;
    @FXML private TableView<TeamMember> projectMembersTable;

    @FXML private TableColumn<Project, String> projectNameColumn;
    @FXML private TableColumn<Project, String> projectDeadlineColumn;
    @FXML private TableColumn<Project, String> projectMembersColumn;
    @FXML private TableColumn<Project, Double> projectProgressColumn;
    @FXML private TableColumn<Project, Void> projectActionsColumn;

    @FXML private TableColumn<Task, String> taskTitleColumn;
    @FXML private TableColumn<Task, String> taskAssigneeColumn;
    @FXML private TableColumn<Task, String> taskStatusColumn;
    @FXML private TableColumn<Task, String> taskDeadlineColumn;

    private Project currentProject;
    private Project selectedProject;

    @FXML private TableColumn<TeamMember, String> memberNameColumn;
    @FXML private TableColumn<TeamMember, String> memberRoleColumn;
    @FXML private TableColumn<TeamMember, String> memberTasksColumn;

    private final TaskRepository taskRepo = new TaskRepository();
    private final ProjectRepository projectRepo = new ProjectRepository();
    private final PersonnelRepository personnelRepo = new PersonnelRepository();

    @FXML
    private void initialize() {
        setupTaskViews();
        loadTasks();
        setupDashboard();
        setupProjectsView();
        setupProjectTasksTable();

        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        projectDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        projectMembersColumn.setCellValueFactory(new PropertyValueFactory<>("membersAsString"));
        projectProgressColumn.setCellValueFactory(new PropertyValueFactory<>("progress")); 

        refreshProjectsTable();

        projectActionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    Project project = getTableView().getItems().get(getIndex());
                    handleEditProject(project);
                });

                deleteButton.setOnAction(event -> {
                    Project project = getTableView().getItems().get(getIndex());
                    handleDeleteProject(project);
                });

                HBox buttons = new HBox(10, editButton, deleteButton);
                buttons.setStyle("-fx-alignment: center;"); 
                setGraphic(buttons);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(getGraphic());
                }
            }
        });




}

    private void setupDashboard() {
        updateTaskStatistics();
        updateTaskStatusChart();
        updateProjectProgressChart();
        setupActivityFeed();
    }

    private void updateTaskStatistics() {
        List<Task> allTasks = taskRepo.getAllTasks();
        int totalTasks = allTasks.size();
        int inProgressTasks = (int) allTasks.stream()
                .filter(t -> t.getStatus().equals(Task.STATUS_IN_PROGRESS))
                .count();
        int completedTasks = (int) allTasks.stream()
                .filter(t -> t.getStatus().equals(Task.STATUS_DONE))
                .count();

        totalTasksLabel.setText(String.valueOf(totalTasks));
        inProgressLabel.setText(String.valueOf(inProgressTasks));
        completedLabel.setText(String.valueOf(completedTasks));
    }

    private void updateTaskStatusChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        List<Task> allTasks = taskRepo.getAllTasks();

        long todoCount = allTasks.stream()
                .filter(t -> t.getStatus().equals(Task.STATUS_TODO))
                .count();
        long inProgressCount = allTasks.stream()
                .filter(t -> t.getStatus().equals(Task.STATUS_IN_PROGRESS))
                .count();
        long doneCount = allTasks.stream()
                .filter(t -> t.getStatus().equals(Task.STATUS_DONE))
                .count();

        pieChartData.add(new PieChart.Data("To Do", todoCount));
        pieChartData.add(new PieChart.Data("In Progress", inProgressCount));
        pieChartData.add(new PieChart.Data("Done", doneCount));

        taskStatusChart.setData(pieChartData);
    }

    private void updateProjectProgressChart() {
        projectProgressChart.getData().clear();
        List<Project> projects = projectRepo.getAllProjects();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Project Progress");

        for (Project project : projects) {
            double progress = calculateProjectProgress(project);
            series.getData().add(new XYChart.Data<>(project.getProjectName(), progress));
        }

        projectProgressChart.getData().add(series);
    }

    private double calculateProjectProgress(Project project) {
        List<Task> projectTasks = taskRepo.getTasksByProject(project.getId());
        if (projectTasks.isEmpty()) return 0.0;

        long completedTasks = projectTasks.stream()
                .filter(t -> t.getStatus().equals(Task.STATUS_DONE))
                .count();

        return (completedTasks * 100.0) / projectTasks.size();
    }

    private void setupActivityFeed() {
        ObservableList<String> activities = FXCollections.observableArrayList();
        
        activities.addAll(
                "New task created: UI Design",
                "Task completed: Database Setup",
                "Project started: Mobile App",
                "New member added to Project X"
        );
        activityListView.setItems(activities);
    }

    
    public void refreshDashboard() {
        updateTaskStatistics();
        updateTaskStatusChart();
        updateProjectProgressChart();
    }


    /**
     * Handles drag-and-drop functionality for tasks.
     */
    private void setupTaskViews() {
        setupDragAndDrop(todoList, Task.STATUS_TODO);
        setupDragAndDrop(inProgressList, Task.STATUS_IN_PROGRESS);
        setupDragAndDrop(doneList, Task.STATUS_DONE);
    }

    /**
     * Sets up drag-and-drop for the given ListView.
     */
    private void setupDragAndDrop(ListView<Task> listView, String targetStatus) {
        // Enable dragging
        listView.setOnDragDetected(event -> {
            Task selectedTask = listView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                Dragboard dragboard = listView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedTask.getUuid().toString());
                dragboard.setContent(content);
                event.consume();
            }
        });

        
        listView.setOnDragOver(event -> {
            if (event.getGestureSource() != listView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        
        listView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if (dragboard.hasString()) {
                String taskId = dragboard.getString();
                Task task = taskRepo.findByUUID(taskId);

                if (task != null) {
                    task.setStatus(targetStatus);
                    try {
                        taskRepo.updateTask(task);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    
                    loadTasks();
                    success = true;
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    /**
     * Loads tasks into their respective ListViews based on their status.
     */
    void loadTasks() {
        ObservableList<Task> todoTasks = FXCollections.observableArrayList();
        ObservableList<Task> inProgressTasks = FXCollections.observableArrayList();
        ObservableList<Task> doneTasks = FXCollections.observableArrayList();

        for (Task task : taskRepo.getAllTasks()) {
            switch (task.getStatus()) {
                case Task.STATUS_TODO -> todoTasks.add(task);
                case Task.STATUS_IN_PROGRESS -> inProgressTasks.add(task);
                case Task.STATUS_DONE -> doneTasks.add(task);
            }
        }

        todoList.setItems(todoTasks);
        inProgressList.setItems(inProgressTasks);
        doneList.setItems(doneTasks);
    }



    private void setupProjectsView() {
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        projectDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));

     
        projectActionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(e -> handleEditProject(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(e -> handleDeleteProject(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, editButton, deleteButton);
                    setGraphic(buttons);
                }
            }
        });


        projectsTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    currentProject = newSelection;
                    handleProjectSelection(newSelection);
                }
        );
    }


   
    private void loadProjectTasks(Project project) {
        if (project == null) {
            projectTasksTable.getItems().clear();
            projectMembersTable.getItems().clear();
            return;
        }

        List<Task> tasks = taskRepo.getTasksByProject(project.getId());
        projectTasksTable.setItems(FXCollections.observableArrayList(tasks));

        List<TeamMember> members = project.getMembers().stream()
                .map(m -> new TeamMember(m, "Role"))
                .collect(Collectors.toList());
        projectMembersTable.setItems(FXCollections.observableArrayList(members));
    }

    @FXML
    private void handleAddTaskToProject() {
        Project selectedProject = projectsTableView.getSelectionModel().getSelectedItem();
        if (selectedProject == null) {
            showAlert("Error", "Select a project first");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/add_task.fxml"));
            Parent root = loader.load();

            AddTaskController controller = loader.getController();
            controller.setProject(selectedProject);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadProjectTasks(selectedProject);
        } catch (Exception e) {
            showAlert("Error", "Failed to create task: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddMemberToProject() {
        Project selectedProject = projectsTableView.getSelectionModel().getSelectedItem();
        if (selectedProject == null) {
            showAlert("Error", "Please select a project first");
            return;
        }

        Dialog<String> dialog = new MemberSelectionDialog(personnelRepo.getAllPersonnel());
        dialog.showAndWait().ifPresent(member -> {
            selectedProject.addMember(member);
           
            loadProjectTasks(selectedProject);
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleNewTask() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/task-window.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Task Management");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleOpen() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            System.out.println("File selected: " + file.getAbsolutePath());
            
        }
    }

    @FXML
    private void handleClose() {

        Stage stage = getStage();
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    private void handleSave() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showSaveDialog(getStage());
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Sample data to save...");
                System.out.println("Data saved to: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleQuit() {

        System.out.println("Application is exiting...");
        System.exit(0);
    }

    private Stage getStage() {
        return (Stage) javafx.stage.Window.getWindows()
                .stream()
                .filter(window -> window instanceof Stage)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("no active stage found"));
    }


    @FXML
    private void handleProfiles() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/profiles.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Personnel Profiles");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleTeamManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/team_management.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("team management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleHistoryAndRoles() {
        System.out.println("Opening History and Roles...");
    }

    @FXML
    private void handleKanbanView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/kanban-view.fxml"));
            Parent root = loader.load();
            KanbanController controller = loader.getController();
            controller.setMainController(this);  

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Kanban Board");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCalendarView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/calendar-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Calendar View");
            stage.setScene(scene);
            stage.setMinWidth(800);  
            stage.setMinHeight(600);

           
            CalendarController controller = loader.getController();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading calendar view: " + e.getMessage());
        }
    }

    @FXML
    private void handleProgressCharts() {
        System.out.println("Opening Progress Charts...");
    }


    @FXML
    private void handlePerformance() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/performance-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Performance Report");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBudgetAnalysis() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/budget-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Performance Report");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleMyTasks() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/my-tasks.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("My Tasks");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleProjectTasks() {
        System.out.println("Opening Project Tasks...");
    }
    @FXML
    private void handleNewProject() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/new_project.fxml"));
            Parent root = loader.load();

            NewProjectController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("New Project");
            stage.setScene(new Scene(root));

            stage.setUserData(this);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleProjectSelection(Project project) {
        currentProject = project;
        loadProjectTasks(project);
    }



    @FXML
    private void handleActiveProjects() {
        showProjectsView("Active Projects", projectRepo.getActiveProjects());
    }

    @FXML
    private void handleArchivedProjects() {
        showProjectsView("Archived Projects", projectRepo.getArchivedProjects());
    }

    private void showProjectsView(String title, List<Project> projects) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/projects-view.fxml"));
            Parent root = loader.load();

            ProjectsViewController controller = loader.getController();
            controller.setProjects(projects);
            controller.setTitle(title);
            controller.setMainController(this);  

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEditProject(Project project) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isep/eigenflow/new_project.fxml"));
            Parent root = loader.load();
            NewProjectController controller = loader.getController();
            controller.loadProject(project);  

            Stage stage = new Stage();
            stage.setTitle("Edit Project");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteProject(Project project) {
        if (project == null) {
            System.err.println("Cannot delete null project.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Project");
        alert.setHeaderText("Are you sure you want to delete this project?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    projectRepo.deleteProject(project.getId());
                    refreshProjectsTable();  
                    loadProjectTasks(null);
                } catch (RuntimeException e) {
                    System.err.println("Failed to delete project: " + e.getMessage());
                }
            }
        });
    }

    private void setupProjectTasksTable() {
        taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskAssigneeColumn.setCellValueFactory(new PropertyValueFactory<>("assignedMembersAsString"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        taskDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        TableColumn<Task, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");
            {
                deleteBtn.setOnAction(e -> {
                    Task task = getTableRow().getItem();
                    if (task != null) {
                        handleDeleteTask(task);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });

        projectTasksTable.getColumns().add(actionCol);
    }


    private void handleDeleteTask(Task task) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Task");
        alert.setContentText("Delete this task?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    taskRepo.deleteTask(task.getUuid().toString());
                    loadProjectTasks(currentProject);
                    refreshDashboard();
                } catch (SQLException e) {
                    showAlert("Error", "Failed to delete task: " + e.getMessage());
                }
            }
        });
    }


    @FXML
    private TableView<Project> projectTable;



    public void refreshProjectsTable() {
        List<Project> projects = projectRepo.getAllProjects();
        projectsTableView.setItems(FXCollections.observableArrayList(projects));
    }

    public void refreshAll() {
        refreshProjectsTable();
        loadProjectTasks(currentProject);
        loadTasks(); 
        refreshDashboard();
    }

    public void refreshDashboardAndGraphs() {
        updateTaskStatistics();
        updateTaskStatusChart();
        updateProjectProgressChart();
    }


    @FXML
    private Button darkModeButton;

    private boolean isDarkMode = false;

    @FXML
    private void toggleDarkMode() {
        Scene scene = darkModeButton.getScene();
        if (scene == null) {
            System.err.println("Scene not found");
            return;
        }

        String cssPath = isDarkMode ? "/org/isep/eigenflow/light-mode.css" : "/org/isep/eigenflow/dark-mode.css";
        URL cssUrl = getClass().getResource(cssPath);

        if (cssUrl == null) {
            System.err.println("Could not find stylesheet: " + cssPath);
            return;
        }

        scene.getStylesheets().clear();
        scene.getStylesheets().add(cssUrl.toExternalForm());
        darkModeButton.setText(isDarkMode ? "🌙" : "☀️");
        isDarkMode = !isDarkMode;
    }


}

package org.isep.eigenflow.ui;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import org.isep.eigenflow.domain.Task;
import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.repo.TaskRepository;
import org.isep.eigenflow.repo.ProjectRepository;

import java.time.LocalDate;
import java.util.List;

public class CalendarController {
    @FXML
    private ComboBox<Project> projectSelector;
    @FXML
    private DatePicker monthPicker;
    @FXML
    private GridPane calendarGrid;

    private final TaskRepository taskRepo = new TaskRepository();
    private final ProjectRepository projectRepo = new ProjectRepository();
    private Project currentProject;

    @FXML
    public void initialize() {
        setupProjectSelector();
        setupMonthPicker();
        setupCalendarGrid();
    }

    private void setupProjectSelector() {
        projectSelector.setItems(FXCollections.observableArrayList(projectRepo.getAllProjects()));
        projectSelector.setOnAction(e -> refreshCalendar());
    }

    private void setupMonthPicker() {
        monthPicker.setValue(LocalDate.now());
        monthPicker.setOnAction(e -> refreshCalendar());
    }

    private void setupCalendarGrid() {
     
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5;");
            calendarGrid.add(dayLabel, i, 0);
        }
    }

    private void refreshCalendar() {
        Project project = projectSelector.getValue();
        LocalDate date = monthPicker.getValue();
        if (project == null || date == null) return;

        
        calendarGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 0);

        
        LocalDate firstDay = date.withDayOfMonth(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue() - 1;
        int daysInMonth = date.lengthOfMonth();

      
        List<Task> tasks = taskRepo.getTasksByProject(project.getId());

        
        int day = 1;
        for (int week = 1; week <= 6; week++) {
            for (int dow = 0; dow < 7; dow++) {
                if ((week == 1 && dow < dayOfWeek) || day > daysInMonth) {
                    continue;
                }

                VBox cell = createCalendarCell(day, tasks);
                calendarGrid.add(cell, dow, week);
                day++;
            }
            if (day > daysInMonth) break;
        }
    }

    private VBox createCalendarCell(int day, List<Task> tasks) {
        VBox cell = new VBox(5);
        cell.getStyleClass().add("calendar-cell");
        cell.setPadding(new Insets(5, 5, 5, 5));

        LocalDate cellDate = monthPicker.getValue().withDayOfMonth(day);

       
        String cellStyle = "-fx-background-color: white; -fx-border-color: #dee2e6;";

        
        Project selectedProject = projectSelector.getValue();
        if (selectedProject != null && selectedProject.getDeadline().equals(cellDate)) {
            cellStyle = "-fx-background-color: #ffe8e8; -fx-border-color: #ff9999;"; 
        }

        cell.setStyle(cellStyle);

      
        Label dayLabel = new Label(String.valueOf(day));
        dayLabel.setStyle(cellDate.equals(LocalDate.now()) ?
                "-fx-font-weight: bold; -fx-text-fill: #0d6efd;" :
                "-fx-font-weight: bold;");
        cell.getChildren().add(dayLabel);

       
        tasks.stream()
                .filter(t -> t.getDeadline() != null && t.getDeadline().equals(cellDate))
                .forEach(task -> {

                    VBox taskBox = new VBox(2);
                    taskBox.setStyle(getTaskStyle(task.getStatus()));
                    taskBox.setPadding(new Insets(3, 5, 3, 5));

                    Label titleLabel = new Label(task.getTitle());
                    titleLabel.setWrapText(true);

                    Tooltip tooltip = new Tooltip(
                            "Task: " + task.getTitle() + "\n" +
                                    "Status: " + task.getStatus() + "\n" +
                                    "Project: " + selectedProject.getProjectName()
                    );
                    Tooltip.install(taskBox, tooltip);

                    taskBox.getChildren().add(titleLabel);

                    taskBox.setOnMouseEntered(e -> {
                        taskBox.setStyle(taskBox.getStyle() + "; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0, 0, 0);");
                        titleLabel.setStyle("-fx-font-weight: bold;");
                    });
                    taskBox.setOnMouseExited(e -> {
                        taskBox.setStyle(getTaskStyle(task.getStatus()));
                        titleLabel.setStyle("");
                    });

                    cell.getChildren().add(taskBox);
                });

        if (selectedProject != null && selectedProject.getDeadline().equals(cellDate)) {
            Label deadlineLabel = new Label("PROJECT DEADLINE");
            deadlineLabel.setStyle("-fx-text-fill: #dc3545; -fx-font-size: 10px; -fx-font-weight: bold;");
            cell.getChildren().add(deadlineLabel);
        }

        return cell;
    }

    private String getTaskStyle(String status) {
        return switch (status) {
            case Task.STATUS_TODO -> "-fx-background-color: #fff3cd; -fx-border-color: #ffeeba; -fx-background-radius: 3;";
            case Task.STATUS_IN_PROGRESS -> "-fx-background-color: #cfe2ff; -fx-border-color: #b6d4fe; -fx-background-radius: 3;";
            case Task.STATUS_DONE -> "-fx-background-color: #d1e7dd; -fx-border-color: #badbcc; -fx-background-radius: 3;";
            default -> "-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-background-radius: 3;";
        };
    }



}
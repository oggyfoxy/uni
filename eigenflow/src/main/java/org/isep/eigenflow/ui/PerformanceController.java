package org.isep.eigenflow.ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.isep.eigenflow.domain.Personel;
import org.isep.eigenflow.domain.Project;
import org.isep.eigenflow.domain.Task;
import org.isep.eigenflow.repo.PersonnelRepository;
import org.isep.eigenflow.repo.ProjectRepository;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.isep.eigenflow.repo.TaskRepository;

import java.time.LocalDate;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PerformanceController {
    @FXML private ComboBox<Project> projectSelector;
    @FXML private PieChart taskCompletionChart;
    @FXML private BarChart<String, Number> memberPerformanceChart;

    private final TaskRepository taskRepo = new TaskRepository();
    private final ProjectRepository projectRepo = new ProjectRepository();

    @FXML
    public void initialize() {
        setupProjectSelector();
        setupChartsStyle();
    }

    private void setupProjectSelector() {
        projectSelector.setItems(FXCollections.observableArrayList(projectRepo.getAllProjects()));
        projectSelector.setPromptText("All Projects");
        projectSelector.setConverter(new StringConverter<>() {
            @Override
            public String toString(Project project) {
                return project == null ? "All Projects" : project.getProjectName();
            }

            @Override
            public Project fromString(String string) {
                return null;
            }
        });
        projectSelector.setOnAction(e -> updateCharts());
    }

    private void setupChartsStyle() {
        
        taskCompletionChart.setStyle("-fx-font-family: 'SF Pro Display', Arial;");
        memberPerformanceChart.setStyle("-fx-font-family: 'SF Pro Display', Arial;");

      
        taskCompletionChart.setAnimated(true);
        memberPerformanceChart.setAnimated(true);
    }

    private void updateCharts() {
        Project selectedProject = projectSelector.getValue();
        List<Task> tasks = selectedProject == null ?
                taskRepo.getAllTasks() :
                taskRepo.getTasksByProject(selectedProject.getId());

        updateTaskCompletionChart(tasks);
        updateMemberPerformanceChart(tasks);
    }

    private void updateTaskCompletionChart(List<Task> tasks) {
        Map<String, Long> statusCounts = tasks.stream()
                .collect(Collectors.groupingBy(
                        Task::getStatus,
                        Collectors.counting()
                ));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("To Do (" + statusCounts.getOrDefault(Task.STATUS_TODO, 0L) + ")",
                        statusCounts.getOrDefault(Task.STATUS_TODO, 0L)),
                new PieChart.Data("In Progress (" + statusCounts.getOrDefault(Task.STATUS_IN_PROGRESS, 0L) + ")",
                        statusCounts.getOrDefault(Task.STATUS_IN_PROGRESS, 0L)),
                new PieChart.Data("Completed (" + statusCounts.getOrDefault(Task.STATUS_DONE, 0L) + ")",
                        statusCounts.getOrDefault(Task.STATUS_DONE, 0L))
        );

        taskCompletionChart.setData(pieChartData);

        
        pieChartData.get(0).getNode().setStyle("-fx-pie-color: #ff9f1c;"); 
        pieChartData.get(1).getNode().setStyle("-fx-pie-color: #2ec4b6;"); 
        pieChartData.get(2).getNode().setStyle("-fx-pie-color: #1b998b;"); 

        double total = tasks.size();
        pieChartData.forEach(data -> {
            String percentage = String.format("%.1f%%", (data.getPieValue() / total) * 100);
            Tooltip tooltip = new Tooltip(data.getName() + "\n" + percentage);
            Tooltip.install(data.getNode(), tooltip);
        });
    }

    private void updateMemberPerformanceChart(List<Task> tasks) {
       
        Map<String, Long> memberCompletions = tasks.stream()
                .filter(t -> t.getStatus().equals(Task.STATUS_DONE))
                .flatMap(t -> t.getAssignedMembers().stream())
                .collect(Collectors.groupingBy(
                        member -> member,
                        Collectors.counting()
                ));

       
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Completed Tasks");

        memberCompletions.forEach((member, count) ->
                series.getData().add(new XYChart.Data<>(member, count))
        );

        memberPerformanceChart.getData().clear();
        memberPerformanceChart.getData().add(series);

        series.getData().forEach(data -> {
            Node node = data.getNode();
            Tooltip tooltip = new Tooltip(
                    data.getXValue() + "\nCompleted Tasks: " + data.getYValue()
            );
            Tooltip.install(node, tooltip);

           
            node.setOnMouseEntered(e ->
                    node.setStyle("-fx-bar-fill: derive(-fx-bar-fill, 20%);")
            );
            node.setOnMouseExited(e ->
                    node.setStyle("")
            );
        });
    }
}
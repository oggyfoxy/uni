package org.isep.eigenflow.domain;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.isep.eigenflow.repo.BaseRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project extends BaseRepository {
    private int projectId;
    private String projectName;
    private LocalDate deadline; // Use LocalDate instead of String
    private ArrayList<String> members;
    private ArrayList<String> tasks;
    private String status; // Add status field

    public Project(int projectId, String projectName, LocalDate deadline) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.deadline = deadline; // Set as LocalDate
        this.members = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.status = "ACTIVE"; // Default status
    }

    private final DoubleProperty progress = new SimpleDoubleProperty(0.0);

    public final DoubleProperty progressProperty() {
        return progress;
    }

    public final double getProgress() {
        return progress.get();
    }

    public final void setProgress(double value) {
        progress.set(value);
    }

    public boolean isActive() {
        return "ACTIVE".equals(status);
    }

    // Getters for functionality
    public int getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Existing methods
    public void editProject(String projectName, LocalDate deadline) {
        this.projectName = projectName;
        this.deadline = deadline;
    }

    public void deleteProject() {
        this.projectId = 0;
        this.projectName = null;
        this.deadline = null;
        this.members.clear();
        this.tasks.clear();
    }

    public void addTask(String task) {
        tasks.add(task);
    }

    public void removeTask(String task) {
        tasks.remove(task);
    }

    public void addMember(String member) {
        members.add(member);
    }

    public void removeMember(String member) {
        members.remove(member);
    }

    public void listTasks() {
        for (String task : tasks) {
            System.out.println(task);
        }
    }

    public void listMembers() {
        for (String member : members) {
            System.out.println(member);
        }
    }

    // Helper methods for repository
    public String getMembersAsString() {
        return String.join(",", members);
    }

    public String getTasksAsString() {
        return String.join(",", tasks);
    }

    public int getId() {
        return projectId;
    }

    public List<Task> getTasksByProject(int projectId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT t.* FROM tasks t " +
                     "JOIN project_tasks pt ON t.uuid = pt.task_uuid " +
                     "WHERE pt.project_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("assignedMembers"),
                        UUID.fromString(rs.getString("uuid"))
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public String toString() {
        return "Project ID: " + this.projectId + "\n" +
               "Project Name: " + this.projectName + "\n" +
               "Deadline: " + (this.deadline != null ? this.deadline.toString() : "No deadline") + "\n" +
               "Status: " + this.status + "\n" +
               "Members: " + this.members + "\n" +
               "Tasks: " + this.tasks;
    }
}

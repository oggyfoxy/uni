package org.isep.eigenflow.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Task {
    private String title;
    private String description;
    private boolean completed;
    private UUID uuid;
    private final List<String> assignedMembers;
    private String status;
    private Integer projectId;
    private LocalDate deadline;

    public static final String STATUS_TODO = "TODO";
    public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    public static final String STATUS_DONE = "DONE";

    // Constructor for new tasks
    public Task(String title) {
        this.title = title;
        this.completed = false;
        this.status = STATUS_TODO;
        this.uuid = UUID.randomUUID();
        this.assignedMembers = new ArrayList<>();
        this.projectId = null;
    }

    // Constructor for database tasks
    public Task(String title, String description, String status, String assignedMembers, UUID uuid) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.uuid = uuid;
        this.assignedMembers = new ArrayList<>();
        this.completed = false; // Default value, can be updated later
        setAssignedMembersFromString(assignedMembers);
        this.projectId = null;                    // Parse assigned members from string
    }


    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }


    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals(STATUS_TODO) || status.equals(STATUS_IN_PROGRESS) || status.equals(STATUS_DONE)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    public List<String> getAssignedMembers() {
        return assignedMembers;
    }

    public void assignMember(String member) {
        if (!assignedMembers.contains(member)) {
            assignedMembers.add(member);
        }
    }

    public void removeMember(String member) {
        assignedMembers.remove(member);
    }

    // Convert assigned members list to a comma-separated string
    public String getAssignedMembersAsString() {
        return String.join(", ", assignedMembers);
    }

    // Parse a comma-separated string into the assigned members list
    public void setAssignedMembersFromString(String assignedMembersStr) {
        if (assignedMembersStr != null && !assignedMembersStr.isEmpty()) {
            this.assignedMembers.clear();
            this.assignedMembers.addAll(Arrays.asList(assignedMembersStr.split(", ")));
        }
    }

    // For ListView display
    @Override
    public String toString() {
        return title + " (" + status + ")";
    }
    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
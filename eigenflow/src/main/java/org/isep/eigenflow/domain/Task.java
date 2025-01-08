package org.isep.eigenflow.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Task {
    private String title;
    private String description;
    private boolean completed;
    private UUID uuid;
    private List<String> assignedMembers;

    public Task(String title) {
        this.title = title;
        this.completed = false;
        this.uuid = UUID.randomUUID();
        this.assignedMembers = new ArrayList<>();
    }

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

    @Override
    public String toString() {
        return "Task: " + title + "\nDescription: " + description +
                "\nAssigned Members: " + assignedMembers +
                "\nStatus: " + (completed ? "Completed" : "Pending");
    }
}

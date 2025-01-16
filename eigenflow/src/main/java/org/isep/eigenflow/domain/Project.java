package org.isep.eigenflow.domain;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.isep.eigenflow.repo.BaseRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project extends BaseRepository {
    private int projectId;
    private String projectName;
    private LocalDate deadline;
    private ArrayList<String> members;
    private ArrayList<String> tasks;
    private String status;
    private double budget;
    private double spentBudget;
    private List<Expense> expenses = new ArrayList<>();

    private DoubleProperty progress = new SimpleDoubleProperty();
    public double getProgress() { return progress.get(); }
    public DoubleProperty progressProperty() { return progress; }
    public void setProgress(double value) { progress.set(value); }

    public Project(int projectId, String projectName, LocalDate deadline) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.deadline = deadline;
        this.members = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.status = "ACTIVE";
    }


    public void addExpense(Expense expense) {
        this.expenses.add(expense);
        this.spentBudget += expense.amount();
    }




    public boolean isActive() {
        return "ACTIVE".equals(status);
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void editProject(String projectName, LocalDate deadline) {
        this.projectName = projectName;
        this.deadline = deadline;
    }

    public void addMember(String member) {
        members.add(member);
    }

    public String getMembersAsString() {
        return String.join(",", members);
    }

    public int getId() {
        return projectId;
    }

    public double getBudget() { return budget; }

    public double getSpentBudget() { return spentBudget; }

    public double getRemainingBudget() {
        return budget - spentBudget;
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

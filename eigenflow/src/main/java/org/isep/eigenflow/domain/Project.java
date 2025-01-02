package org.isep.eigenflow.domain;

import java.util.ArrayList;

public class Project {

    private int projectId;
    private String projectName;
    private String deadline;
    private ArrayList<String> members;
    private ArrayList<String> tasks;

    public Project(int projectId, String projectName, String deadline) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.deadline = deadline;
        this.members = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public void editProject(String projectName, String deadline) {
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

    @Override
    public String toString() {
        return "Project ID: " + this.projectId + "\n" +
               "Project Name: " + this.projectName + "\n" +
               "Deadline: " + this.deadline + "\n" +
               "Members: " + this.members + "\n" +
               "Tasks: " + this.tasks;
    }
}

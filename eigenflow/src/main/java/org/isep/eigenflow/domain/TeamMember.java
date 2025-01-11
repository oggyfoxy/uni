package org.isep.eigenflow.domain;

import java.util.ArrayList;
import java.util.List;

public class TeamMember {
    private final String name;
    private final String role;
    private final List<String> tasks;

    public TeamMember(String name, String role) {
        this.name = name;
        this.role = role;
        this.tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public List<String> getTasks() {
        return tasks;
    }

    public void addTask(String task) {
        tasks.add(task);
    }
}

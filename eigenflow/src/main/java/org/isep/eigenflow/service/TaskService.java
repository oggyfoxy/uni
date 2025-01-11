package org.isep.eigenflow.service;

import org.isep.eigenflow.domain.Task;
import org.isep.eigenflow.repo.TaskRepository;

import java.sql.SQLException;
import java.util.UUID;

public class TaskService {
    private final TaskRepository repository;

    public TaskService() {
        this.repository = new TaskRepository();
    }

    public void createTask(String title, String description, String member) throws SQLException {
        Task task = new Task(title);
        task.setDescription(description);
        task.setUuid(UUID.randomUUID());
        task.assignMember(member);
        repository.save(task);
    }
}
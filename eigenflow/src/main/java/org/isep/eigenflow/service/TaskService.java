package org.isep.eigenflow.service;

import org.isep.eigenflow.domain.Task;
import org.isep.eigenflow.repo.ProjectRepository;
import org.isep.eigenflow.repo.TaskRepository;

import java.util.List;

public class TaskService {
    private TaskRepository taskRepo;
    private ProjectRepository projectRepo;


    public TaskService() {
        this.taskRepo = new TaskRepository();
    }

    public void createTask(String title, String description, String assignee, Integer projectId) {
        Task task = new Task(title);
        task.setDescription(description);
        task.assignMember(assignee);
        taskRepo.save(task);
        if (projectId != null) {
            taskRepo.addTaskToProject(task.getUuid(), projectId);
        }
    }

    public List<Task> getTasksForProject(Integer projectId) {
        return projectId == null ?
                taskRepo.getUnassignedTasks() :
                taskRepo.getTasksByProject(projectId);
    }
}
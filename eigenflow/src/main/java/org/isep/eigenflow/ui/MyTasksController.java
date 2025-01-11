package org.isep.eigenflow.ui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.isep.eigenflow.domain.Personel;
import org.isep.eigenflow.domain.Task;
import org.isep.eigenflow.repo.PersonnelRepository;
import org.isep.eigenflow.repo.TaskRepository;

import java.sql.SQLException;
import java.util.List;

public class MyTasksController {
    @FXML
    private ComboBox<String> memberSelector;
    @FXML private ListView<Task> todoListView;
    @FXML private ListView<Task> inProgressListView;
    @FXML private ListView<Task> doneListView;

    @FXML private Label taskTitleLabel;
    @FXML private Label taskDescriptionLabel;
    @FXML private Label taskAssigneeLabel;
    @FXML private Label taskStatusLabel;

    private final PersonnelRepository personnelRepo = new PersonnelRepository();
    private final TaskRepository taskRepo = new TaskRepository();

    @FXML
    public void initialize() {
        loadMembers();
        setupListViews();
        setupMemberSelection();
    }

    private void loadMembers() {
        List<Personel> personnel = personnelRepo.getAllPersonnel();
        ObservableList<String> memberNames = FXCollections.observableArrayList();
        for (Personel p : personnel) {
            memberNames.add(p.getName());
        }
        memberSelector.setItems(memberNames);
    }

    private void setupListViews() {
        setupDragAndDrop(todoListView, Task.STATUS_TODO);
        setupDragAndDrop(inProgressListView, Task.STATUS_IN_PROGRESS);
        setupDragAndDrop(doneListView, Task.STATUS_DONE);
    }


    private void setupMemberSelection() {
        memberSelector.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        loadTasksForMember(newVal);
                    }
                });
    }

    private void setupDragAndDrop(ListView<Task> listView, String targetStatus) {
        // Enable dragging from the ListView
        listView.setOnDragDetected(event -> {
            Task selectedTask = listView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                Dragboard dragboard = listView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedTask.getUuid().toString()); // Use task UUID to identify it
                dragboard.setContent(content);
                event.consume();
            }
        });

        // Accept dragging into the ListView
        listView.setOnDragOver(event -> {
            if (event.getGestureSource() != listView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
                listView.setStyle("-fx-border-color: blue; -fx-border-width: 2px;"); // Highlight
            }
            event.consume();
        });

        listView.setOnDragExited(event -> {
            listView.setStyle(""); // Remove highlight
            event.consume();
        });


        // Handle dropping into the ListView
        listView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if (dragboard.hasString()) {
                String taskId = dragboard.getString(); // Retrieve the UUID of the task
                Task task = taskRepo.findByUUID(taskId); // Get the task from the database
                if (task != null) {
                    task.setStatus(targetStatus); // Update the task's status
                    try {
                        taskRepo.updateTask(task); // Save changes to the database
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    // Update the ListViews
                    loadTasksForMember(memberSelector.getSelectionModel().getSelectedItem());
                    success = true;
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }


    private void loadTasksForMember(String memberName) {
        // Clear existing items
        todoListView.getItems().clear();
        inProgressListView.getItems().clear();
        doneListView.getItems().clear();

        // Load from database
        List<Task> tasks = taskRepo.getTasksByAssignee(memberName);
        for (Task task : tasks) {
            switch (task.getStatus()) {
                case Task.STATUS_TODO -> todoListView.getItems().add(task);
                case Task.STATUS_IN_PROGRESS -> inProgressListView.getItems().add(task);
                case Task.STATUS_DONE -> doneListView.getItems().add(task);
                }

            }
        }

    private void showTaskDetails(Task task) {
        if (task != null) {
            taskTitleLabel.setText(task.getTitle());
            taskDescriptionLabel.setText(task.getDescription());
            taskAssigneeLabel.setText("Assigned to: " + task.getAssignedMembersAsString());
            taskStatusLabel.setText("Status: " + task.getStatus());
        } else {
            taskTitleLabel.setText("");
            taskDescriptionLabel.setText("");
            taskAssigneeLabel.setText("");
            taskStatusLabel.setText("");
        }
    }

}
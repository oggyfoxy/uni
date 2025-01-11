package org.isep.eigenflow.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class KanbanController {

    @FXML
    private ListView<String> todoList;

    @FXML
    private ListView<String> inProgressList;

    @FXML
    private ListView<String> doneList;

    @FXML
    public void initialize() {
        todoList.getItems().addAll("Task 1", "Task 2", "Task 3");
        setupDragAndDrop(todoList);
        setupDragAndDrop(inProgressList);
        setupDragAndDrop(doneList);
    }


    private void setupDragAndDrop(ListView<String> listView) {
        // Drag detected
        listView.setOnDragDetected(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Dragboard dragboard = listView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedItem);
                dragboard.setContent(content);
                event.consume();
            }
        });

        // Drag over target ListView
        listView.setOnDragOver(event -> {
            if (event.getGestureSource() != listView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        // Drop into target ListView
        listView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasString()) {
                String draggedItem = dragboard.getString();
                listView.getItems().add(draggedItem);

                // Remove item from source ListView
                ListView<String> source = (ListView<String>) event.getGestureSource();
                source.getItems().remove(draggedItem);
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });

        // Drag done (cleanup)
        listView.setOnDragDone(DragEvent::consume);
    }
}

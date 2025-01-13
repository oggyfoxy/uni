package org.isep.eigenflow.kanban;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

// Controller for handling the Kanban UI interactions
public class KanbanController {
    private final BoardService boardService = new BoardService();
    private final ColumnService columnService = new ColumnService();
    private final TaskService taskService = new TaskService();

    private Board board;

    @FXML
    private ListView<String> todoList;
    @FXML
    private ListView<String> inProgressList;
    @FXML
    private ListView<String> doneList;

    @FXML
    public void initialize() {
        // Initialize board and columns
        board = boardService.getBoardByName("Default Board");

        if (board.getColumns().isEmpty()) {
            board.addColumn(new Column("To Do"));
            board.addColumn(new Column("In Progress"));
            board.addColumn(new Column("Done"));
        }

        // Bind columns to list views
        bindColumnsToLists();

        // Configure drag-and-drop functionality
        configureDragAndDrop(todoList, board.getColumns().get(0));
        configureDragAndDrop(inProgressList, board.getColumns().get(1));
        configureDragAndDrop(doneList, board.getColumns().get(2));
    }

    private void bindColumnsToLists() {
        todoList.setItems(FXCollections.observableArrayList(board.getColumns().get(0).getTasks()));
        inProgressList.setItems(FXCollections.observableArrayList(board.getColumns().get(1).getTasks()));
        doneList.setItems(FXCollections.observableArrayList(board.getColumns().get(2).getTasks()));
    }

    private void configureDragAndDrop(ListView<String> listView, Column column) {
        ObservableList<String> listItems = listView.getItems();

        // Set custom cell factory for drag-and-drop
        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                    }
                };

                // Enable drag detection on cells
                cell.setOnDragDetected(event -> {
                    if (cell.getItem() == null) {
                        return;
                    }
                    Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(cell.getItem());
                    dragboard.setContent(content);
                    event.consume();
                });

                // Define drag-over behavior
                cell.setOnDragOver(event -> {
                    if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                // Handle drop events
                cell.setOnDragDropped(event -> {
                    Dragboard dragboard = event.getDragboard();
                    if (dragboard.hasString()) {
                        String draggedItem = dragboard.getString();
                        int index = cell.isEmpty() ? listItems.size() : cell.getIndex();

                        // Remove from source column
                        removeTaskFromAllColumns(draggedItem);

                        // Add to target column
                        column.getTasks().add(index, draggedItem);

                        // Update UI
                        listItems.add(index, draggedItem);

                        event.setDropCompleted(true);
                        refreshAllLists();
                    } else {
                        event.setDropCompleted(false);
                    }
                    event.consume();
                });

                return cell;
            }
        });

        // Handle drops on empty areas of the list view
        listView.setOnDragOver(event -> {
            if (event.getGestureSource() != listView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        listView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasString()) {
                String draggedItem = dragboard.getString();

                // Remove from source column
                removeTaskFromAllColumns(draggedItem);

                // Add to target column
                column.getTasks().add(draggedItem);

                // Update UI
                listItems.add(draggedItem);

                event.setDropCompleted(true);
                refreshAllLists();
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
    }

    private void removeTaskFromAllColumns(String task) {
        for (Column col : board.getColumns()) {
            col.getTasks().remove(task);
        }
    }

    private void refreshAllLists() {
        todoList.setItems(FXCollections.observableArrayList(board.getColumns().get(0).getTasks()));
        inProgressList.setItems(FXCollections.observableArrayList(board.getColumns().get(1).getTasks()));
        doneList.setItems(FXCollections.observableArrayList(board.getColumns().get(2).getTasks()));
    }
}

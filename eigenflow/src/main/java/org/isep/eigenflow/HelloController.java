package org.isep.eigenflow;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class HelloController {
    @FXML
    private ListView<String> todoList;
    @FXML
    private ListView<String> inProgressList;
    @FXML
    private ListView<String> doneList;

    @FXML
    private void handleNewTask() {
        todoList.getItems().add("New Task");
    }
}
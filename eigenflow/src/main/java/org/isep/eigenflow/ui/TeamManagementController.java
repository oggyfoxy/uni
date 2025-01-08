package org.isep.eigenflow.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TeamManagementController {

    @FXML
    private TextField textField;

    @FXML
    public void initialize() {
        // this will run once after the fxml is loaded
        System.out.println("profiles window opened.");
    }

    // add any event handlers or logic for the profiles window here
}

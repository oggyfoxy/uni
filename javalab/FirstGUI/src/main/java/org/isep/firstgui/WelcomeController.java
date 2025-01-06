package org.isep.firstgui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WelcomeController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private Label emailLabel;

    public void initData(String name, String email) {
        welcomeLabel.setText("Welcome " + name);
        emailLabel.setText("Your mails is : " + email);
    }
}
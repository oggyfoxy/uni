package org.isep.eigenflow.ui;


import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

public class SettingsController {

    @FXML
    private ToggleButton themeButton;

    @FXML
    private ChoiceBox<String> fontChoice;

    @FXML
    private Slider textSize;

    private static String themebase = "bright";
    private static String textbase = "system";
    private static double textsizebase = 14;

    private static Region rootRegion;
        
}

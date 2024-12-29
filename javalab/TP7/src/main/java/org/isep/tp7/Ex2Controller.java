// Ex2Controller.java
package org.isep.tp7;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Ex2Controller {
    @FXML
    private TextField number1Field;
    @FXML
    private TextField number2Field;
    @FXML
    private Label resultLabel;

    @FXML
    protected void onAddClick() {
        calculate("+");
    }

    @FXML
    protected void onSubtractClick() {
        calculate("-");
    }

    @FXML
    protected void onMultiplyClick() {
        calculate("*");
    }

    @FXML
    protected void onDivideClick() {
        calculate("/");
    }

    private void calculate(String operation) {
        try {
            double num1 = Double.parseDouble(number1Field.getText());
            double num2 = Double.parseDouble(number2Field.getText());
            double result = 0;
            switch (operation) {
                case "+" -> result = num1 + num2;
                case "-" -> result = num1 - num2;
                case "*" -> result = num1 * num2;
                case "/" -> {
                    if (num2 == 0) {
                        showError("cant divide by 0");
                        return;
                    }
                    result = num1 / num2;
                }
            }
            resultLabel.setText("Result: " + result);
        } catch (NumberFormatException e) {
            showError("enter valid number please");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

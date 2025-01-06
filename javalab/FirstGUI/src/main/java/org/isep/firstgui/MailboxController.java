package org.isep.firstgui;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class MailboxController {
    @FXML private ListView<String> messageList;
    @FXML
    private void handleViewMessage() {
        showAlert("Afficher le message sélectionné");
    }

    @FXML
    private void handleDeleteMessage()
    {
        messageList.getItems().remove(messageList.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleNewMessage() {
        showAlert("Rédiger un nouveau message");
    }

    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }
}

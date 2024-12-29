package org.isep.tp7;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class Ex4Controller implements Initializable {
    @FXML
    private GridPane chessboard;
    private static final int SQUARE_SIZE = 50;
    private static final int BOARD_SIZE = 8;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createChessboard();
    }

    private void createChessboard() {
        chessboard.getChildren().clear();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
                if ((row + col) % 2 == 0) {
                    square.setFill(Color.WHITE);
                } else {
                    square.setFill(Color.BLACK);
                }

                // borders
                square.setStroke(Color.GRAY);
                square.setStrokeWidth(0.5);

                chessboard.add(square, col, row);
            }
        }
    }
}
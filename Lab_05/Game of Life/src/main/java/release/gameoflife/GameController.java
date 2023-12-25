package release.gameoflife;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameController {

    @FXML
    private GridPane grid;

    @FXML
    private Button stepButton;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button clearButton;

    private GameOfLife game;
    private Timeline timeline;

    @FXML
    public void initialize() {
        game = new GameOfLife(64, 64);
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                Rectangle cell = new Rectangle(10, 10);
                int finalI = i;
                int finalJ = j;
                cell.setOnMouseClicked(event -> {
                    if (event.getButton().toString().equals("PRIMARY")) {
                        cell.setFill(javafx.scene.paint.Color.BLACK);
                        game.setCellAlive(finalI, finalJ, true);
                    } else if (event.getButton().toString().equals("SECONDARY")) {
                        cell.setFill(javafx.scene.paint.Color.WHITE);
                        game.setCellAlive(finalI, finalJ, false);
                    }
                });
                grid.add(cell, i, j);
            }
        }

        stepButton.setOnAction(event -> step());
        startButton.setOnAction(event -> start());
        stopButton.setOnAction(event -> stop());
        clearButton.setOnAction(event -> clear());

        timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> step()));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void step() {
        game.step();
        updateGrid();
    }

    private void start() {
        timeline.play();
    }

    private void stop() {
        timeline.stop();
    }

    private void clear() {
        game = new GameOfLife(64, 64);
        updateGrid();
    }

    private void updateGrid() {
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                Rectangle cell = (Rectangle) grid.getChildren().get(i * 64 + j);
                cell.setFill(game.isCellAlive(i, j) ? javafx.scene.paint.Color.BLACK : javafx.scene.paint.Color.WHITE);
            }
        }
    }
}

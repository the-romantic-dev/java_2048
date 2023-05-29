package org.example.controller;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.example.model.GameModel;
import org.example.view.MainFrame;
import org.example.view.Tile;
import java.util.List;

public class GameController {
    private final GameModel model;
    private final MainFrame frame;

    public GameController(MainFrame frame) {
        model = GameModel.getInstance();
        this.frame = frame;
        setOnKeyPressedListeners();

        frame.onRestartButtonClick(event -> startNewGame());
    }

    public void startNewGame() {
        model.startNewGame();
        frame.hideEndGameLabel();
        updateView();
    }
    public void updateView() {
        updateGameField();
        updateScores();
        tryGameOver();
    }

    private void updateGameField() {
        int[][] grid = model.getGrid();
        List<Node> oldTiles = frame.getGameField().getChildren();

        oldTiles.forEach(tile -> {
            int column = GridPane.getColumnIndex(tile);
            int row = GridPane.getRowIndex(tile);
            ((Tile) tile).setValue(grid[row][column]);
        });
    }

    private void updateScores() {
        frame.setScoresLabelValue(model.getScores());
    }

    private void tryGameOver() {
        if (!model.isMoveAvailable()) {
            frame.showEndGameLabel();
        }
    }

    private void setOnKeyPressedListeners() {
        frame.getMainScene().setOnKeyPressed(event -> {
            if (event.getCode().isArrowKey()){
                GameModel.MoveDirection direction = GameModel.MoveDirection.valueOf(event.getCode().getName().toUpperCase());
                model.move(direction);
            }
            updateView();
        });
    }
}

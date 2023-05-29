package org.example.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.example.model.GameModel;

public class MainFrame {
    private static final int WINDOW_WIDTH = GameModel.GRID_SIZE * Tile.TILE_SIZE + 200;
    private static final int WINDOW_HEIGHT = GameModel.GRID_SIZE  * Tile.TILE_SIZE + 200;
    public static final int GAP = 10;

    private GridPane gameField;
    GridPane emptyGrid;
    public Label scoresLabel;
    private Button restartButton;
    private Scene mainScene;
    private Stage primaryStage;
    private EndGameLabel endGameLabel;

    public GridPane getGameField() {
        return gameField;
    }

    public void setScoresLabelValue(int scores) {
        scoresLabel.setText("Scores: " + scores);
    }

    public void onRestartButtonClick(EventHandler<? super MouseEvent> handler) {
        restartButton.setOnMouseClicked(handler);
    }
    public void showEndGameLabel() {
        endGameLabel.setVisible(true);
    }

    public void hideEndGameLabel() {
        endGameLabel.setVisible(false);
    }
    public Scene getMainScene() {
        return mainScene;
    }
    public MainFrame (Stage primaryStage) {
        this.primaryStage = primaryStage;
        endGameLabel = new EndGameLabel();
        endGameLabel.setVisible(false);

        setupPrimaryStage();
        setupGameField();
        setupEmptyGrid();
        setupScoreLabel();
        setupRestartButton();

        StackPane gameFieldStack = new StackPane(emptyGrid, gameField);

        HBox hBoxPane = new HBox();
        Region hBoxSpacer = new Region();
        HBox.setHgrow(hBoxSpacer, Priority.ALWAYS);
        hBoxPane.getChildren().addAll(scoresLabel, hBoxSpacer, restartButton);

        VBox vBoxPane = new VBox();
        Region vBoxSpacer = new Region();
        vBoxSpacer.setMinHeight(30);
        vBoxPane.getChildren().addAll(hBoxPane, vBoxSpacer, gameFieldStack);

        StackPane topLevelPane = new StackPane();
        topLevelPane.getChildren().addAll(vBoxPane, endGameLabel);

        mainScene = new Scene(topLevelPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(mainScene);
    }
    private void setupRestartButton() {
        restartButton = new Button();
        restartButton.setText("Restart");
        restartButton.setFont(Font.font("Comic Sans", 30));
        restartButton.setAlignment(Pos.BASELINE_RIGHT);
        restartButton.setFocusTraversable(false);
    }

    private void setupEmptyGrid() {
        emptyGrid = new GridPane();
        emptyGrid.setAlignment(Pos.CENTER);
        emptyGrid.setHgap(GAP);
        emptyGrid.setVgap(GAP);

        for (int row = 0; row < GameModel.GRID_SIZE; row++) {
            for (int column = 0; column < GameModel.GRID_SIZE; column++) {
                Rectangle tile = new Rectangle(Tile.TILE_SIZE, Tile.TILE_SIZE);
                tile.setFill(Color.valueOf("#CDC1B4"));
                emptyGrid.add(tile, column, row);
            }
        }
    }

    private void setupGameField() {
        gameField = new GridPane();
        gameField.setAlignment(Pos.CENTER);
        gameField.setHgap(GAP);
        gameField.setVgap(GAP);

        for (int row = 0; row < GameModel.GRID_SIZE; row++) {
            for (int column = 0; column < GameModel.GRID_SIZE; column++) {
                Tile tile = new Tile(0);
                gameField.add(tile, column, row);
            }
        }
    }

    private void setupScoreLabel() {
        scoresLabel = new Label("Score: 0");
        scoresLabel.setFont(Font.font("Arial", 30));
        scoresLabel.setTextAlignment(TextAlignment.CENTER);
    }

    private void setupPrimaryStage() {
        primaryStage.setTitle("2048");
        primaryStage.setResizable(false);
    }

    public void show() {
        primaryStage.show();
    }
}

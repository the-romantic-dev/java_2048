package org.example.model;

import javafx.util.Pair;
import org.example.Util;

import java.util.*;

public class GameModel {
    public enum MoveDirection {
        UP, LEFT, RIGHT, DOWN
    }
    public static final int GRID_SIZE = 4;
    private int scores;
    public void setScores(int scores) {
        this.scores = scores;
    }
    public int getScores() {
        return scores;
    }
    private final int[][] grid;
    public int[][] getGrid() {
        return grid;
    }
    private static volatile GameModel instance;

    private GameModel() {
        grid = new int[GRID_SIZE][GRID_SIZE];
        startNewGame();
    }

    public static GameModel getInstance() {
        if (instance == null) {
            synchronized (GameModel.class) {
                if (instance == null) {
                    instance = new GameModel();
                }
            }
        }
        return instance;
    }

    public void move(MoveDirection direction) {
        int[][] gridBefore = Util.cloneMatrix(grid);

        switch (direction) {
            case UP -> moveUp();
            case DOWN -> moveDown();
            case RIGHT -> moveRight();
            case LEFT -> moveLeft();
        }

        if (!Arrays.deepEquals(gridBefore, grid)) addNewTile();
    }

    private void mergeTile(
            int targetTileRow, int targetTileColumn,
            int sourceTileRow, int sourceTileColumn) {
        grid[targetTileRow][targetTileColumn] += grid[sourceTileRow][sourceTileColumn];
        grid[sourceTileRow][sourceTileColumn] = 0;

        scores += grid[targetTileRow][targetTileColumn];
    }

    private void moveTile(
            int targetTileRow, int targetTileColumn,
            int sourceTileRow, int sourceTileColumn) {
        grid[targetTileRow][targetTileColumn] = grid[sourceTileRow][sourceTileColumn];
        grid[sourceTileRow][sourceTileColumn] = 0;
    }

    public void moveRight() {
        for (int i = 0; i < GRID_SIZE; i++) {
            int lastRowPosition = GRID_SIZE - 1;
            int mergeCounter = 0;
            for (int j = lastRowPosition; j >= 0; j--) {
                if (grid[i][j] != 0) {
                    if (lastRowPosition + 1 < GRID_SIZE - mergeCounter && grid[i][lastRowPosition + 1] == grid[i][j]) {
                        mergeTile(i, lastRowPosition + 1, i, j);
                        mergeCounter++;
                    } else {
                        if (j != lastRowPosition) {
                            moveTile(i, lastRowPosition, i, j);
                        }
                        lastRowPosition--;
                    }

                }
            }
        }
    }

    public void moveLeft() {
        for (int i = 0; i < GRID_SIZE; i++) {
            int lastRowPosition = 0;
            int mergeCounter = 0;
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] != 0) {
                    if (lastRowPosition - 1 >= mergeCounter && grid[i][lastRowPosition - 1] == grid[i][j]) {
                        mergeTile(i, lastRowPosition - 1, i, j);
                        mergeCounter++;
                    } else {
                        if (j != lastRowPosition) {
                            moveTile(i, lastRowPosition, i, j);
                        }
                        lastRowPosition++;
                    }
                }
            }
        }
    }

    public void moveUp() {
        for (int j = 0; j < GRID_SIZE; j++) {
            int lastColumnPosition = 0;
            int mergeCounter = 0;
            for (int i = 0; i < GRID_SIZE; i++) {
                if (grid[i][j] != 0) {
                    if (lastColumnPosition - 1 >= mergeCounter && grid[lastColumnPosition - 1][j] == grid[i][j]) {
                        mergeTile(lastColumnPosition - 1, j, i, j);
                        mergeCounter++;
                    } else {
                        if (i != lastColumnPosition) {
                            moveTile(lastColumnPosition, j, i, j);
                        }
                        lastColumnPosition++;
                    }

                }
            }
        }
    }

    public void moveDown() {
        for (int j = 0; j < GRID_SIZE; j++) {
            int lastColumnPosition = GRID_SIZE - 1;
            int mergeCounter = 0;
            for (int i = lastColumnPosition; i >= 0; i--) {
                if (grid[i][j] != 0) {
                    if (lastColumnPosition + 1 < GRID_SIZE - mergeCounter && grid[lastColumnPosition + 1][j] == grid[i][j]) {
                        mergeTile(lastColumnPosition + 1, j, i, j);
                        mergeCounter++;
                    } else {
                        if (i != lastColumnPosition) {
                            moveTile(lastColumnPosition, j, i, j);
                        }
                        lastColumnPosition--;
                    }
                }
            }
        }
    }


    public List<Pair<Integer, Integer>> getEmptyTiles() {
        List<Pair<Integer, Integer>> list = new ArrayList<>();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 0) {
                    list.add(new Pair<>(i, j));
                }
            }
        }

        return list;
    }

    public boolean isMoveAvailable() {
        if (getEmptyTiles().size() > 0) return true;

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (i + 1 < GRID_SIZE && grid[i + 1][j] == grid[i][j]) return true;
                if (i - 1 >= 0 && grid[i - 1][j] == grid[i][j]) return true;
                if (j + 1 < GRID_SIZE && grid[i][j + 1] == grid[i][j]) return true;
                if (j - 1 >= 0 && grid[i][j - 1] == grid[i][j]) return true;
            }
        }

        return false;
    }



    public void addNewTile() {
        List<Pair<Integer, Integer>> emptyTiles = getEmptyTiles();
        if (emptyTiles.size() == 0) return;

        Random random = new Random();

        int value = 2;
        if (random.nextDouble() <= 0.1) {
            value = 4;
        }
        Pair<Integer, Integer> randomEmptyTileCords = emptyTiles.get(random.nextInt(emptyTiles.size()));
        int row = randomEmptyTileCords.getKey();
        int column = randomEmptyTileCords.getValue();
        grid[row][column] = value;
    }

    private void cleanGrid() {
        for (int[] row : grid) {
            Arrays.fill(row, 0);
        }
    }

    public void startNewGame() {
        cleanGrid();
        setScores(0);
        addNewTile();
    }
}

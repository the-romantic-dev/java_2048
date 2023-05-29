package org.example.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    enum MoveDirection {
        UP, LEFT, RIGHT, DOWN
    }

    private int[][] convertStringToMatrix(String matrixString) {
        String[] rows = matrixString.trim().split("\\r?\\n");
        int rowCount = rows.length;
        int columnCount = rows[0].split("\\s+").length;
        int[][] matrix = new int[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            String[] elements = rows[i].trim().split("\\s+");
            for (int j = 0; j < columnCount; j++) {
                matrix[i][j] = Integer.parseInt(elements[j]);
            }
        }

        return matrix;
    }

    private void setNewGrid(GameModel model, int[][] newGrid) {
        int[][] oldGrid = model.getGrid();
        for (int i = 0; i < oldGrid.length; i++) {
            System.arraycopy(newGrid[i], 0, oldGrid[i], 0, oldGrid[i].length);
        }
    }

    private boolean isMatricesEquals(int[][] matrix1, int[][] matrix2) {
        return Arrays.deepEquals(matrix1, matrix2);
    }

    private void assertMove(int[][] testGrid, int[][] expectedGird, MoveDirection direction) {
        GameModel model = GameModel.getInstance();
        setNewGrid(model, testGrid);
        switch (direction) {
            case UP -> model.moveUp();
            case DOWN -> model.moveDown();
            case RIGHT -> model.moveRight();
            case LEFT -> model.moveLeft();
        }
        assertTrue(isMatricesEquals(model.getGrid(), expectedGird));
    }

    private void assertIsMoveAvailable(int[][] testGrid, boolean expected) {
        GameModel model = GameModel.getInstance();
        setNewGrid(model, testGrid);

        assertEquals(model.isMoveAvailable(), expected);
    }

    @Test
    public void singletonTest() {
        GameModel instance1 = GameModel.getInstance();
        GameModel instance2 = GameModel.getInstance();
        GameModel instance3 = GameModel.getInstance();

        assertSame(instance1, instance2);
        assertSame(instance1, instance3);
        assertSame(instance2, instance3);
    }

    @Test
    public void testMoveRight() {
        int[][] testGrid1 = convertStringToMatrix(
                """
                        0 0 0 2
                        0 0 0 4
                        0 0 0 0
                        0 0 0 8
                                """
        );
        int[][] testGrid2 = convertStringToMatrix(
                """
                        2 0 0 0
                        0 0 0 0
                        0 0 0 0
                        0 0 0 2
                                """
        );
        int[][] resultGrid2 = convertStringToMatrix(
                """
                        0 0 0 2
                        0 0 0 0
                        0 0 0 0
                        0 0 0 2
                                """
        );

        int[][] testGrid3 = convertStringToMatrix(
                """
                        4 0 0 2
                        0 8 0 8
                        32 32 16 16
                        2 2 2 2
                                """
        );
        int[][] resultGrid3 = convertStringToMatrix(
                """
                        0 0 4 2
                        0 0 0 16
                        0 0 64 32
                        0 0 4 4
                                """
        );

        assertMove(testGrid1, testGrid1, MoveDirection.RIGHT);
        assertMove(testGrid2, resultGrid2, MoveDirection.RIGHT);
        assertMove(testGrid3, resultGrid3, MoveDirection.RIGHT);
    }

    @Test
    public void testMoveLeft() {
        int[][] testGrid1 = convertStringToMatrix(
                """
                        2 0 0 0
                        4 0 0 0
                        0 0 0 0
                        8 0 0 0
                                """
        );
        int[][] testGrid2 = convertStringToMatrix(
                """
                        0 0 0 2
                        0 0 2 0
                        0 4 0 8
                        0 0 0 2
                                """
        );
        int[][] resultGrid2 = convertStringToMatrix(
                """
                        2 0 0 0
                        2 0 0 0
                        4 8 0 0
                        2 0 0 0
                                """
        );

        int[][] testGrid3 = convertStringToMatrix(
                """
                        4 0 0 2
                        0 8 0 8
                        16 16 32 32
                        2 2 2 2
                                """
        );
        int[][] resultGrid3 = convertStringToMatrix(
                """
                        4 2 0 0
                        16 0 0 0
                        32 64 0 0
                        4 4 0 0
                                """
        );

        assertMove(testGrid1, testGrid1, MoveDirection.LEFT);
        assertMove(testGrid2, resultGrid2, MoveDirection.LEFT);
        assertMove(testGrid3, resultGrid3, MoveDirection.LEFT);
    }

    @Test
    public void testMoveDown() {
        int[][] testGrid1 = convertStringToMatrix(
                """
                        0 0 0 0
                        0 0 0 0
                        0 0 0 0
                        8 0 4 2
                                """
        );
        int[][] testGrid2 = convertStringToMatrix(
                """
                        0 16 0 2
                        0 0 2 2
                        0 4 0 8
                        2 0 0 2
                                """
        );
        int[][] resultGrid2 = convertStringToMatrix(
                """
                        0 0 0 0
                        0 0 0 4
                        0 16 0 8
                        2 4 2 2
                                """
        );

        int[][] testGrid3 = convertStringToMatrix(
                """
                        16 8 2 2
                        16 8 0 8
                        32 4 2 32
                        32 4 0 2
                                """
        );
        int[][] resultGrid3 = convertStringToMatrix(
                """
                        0 0 0 2
                        0 0 0 8
                        32 16 0 32
                        64 8 4 2
                                """
        );

        assertMove(testGrid1, testGrid1, MoveDirection.DOWN);
        assertMove(testGrid2, resultGrid2, MoveDirection.DOWN);
        assertMove(testGrid3, resultGrid3, MoveDirection.DOWN);
    }

    @Test
    public void testMoveUp() {
        int[][] testGrid1 = convertStringToMatrix(
                """
                        8 0 4 2
                        0 0 0 0
                        0 0 0 0
                        0 0 0 0
                                """
        );
        int[][] testGrid2 = convertStringToMatrix(
                """
                        0 16 0 2
                        0 4 2 2
                        0 4 0 8
                        2 0 0 2
                                """
        );
        int[][] resultGrid2 = convertStringToMatrix(
                """
                        2 16 2 4
                        0 8 0 8
                        0 0 0 2
                        0 0 0 0
                                """
        );

        int[][] testGrid3 = convertStringToMatrix(
                """
                        16 8 2 2
                        16 8 0 8
                        32 4 2 32
                        32 4 0 2
                                """
        );
        int[][] resultGrid3 = convertStringToMatrix(
                """
                        32 16 4 2
                        64 8 0 8
                        0 0 0 32
                        0 0 0 2
                                """
        );

        assertMove(testGrid1, testGrid1, MoveDirection.UP);
        assertMove(testGrid2, resultGrid2, MoveDirection.UP);
        assertMove(testGrid3, resultGrid3, MoveDirection.UP);
    }

    @Test
    public void testIsMoveAvailable() {
        int[][] testGrid1 = convertStringToMatrix(
                """
                        0 0 0 2
                        0 0 0 0
                        0 0 0 0
                        0 0 0 0
                                """
        );
        int[][] testGrid2 = convertStringToMatrix(
                """
                        2 2 2 2
                        2 2 2 2
                        2 2 2 2
                        2 2 2 2
                                """
        );
        int[][] testGrid3 = convertStringToMatrix(
                """
                        2 4 2 4
                        4 2 4 2
                        2 4 2 4
                        4 2 4 2
                                """
        );
        int[][] testGrid4 = convertStringToMatrix(
                """
                        0 4 2 4
                        4 2 4 2
                        2 4 2 4
                        4 2 4 2
                                """
        );
        int[][] testGrid5 = convertStringToMatrix(
                """
                        4 4 2 4
                        4 2 4 2
                        2 4 2 4
                        4 2 4 2
                                """
        );

        assertIsMoveAvailable(testGrid1, true);
        assertIsMoveAvailable(testGrid2, true);
        assertIsMoveAvailable(testGrid3, false);
        assertIsMoveAvailable(testGrid4, true);
        assertIsMoveAvailable(testGrid5, true);
    }

    @Test
    public void testAddNewTile() {
        GameModel model = GameModel.getInstance();
        model.startNewGame();
        int emptyTilesCountBefore = model.getEmptyTiles().size();
        model.addNewTile();
        int emptyTilesCountAfter = model.getEmptyTiles().size();

        assertEquals(emptyTilesCountBefore, emptyTilesCountAfter + 1);
    }
}
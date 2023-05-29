package org.example;

import org.example.model.GameModel;

public class Util {
    public static int[][] cloneMatrix(int[][] matrix) {
        int[][] result = new int[matrix.length][matrix.length];
        for (int i = 0; i < GameModel.GRID_SIZE; i++) {
            result[i] = matrix[i].clone();
        }
        return result;
    }
}

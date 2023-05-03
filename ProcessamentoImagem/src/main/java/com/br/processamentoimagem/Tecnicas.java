package com.br.processamentoimagem;

import java.io.Serializable;

/**
 *
 * @author Piaia
 */
public class Tecnicas implements Serializable {

    private static int getValueInBounds(int value) {
        if (value < 0) {
            return 0;
        }
        if (value > 255) {
            return 255;
        }
        return value;
    }

    private static int getMaxValue(int value1, int value2) {
        if (value1 > value2) {
            return value1;
        }
        return value2;
    }

    public static int[][] rgbToGray(int[][] red, int[][] green, int[][] blue) {
        int width = red.length;
        int height = red[0].length;

        int[][] grayMatrix = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int valorFinal;
                int valMatrizR = red[x][y];
                int valMatrizG = green[x][y];
                int valMatrizB = blue[x][y];
                valorFinal = (valMatrizR + valMatrizG + valMatrizB) / 3;
                valorFinal = getValueInBounds(valorFinal);
                grayMatrix[x][y] = valorFinal;
            }
        }
        return grayMatrix;
    }

    public static int[][] rgbToBinary(int[][] red, int[][] green, int[][] blue, int limit) {
        int width = red.length;
        int height = red[0].length;

        int[][] binaryMatrix = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int valorFinal;
                int valMatrizR = red[x][y];
                int valMatrizG = green[x][y];
                int valMatrizB = blue[x][y];
                valorFinal = (valMatrizR + valMatrizG + valMatrizB) / 3;
                valorFinal = getValueInBounds(valorFinal);
                if (valorFinal >= limit) {
                    valorFinal = 0;
                } else {
                    valorFinal = 1;
                }
                binaryMatrix[x][y] = valorFinal;
            }
        }
        return binaryMatrix;
    }

    private static int[][] operationInTwoMatrixes(int[][] matrix1, int[][] matrix2, Operation operation) {
        int maxWidth = getMaxValue(matrix1.length, matrix2.length);
        int minWidth = getMaxValue(matrix1.length, matrix2.length);
        int maxHeight = getMaxValue(matrix1[0].length, matrix2[0].length);
        int minHeight = getMaxValue(matrix1[0].length, matrix2[0].length);
        int[][] result = new int[maxWidth][maxHeight];
        int[][] widerMatrix;
        int[][] tallerMatrix;
        if (matrix1.length > matrix2.length) {
            widerMatrix = matrix1;
        } else {
            widerMatrix = matrix2;
        }
        if (matrix1[0].length > matrix2[0].length) {
            tallerMatrix = matrix1;
        } else {
            tallerMatrix = matrix2;
        }

        for (int y = 0; y < minHeight; y++) {
            for (int x = 0; x < minWidth; x++) {
                // Faz as operações
                result[x][y] = getValueInBounds(operation.getResult(matrix1[x][y], matrix2[x][y]));
            }
            for (int x1 = minWidth; x1 < maxWidth; x1++) {
                // Preenche os valores a direita com os valores da matriz mais larga
                result[x1][y] = widerMatrix[x1][y];
            }
        }
        // Preenche os valores embaixo com os valores da matriz mais alta
        for (int y1 = minHeight; y1 < maxHeight; y1++) {
            for (int x = 0; x < tallerMatrix.length; x++) {
                result[x][y1] = tallerMatrix[x][y1];
            }
        }
        return result;
    }

}

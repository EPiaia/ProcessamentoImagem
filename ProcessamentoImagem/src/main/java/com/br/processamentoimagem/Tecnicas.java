package com.br.processamentoimagem;

import java.io.Serializable;

/**
 *
 * @author Piaia
 */
public class Tecnicas implements Serializable {

    public static int getValueInBounds(int value) {
        if (value < 0) {
            return 0;
        }
        if (value > 255) {
            return 255;
        }
        return value;
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

    public static Imagem rgbBrigthness(int[][] red, int[][] green, int[][] blue, double bright) {
        int width = red.length;
        int height = red[0].length;

        Imagem resultado = new Imagem(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int valMatrizR = red[x][y];
                int valMatrizG = green[x][y];
                int valMatrizB = blue[x][y];

                int valR = (int) Math.round(valMatrizR * bright);
                valR = getValueInBounds(valR);
                int valG = (int) Math.round(valMatrizG * bright);
                valG = getValueInBounds(valG);
                int valB = (int) Math.round(valMatrizB * bright);
                valB = getValueInBounds(valB);

                resultado.getRed()[x][y] = valR;
                resultado.getGreen()[x][y] = valG;
                resultado.getBlue()[x][y] = valB;
            }
        }

        return resultado;
    }
}

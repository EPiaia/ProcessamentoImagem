package com.br.processamentoimagem;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

    private static int getMinValue(int value1, int value2) {
        if (value1 > value2) {
            return value2;
        }
        return value1;
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

    public static BufferedImage getGrayImage(int[][] grayMatrix) {
        int width = grayMatrix.length;
        int height = grayMatrix[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int grayValue = grayMatrix[x][y];
                // Setando valores do RED (16) GREEN (8) e BLUE
                int rgbValue = (grayValue << 16) | (grayValue << 8) | grayValue;
                image.setRGB(x, y, rgbValue);
            }
        }
        return image;
    }

    private static int[][] operationInTwoMatrixes(int[][] matrix1, int[][] matrix2, Integer coeficient, Operation operation) {
        int maxWidth = getMaxValue(matrix1.length, matrix2.length);
        int minWidth = getMinValue(matrix1.length, matrix2.length);
        int maxHeight = getMaxValue(matrix1[0].length, matrix2[0].length);
        int minHeight = getMinValue(matrix1[0].length, matrix2[0].length);
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
                int opResult;
                if (coeficient == null) {
                    opResult = operation.getResult(matrix1[x][y], matrix2[x][y]);
                } else {
                    opResult = operation.getResult(matrix1[x][y], matrix2[x][y], coeficient);
                }
                result[x][y] = getValueInBounds(opResult);
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

    private static int[][] operationSingleMatrix(int[][] matrix, Integer value, Operation operation) {
        int width = matrix.length;
        int height = matrix[0].length;
        int[][] result = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Faz as operações
                result[x][y] = getValueInBounds(operation.getResult(matrix[x][y], value));
            }
        }
        return result;
    }

    private static int[][] filterSingleMatrix(int[][] matrix, Integer bounds, Filter filter) {
        int width = matrix.length;
        int height = matrix[0].length;
        int[][] filteredMatrix = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Integer[][] focus = getFocus(matrix, bounds, x, y);
                filteredMatrix[x][y] = filter.getResult(focus);
            }
        }

        return filteredMatrix;
    }

    private static Integer[][] getFocus(int[][] matrix, Integer bounds, int posX, int posY) {
        int focusSize = bounds * 2 + 1;
        Integer[][] focus = new Integer[focusSize][focusSize];

        for (int y = 0; y < focusSize; y++) {
            for (int x = 0; x < focusSize; x++) {
                int posFocusX = posX + (x - bounds);
                int posFocusY = posY + (y - bounds);
                if (isPositionValid(matrix, posFocusX, posFocusY)) {
                    focus[x][y] = matrix[posFocusX][posFocusY];
                }
            }
        }
        return focus;
    }

    private static boolean isPositionValid(int[][] matrix, int posX, int posY) {
        int width = matrix.length;
        int height = matrix[0].length;

        return posX >= 0 && posX < width && posY >= 0 && posY < height;
    }

    public static Map<Integer, Integer> getImageHistogram(Imagem image) {
        Map<Integer, Integer> histogram = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            histogram.put(i, 0);
        }
        int[][] grayMatrix = rgbToGray(image.getRed(), image.getGreen(), image.getBlue());
        int width = grayMatrix.length;
        int height = grayMatrix[0].length;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = grayMatrix[x][y];
                int histoCount = histogram.get(pixelValue);
                histogram.put(pixelValue, histoCount + 1);
            }
        }
        return histogram;
    }

    public static int[][] getEqualizedImage(Imagem image) {
        Map<Integer, Integer> histogram = getImageHistogram(image);
        int[][] grayMatrix = rgbToGray(image.getRed(), image.getGreen(), image.getBlue());

        int width = grayMatrix.length;
        int height = grayMatrix[0].length;
        int[][] resultMatrix = new int[width][height];

        int cfdMin = getCumulativeFrequency(histogram, 0);
        int mxn = width * height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int cfd = getCumulativeFrequency(histogram, grayMatrix[x][y]);
                double result = Math.floor((cfd - cfdMin / mxn - cfdMin) * (255 - 1));
                resultMatrix[x][y] = (int) Math.round(result);
            }
        }
        return resultMatrix;
    }

    private static int getCumulativeFrequency(Map<Integer, Integer> histogram, int value) {
        int count = 0;
        for (int i = 0; i < value; i++) {
            count += histogram.get(i);
        }
        return count;
    }

    private static Imagem doOperationInImages(Imagem image1, Imagem image2, Integer coeficient, Operation operation) {
        Imagem imageResult = new Imagem();
        imageResult.setRed(operationInTwoMatrixes(image1.getRed(), image2.getRed(), coeficient, operation));
        imageResult.setGreen(operationInTwoMatrixes(image1.getGreen(), image2.getGreen(), coeficient, operation));
        imageResult.setBlue(operationInTwoMatrixes(image1.getBlue(), image2.getBlue(), coeficient, operation));
        imageResult.setAlpha(operationInTwoMatrixes(image1.getAlpha(), image2.getAlpha(), coeficient, operation));
        return imageResult;
    }

    private static Imagem doOperationInImage(Imagem image, Integer value, Operation operation) {
        Imagem imageResult = new Imagem();
        imageResult.setRed(operationSingleMatrix(image.getRed(), value, operation));
        imageResult.setGreen(operationSingleMatrix(image.getGreen(), value, operation));
        imageResult.setBlue(operationSingleMatrix(image.getBlue(), value, operation));
        return imageResult;
    }

    private static Imagem doOperationNOTInImage(Imagem image) {
        Imagem imageResult = new Imagem();
        imageResult.setRed(operationSingleMatrix(image.getRed(), null, Operation.NOT));
        imageResult.setGreen(operationSingleMatrix(image.getGreen(), null, Operation.NOT));
        imageResult.setBlue(operationSingleMatrix(image.getBlue(), null, Operation.NOT));
        return imageResult;
    }

    public static Imagem sumImages(Imagem image1, Imagem image2) {
        return doOperationInImages(image1, image2, null, Operation.SUM);
    }

    public static Imagem sumValue(Imagem image, int value) {
        return doOperationInImage(image, value, Operation.SUM);
    }

    public static Imagem subtractImages(Imagem image1, Imagem image2) {
        return doOperationInImages(image1, image2, null, Operation.SUBTRACT);
    }

    public static Imagem subtractValue(Imagem image, int value) {
        return doOperationInImage(image, value, Operation.SUBTRACT);
    }

    public static Imagem multiplyImages(Imagem image1, Imagem image2) {
        return doOperationInImages(image1, image2, null, Operation.MULTIPLICATION);
    }

    public static Imagem multiplyValue(Imagem image, int value) {
        return doOperationInImage(image, value, Operation.MULTIPLICATION);
    }

    public static Imagem divideImages(Imagem image1, Imagem image2) {
        return doOperationInImages(image1, image2, null, Operation.DIVISION);
    }

    public static Imagem divideValue(Imagem image, int value) {
        return doOperationInImage(image, value, Operation.DIVISION);
    }

    public static Imagem operationANDImages(Imagem image1, Imagem image2) {
        return doOperationInImages(image1, image2, null, Operation.AND);
    }

    public static Imagem operationORImages(Imagem image1, Imagem image2) {
        return doOperationInImages(image1, image2, null, Operation.OR);
    }

    public static Imagem operationXORImages(Imagem image1, Imagem image2) {
        return doOperationInImages(image1, image2, null, Operation.XOR);
    }

    public static Imagem operationNOTImage(Imagem image1) {
        return doOperationNOTInImage(image1);
    }

    public static Imagem blendImages(Imagem image1, Imagem image2, Integer coeficient) {
        return doOperationInImages(image1, image2, coeficient, Operation.BLENDING);
    }

    public static Imagem negativeImage(Imagem image) {
        return doOperationInImage(image, null, Operation.NEGATIVE);
    }

    public static Imagem applyFilterInImage(Imagem image, int bounds, Filter filter) {
        Imagem imageResult = new Imagem();
        imageResult.setRed(filterSingleMatrix(image.getRed(), bounds, filter));
        imageResult.setGreen(filterSingleMatrix(image.getGreen(), bounds, filter));
        imageResult.setBlue(filterSingleMatrix(image.getBlue(), bounds, filter));
        return imageResult;
    }

    public static Imagem applyMaxFilter(Imagem image) {
        return applyFilterInImage(image, 1, Filter.MAX);
    }

    public static Imagem applyMinFilter(Imagem image) {
        return applyFilterInImage(image, 1, Filter.MIN);
    }

    public static Imagem applyMeanFilter(Imagem image) {
        return applyFilterInImage(image, 1, Filter.MEAN);
    }
}

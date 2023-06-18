package com.br.processamentoimagem;

import java.util.Arrays;

/**
 *
 * @author Eduardo Piaia
 */
public enum Filter {

    MAX {
        @Override
        public int getResult(Integer[][] focus, Double desvioPadrao) {
            int width = focus.length;
            int height = focus[0].length;
            int maxValue = 0;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (focus[x][y] != null && focus[x][y] > maxValue) {
                        maxValue = focus[x][y];
                    }
                }
            }

            return maxValue;
        }
    }, MIN {
        @Override
        public int getResult(Integer[][] focus, Double desvioPadrao) {
            int width = focus.length;
            int height = focus[0].length;
            Integer minValue = null;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Integer valorFoco = focus[x][y];
                    if (valorFoco == null) {
                        valorFoco = 0;
                    }
                    if (minValue == null || valorFoco < minValue) {
                        minValue = valorFoco;
                    }
                }
            }

            return minValue;
        }
    }, MEAN {
        @Override
        public int getResult(Integer[][] focus, Double desvioPadrao) {
            int width = focus.length;
            int height = focus[0].length;

            int sum = 0;
            int count = 0;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (focus[x][y] != null) {
                        sum += focus[x][y];
                        count++;
                    }
                }
            }

            if (count == 0) {
                return 0;
            }
            int mean = sum / count;
            return mean;
        }
    }, MEANING {
        @Override
        public int getResult(Integer[][] focus, Double desvioPadrao) {
            int width = focus.length;
            int height = focus[0].length;

            int[] numbers = new int[width * height];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int count = (y * width) + x;
                    if (focus[x][y] == null) {
                        numbers[count] = 0;
                    } else {
                        numbers[count] = focus[x][y];
                    }
                }
            }

            Arrays.sort(numbers);

            int meaning;
            if (numbers.length % 2 == 0) {
                int num1 = numbers[numbers.length / 2];
                int num2 = numbers[(numbers.length / 2) + 1];
                meaning = (num1 + num2) / 2;
            } else {
                meaning = numbers[numbers.length / 2];
            }

            return meaning;
        }
    }, CONSERVATIVE_SMOOTH {
        @Override
        public int getResult(Integer[][] focus, Double desvioPadrao) {
            int width = focus.length;
            int height = focus[0].length;

            Integer min = null;
            Integer max = null;
            Integer center = null;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (focus[x][y] == null) {
                        continue;
                    }
                    if (min == null && max == null) {
                        min = focus[x][y];
                        max = focus[x][y];
                    }
                    if (y == height / 2 && x == width / 2) {
                        center = focus[x][y];
                        continue;
                    }
                    if (focus[x][y] > max) {
                        max = focus[x][y];
                    }
                    if (focus[x][y] < min) {
                        min = focus[x][y];
                    }
                }
            }

            if (center > max) {
                center = max;
            }
            if (center < min) {
                center = min;
            }

            return center;
        }
    }, GAUSSIAN {
        @Override
        public int getResult(Integer[][] focus, Double desvioPadrao) {
            int tamanhoKernel = 5;
            double[][] kernel = Tecnicas.getKernelGaussiano(tamanhoKernel, desvioPadrao);

            double soma = 0.0;
            int metadeTamanho = tamanhoKernel / 2;

            for (int i = 0; i < focus.length; i++) {
                for (int j = 0; j < focus[i].length; j++) {
                    for (int x = 0; x < tamanhoKernel; x++) {
                        for (int y = 0; y < tamanhoKernel; y++) {
                            int novoX = i + x - metadeTamanho;
                            int novoY = j + y - metadeTamanho;

                            if (novoX >= 0 && novoX < focus.length && novoY >= 0 && novoY < focus[i].length) {
                                soma += focus[novoX][novoY] * kernel[x][y];
                            }
                        }
                    }
                }
            }

            int novoValor = (int) Math.round(soma);
            return novoValor;
        }
    };

    public abstract int getResult(Integer[][] focus, Double desvioPadrao);

}

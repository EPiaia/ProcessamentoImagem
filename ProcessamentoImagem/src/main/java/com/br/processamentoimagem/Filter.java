package com.br.processamentoimagem;

/**
 *
 * @author Eduardo Piaia
 */
public enum Filter {

    MAX {
        @Override
        public int getResult(Integer[][] focus) {
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
        public int getResult(Integer[][] focus) {
            int width = focus.length;
            int height = focus[0].length;
            Integer minValue = null;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (focus[x][y] != null && (minValue == null || focus[x][y] < minValue)) {
                        minValue = focus[x][y];
                    }
                }
            }

            return minValue;
        }
    }, MEAN {
        @Override
        public int getResult(Integer[][] focus) {
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
    };

    public abstract int getResult(Integer[][] focus);

}

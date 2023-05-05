package com.br.processamentoimagem;

/**
 *
 * @author Eduardo Piaia
 */
public enum Operation {

    SUM {
        @Override
        public Integer getCode() {
            return 1;
        }

        @Override
        public Integer getResult(Integer value1, Integer value2) {
            return getResult(value1, value2, null);
        }

        @Override
        public Integer getResult(Integer value1, Integer value2, Integer coeficient) {
            return value1 + value2;
        }
    }, SUBTRACT {
        @Override
        public Integer getCode() {
            return 2;
        }

        @Override
        public Integer getResult(Integer value1, Integer value2) {
            return getResult(value1, value2, null);
        }

        @Override
        public Integer getResult(Integer value1, Integer value2, Integer coeficient) {
            return value1 - value2;
        }
    }, DIVISION {
        @Override
        public Integer getCode() {
            return 3;
        }

        @Override
        public Integer getResult(Integer value1, Integer value2) {
            return getResult(value1, value2, null);
        }

        @Override
        public Integer getResult(Integer value1, Integer value2, Integer coeficient) {
            if (value2 == 0) {
                return value1;
            }
            return value1 / value2;
        }
    }, MULTIPLICATION {
        @Override
        public Integer getCode() {
            return 4;
        }

        @Override
        public Integer getResult(Integer value1, Integer value2) {
            return getResult(value1, value2, null);
        }

        @Override
        public Integer getResult(Integer value1, Integer value2, Integer coeficient) {
            return value1 * value2;
        }
    }, AND {
        @Override
        public Integer getCode() {
            return 5;
        }

        @Override
        public Integer getResult(Integer value1, Integer value2) {
            return getResult(value1, value2, null);
        }

        @Override
        public Integer getResult(Integer value1, Integer value2, Integer coeficient) {
            boolean v1 = value1 > 0;
            boolean v2 = value2 > 0;
            return (v1 && v2) ? 255 : 0;
        }
    }, OR {
        @Override
        public Integer getCode() {
            return 6;
        }

        @Override
        public Integer getResult(Integer value1, Integer value2) {
            return getResult(value1, value2, null);
        }

        @Override
        public Integer getResult(Integer value1, Integer value2, Integer coeficient) {
            boolean v1 = value1 > 0;
            boolean v2 = value2 > 0;
            return (v1 || v2) ? 255 : 0;
        }
    }, XOR {
        @Override
        public Integer getCode() {
            return 7;
        }

        @Override
        public Integer getResult(Integer value1, Integer value2) {
            return getResult(value1, value2, null);
        }

        @Override
        public Integer getResult(Integer value1, Integer value2, Integer coeficient) {
            boolean v1 = value1 > 0;
            boolean v2 = value2 > 0;
            return (v1 ^ v2) ? 255 : 0;
        }
    }, NOT {
        @Override
        public Integer getCode() {
            return 8;
        }

        @Override
        public Integer getResult(Integer value1, Integer value2) {
            return getResult(value1, null, null);
        }

        @Override
        public Integer getResult(Integer value1, Integer value2, Integer coeficient) {
            if (value1 == 255) {
                return 0;
            }
            return 255;
        }
    }, BLENDING {
        @Override
        public Integer getCode() {
            return 9;
        }

        @Override
        public Integer getResult(Integer value1, Integer value2) {
            return getResult(value1, value2, 1);
        }

        @Override
        public Integer getResult(Integer value1, Integer value2, Integer coeficient) {
            double coeficientResult = coeficient / 100.0;
            double result = coeficientResult * value1 + (1 - coeficientResult) * value2;
            return (int) Math.round(result);
        }
    }, NEGATIVE {
        @Override
        public Integer getCode() {
            return 10;
        }

        @Override
        public Integer getResult(Integer value1, Integer value2) {
            return getResult(value1, null, null);
        }

        @Override
        public Integer getResult(Integer value1, Integer value2, Integer coeficient) {
            return 255 - value1;
        }
    };

    public abstract Integer getCode();

    public abstract Integer getResult(Integer value1, Integer value2);

    public abstract Integer getResult(Integer value1, Integer value2, Integer coeficient);

}

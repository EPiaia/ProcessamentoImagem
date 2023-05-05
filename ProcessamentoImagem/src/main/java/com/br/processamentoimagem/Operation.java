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
        public Integer getResult(int value1, int value2) {
            return value1 + value2;
        }
    }, SUBTRACT {
        @Override
        public Integer getCode() {
            return 2;
        }

        @Override
        public Integer getResult(int value1, int value2) {
            return value1 - value2;
        }
    }, DIVISION {
        @Override
        public Integer getCode() {
            return 3;
        }

        @Override
        public Integer getResult(int value1, int value2) {
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
        public Integer getResult(int value1, int value2) {
            return value1 * value2;
        }
    }, AND {
        @Override
        public Integer getCode() {
            return 5;
        }

        @Override
        public Integer getResult(int value1, int value2) {
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
        public Integer getResult(int value1, int value2) {
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
        public Integer getResult(int value1, int value2) {
            boolean v1 = value1 > 0;
            boolean v2 = value2 > 0;
            return (v1 ^ v2) ? 255 : 0;
        }
    };

    public abstract Integer getCode();

    public abstract Integer getResult(int value1, int value2);

}

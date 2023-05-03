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
    };

    public abstract Integer getCode();

    public abstract Integer getResult(int value1, int value2);

}

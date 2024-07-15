package battleship;

import battleship.exceptions.AdjacentShipException;
import battleship.exceptions.WrongShipCoordinates;

class BattleField {

    static final int WIDTH = 10;
    static final int HEIGHT = 10;
    Cell[][] field = new Cell[HEIGHT][WIDTH];

    BattleField() {
        init();
    }

    private void init() {
        for (int i = 0; i < HEIGHT; i++) {
            char letter = (char) ('A' + i);
            for (int j = 0; j < WIDTH; j++) {
                field[i][j] = new Cell(letter, j + 1);
            }
        }
    }

    void print() {
        for (int i = 0; i < HEIGHT; i++) {
            if (i == 0) {
                System.out.println(" 1 2 3 4 5 6 7 8 9 10");
            }
            char letter = (char) (65 + i);
            for (int j = 0; j < WIDTH; j++) {
                if (j == 0) {
                    System.out.print(letter + " ");
                }
                System.out.print(field[i][j]);
                if (j != WIDTH - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    void printFogged() {
        for (int i = 0; i < HEIGHT; i++) {
            if (i == 0) {
                System.out.println(" 1 2 3 4 5 6 7 8 9 10");
            }
            char letter = (char) (65 + i);
            for (int j = 0; j < WIDTH; j++) {
                if (j == 0) {
                    System.out.print(letter + " ");
                }
                if (field[i][j].state == CellState.SHIP) {
                    System.out.print(CellState.WAVE);
                } else {
                    System.out.print(field[i][j]);
                }
                if (j != WIDTH - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }


    Cell getCell(char letter, int number) {
        int rowIndex = letter - 'A';
        int colIndex = number - 1;
        if (rowIndex >= 0 && rowIndex < HEIGHT && colIndex >= 0 && colIndex < WIDTH) {
            return field[rowIndex][colIndex];
        } else {
            throw new IllegalArgumentException("Invalid cell coordinates: " + letter + number);
        }
    }

    Cell getCell(Cell cell) {
        int rowIndex = cell.getLetter() - 'A';
        int colIndex = cell.getNumber() - 1;
        if (rowIndex >= 0 && rowIndex < HEIGHT && colIndex >= 0 && colIndex < WIDTH) {
            return field[rowIndex][colIndex];
        } else {
            throw new IllegalArgumentException("Invalid cell coordinates: " + cell.getLetter() + cell.getNumber());
        }
    }


    void validateShipPlace(Cell[] ship, Ships type) {
        if (ship.length != type.capacity) {
            throw new WrongShipCoordinates("Error! Wrong length of the " + type.commonName + "! Try again:");
        }
        for (Cell cell : ship) {
            if (!validateAdjacent(cell)) {
                throw new AdjacentShipException("Error! You placed it too close to another one. Try again:");
            }
        }
    }

    boolean isThereShips() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (field[i][j].state == CellState.SHIP) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateAdjacent(Cell cell) {
        Cell top = null;
        Cell left = null;
        Cell bottom = null;
        Cell right = null;

        if (cell.getLetter() != 'A') {
            top = getCell((char) (cell.getLetter() - 1), cell.getNumber());
        }

        if (cell.getLetter() != 'J') {
            bottom = getCell((char) (cell.getLetter() + 1), cell.getNumber());
        }

        if (cell.getNumber() != 1) {
            left = getCell(cell.getLetter(), cell.getNumber() - 1);
        }

        if (cell.getNumber() != 10) {
            right = getCell(cell.getLetter(), cell.getNumber() + 1);
        }

        return (top == null || top.getState() != CellState.SHIP) &&
                (left == null || left.getState() != CellState.SHIP) &&
                (right == null || right.getState() != CellState.SHIP) &&
                (bottom == null || bottom.getState() != CellState.SHIP);
    }
}

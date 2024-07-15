package battleship;

class Cell {
    private char letter;
    private int number;
    CellState state;


    Cell(char letter, int number) {
        this.letter = letter;
        this.number = number;
        this.state = CellState.WAVE;
    }

    public void setState(CellState state) {
        if (this.getState() == state) {
            throw new IllegalArgumentException("You shouldn't set the same state");
        }
        if (this.getState() == CellState.SHIP && state != CellState.HIT_SHIP) {
            throw new IllegalArgumentException("A cell with a ship might be only hit");
        }
        this.state = state;
    }

    public CellState getState() {
        return state;
    }

    public char getLetter() {
        return letter;
    }

    public int getNumber() {
        return number;
    }

    public void setLetter(char letter) {
        if (letter > 'J' || letter < 'A') {
            throw new IllegalArgumentException("letter should be in A-J range");
        }
        this.letter = letter;
    }

    public void setNumber(int number) {
        if (number < 0 || number > 10) {
            throw new IllegalArgumentException("letter should be in A-J range");
        }
        this.number = number;
    }

    boolean isInTheSameRowAs(Cell other) {
        return this.letter == other.letter;
    }

    boolean isInTheSameColumnAs(Cell other) {
        return this.number == other.number;
    }

    public boolean equals(Cell other) {
        return this.isInTheSameColumnAs(other) && this.isInTheSameRowAs(other);
    }

    public static Cell[] calculateCellSequence(Cell start, Cell end) {
        int length;
        if (start.equals(end)) {
            return new Cell[]{start};
        }

        if (start.isInTheSameRowAs(end)) {
            length = Math.abs(start.number - end.number) + 1;
            Cell[] cellSequence = new Cell[length];
            if (start.number > end.number) {
                for (int i = 0; i < length; i++) {
                    cellSequence[i] = new Cell(start.letter, start.number - i);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    cellSequence[i] = new Cell(start.letter, start.number + i);
                }
            }
            return cellSequence;
        }

        if (start.isInTheSameColumnAs(end)) {
            length = Math.abs(start.letter - end.letter) + 1;
            Cell[] cellSequence = new Cell[length];
            if (start.letter > end.letter) {
                for (int i = 0; i < length; i++) {
                    cellSequence[i] = new Cell((char) (start.letter - i), start.number);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    cellSequence[i] = new Cell((char) (start.letter + i), start.number);
                }
            }
            return cellSequence;
        }

        throw new IllegalArgumentException("Provided cells should be either from the same row or collumn");
    }


    @Override
    public String toString() {
        return this.getState().toString();
    }
}
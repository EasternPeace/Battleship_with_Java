package battleship;

enum CellState {
    WAVE('~'),
    SHIP('O'),
    HIT_SHIP('X'),
    MISS('M');

    private final char sign;

    CellState(char sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return String.valueOf(this.sign);
    }
}

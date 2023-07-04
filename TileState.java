public enum TileState {
    UNKNOWN(" "),
    FILLED("0"),
    EMPTY("-");

    private final String symbol;

    private TileState(String symbol) {
        this.symbol = symbol;
    }

    public boolean checkValidState(TileState other) {
        if (this == other || this == UNKNOWN || other == UNKNOWN) {
            return true;
        }
        return false;
    }

    public String toString() {
        return symbol;
    }
}
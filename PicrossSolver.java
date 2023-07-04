import java.util.List;

public class PicrossSolver {

    private int width;
    private int height;
    private Board board;

    public PicrossSolver(int width, int height, Board board) {
        this.width = width;
        this.height = height;
        this.board = board;
    }

    public Board solveBoard() {
        boolean isSolved = false;
        while (!isSolved) {
            findSuperpositions();
            isSolved = board.checkIfSolved();
            board.printBoard();
        }
        return board;
    }

    // Method 1: Find filled or empty squares common to every possibility
    public void findSuperpositions() {
        for (int row = 0; row < height; row++) {
            List<TileState[]> stripStates = board.rows[row].getStripStates(board.getRow(row));
            TileState[] finalState = superimpose(stripStates, width);
            board.setRow(row, finalState);
        }
        for (int col = 0; col < width; col++) {
            List<TileState[]> stripStates = board.cols[col].getStripStates(board.getCol(col));
            TileState[] finalState = superimpose(stripStates, height);
            board.setCol(col, finalState);
        }
    }

    public TileState[] superimpose(List<TileState[]> stripStates, int length) {
        int states = stripStates.size();
        boolean[] invalidTiles = new boolean[length];

        for (int i = 0; i < states - 1; i++) {
            TileState[] stripState1 = stripStates.get(i);
            for (int j = i + 1; j < states; j++) {
                TileState[] stripState2 = stripStates.get(j);

                for (int tile = 0; tile < length; tile++) {
                    if (!stripState1[tile].checkValidState(stripState2[tile])) {
                        invalidTiles[tile] = true;
                    }
                }
            }
        }

        TileState[] finalState = stripStates.get(0).clone();
        for (int tile = 0; tile < length; tile++) {
            if (invalidTiles[tile]) {
                finalState[tile] = TileState.UNKNOWN;
            } 
        }

        return finalState;
    }

    public static void main(String[] args) {
        int width = 15;
        int height = 15;

        Row[] rows = new Row[height];
        Column[] cols = new Column[width];
        int rowValues[][] = {{2, 1}, {3, 1}, {3, 2, 2}, {2, 3, 6}, {4, 2, 6}, {4, 1, 6}, {2, 1, 7}, {2, 6}, {2, 4}, {2, 3}, {2, 2}, {2, 4}, {3, 1, 2, 2}, {3, 1, 1, 2}, {5, 1, 1}};
        int colValues[][] = {{3}, {9}, {5, 3}, {3, 3}, {3, 2, 2}, {4, 1}, {6, 3}, {2, 1}, {2, 1}, {6, 1}, {8, 1}, {8, 2}, {9, 1}, {6, 4}, {5, 2, 3}};

        for (int row = 0; row < height; row++) {
            rows[row] = new Row(width, rowValues[row]);
        }
        for (int col = 0; col < width; col++) {
            cols[col] = new Column(height, colValues[col]);
        }

        Board board = new Board(width, height, rows, cols);
        PicrossSolver solver = new PicrossSolver(width, height, board);
        solver.solveBoard();
    }
}

import java.util.Arrays;

public class Board {
    public final int width;
    public final int height;
    public final Row[] rows;
    public final Column[] cols;

    private TileState[][] grid;

    public Board(int width, int height, Row[] rows, Column[] cols) {
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;

        SetupGrid();
    }

    private void SetupGrid() {
        grid = new TileState[height][width];
        for (int row = 0; row < height; row++) {
            Arrays.fill(grid[row], TileState.UNKNOWN);
        }
    }

    public TileState[] getRow(int y) {
        return grid[y];
    }

    public TileState[] getCol(int x) {
        TileState[] tileColumn = new TileState[height];
        for (int y = 0; y < height; y++) {
            tileColumn[y] = grid[y][x];
        }
        return tileColumn;
    }

    public void setRow(int y, TileState[] row) {
        grid[y] = row;
    }

    public void setCol(int x, TileState[] col) {
        for (int y = 0; y < height; y++) {
            grid[y][x] = col[y];
        }
    }

    public boolean checkIfSolved() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col] == TileState.UNKNOWN) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printBoard() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                System.out.print(grid[row][col]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
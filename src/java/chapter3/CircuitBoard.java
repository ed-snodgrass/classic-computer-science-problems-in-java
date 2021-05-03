package chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CircuitBoard {
    public static class GridLocation {
        public final int row;
        public final int column;

        public GridLocation(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GridLocation that = (GridLocation) o;
            return row == that.row && column == that.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    public static class Component {
        int length;
        int height;

        public Component(int length, int height) {
            this.length = length;
            this.height = height;
        }
    }

    private final int rows;
    private final int columns;
    private final boolean[][] board;

    public CircuitBoard(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        board = new boolean[rows][columns];
    }

    public List<List<GridLocation>> generateDomain(Component component) {
        List<List<GridLocation>> domain = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (column + component.length <= columns && row + component.height <= rows) {
                    fill(domain, row, column, component.length, component.height);
                }
                if (row + component.length <= rows && column + component.height <= columns) {
                    fill(domain, row, column, component.height, component.length);
                }
            }
        }
        return domain;
    }

    private void fill(List<List<GridLocation>> domain, int row, int column, int length, int height) {
        List<GridLocation> locations = new ArrayList<>();
        for (int r = row; r < (row + height); r++) {
            for (int c = column; c < (column + length); c++) {
                locations.add(new GridLocation(r, c));
            }
        }
        domain.add(locations);
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (boolean[] rowArray : board) {
            for (boolean column : rowArray) {
                stringBuilder.append(column ? 'X' : 'O');
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public void mark(List<GridLocation> locations) {
        //  pass component into here if you wanted to show different components
        for (GridLocation location : locations) {
            board[location.row][location.column] = true;
        }
    }
}

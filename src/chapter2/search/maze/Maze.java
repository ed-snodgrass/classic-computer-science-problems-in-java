package chapter2.search.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maze {
    private final int rows;
    private final int columns;
    private final MazeLocation start;
    private final MazeLocation goal;

    private Cell[][] grid;

    public Maze(int rows, int columns, MazeLocation start, MazeLocation goal, double sparseness) {
        this.rows = rows;
        this.columns = columns;
        this.start = start;
        this.goal = goal;

        grid = new Cell[rows][columns];
        for (Cell[] row : grid) {
            Arrays.fill(row, Cell.EMPTY);
        }
        randomlyFill(sparseness);
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public Maze() {
        this(10, 10, new MazeLocation(0, 0), new MazeLocation(9, 9), 0.2);
    }

    public boolean goalTest(MazeLocation mazeLocation) {
        return goal.equals(mazeLocation);
    }

    public List<MazeLocation> successors(MazeLocation mazeLocation) {
        List<MazeLocation> locations = new ArrayList<>();
        if (mazeLocation.row + 1 < rows && grid[mazeLocation.row + 1][mazeLocation.column] != Cell.BLOCKED) {
            locations.add(new MazeLocation(mazeLocation.row + 1, mazeLocation.column));
        }
        if (mazeLocation.row - 1 >= 0 && grid[mazeLocation.row - 1][mazeLocation.column] != Cell.BLOCKED) {
            locations.add(new MazeLocation(mazeLocation.row - 1, mazeLocation.column));
        }
        if (mazeLocation.column + 1 < columns && grid[mazeLocation.row][mazeLocation.column + 1] != Cell.BLOCKED) {
            locations.add(new MazeLocation(mazeLocation.row, mazeLocation.column + 1));
        }
        if (mazeLocation.column - 1 >= 0 && grid[mazeLocation.row][mazeLocation.column - 1] != Cell.BLOCKED) {
            locations.add(new MazeLocation(mazeLocation.row, mazeLocation.column - 1));
        }
        return locations;
    }

    private void randomlyFill(double sparseness) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (Math.random() < sparseness) {
                    grid[row][column] = Cell.BLOCKED;
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                sb.append(cell.toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Maze maze = new Maze();
        System.out.println(maze);
    }

    public enum Cell {
        EMPTY(" "),
        BLOCKED("X"),
        START("S"),
        GOAL("G"),
        PATH("*");

        private final String code;

        Cell(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }
}

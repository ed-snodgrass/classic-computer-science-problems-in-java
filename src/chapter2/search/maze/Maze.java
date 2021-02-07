package chapter2.search.maze;

import chapter2.search.GenericSearch;
import chapter2.search.GenericSearch.Node;

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

    public void mark(List<MazeLocation> path) {
        for (MazeLocation mazeLocation: path) {
            grid[mazeLocation.row][mazeLocation.column] = Cell.PATH;
        }
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.row] = Cell.GOAL;
    }

    public void clear(List<MazeLocation> path) {
        for (MazeLocation mazeLocation : path) {
            grid[mazeLocation.row][mazeLocation.column] = Cell.EMPTY;
        }
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.row] = Cell.GOAL;
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

    public double euclideanDistance(MazeLocation mazeLocation) {
        int xDistance = mazeLocation.column - goal.column;
        int yDistance = mazeLocation.row - goal.row;
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    public double manhattanDistance(MazeLocation mazeLocation) {
        int xDistance = Math.abs(mazeLocation.column - goal.column);
        int yDistance = Math.abs(mazeLocation.row - goal.row);
        return xDistance + yDistance;
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

        Node<MazeLocation> solution1 = GenericSearch.depthFirstSearch(maze.start, maze::goalTest, maze::successors);
        if (solution1 == null) {
            System.out.println("No solution found using depth-first search!");
        } else {
            List<MazeLocation> path1 = GenericSearch.nodeToPath(solution1);
            maze.mark(path1);
            System.out.println(maze);
            maze.clear(path1);
        }

        Node<MazeLocation> solution2 = GenericSearch.breadthFirstSearch(maze.start, maze::goalTest, maze::successors);
        if (solution2 == null) {
            System.out.println("No solution found using breadth-first search!");
        } else {
            List<MazeLocation> path2 = GenericSearch.nodeToPath(solution2);
            maze.mark(path2);
            System.out.println(maze);
            maze.clear(path2);
        }

        Node<MazeLocation> aStarSolution = GenericSearch.aStar(maze.start, maze::goalTest, maze::successors, maze::manhattanDistance);
        if (aStarSolution == null) {
            System.out.println("No solution found using A* search!");
        } else {
            List<MazeLocation> path3 = GenericSearch.nodeToPath(aStarSolution);
            maze.mark(path3);
            System.out.println(maze);
            maze.clear(path3);
        }
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

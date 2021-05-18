package chapter8.adversarialsearch;

import java.util.Scanner;

public class TicTacToe {
    private TicTacToeBoard ticTacToeBoard = new TicTacToeBoard();
    private final Scanner scanner = new Scanner(System.in);

    private Integer getPlayerMove() {
        int playerMove = -1;
        while (!ticTacToeBoard.getLegalMoves().contains(playerMove)) {
            System.out.println("Enter a legal square (0-8):");
            playerMove = scanner.nextInt();
        }
        return playerMove;
    }

    private void runGame() {
        while (true) {
            Integer humanMove = getPlayerMove();
            ticTacToeBoard = ticTacToeBoard.move(humanMove);
            if (ticTacToeBoard.isWin()) {
                System.out.println("Human wins!");
                break;
            } else if (ticTacToeBoard.isDraw()) {
                System.out.println("Draw!");
                break;
            }
            Integer computerMove = Minimax.findBestMove(ticTacToeBoard, 9);
            System.out.println("Computer move is " + computerMove);
            ticTacToeBoard = ticTacToeBoard.move(computerMove);
            System.out.println(ticTacToeBoard);
            if (ticTacToeBoard.isWin()) {
                System.out.println("Computer wins!");
                break;
            } else if (ticTacToeBoard.isDraw()) {
                System.out.println("Draw!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        new TicTacToe().runGame();
    }
}

package chapter8.adversarialsearch;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeBoard implements Board<Integer> {
    private static final int NUM_SQUARES = 9;
    private TicTacToePiece[] position;
    private TicTacToePiece turn;

    public TicTacToeBoard(TicTacToePiece[] position, TicTacToePiece turn) {
        this.position = position;
        this.turn = turn;
    }

    public TicTacToeBoard() {
        position = new TicTacToePiece[NUM_SQUARES];
        Arrays.fill(position, TicTacToePiece.EMPTY);
        turn = TicTacToePiece.X;
    }

    @Override
    public Piece getTurn() {
        return turn;
    }

    @Override
    public TicTacToeBoard move(Integer location) {
        TicTacToePiece[] tempPosition = Arrays.copyOf(position, position.length);
        tempPosition[location] = turn;
        return new TicTacToeBoard(tempPosition, turn.opposite());
    }

    @Override
    public List<Integer> getLegalMoves() {
        ArrayList<Integer> legalMoves = new ArrayList<>();
        for (int i = 0; i < NUM_SQUARES; i++) {
            if (position[i] == TicTacToePiece.EMPTY) {
                legalMoves.add(i);
            }
        }
        return legalMoves;
    }

    @Override
    public boolean isWin() {
        return checkPos(0, 1, 2) || checkPos(3, 4, 5) || checkPos(6, 7, 8) || checkPos(0, 3, 6) || checkPos(1, 4, 7) || checkPos(2, 5, 8) || checkPos(0, 4, 8) || checkPos(2, 4, 6);
    }

    private boolean checkPos(int p0, int p1, int p2) {
        return position[p0] == position[p1] && position[p0] == position[p2] && position[p0] != TicTacToePiece.EMPTY;
    }

    @Override
    public double evaluate(Piece player) {
        if (isWin() && turn == player) {
            return -1;
        } else if (isWin() && turn != player) {
            return 1;
        } else {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                stringBuilder.append(position[row * 3 + col].toString());
                if (col !=2) {
                    stringBuilder.append("|");
                }
            }
            stringBuilder.append(System.lineSeparator());
            if (row != 2) {
                stringBuilder.append("-----");
                stringBuilder.append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }
}

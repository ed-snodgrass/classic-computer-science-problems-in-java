package chapter8.adversarialsearch;

public enum TicTacToePiece implements Piece {
    X, O, EMPTY;

    @Override
    public TicTacToePiece opposite() {
        switch (this) {
            case X:
                return TicTacToePiece.O;
            case O:
                return TicTacToePiece.X;
            default:
                return TicTacToePiece.EMPTY;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case X:
                return "X";
            case O:
                return "O";
            default:
                return " ";
        }
    }
}

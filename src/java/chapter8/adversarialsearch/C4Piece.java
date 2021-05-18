package chapter8.adversarialsearch;

public enum C4Piece implements Piece {
    B, R, EMPTY;

    @Override
    public C4Piece opposite() {
        switch (this) {
            case B:
                return C4Piece.R;
            case R:
                return C4Piece.B;
            default:
                return C4Piece.EMPTY;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case B:
                return "B";
            case R:
                return "R";
            default:
                return " ";
        }
    }
}

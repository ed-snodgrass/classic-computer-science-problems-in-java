package chapter8.adversarialsearch;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@interface UnitTest {
    String name() default "";
}

public class TicTacToeMinimaxTests {

    public static <T> void assertEquality(T actual, T expected) {
        if (actual.equals(expected)) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
            System.out.println("Actual: " + actual.toString());
            System.out.println("Expected: " + expected.toString());
        }
    }

    @UnitTest(name = "Easy Position")
    public void easyPosition() {
        TicTacToePiece[] position = new TicTacToePiece[] {
                TicTacToePiece.X, TicTacToePiece.O, TicTacToePiece.X,
                TicTacToePiece.X, TicTacToePiece.EMPTY, TicTacToePiece.O,
                TicTacToePiece.EMPTY, TicTacToePiece.EMPTY, TicTacToePiece.O
        };
        TicTacToeBoard testBoard = new TicTacToeBoard(position, TicTacToePiece.X);
        Integer expectedAnswer = Minimax.findBestMove(testBoard, 8);
        assertEquality(expectedAnswer, 6);
    }


    @UnitTest(name = "Block Position")
    public void blockPosition() {
        TicTacToePiece[] position = new TicTacToePiece[] {
                TicTacToePiece.X, TicTacToePiece.EMPTY, TicTacToePiece.EMPTY,
                TicTacToePiece.EMPTY, TicTacToePiece.EMPTY, TicTacToePiece.O,
                TicTacToePiece.EMPTY, TicTacToePiece.X, TicTacToePiece.O
        };
        TicTacToeBoard testBoard = new TicTacToeBoard(position, TicTacToePiece.X);
        Integer expectedAnswer = Minimax.findBestMove(testBoard, 8);
        assertEquality(expectedAnswer, 2);
    }


    @UnitTest(name = "Hard Position")
    public void hardPosition() {
        TicTacToePiece[] position = new TicTacToePiece[] {
                TicTacToePiece.X, TicTacToePiece.EMPTY, TicTacToePiece.EMPTY,
                TicTacToePiece.EMPTY, TicTacToePiece.EMPTY, TicTacToePiece.O,
                TicTacToePiece.O, TicTacToePiece.X, TicTacToePiece.EMPTY
        };
        TicTacToeBoard testBoard = new TicTacToeBoard(position, TicTacToePiece.X);
        Integer expectedAnswer = Minimax.findBestMove(testBoard, 8);
        assertEquality(expectedAnswer, 1);
    }

    public void runAllTests() {
        for (Method method : this.getClass().getMethods()) {
            for (UnitTest annotation : method.getAnnotationsByType(UnitTest.class)) {
                System.out.println("Running Test " + annotation.name());
                try {
                    method.invoke(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("__________________________");
            }
        }
    }

    public static void main(String[] args) {
        new TicTacToeMinimaxTests().runAllTests();
    }
}

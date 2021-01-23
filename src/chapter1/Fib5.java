package chapter1;

import java.util.stream.IntStream;

public class Fib5 {
    private int last = 0, next = 1;

    public IntStream stream(int n) {
        return IntStream.generate(() -> {
            int oldLast = last;
            last = next;
            next = oldLast + next;
            return oldLast;
        }).limit(n + 1);
    }
}

package chapter1;

public class Fib4 {
    public int fib4(int n) {
        int last = 0, next = 1;
        for (int i = 0; i < n; i++) {
            int oldLast = last;
            last = next;
            next = next + oldLast;
        }
        return last;
    }
}

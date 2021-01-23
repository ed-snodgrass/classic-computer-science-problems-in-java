package chapter1.fibonacci;

public class Fib1 {
    private static int fib1(int n) {
        // the original text doesn't have the check... but for the sake of not being ridiculous, comment to see the SO
        if (n < 2) { return n;}
        return fib1(n-1) + fib1(n-2);
    }

    public static void main(String[] args) {
        // This produces a StackOverflow
        System.out.println(fib1(5));
    }
}

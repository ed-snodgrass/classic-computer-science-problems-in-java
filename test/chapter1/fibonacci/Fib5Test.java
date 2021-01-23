package chapter1.fibonacci;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class Fib5Test {


    @Test
    void fibonacciStreamTestWithZero() {
        Fib5 fib5 = new Fib5();
        assertThat(fib5.stream(0).max()).hasValue(0);
    }

    @Test
    void fibonacciStreamTestWithOne() {
        Fib5 fib5 = new Fib5();
        assertThat(fib5.stream(1).max()).hasValue(1);
    }

    @Test
    void fibonacciStreamTestWithTwo() {
        Fib5 fib5 = new Fib5();
        assertThat(fib5.stream(2).max()).hasValue(1);
    }

    @Test
    void fibonacciStreamTestWithThree() {
        Fib5 fib5 = new Fib5();
        assertThat(fib5.stream(3).max()).hasValue(2);
    }

    @Test
    void fibonacciStreamTestWithFour() {
        Fib5 fib5 = new Fib5();
        assertThat(fib5.stream(4).max()).hasValue(3);
    }

    @Test
    void fibonacciStreamTestWithFive() {
        Fib5 fib5 = new Fib5();
        assertThat(fib5.stream(5).max()).hasValue(5);
    }
}
package chapter1.fibonacci;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Fib4Test {

    @Test
    void fibonacciTestWithZero() {
        Fib4 fib4 = new Fib4();
        assertThat(fib4.fib4(0)).isEqualTo(0);
    }

    @Test
    void fibonacciTestWithOne() {
        Fib4 fib4 = new Fib4();
        assertThat(fib4.fib4(1)).isEqualTo(1);
    }

    @Test
    void fibonacciTestWithTwo() {
        Fib4 fib4 = new Fib4();
        assertThat(fib4.fib4(2)).isEqualTo(1);
    }

    @Test
    void fibonacciTestWithThree() {
        Fib4 fib4 = new Fib4();
        assertThat(fib4.fib4(3)).isEqualTo(2);
    }

    @Test
    void fibonacciTestWithFour() {
        Fib4 fib4 = new Fib4();
        assertThat(fib4.fib4(4)).isEqualTo(3);
    }

    @Test
    void fibonacciTestWithFive() {
        Fib4 fib4 = new Fib4();
        assertThat(fib4.fib4(5)).isEqualTo(5);
    }

    @Test
    void fibonacciTestWithTwenty() {
        Fib4 fib4 = new Fib4();
        assertThat(fib4.fib4(20)).isEqualTo(6765);
    }

    @Test
    void fibonacciTestWithForty() {
        Fib4 fib4 = new Fib4();
        assertThat(fib4.fib4(40)).isEqualTo(102334155);
    }
}
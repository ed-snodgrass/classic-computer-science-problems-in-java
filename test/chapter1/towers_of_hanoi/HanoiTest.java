package chapter1.towers_of_hanoi;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;

class HanoiTest {

    private final Stack<Integer> expectedEmpty = new Stack<>();

    @Test
    void solvingForOneRing() {
        Hanoi hanoi = new Hanoi(1);
        hanoi.solve();
        Stack<Integer> expectedTowerC = new Stack<>();
        expectedTowerC.push(1);
        assertThat(hanoi.towerC).isEqualTo(expectedTowerC);
        assertThat(hanoi.towerB).isEqualTo(expectedEmpty);
        assertThat(hanoi.towerA).isEqualTo(expectedEmpty);
    }

    @Test
    void solvingForTwoRings() {
        Hanoi hanoi = new Hanoi(2);
        hanoi.solve();
        Stack<Integer> expectedTowerC = new Stack<>();
        expectedTowerC.push(1);
        expectedTowerC.push(2);
        assertThat(hanoi.towerC).isEqualTo(expectedTowerC);
        assertThat(hanoi.towerB).isEqualTo(expectedEmpty);
        assertThat(hanoi.towerA).isEqualTo(expectedEmpty);
    }

    @Test
    void solvingForThreeRings() {
        Hanoi hanoi = new Hanoi(3);
        hanoi.solve();
        Stack<Integer> expectedTowerC = new Stack<>();
        expectedTowerC.push(1);
        expectedTowerC.push(2);
        expectedTowerC.push(3);
        assertThat(hanoi.towerC).isEqualTo(expectedTowerC);
        assertThat(hanoi.towerB).isEqualTo(expectedEmpty);
        assertThat(hanoi.towerA).isEqualTo(expectedEmpty);
    }
}
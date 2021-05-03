package chapter1.towers_of_hanoi;

import java.util.Stack;

public class Hanoi {
    private final int discCount;
    public final Stack<Integer> towerA = new Stack<>();
    public final Stack<Integer> towerB = new Stack<>();
    public final Stack<Integer> towerC = new Stack<>();

    public Hanoi(int discs) {
        discCount = discs;
        for (int i = 1; i <= discs; i++) {
            towerA.push(i);
        }
        //_1_     __    __
        //__      __    _1_
        //*********************
        //_1,2_   __    __
        //_1_     _2_   __
        //__      _2_   _1_
        //__      __    _1,2_
        //*********************
        //_1,2,3_ __    __
        //_1,2_   __    _3_
        //_1_     _2_   _3_
        //_1_     _2,3  __
        //__      _2,3_ _1_
        //_3_     _2_   _1_
        //_3_     __    _1,2_
        //__      __    _1,2,3
    }
    private void move(Stack<Integer> begin, Stack<Integer> end, Stack<Integer> temp, int n) {
        if (n == 1) {
            end.push(begin.pop());
        } else {
            move(begin, temp, end, n - 1);
            move(begin, end, end, 1);
            move(temp, end, begin, n - 1);
        }
    }

    public void solve() {
        move(towerA, towerC, towerB, discCount);
    }

    public static void main(String[] args) {
        Hanoi hanoi = new Hanoi(10);
        hanoi.solve();
        System.out.println(hanoi.towerA);
        System.out.println(hanoi.towerB);
        System.out.println(hanoi.towerC);
    }
}

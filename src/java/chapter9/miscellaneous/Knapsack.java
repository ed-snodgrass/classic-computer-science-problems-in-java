package chapter9.miscellaneous;

import java.util.ArrayList;
import java.util.List;

public final class Knapsack {
    public static final class Item {
        public final String name;
        public final int weight;
        public final double value;

        public Item(String name, int weight, double value) {
            this.name = name;
            this.weight = weight;
            this.value = value;
        }
    }

    public static List<Item> knapsack(List<Item> items, int maxCapacity) {
        // build up dynamic programming table
        double[][] table = new double[items.size() + 1][maxCapacity + 1];
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            for (int capacity = 1; capacity <= maxCapacity; capacity++) {
                double prevItemValue = table[i][capacity];
                if (capacity >= item.weight) { // item fits in knapsack
                    double valueFreeingWeightForItem = table[i][capacity - item.weight];
                    // only take if more valuable than previous item
                    table[i+1][capacity] = Math.max(valueFreeingWeightForItem + item.value, prevItemValue);
                } else { // no room for this item
                    table[i + 1][capacity] = prevItemValue;
                }
            }
        }
        // figure out solution from table
        List<Item> solution = new ArrayList<>();
        int capacity = maxCapacity;
        for (int i = items.size(); i > 0; i--) { //work backwards
            // was this item used?
            if (table[i - 1][capacity] != table[i][capacity]) {
                solution.add(items.get(i - 1));
                // if the item was used, remove its weight
                capacity -= items.get(i - 1).weight;
            }
        }
        return solution;
    }

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        items.add(new Item("television", 50, 500));
        items.add(new Item("candlesticks", 2, 300));
        items.add(new Item("stereo", 35, 400));
        items.add(new Item("laptop", 3, 1000));
        items.add(new Item("food", 15, 50));
        items.add(new Item("clothing", 20, 800));
        items.add(new Item("jewelry", 1, 4000));
        items.add(new Item("books", 100, 300));
        items.add(new Item("printer", 18, 30));
        items.add(new Item("refrigerator", 200, 700));
        items.add(new Item("painting", 10, 1000));
        List<Item> toSteal = knapsack(items, 75);
        System.out.println("The best items for the thief to steal are:");
        System.out.printf("%-15.15s %-15.15s %-15.15s%n", "Name", "Weight", "Value");
        for (Item item : toSteal) {
            System.out.printf("%-15.15s %-15.15s %-15.15s%n", item.name, item.weight, item.value);
        }
    }
}

package chapter2.search;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class GenericSearch {
    public static <T extends Comparable<T>> boolean linearContains(List<T> list, T key) {
        for (T item : list) {
            if (item.compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable<T>> boolean binaryContains(List<T> sortedList, T key) {
        int low = 0;
        int high = sortedList.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            int comparison = sortedList.get(middle).compareTo(key);
            if (comparison < 0) {
                low = middle + 1;
            } else if (comparison > 0) {
                high = middle - 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public static <T> List<T> nodeToPath(Node<T> node) {
        List<T> path = new ArrayList<>();
        path.add(node.state);

        while (node.parent != null) {
            node = node.parent;
            path.add(0, node.state);
        }
        return path;
    }

    public static <T> Node<T> depthFirstSearch(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        Stack<Node<T>> frontier = new Stack<>();
        frontier.push(new Node<>(initial, null));
        Set<T> explored = new HashSet<>();
        explored.add(initial);

        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.pop();
            T currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                return currentNode;
            }

            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue;
                }
                explored.add(child);
                frontier.push(new Node<>(child, currentNode));
            }
        }
        return null;
    }

    public static <T> Node<T> breadthFirstSearch(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        Queue<Node<T>> frontier = new LinkedList<>();
        frontier.offer(new Node<>(initial, null));
        Set<T> explored = new HashSet<>();
        explored.add(initial);

        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.poll();
            T currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                return currentNode;
            }

            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue;
                }
                explored.add(child);
                frontier.offer(new Node<>(child, currentNode));
            }
        }
        return null;
    }

    public static <T> Node<T> aStar(T initial, Predicate<T> goalTest, Function<T, List<T>> successors, ToDoubleFunction<T> heuristic) {
        PriorityQueue<Node<T>> frontier = new PriorityQueue<>();
        frontier.offer(new Node<>(initial, null, 0.0, heuristic.applyAsDouble(initial)));
        Map<T, Double> explored = new HashMap<>();
        explored.put(initial, 0.0);

        while (!frontier.isEmpty()) {
            Node <T> currentNode = frontier.poll();
            T currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                return currentNode;
            }

            for (T child : successors.apply(currentState)) {
                // 1 here assumes a grid, need a cost function for more sophisticated apps
                double newCost = currentNode.cost + 1;
                if (!explored.containsKey(child) || explored.get(child) > newCost) {
                    explored.put(child, newCost);
                    frontier.offer(new Node<>(child, currentNode, newCost, heuristic.applyAsDouble(child)));
                }
            }
        }
        return null;
    }

    public static class Node<T> implements Comparable<Node<T>> {
        final T state;
        Node<T> parent;
        double cost;
        double heuristic;

        Node(T state, Node<T> parent) {
            this.state = state;
            this.parent = parent;
        }

        Node(T state, Node<T> parent, double cost, double heuristic) {
            this.state = state;
            this.parent = parent;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(Node<T> other) {
            Double mine = cost + heuristic;
            Double theirs = other.cost + other.heuristic;
            return mine.compareTo(theirs);
        }
    }
}

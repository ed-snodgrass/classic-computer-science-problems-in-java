package chapter4.graphproblems;

import com.jetbrains.management.JitState;

import java.util.*;
import java.util.function.IntConsumer;

import static chapter4.graphproblems.MetropolitanStatisticalAreas.*;

public class WeightedGraph<V> extends Graph<V, WeightedEdge> {

    public WeightedGraph(List<V> vertices) {
        super(vertices);
    }

    public void addEdge(WeightedEdge edge) {
        edges.get(edge.fromVertexU).add(edge);
        edges.get(edge.toVertexV).add(edge.reversed());
    }

    public void addEdge(int fromVertexU, int toVertexV, float weight) {
        addEdge(new WeightedEdge(fromVertexU, toVertexV, weight));
    }

    public void addEdge(V first, V second, float weight) {
        addEdge(indexOf(first), indexOf(second), weight);
    }

    public static double totalWeight(List<WeightedEdge> path) {
        return path.stream().mapToDouble(weightedEdge -> weightedEdge.weight).sum();
    }

    public List<WeightedEdge> minimumSpanningTree(int start) {
        LinkedList<WeightedEdge> result  = new LinkedList<>();
        if (start < 0 || start > (getVertexCount() - 1)) {
            return result;
        }
        PriorityQueue<WeightedEdge> priorityQueue = new PriorityQueue<>();
        boolean[] visited = new boolean[getVertexCount()];

        IntConsumer visit = index -> {
            visited[index] = true;
            for (WeightedEdge edge : edgesOf(index)) {
                if (!visited[edge.toVertexV]) {
                    priorityQueue.offer(edge);
                }
            }
        };

        visit.accept(start);
        while (!priorityQueue.isEmpty()) {
            WeightedEdge edge = priorityQueue.poll();
            if (visited[edge.toVertexV]) {
                continue;
            }
            result.add(edge);
            visit.accept(edge.toVertexV);
        }
        return result;
    }

    public void printWeightedPath(List<WeightedEdge> weightedPath) {
        for (WeightedEdge edge : weightedPath) {
            System.out.println(vertexAt(edge.fromVertexU) + " " + edge.weight + " -> " + vertexAt(edge.toVertexV));
        }
        System.out.println("Total Weight: " + totalWeight(weightedPath));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getVertexCount(); i++) {
            stringBuilder.append(vertexAt(i));
            stringBuilder.append(" -> ");
            stringBuilder.append(Arrays.toString(edgesOf(i).stream()
                    .map(weightedEdge -> "(" + vertexAt(weightedEdge.toVertexV) + ", " + weightedEdge.weight + ")").toArray()));
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    public static final class DijkstraNode implements Comparable<DijkstraNode> {
        public final int vertex;
        public final double distance;

        public DijkstraNode(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(DijkstraNode other) {
            Double mine = distance;
            Double theirs = other.distance;
            return mine.compareTo(theirs);
        }
    }

    public static final class DijkstraResult {
        public final double[] distances;
        public final Map<Integer, WeightedEdge> pathMap;

        public DijkstraResult(double[] distances, Map<Integer, WeightedEdge> pathMap) {
            this.distances = distances;
            this.pathMap = pathMap;
        }
    }

    public DijkstraResult dijkstraResult(V root) {
        int first = indexOf(root);
        double[] distances = new double[getVertexCount()];
        distances[first] = 0;
        boolean[] visited = new boolean[getVertexCount()];
        visited[first] = true;

        HashMap<Integer, WeightedEdge> pathMap = new HashMap<>();
        PriorityQueue<DijkstraNode> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new DijkstraNode(first, 0));

        while(!priorityQueue.isEmpty()) {
            int fromVertexU = priorityQueue.poll().vertex;
            double distU = distances[fromVertexU];
            for (WeightedEdge weightedEdge : edgesOf(fromVertexU)) {
                double distV = distances[weightedEdge.toVertexV];
                double pathWeight = weightedEdge.weight + distU;

                if (!visited[weightedEdge.toVertexV] || (distV > pathWeight)) {
                    visited[weightedEdge.toVertexV] = true;
                    distances[weightedEdge.toVertexV] = pathWeight;
                    pathMap.put(weightedEdge.toVertexV, weightedEdge);
                    priorityQueue.offer(new DijkstraNode(weightedEdge.toVertexV, pathWeight));
                }
            }
        }

        return new DijkstraResult(distances, pathMap);
    }

    public Map<V, Double> distanceArrayToDistanceMap(double[] distances) {
        HashMap<V, Double> distanceMap = new HashMap<>();
        for (int i = 0; i < distances.length; i++) {
            distanceMap.put(vertexAt(i), distances[i]);
        }
        return distanceMap;
    }

    public static List<WeightedEdge> pathMapToPath(int start, int end, Map<Integer, WeightedEdge> pathMap) {
        if (pathMap.size() == 0) {
            return List.of();
        }
        LinkedList<WeightedEdge> path = new LinkedList<>();
        WeightedEdge edge = pathMap.get(end);
        path.add(edge);
        while (edge.fromVertexU != start) {
            edge = pathMap.get(edge.fromVertexU);
            path.add(edge);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        WeightedGraph<String> weightedCityGraph = new WeightedGraph<>(
                List.of(SEATTLE, SAN_FRANCISCO, LOS_ANGELES, RIVERSIDE, PHOENIX, CHICAGO, BOSTON, NEW_YORK,
                        ATLANTA, MIAMI, DALLAS, HOUSTON, DETROIT, PHILADELPHIA, WASHINGTON)
        );
        weightedCityGraph.addEdge(SEATTLE, CHICAGO, 1737);
        weightedCityGraph.addEdge(SEATTLE, SAN_FRANCISCO, 678);
        weightedCityGraph.addEdge(SAN_FRANCISCO, RIVERSIDE, 386);
        weightedCityGraph.addEdge(SAN_FRANCISCO, LOS_ANGELES, 348);
        weightedCityGraph.addEdge(LOS_ANGELES, RIVERSIDE, 50);
        weightedCityGraph.addEdge(LOS_ANGELES, PHOENIX, 357);
        weightedCityGraph.addEdge(RIVERSIDE, PHOENIX, 307);
        weightedCityGraph.addEdge(RIVERSIDE, CHICAGO, 1704);
        weightedCityGraph.addEdge(PHOENIX, DALLAS, 887);
        weightedCityGraph.addEdge(PHOENIX, HOUSTON, 1015);
        weightedCityGraph.addEdge(DALLAS, CHICAGO, 805);
        weightedCityGraph.addEdge(DALLAS, ATLANTA, 721);
        weightedCityGraph.addEdge(DALLAS, HOUSTON, 225);
        weightedCityGraph.addEdge(HOUSTON, ATLANTA, 702);
        weightedCityGraph.addEdge(HOUSTON, MIAMI, 968);
        weightedCityGraph.addEdge(ATLANTA, CHICAGO, 588);
        weightedCityGraph.addEdge(ATLANTA, WASHINGTON, 543);
        weightedCityGraph.addEdge(ATLANTA, MIAMI, 604);
        weightedCityGraph.addEdge(MIAMI, WASHINGTON, 923);
        weightedCityGraph.addEdge(CHICAGO, DETROIT, 238);
        weightedCityGraph.addEdge(DETROIT, BOSTON, 613);
        weightedCityGraph.addEdge(DETROIT, WASHINGTON, 396);
        weightedCityGraph.addEdge(DETROIT, NEW_YORK, 482);
        weightedCityGraph.addEdge(BOSTON, NEW_YORK, 190);
        weightedCityGraph.addEdge(NEW_YORK, PHILADELPHIA, 81);
        weightedCityGraph.addEdge(PHILADELPHIA, WASHINGTON, 123);

        System.out.println(weightedCityGraph);

        List<WeightedEdge> minimumSpanningTree = weightedCityGraph.minimumSpanningTree(0);
        weightedCityGraph.printWeightedPath(minimumSpanningTree);

        System.out.println();

        DijkstraResult dijkstraResult = weightedCityGraph.dijkstraResult(LOS_ANGELES);
        Map<String, Double> nameDistance = weightedCityGraph.distanceArrayToDistanceMap(dijkstraResult.distances);
        System.out.println("Distances from Los Angeles: ");
        nameDistance.forEach((name, distance) -> System.out.println(name + " : " + distance));

        System.out.println();

        System.out.println("Shortest path from Los Angeles to Boston: ");
        List<WeightedEdge> path = pathMapToPath(weightedCityGraph.indexOf(LOS_ANGELES), weightedCityGraph.indexOf(BOSTON), dijkstraResult.pathMap);
        weightedCityGraph.printWeightedPath(path);
    }
}

package chapter4.graphproblems;

import chapter2.search.GenericSearch;
import chapter2.search.GenericSearch.Node;

import java.util.List;

import static chapter4.graphproblems.MetropolitanStatisticalAreas.*;

public class UnweightedGraph<V> extends Graph<V, Edge> {

    public UnweightedGraph(List<V> vertices) {
        super(vertices);
    }

    public void addEdge(Edge edge) {
        edges.get(edge.fromVertexU).add(edge);
        edges.get(edge.toVertexV).add(edge.reversed());
    }

    public void addEdge(int fromVertexU, int toVertexV) {
        addEdge(new Edge(fromVertexU, toVertexV));
    }

    public void addEdge(V first, V second) {
        addEdge(new Edge(indexOf(first), indexOf(second)));
    }

    public static void main(String[] args) {
        UnweightedGraph<String> cityGraph = new UnweightedGraph<>(List.of(
                SEATTLE, SAN_FRANCISCO, LOS_ANGELES, RIVERSIDE, PHOENIX, CHICAGO, BOSTON, NEW_YORK,
                ATLANTA, MIAMI, DALLAS, HOUSTON, DETROIT, PHILADELPHIA, WASHINGTON
        ));

        cityGraph.addEdge(SEATTLE, CHICAGO);
        cityGraph.addEdge(SEATTLE, SAN_FRANCISCO);
        cityGraph.addEdge(SAN_FRANCISCO, RIVERSIDE);
        cityGraph.addEdge(SAN_FRANCISCO, LOS_ANGELES);
        cityGraph.addEdge(LOS_ANGELES, RIVERSIDE);
        cityGraph.addEdge(LOS_ANGELES, PHOENIX);
        cityGraph.addEdge(RIVERSIDE, PHOENIX);
        cityGraph.addEdge(RIVERSIDE, CHICAGO);
        cityGraph.addEdge(PHOENIX, DALLAS);
        cityGraph.addEdge(PHOENIX, HOUSTON);
        cityGraph.addEdge(DALLAS, CHICAGO);
        cityGraph.addEdge(DALLAS, ATLANTA);
        cityGraph.addEdge(DALLAS, HOUSTON);
        cityGraph.addEdge(HOUSTON, ATLANTA);
        cityGraph.addEdge(HOUSTON, MIAMI);
        cityGraph.addEdge(ATLANTA, CHICAGO);
        cityGraph.addEdge(ATLANTA, WASHINGTON);
        cityGraph.addEdge(ATLANTA, MIAMI);
        cityGraph.addEdge(MIAMI, WASHINGTON);
        cityGraph.addEdge(CHICAGO, DETROIT);
        cityGraph.addEdge(DETROIT, BOSTON);
        cityGraph.addEdge(DETROIT, WASHINGTON);
        cityGraph.addEdge(DETROIT, NEW_YORK);
        cityGraph.addEdge(BOSTON, NEW_YORK);
        cityGraph.addEdge(NEW_YORK, PHILADELPHIA);
        cityGraph.addEdge(PHILADELPHIA, WASHINGTON);

        checkShortestDistance(BOSTON, MIAMI, cityGraph);
        checkShortestDistance(CHICAGO, RIVERSIDE, cityGraph);
    }

    private static void checkShortestDistance(String fromCity, String toCity, UnweightedGraph<String> cityGraph) {
        Node<String> breadthFirstSearchResult = GenericSearch.breadthFirstSearch(fromCity, toVertex -> toVertex.equals(toCity), cityGraph::neighborsOf);
        if (breadthFirstSearchResult == null) {
            System.out.printf("No solution found from {%s} to {%s} using breadth-first search!%n", fromCity, toCity);
        } else {
            List<String> path = GenericSearch.nodeToPath(breadthFirstSearchResult);
            System.out.printf("Path from {%s} to {%s}: %n%s%n", fromCity, toCity, path);
        }
    }
}

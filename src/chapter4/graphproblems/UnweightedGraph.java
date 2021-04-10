package chapter4.graphproblems;

import java.util.List;

public class UnweightedGraph<V> extends Graph<V, Edge> {

    public static final String SEATTLE = "Seattle";
    public static final String SAN_FRANCISCO = "San Francisco";
    public static final String LOS_ANGELES = "Los Angeles";
    public static final String RIVERSIDE = "Riverside";
    public static final String PHOENIX = "Phoenix";
    public static final String CHICAGO = "Chicago";
    public static final String BOSTON = "Boston";
    public static final String NEW_YORK = "New York";
    public static final String ATLANTA = "Atlanta";
    public static final String MIAMI = "Miami";
    public static final String DALLAS = "Dallas";
    public static final String HOUSTON = "Houston";
    public static final String DETROIT = "Detroit";
    public static final String PHILADELPHIA = "Philadelphia";
    public static final String WASHINGTON = "Washington";

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
        System.out.println(cityGraph.toString());
    }
}

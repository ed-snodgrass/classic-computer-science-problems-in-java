package chapter4.graphproblems;

public class Edge {
    public final int fromVertexU;
    public final int toVertexV;

    public Edge(int fromVertexU, int toVertexV) {
        this.fromVertexU = fromVertexU;
        this.toVertexV = toVertexV;
    }

    public Edge reversed() {
        return new Edge(toVertexV, fromVertexU);
    }

    @Override
    public String toString() {
        return fromVertexU + " -> " + toVertexV;
    }
}

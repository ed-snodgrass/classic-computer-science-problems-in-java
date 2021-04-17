package chapter4.graphproblems;

public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {
    public final double weight;

    public WeightedEdge(int fromVertexU, int toVertexV, double weight) {
        super(fromVertexU, toVertexV);
        this.weight = weight;
    }

    @Override
    public WeightedEdge reversed() {
        return new WeightedEdge(toVertexV, fromVertexU, weight);
    }

    @Override
    public int compareTo(WeightedEdge other) {
        Double mine = weight;
        Double theirs = other.weight;
        return mine.compareTo(theirs);
    }

    @Override
    public String toString() {
        return String.format("%d %s > %d", fromVertexU, weight, toVertexV);
    }
}

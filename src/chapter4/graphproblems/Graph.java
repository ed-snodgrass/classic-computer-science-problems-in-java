package chapter4.graphproblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Graph<V, E extends Edge> {
    private ArrayList<V> vertices = new ArrayList<>();
    protected ArrayList<ArrayList<E>> edges = new ArrayList<>();

    public Graph() {
    }

    public Graph(List<V> vertices) {
        this.vertices.addAll(vertices);
        for (V vertex : vertices) {
            edges.add(new ArrayList<>());
        }
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.stream().mapToInt(ArrayList::size).sum();
    }

    public int addVertex(V vertex) {
        vertices.add(vertex);
        edges.add(new ArrayList<>());
        return getVertexCount() - 1;
    }

    public V vertexAt(int index) {
        return vertices.get(index);
    }

    public int indexOf(V vertex) {
        return vertices.indexOf(vertex);
    }

    public List<V> neighborsOf(int index) {
        return edges.get(index).stream()
                .map(edge -> vertexAt(edge.toVertexV))
                .collect(Collectors.toList());
    }

    public List<E> edgesOf(int index) {
        return edges.get(index);
    }

    public List<E> edgesOf(V vertex) {
        return edgesOf(indexOf(vertex));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getVertexCount(); i++) {
            stringBuilder.append(vertexAt(i));
            stringBuilder.append(" -> ");
            stringBuilder.append(Arrays.toString(neighborsOf(i).toArray()));
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}

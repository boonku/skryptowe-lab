package graph;

import java.util.Objects;

public class Edge {
    private final Vertex destination;
    private int weight;

    public Edge(Vertex destination) {
        this.destination = destination;
        this.weight = 0;
    }

    public Edge(Vertex destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return weight == edge.weight && Objects.equals(destination, edge.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, weight);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "destination=" + destination +
                ", weight=" + weight +
                '}';
    }
}

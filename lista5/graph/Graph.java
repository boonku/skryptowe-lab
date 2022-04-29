package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private final Map<Vertex, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    // adds vertex to graph if it doesn't exist
    public boolean addVertex(String label) {
        Vertex vertex = getVertexFromLabel(label);
        if (!checkIfVertexExists(vertex)) {
            adjacencyList.putIfAbsent(vertex, new ArrayList<>());
            return true;
        }
        return false;
    }

    // removes the vertex and all the edges where it is its destination if the vertex exists
    public boolean removeVertex(String label) {
        Vertex vertex = getVertexFromLabel(label);
        Object returnedValue = adjacencyList.remove(vertex);
        if (returnedValue == null) {
            return false;
        }
        for (List<Edge> edges : adjacencyList.values()) {
            edges.removeIf(edge -> edge.getDestination().equals(vertex));
        }
        return true;
    }

    // adds the edge if both vertices exist
    public boolean addEdge(String source, String destination, int weight) {
        Vertex sourceVertex = getVertexFromLabel(source);
        Vertex destinationVertex = getVertexFromLabel(destination);
        if (!checkIfVertexExists(sourceVertex) || !checkIfVertexExists(destinationVertex)) {
            return false;
        }
        adjacencyList.computeIfPresent(sourceVertex, (vertex, edges) -> {
            edges.add(new Edge(destinationVertex, weight));
            return edges;
        });
        return true;
    }

    // removes the edge between two vertices if both of them and the edge exist
    public boolean removeEdge(String source, String destination) {
        Vertex sourceVertex = getVertexFromLabel(source);
        Vertex destinationVertex = getVertexFromLabel(destination);
        if (!checkIfVertexExists(sourceVertex) || !checkIfVertexExists(destinationVertex)) {
            return false;
        }
        adjacencyList.computeIfPresent(sourceVertex, ((vertex, edges) -> {
            edges.removeIf(edge -> edge.getDestination().equals(destinationVertex));
            return edges;
        }));
        return true;
    }

    private boolean checkIfVertexExists(Vertex vertex) {
        return adjacencyList.get(vertex) != null;
    }

    // returns vertex from graph based on its label
    // can be more useful if more fields are added to vertex as equals
    public Vertex getVertexFromLabel(String label) {
        return adjacencyList.keySet()
                .stream()
                .filter(v -> v.getLabel().equals(label))
                .findFirst()
                .orElse(null);
    }
}

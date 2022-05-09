package graph;

import java.util.*;

public class Graph {
    private final Map<Vertex, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    // adds vertex to graph if it doesn't exist
    public boolean addVertex(String label) {
        Vertex vertex = new Vertex(label);
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

    // adds new edge between vertices if both vertices exist
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

    // creates both vertices if needed and then edge between them
    public boolean addEdgeWithVertices(String source, String destination, int weight) {
        addVertex(source);
        addVertex(destination);
        return addEdge(source, destination, weight);
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

    // changes vertex label to new one if vertex exists
    public boolean changeVertexLabel(String oldLabel, String newLabel) {
        Vertex vertex = getVertexFromLabel(oldLabel);
        if (vertex == null) {
            return false;
        }
        /*
        Note: great care must be exercised if mutable objects are used as map keys.
        The behavior of a map is not specified if the value of an object is changed in
        a manner that affects equals comparisons while the object is a key in the map.
        */
        // Vertex is removed from map and then re-added with new label and correct hashcode
        List<Edge> edges = adjacencyList.remove(vertex);
        vertex.setLabel(newLabel);
        adjacencyList.put(vertex, edges);
        return true;
    }

    private boolean checkIfVertexExists(Vertex vertex) {
        return adjacencyList.get(vertex) != null;
    }

    // returns vertex with given label from graph
    // can be more useful if more fields are added to vertex, e.g. generic value
    private Vertex getVertexFromLabel(String label) {
        return adjacencyList.keySet()
                .stream()
                .filter(v -> v.getLabel().equals(label))
                .findFirst()
                .orElse(null);
    }

    // depth first search algorithm
    public List<Vertex> DFS(String startingVertexLabel) {
        List<Vertex> vertices = new ArrayList<>();
        Vertex startingVertex = getVertexFromLabel(startingVertexLabel);
        if (startingVertex == null) {
            return vertices;
        }
        Deque<Vertex> stack = new ArrayDeque<>();
        Set<Vertex> visited = new HashSet<>();
        stack.add(startingVertex);
        while (!stack.isEmpty()) {
            Vertex vertex = stack.pop();
            List<Edge> edges = adjacencyList.getOrDefault(vertex, List.of());
            if (!visited.contains(vertex)) {
                vertices.add(vertex);
                visited.add(vertex);
                for (Edge edge: edges) {
                    stack.push(edge.getDestination());
                }
            }
        }
        return vertices;
    }

    // breadth first search algorithm
    public List<Vertex> BFS(String startingVertexLabel) {
        List<Vertex> vertices = new ArrayList<>();
        Vertex startingVertex = getVertexFromLabel(startingVertexLabel);
        if (startingVertex == null) {
            return vertices;
        }
        Queue<Vertex> queue = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        queue.add(startingVertex);
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            List<Edge> edges = adjacencyList.getOrDefault(vertex, List.of());
            if (!visited.contains(vertex)) {
                vertices.add(vertex);
                visited.add(vertex);
                for (Edge edge: edges) {
                    queue.add(edge.getDestination());
                }
            }
        }
        return vertices;
    }
}

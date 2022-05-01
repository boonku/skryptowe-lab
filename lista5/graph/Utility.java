package graph;

import java.util.*;

public class Utility {
    public static List<Vertex> DFS(Graph graph, String startingVertexLabel) {
        List<Vertex> vertices = new ArrayList<>();
        Vertex startingVertex = graph.getVertexFromLabel(startingVertexLabel);
        if (startingVertex == null) {
            return vertices;
        }
        Deque<Vertex> stack = new ArrayDeque<>();
        Set<Vertex> visited = new HashSet<>();
        Map<Vertex, List<Edge>> adjacencyList = graph.getAdjacencyList();
        stack.add(startingVertex);
        while (!stack.isEmpty()) {
            Vertex vertex = stack.pop();
            List<Edge> edges = adjacencyList.get(vertex);
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

    public static List<Vertex> BFS(Graph graph, String startingVertexLabel) {
        List<Vertex> vertices = new ArrayList<>();
        Vertex startingVertex = graph.getVertexFromLabel(startingVertexLabel);
        if (startingVertex == null) {
            return vertices;
        }
        Queue<Vertex> queue = new LinkedList<>();
        Set<Vertex> visited = new HashSet<>();
        Map<Vertex, List<Edge>> adjacencyList = graph.getAdjacencyList();
        queue.add(startingVertex);
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            List<Edge> edges = adjacencyList.get(vertex);
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

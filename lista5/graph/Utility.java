package graph;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static List<Vertex> DFS(Graph graph, String vertexLabel) {
        Vertex vertex = graph.getVertexFromLabel(vertexLabel);
        List<Vertex> vertices = new ArrayList<>();
        if (vertex == null) {
            return vertices;
        }
        return vertices;

    }

    public static List<Vertex> BFS(Graph graph, String vertexLabel) {
        Vertex vertex = graph.getVertexFromLabel(vertexLabel);
        List<Vertex> vertices = new ArrayList<>();
        if (vertex == null) {
            return vertices;
        }
        return vertices;
    }

    public static void pathfindingA(Graph graph) {

    }
}

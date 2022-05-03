package graph;

import java.util.List;

public class Main {
    public static String createRoute(List<Vertex> vertices) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Vertex vertex : vertices) {
            stringBuilder.append(vertex.getLabel());
            stringBuilder.append(" -> ");
        }
        if (stringBuilder.length()> 4) {
            stringBuilder.setLength(stringBuilder.length() - 4);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addVertex("0");
        graph.addVertex("1");
         graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");

        graph.addEdge("0", "1", 0);
        graph.addEdge("0", "2", 0);
        graph.addEdge("0", "3", 0);
        graph.addEdge("1", "0", 0);
        graph.addEdge("1", "2", 0);
        graph.addEdge("2", "4", 0);


        List<Vertex> dfs = Utility.DFS(graph, "1");
        List<Vertex> bfs = Utility.BFS(graph, "0");
        System.out.println("DFS from vertex '1': " + createRoute(dfs));
        System.out.println("BFS from vertex '0': " + createRoute(bfs));

        graph.changeVertexLabel("0", "5");
        System.out.println("\nChange Vertex with label '0' to '5'\n");
        dfs = Utility.DFS(graph, "1");
        bfs = Utility.BFS(graph, "5");
        System.out.println("DFS from vertex '1': " + createRoute(dfs));
        System.out.println("BFS from vertex '5': " + createRoute(bfs));
    }
}

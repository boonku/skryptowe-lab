package graph;

import py4j.GatewayServer;

public class GraphEntryPoint {
    public Graph getNewGraph() {
        return new Graph();
    }

    public static void main(String[] args) {
        GatewayServer gateway = new GatewayServer(new GraphEntryPoint());
        gateway.start();
    }
}

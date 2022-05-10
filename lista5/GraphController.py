from py4j.java_gateway import JavaGateway
import subprocess


class GraphController:

    def __init__(self):
        subprocess.Popen("java -jar graph/graph.jar", shell=True)
        self.gateway = JavaGateway()
        self.graph = None

    def create_new_graph(self):
        self.graph = self.gateway.entry_point.getNewGraph()

    def add_vertex(self, label):
        return self.graph.addVertex(str(label))

    def remove_vertex(self, label):
        return self.graph.removeVertex(str(label))

    def add_edge(self, source, destination, weight):
        return self.graph.addEdge(str(source), str(destination), weight)

    def add_edge_with_vertices(self, source, destination, weight):
        return self.graph.addEdgeWithVertices(str(source), str(destination), weight)

    def remove_edge(self, source, destination):
        return self.graph.removeEdge(source, destination)

    def change_vertex_label(self, old_label, new_label):
        return self.graph.changeVertexLabel(str(old_label), str(new_label))

    def dfs(self, starting_vertex_label):
        result = self.graph.DFS(str(starting_vertex_label))
        return self.__create_normal_list_with_labels(result)

    def bfs(self, starting_vertex_label):
        result = self.graph.BFS(str(starting_vertex_label))
        return self.__create_normal_list_with_labels(result)

    def print_graph(self):
        print(self.graph)

    def __create_normal_list_with_labels(self, java_list):
        result = []
        for i in range(len(java_list)):
            result.append(java_list[i].getLabel())
        return result

    def set_graph(self, graph):
        self.graph = graph

    def close(self):
        self.gateway.close()
        self.gateway.shutdown()

from GraphController import GraphController
import py4j.protocol


def main():
    graph_controller = GraphController()
    try:
        graph_controller.create_new_graph()
        graph_controller.add_edge_with_vertices('0', '1', 0)
        graph_controller.add_edge_with_vertices('0', '2', 0)
        graph_controller.add_edge_with_vertices('0', '3', 0)
        graph_controller.add_edge_with_vertices('1', '0', 0)
        graph_controller.add_edge_with_vertices('1', '2', 0)
        graph_controller.add_edge_with_vertices('2', '4', 0)
        graph_controller.change_vertex_label('0', '5')
        graph_controller.print_graph()
        print(graph_controller.bfs('5'))
    except py4j.protocol.ERROR as err:
        print(err)
    finally:
        graph_controller.close()


if __name__ == '__main__':
    main()

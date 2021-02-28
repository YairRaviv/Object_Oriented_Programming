import json
import random
import time
import timeit
import unittest
import networkx as nx

import matplotlib.pyplot as plt

from DiGraph import DiGraph
from GraphAlgo import GraphAlgo


class MyTestCase(unittest.TestCase):

    def test_my_draw(self):
        G_10_Algo = GraphAlgo()
        G_10_Algo.load_from_json("data/G_10000_80000_0.json")
        print(G_10_Algo.shortest_path(1, 5000))

        nx_10000_graph = nx.DiGraph()  # Loads a small graph using NetworkX.
        Nodes = []
        Edges = []
        with open("data/G_10000_80000_0.json", "r") as json_file:
            data = json.load(json_file)
            nodes = data["Nodes"]
            for i in nodes:
                Nodes.append(i)
            edges = data["Edges"]
            for i in edges:
                Edges.append(i)
        for i in Nodes:
            nx_10000_graph.add_node(i["id"])
        for i in Edges:
            nx_10000_graph.add_edge(i["src"], i["dest"], weight=i["w"])

        print(nx.dijkstra_path_length(nx_10000_graph, 1, 5000))

    def test_times_measure(self):

        G_10_Algo = GraphAlgo()
        G_100_Algo = GraphAlgo()
        G_1000_Algo = GraphAlgo()
        G_10000_Algo = GraphAlgo()
        G_20000_Algo = GraphAlgo()
        G_30000_Algo = GraphAlgo()

        G_10_Algo.load_from_json("data/G_10_80_0.json")
        G_100_Algo.load_from_json("data/G_100_800_0.json")
        G_1000_Algo.load_from_json("data/G_1000_8000_0.json")
        G_10000_Algo.load_from_json("data/G_10000_80000_0.json")
        G_20000_Algo.load_from_json("data/G_20000_160000_0.json")
        G_30000_Algo.load_from_json("data/G_30000_240000_0.json")

        # shortest path:

        start = time.time()
        for i in range(1, 50):
            graph_10_sp = G_10_Algo.shortest_path(1, 5)
        end = time.time()
        graph_10_sp_time=end-start
        for i in range(1, 50):
            graph_100_sp = G_100_Algo.shortest_path(1, 50)
        for i in range(1, 50):
            graph_1000_sp = G_1000_Algo.shortest_path(1, 500)
        for i in range(1, 20):
            graph_10000_sp = G_10000_Algo.shortest_path(1, 5000)
        for i in range(1, 10):
            graph_20000_sp = G_20000_Algo.shortest_path(1, 10000)
        for i in range(1, 5):
            graph_30000_sp = G_30000_Algo.shortest_path(1, 15000)

        end = time.time()

        graph_10_sp = G_10_Algo.shortest_path(1, 5)
        graph_100_sp = G_100_Algo.shortest_path(1, 50)
        graph_1000_sp = G_1000_Algo.shortest_path(1, 500)
        graph_10000_sp = G_10000_Algo.shortest_path(1, 5000)
        graph_20000_sp = G_20000_Algo.shortest_path(1, 10000)
        graph_30000_sp = G_30000_Algo.shortest_path(1, 15000)

        shortest_path_times = end - start

        # connected component

        start = time.time()
        for i in range(1, 6):
            graph_10_cc = G_10_Algo.connected_component(5)
            graph_100_cc = G_100_Algo.connected_component(50)
            graph_1000_cc = G_1000_Algo.connected_component(500)
            graph_10000_cc = G_10000_Algo.connected_component(5000)
            graph_20000_cc = G_20000_Algo.connected_component(10000)
            graph_30000_cc = G_30000_Algo.connected_component(15000)
        graph_10_cc = G_10_Algo.connected_component(5)
        graph_100_cc = G_100_Algo.connected_component(50)
        graph_1000_cc = G_1000_Algo.connected_component(500)
        graph_10000_cc = G_10000_Algo.connected_component(5000)
        graph_20000_cc = G_20000_Algo.connected_component(10000)
        graph_30000_cc = G_30000_Algo.connected_component(15000)
        end = time.time()

        connected_component_times = end - start

        # connected components

        start = time.time()
        graph_10_ccs = G_10_Algo.connected_components()
        graph_100_ccs = G_100_Algo.connected_components()
        graph_1000_ccs = G_1000_Algo.connected_components()
        graph_10000_ccs = G_10000_Algo.connected_components()
        graph_20000_ccs = G_20000_Algo.connected_components()
        graph_30000_ccs = G_30000_Algo.connected_components()
        end = time.time()

        connected_components_times = end - start

        for lists in graph_10_ccs:
            lists.sort()
        for lists in graph_100_ccs:
            lists.sort()
        for lists in graph_1000_ccs:
            lists.sort()
        for lists in graph_10000_ccs:
            lists.sort()
        for lists in graph_20000_ccs:
            lists.sort()
        for lists in graph_30000_ccs:
            lists.sort()

        nx_10_graph = nx.DiGraph()  # Loads a small graph using NetworkX.
        Nodes = []
        Edges = []
        with open("data/G_10_80_0.json", "r") as json_file:
            data = json.load(json_file)
            nodes = data["Nodes"]
            for i in nodes:
                Nodes.append(i)
            edges = data["Edges"]
            for i in edges:
                Edges.append(i)
        for i in Nodes:
            nx_10_graph.add_node(i["id"])
        for i in Edges:
            nx_10_graph.add_edge(i["src"], i["dest"], weight=i["w"])

        nx_100_graph = nx.DiGraph()  # Loads a small graph using NetworkX.
        Nodes = []
        Edges = []
        with open("data/G_100_800_0.json", "r") as json_file:
            data = json.load(json_file)
            nodes = data["Nodes"]
            for i in nodes:
                Nodes.append(i)
            edges = data["Edges"]
            for i in edges:
                Edges.append(i)
        for i in Nodes:
            nx_100_graph.add_node(i["id"])
        for i in Edges:
            nx_100_graph.add_edge(i["src"], i["dest"], weight=i["w"])

        nx_1000_graph = nx.DiGraph()  # Loads a small graph using NetworkX.
        Nodes = []
        Edges = []
        with open("data/G_1000_8000_0.json", "r") as json_file:
            data = json.load(json_file)
            nodes = data["Nodes"]
            for i in nodes:
                Nodes.append(i)
            edges = data["Edges"]
            for i in edges:
                Edges.append(i)
        for i in Nodes:
            nx_1000_graph.add_node(i["id"])
        for i in Edges:
            nx_1000_graph.add_edge(i["src"], i["dest"], weight=i["w"])

        nx_10000_graph = nx.DiGraph()  # Loads a small graph using NetworkX.
        Nodes = []
        Edges = []
        with open("data/G_10000_80000_0.json", "r") as json_file:
            data = json.load(json_file)
            nodes = data["Nodes"]
            for i in nodes:
                Nodes.append(i)
            edges = data["Edges"]
            for i in edges:
                Edges.append(i)
        for i in Nodes:
            nx_10000_graph.add_node(i["id"])
        for i in Edges:
            nx_10000_graph.add_edge(i["src"], i["dest"], weight=i["w"])

        nx_20000_graph = nx.DiGraph()  # Loads a small graph using NetworkX.
        Nodes = []
        Edges = []
        with open("data/G_20000_160000_0.json", "r") as json_file:
            data = json.load(json_file)
            nodes = data["Nodes"]
            for i in nodes:
                Nodes.append(i)
            edges = data["Edges"]
            for i in edges:
                Edges.append(i)
        for i in Nodes:
            nx_20000_graph.add_node(i["id"])
        for i in Edges:
            nx_20000_graph.add_edge(i["src"], i["dest"], weight=i["w"])

        nx_30000_graph = nx.DiGraph()  # Loads a small graph using NetworkX.
        Nodes = []
        Edges = []
        with open("data/G_30000_240000_0.json", "r") as json_file:
            data = json.load(json_file)
            nodes = data["Nodes"]
            for i in nodes:
                Nodes.append(i)
            edges = data["Edges"]
            for i in edges:
                Edges.append(i)
        for i in Nodes:
            nx_30000_graph.add_node(i["id"])
        for i in Edges:
            nx_30000_graph.add_edge(i["src"], i["dest"], weight=i["w"])

        start = time.time()
        for i in range(1, 50):
            nx_10_sp = nx.dijkstra_path(nx_10_graph, 1, 5)
            nx_100_sp = nx.dijkstra_path(nx_100_graph, 1, 50)
            nx_1000_sp = nx.dijkstra_path(nx_1000_graph, 1, 500)
        for i in range(1, 20):
            nx_10000_sp = nx.dijkstra_path(nx_10000_graph, 1, 5000)
        for i in range(1, 10):
            nx_20000_sp = nx.dijkstra_path(nx_20000_graph, 1, 10000)
        for i in range(1, 5):
            nx_30000_sp = nx.dijkstra_path(nx_30000_graph, 1, 15000)
        nx_10_sp = nx.dijkstra_path(nx_10_graph, 1, 5)
        nx_100_sp = nx.dijkstra_path(nx_100_graph, 1, 50)
        nx_1000_sp = nx.dijkstra_path(nx_1000_graph, 1, 500)
        nx_10000_sp = nx.dijkstra_path(nx_10000_graph, 1, 5000)
        nx_20000_sp = nx.dijkstra_path(nx_20000_graph, 1, 10000)
        nx_30000_sp = nx.dijkstra_path(nx_30000_graph, 1, 15000)
        end = time.time()

        nx_shortest_path_times = end - start

        start = time.time()
        nx_10_ccs = list(nx.strongly_connected_components(nx_10_graph))
        nx_100_ccs = list(nx.strongly_connected_components(nx_100_graph))
        nx_1000_ccs = list(nx.strongly_connected_components(nx_1000_graph))
        nx_10000_ccs = list(nx.strongly_connected_components(nx_10000_graph))
        nx_20000_ccs = list(nx.strongly_connected_components(nx_20000_graph))
        nx_30000_ccs = list(nx.strongly_connected_components(nx_30000_graph))
        end = time.time()

        nx_connected_components_times = end - start

        print("shortest path times : ", shortest_path_times)
        print("connected component times : ", connected_component_times)
        print("connected components times : ", connected_components_times)
        print("networkx shortest path times : ", nx_shortest_path_times)
        print("networkx connected components times : ", nx_connected_components_times)

        self.assertListEqual(graph_10_sp[1], nx_10_sp)
        self.assertListEqual(graph_100_sp[1], nx_100_sp)
        self.assertListEqual(graph_1000_sp[1], nx_1000_sp)
        self.assertListEqual(graph_10000_sp[1], nx_10000_sp)
        self.assertListEqual(graph_20000_sp[1], nx_20000_sp)
        self.assertListEqual(graph_30000_sp[1], nx_30000_sp)
        #
        # for set in graph_10_ccs:
        #     self.assertTrue(nx_10_graph.__contains__(set))
        # for set in nx_10_ccs:
        #     self.assertTrue(graph_10_ccs.__contains__(set))
        #
        # for set in graph_100_ccs:
        #     self.assertTrue(nx_100_graph.__contains__(set))
        # for set in nx_100_ccs:
        #     self.assertTrue(graph_100_ccs.__contains__(set))
        #
        # for set in graph_1000_ccs:
        #     self.assertTrue(nx_1000_graph.__contains__(set))
        # for set in nx_1000_ccs:
        #     self.assertTrue(graph_1000_ccs.__contains__(set))
        #
        # for set in graph_10000_ccs:
        #     self.assertTrue(nx_10000_graph.__contains__(set))
        # for set in nx_10000_ccs:
        #     self.assertTrue(graph_10000_ccs.__contains__(set))
        #
        # for set in graph_20000_ccs:
        #     self.assertTrue(nx_20000_graph.__contains__(set))
        # for set in nx_20000_ccs:
        #     self.assertTrue(graph_20000_ccs.__contains__(set))
        #
        # for set in graph_30000_ccs:
        #     self.assertTrue(nx_30000_graph.__contains__(set))
        # for set in nx_30000_ccs:
        #     self.assertTrue(graph_30000_ccs.__contains__(set))

        if __name__ == '__main__':
            unittest.main()

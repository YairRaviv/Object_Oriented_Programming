import copy
import math
from collections import deque
from queue import Queue

from DiGraph import DiGraph
from GraphAlgoInterface import GraphAlgoInterface
import json
import heapq


class GraphAlgo(GraphAlgoInterface):

    def __init__(self, graph: DiGraph = None):
        if graph is None:
            graph = DiGraph()
        self.__graph = graph

    def get_graph(self) -> DiGraph:
        return self.__graph

    def save_to_json(self, file_name: str):
        nodes = []
        edges = []
        for node in self.get_graph().get_all_v():
            nodes_dict = {}
            nodes_dict["id"] = node
            nodes.append(nodes_dict)
            node_edges = self.get_graph().all_out_edges_of_node(node)
            node_edges_keys = self.get_graph().all_out_edges_of_node(node).keys()
            for edge in node_edges_keys:
                edges_dict = {}
                edges_dict["src"] = node
                edges_dict["dest"] = edge
                edges_dict["w"] = node_edges[edge]
                edges.append(edges_dict)
        ans_dict = {}
        ans_dict["Nodes"] = nodes
        ans_dict["Edges"] = edges

        try:
            with open(file_name, "w") as file:
                json.dump(ans_dict, default=lambda m: m.__dict__, indent=4, fp=file)
                return True
        except IOError as e:
            print(e)
            return False

    def load_from_json(self, file_name: str) -> bool:
        my_dict = {}
        graph = DiGraph()
        try:
            with open(file_name, "r")as file:
                my_dict = json.load(file)
                nodes = my_dict["Nodes"]
                edges = my_dict["Edges"]
                for node_dict in nodes:
                    graph.add_node(node_dict["id"])
                for edge_dict in edges:
                    graph.add_edge(edge_dict["src"], edge_dict["dest"], edge_dict["w"])

            self.__graph = graph
            return True
        except IOError as e:
            print(e)
            return False

    def shortest_path(self, id1: int, id2: int) -> (float, list):
        ans = (math.inf, [])
        graph = self.get_graph()
        graph_nodes = graph.get_all_v()
        visited = {}
        distance = {-1: math.inf}
        path = {}
        queue = []
        if id1 not in graph_nodes or id2 not in graph_nodes or id1 == id2:
            return ans
        for node in graph.get_all_v():
            distance[node] = math.inf
        distance[id1] = 0
        heapq.heappush(queue, (distance[id1], id1))
        while len(queue) != 0:
            current = heapq.heappop(queue)[1]
            if current in visited:
                continue
            else:
                visited[current] = 1
                curr_edges = graph.all_out_edges_of_node(current)
                for tmp_node in curr_edges:
                    edge = curr_edges[tmp_node]
                    if edge + distance[current] < distance[tmp_node]:
                        distance[tmp_node] = edge + distance[current]
                        path[tmp_node] = current
                    if tmp_node not in visited:
                        heapq.heappush(queue, (distance[tmp_node], tmp_node))

        if id2 not in path:
            return ans

        final_distance = 0
        final_list = []
        final_list.append(id2)
        final_distance = distance[id2]
        tmp = path[id2]
        while tmp != id1:
            final_list.append(tmp)
            tmp = path[tmp]
        final_list.append(id1)
        final_list.reverse()
        return (final_distance, final_list)

    def connected_component(self, id1: int):
        if self.get_graph() is None:
            return []
        graph = self.__graph
        ans = {}
        if id1 not in graph.get_all_v():
            return []
        ans = self.DFS(id1)
        ans_2 = {}
        dic = graph.end_edges
        graph.end_edges = graph.edges
        graph.edges = dic
        ans_2 = self.DFS(id1)
        intersection = []
        for node in ans:
            if node in ans_2:
                intersection.append(node)
        dic = graph.end_edges
        graph.end_edges = graph.edges
        graph.edges = dic
        # intersection.sort()
        return intersection

    def connected_components(self):
        ans = []
        vis = {}
        if self.get_graph() is None:
            return ans
        for node in self.get_graph().get_all_v():
            if node not in vis:
                tmp_list = self.connected_component(node)
                for n in tmp_list:
                    vis[n] = 1
                ans.append(tmp_list)
        return ans

    def plot_graph(self) -> None:
        fig, ax = plt.subplots()
        coordsA = "data"
        coordsB = "data"
        all_v = self.graph.get_all_v().keys()
        for node in all_v:
            x, y, z = self.graph.get_node(node).get_pos()
            curr_point = np.array([x, y])
            xyA = curr_point

            ax.annotate(self.graph.get_node(node).get_node_id(), (x, y),
                        color='black',
                        fontsize=12)  # draw id
            for e in self.graph.all_out_edges_of_node(node).keys():
                x, y, z = self.graph.get_node(e).get_pos()
                curr_point = np.array([x, y])
                xyB = curr_point
                con = ConnectionPatch(xyA, xyB, coordsA, coordsB,
                                      arrowstyle="-|>", shrinkA=6, shrinkB=6,
                                      mutation_scale=14, fc="black", color="black")
                ax.plot([xyA[0], xyB[0]], [xyA[1], xyB[1]], "o", color='gray', markersize=8, linewidth=10)

                ax.add_artist(con)

        plt.xlabel("x")
        plt.ylabel("y")
        plt.title("My Python Graph")
        plt.show()

    def BFS(self, id1: int) -> list:
        graph = self.__graph
        ans = [id1]
        vis = {}
        vis[id1] = 1
        queue = Queue()
        queue.put(id1)
        while not queue.empty():
            curr = queue.get()
            vis[curr] = 1
            for neighbor in graph.all_out_edges_of_node(curr):
                if neighbor not in vis:
                    ans.append(neighbor)
                    queue.put(neighbor)
                    vis[neighbor] = 1

        return ans

    def DFS(self, id1: int) -> dict:
        graph = self.__graph
        ans = {id1: 1}
        vis = {}
        stack = deque()
        stack.append(id1)
        while len(stack) != 0:
            curr = stack.pop()
            if curr not in vis:
                vis[curr] = 1
                for neighbor in graph.all_out_edges_of_node(curr):
                    if neighbor not in vis:
                        stack.append(neighbor)
                        ans[neighbor] = 1
        return ans

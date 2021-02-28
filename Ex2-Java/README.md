Ex2 project!

this project has 2 parts,

  Part A - interfaces and implementations of directed weighted graph object.
  and include some algorithms for shortests path calculating, connectivity check,deep copy.. and so on

  Part B- Game server and implementatios of pokemosn game

the src directory contains 2 packages:
api and gameclient.

api:

  this folder contains everything that related to part A. 
  the interfaces and the implementation of the next classes:
  interfaces:
    node_data
    edge_data
    geo_location
    directed_weighted_graph
    dw_graph_algorithms
  implementations:
    Node_D(implement node_data, represent node at the graph)
    Edge_D(implement edge_data, represent edge at the graph)
    Geo_l(implement geo_location, represent 3D location of node)
    DW_Graph(implement directed_weighted_graph, represent the graph)
    DWG_Algo(implement dw_graph_algorithms, represent the object that performs the algorithms over the graph)
    
  the mainly capabilities of those implementations is:
  1)return a collection with all nodes at the graph in O(1) comlexity
  2)return a collection with all edges getting out from given node in O(1) comlexity
  3)return a deep copy of given graph in O(n) complexity
  4)perform a hard connectivity check in  O(n^2) complexity
  5)return a list of nodes that represent the shortests path between two given nodes(src->dest) in O(k * n) n=nodes number, k= edges number
  6)return the shortest distance between two given nodes(src->dest) in O(k * n) n=nodes number, k= edges number
  7)save and load graph to/from file/String as json format.
  
  
 gameclient:
  
  this folder contains everything that related to part B.
  
  the classes:
  -Ex2(this is the main class that responsible to connect to the server,accepct the information about the desiere level,init the level with the agents, and control
  the thread that run the game)
  
  -Frame(responsible on the login graphics frame of the the game)
  -CL_agent(represent the agents of the game)
  -CL_pokemon(represent the pokemons of the game)
  -MyArena(responsible to manage the game's board,and to deserialize the information that arrive from the server to acctual objects)
  -MyFrame(responsible to show graphicly the progress of the game)
  
  the mainly capabilties of this part is:
  1) show the game progress in a very noce way(with background and acctual pokemons&agents images)
  2) smart managment of the game prosess(by using "move agents" algorithm, that decide for each agent what is the best next move for him)
  
  the other directories of the project:
  
  test: contains test classes for the graph implementation, for the graph_algo implementation, and for the logical classes of the game
  data: contains the json files of the graphs that used for the game
  doc: contains the documentation of the project
  
  the project also containts a executable jar file named Ex2.jar for running the game comfortly. 
  
 
    

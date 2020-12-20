# OOP-EX2-Game
Part 1:

1.file list: 
1.1 interface directed_weighted_graph//
     class DWGraph_DS implements directed_weighted_graph, Serializable//
     interface dw_graph_algorithms //
     class DWGraph_Algo implements dw_graph_algorithms//   
     interface edge_data//
     class EdgeData implements edge_data, Serializable//
     interface game_service extends Serializable.
     interface geo_location//
     interface node_data//
     internal class Geo implements geo_location,Serializable//
     class NodeData implements node_data, Serializable//
     class NodeTag- This class only exists because the tag is an integer.//
     class NodeCompareForQueue implements Comparator<NodeTag>, Serializable//
1.2 tests file: DWGraphAlgo_Test 
	     DWGraph_DS_Test
	     EdgeData_Test
	     NodeData_Test

2.classes 
 2.1 class DWGraph_DS This class is the implements of the interface directed_weighted_graph. 
 In this class, I have implemented two Int variables and two data structures of type HashMap.
 This class is actually creating a weighted graph.

2.2 class NodeData implements node_data This class redeems the nodes in the graph along with weight on each edge.
 This class has all the functions that need for node and as internal class the internal class Geo that implements geo_location.

2.2 class DWGraph_Algo This class implements dw_graph_algorithms. 
 This class has functions that check whether a graph is binding to the shortest distance between nodes, and a list of nodes that pass through them at this distance. 
 The distance is basically the weights of each edge between a particular node and another.

2.3 class EdgeData implements edge_data.
 This class represents the weight of each edge the source of the edge and its end in a directed graph

3.Why HashMap data structure? I chose this data structure because it has very good running times and has a key and value. To remove/get/put from it this O(1). 
Because it has value, and a key you can do a lot of functions with good efficiency that get either the key or the value. 
The value and key can be anything both of which can be objects or variables or one variable and the other object and all that makes this data structure very convenient. 
I used this data structure in all class because of this convenience.

4.How I implemented the priority queue? I did a class called NodeCompareForQueue I'll explain her use now. 
This class is used to prioritize nodes by tag. 
I tried to do a comparison function for the priority queue, but I couldn't help but do another class.

5.Complexity of functions- except from functions with O(1) Complexity. 
5.1 Complexity of functions in DWGraph_DS 
 1. public DWGraph_DS(DWGraph_DS g)- It's a copying constructor with the complexity of the size of the nodes or edges (The bigger of the two, of course).(O(edgeSize())||(O(nodeSize())) 
 2. public Collection<node_info> getV(int node_id)- The complication is the number of neighbors to have for the node.(O(neighbors.size)) 
 3. public node_info removeNode(int key)- The reasoning is the number of the nodes.(O(nodes.size))
 4. public boolean equals(Object g) -It's equals function with the complexity of the size of the nodes or edges (The bigger of the two, of course).(O(edgeSize())||(O(nodeSize())) 
 5. public String toString() - Complexity of (O(edgeSize())||(O(nodeSize())) The bigger of the two, of course. 
5.2 Complexity of functions inl class NodeData 
 1.public boolean equals(Object n) - Function with complexity of the number of neighbors (O(neighbors.size)). 
5.3 Complexity of functions in DWGraph_Algo
 1. public boolean isConnected() - (O(edgeSize())||(O(nodeSize())).
 2. public directed_weighted_graph copy() - public DWGraph_DS(DWGraph_DS g) Complexity. 
 3. public double shortestPathDist(int src, int dest) - (O(edgeSize())||(O(nodeSize())).
 4. public List<node_info> shortestPath(int src, int dest) - The same complexity as the last one is very similar. 
 5. public boolean save(String file) -  (O(edgeSize())||(O(nodeSize())).
 6. public boolean load(String file) -  (O(edgeSize())||(O(nodeSize())).

part 2: 

1.file list: 
1.1 : class Arena
       class CL_Agent
       class CL_Pokemon
       class Login
       class MyFrame1
       class myPanel
       class Point3D implements geo_location
       class Range
       class Range2D
       class Range2Range
1.2 : tests: 
       class Ex2

2. classes
2.1: class Arena- This class basically produces the arena where the game takes place 
 with everything that's needed like absorbing all the anointing stuff.
 
2.2: class CL_Agent-  This class has everything the agent needs, like updating him and 
 knowing the next node he's going to go to. 

2.3: class CL_Pokemon - This class has everything you need to know about the Pokemon.

2.4: class Login - This class is basically a frame through which you enter the game.

2.5: class MyFrame1 - This class is basically the frame in which the game takes place.

2.6: class myPanel - In this class, you actually draw the Pokémon graph and everything else inside the frame panel.

2.7: class Point3D - similar to Geo.

2.8: class Range - This class checks the dimension range and checks the portion to use.

2.9: class Range2D - Make the same thigs like Range but with 2 dimensions.

2.10 -  class Range2Range - This class connects the dimensions needed and the portion needed for the frame.

3.Complexity of functions:
 Complexity of functions in class Arena - the most important class:
 1.  public void setGraph(String g) - Same complexity as public boolean save(String file).
 2.  public static List<CL_Agent> getAgents - (agent.size).
 3. public static ArrayList<CL_Pokemon> json2Pokemons(String fs) - (pokemon.size).
 4. public static void updateEdge -  (edge.size).
 5. private static Range2D GraphRange - (nodes.size).
 6. public static Range2Range w2f - same as GraphRange.
 7. jsonToGraph - same as setGraph(String g).

4. Explanation of function:
 1. public void update(String json) - in CL_Agent that function update all the agent data.
 2. public static void updateEdge - in Arena that function is updating with the Pokemon 
 on which edge it's found using a calculation on each edge.
 3. public static Range2Range w2f(directed_weighted_graph g, Range2D frame) - 
 This function checks the space the graph extends to for the frame.
   
6.test-run: To run the project you must press the pointy green button to the left of each class head of Ex2. 
 There will be a frame that has two places, one for id and the other for a stage name.

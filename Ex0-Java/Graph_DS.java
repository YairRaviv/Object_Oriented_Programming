package ex0;

import java.util.*;

public class Graph_DS implements graph {
    private int size;
    private int NumOfEdges;
    private HashMap<Integer,node_data> MyGraph;
    private int MC;

    /**
     *  create new graph
     */
    public Graph_DS()
    {
        this.MyGraph=new HashMap<Integer, node_data>();
        this.NumOfEdges=0;
    }

    /**
     * deep copy constructor fpr graph
     * @param g
     */
    public Graph_DS(Graph_DS g)
    {
     this.MyGraph=new HashMap<Integer, node_data>();
     Iterator<node_data>i=g.getV().iterator();
     while(i.hasNext())
     {
         node_data n=i.next();
         node_data w=new NodeData((NodeData) n);
         this.MyGraph.put(w.getKey(),w);
     }

     for(node_data y:g.getV())
     {
         for(node_data ni:y.getNi())
         {
             this.MyGraph.get(y.getKey()).addNi(this.MyGraph.get(ni.getKey()));
         }
     }
     this.NumOfEdges=g.NumOfEdges;
     this.MC=g.MC;
     this.size=g.size;
    }

    /**
     * return node accoriding his position(id) in the graph(hashmap)
     * @param key - the node_id
     * @return
     */
    public node_data getNode(int key) {
        if(null==MyGraph.get(key))return null;
      return MyGraph.get(key);
    }

    /**
     *  return true if node 1 and node 2 (the actual nodes) are neighbors
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2)
    {
        if(null==getNode(node1)||null==getNode(node2))return false;
        if(getNode(node1).hasNi(node2))return true;
        else return false;
    }

    /**
     *add node(according his id) to the grap
     * @param n
     */
    @Override
    public void addNode(node_data n)
    {
        n.setTag(0);
        MyGraph.put(n.getKey(),n);
       this.size++;
       this.MC++;
    }

    /**
     *  make node 1 and node 2 neighbors
     * @param node1
     * @param node2
     */
    @Override
    public void connect(int node1, int node2)
    {
        if(node1==node2)return;
        if(null==getNode(node1)||null==getNode(node2))return;
        if(getNode(node1).hasNi(node2)||getNode(node2).hasNi(node1))return;
           getNode(node1).addNi(getNode(node2));
           getNode(node2).addNi(getNode(node1));
           NumOfEdges++;
           MC++;

    }

    /**
     * return collection of all the nodes at this graph
     * @return
     */
    @Override
    public Collection<node_data> getV() {
        return this.MyGraph.values();
    }

    /**
     * return collection of node_id's(the actual node) neighbors
     * @param node_id
     * @return
     */
    @Override
    public Collection<node_data> getV(int node_id) {
        return getNode(node_id).getNi();
    }

    /**
     *  remove node according his id(key) from the graph
     * @param key
     * @return
     */
    @Override
    public node_data removeNode(int key)
    {
        if(null==getNode(key))return null;
        node_data ans=new NodeData((NodeData) getNode(key));
        if(!MyGraph.containsKey(key))return getNode(key);
        else
        {
            for(node_data n:getNode(key).getNi())
            {
               n.removeNode(getNode(key));
               NumOfEdges--;
               MC++;
            }
            MyGraph.remove(key,getNode(key));
            size--;
            MC++;
        }
      return ans;
    }

    /**
     * disconnect node 1 and node 2(the actual nodes)
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2)
    {
        if(null==getNode(node1)||null==getNode(node2))return;
        if(getNode(node1).getNi().contains(getNode(node2))&&getNode(node2).getNi().contains(getNode(node1)))
        {
            getNode(node1).getNi().remove(getNode(node2));
            getNode(node2).getNi().remove(getNode(node1));
            this.MC++;
            NumOfEdges--;
        }
    }

    /**
     * return the number of nodes at the graph
     * @return
     */
    @Override
    public int nodeSize() {
        return this.size;
    }

    /**
     * return the number of edges of the graph
     * @return
     */
    @Override
    public int edgeSize() {
        return NumOfEdges;
    }

    /**
     * return The number of operations performed in the graph
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }
}

package api;

import com.google.gson.*;
import gameClient.util.Point3D;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DW_Graph implements directed_weighted_graph
{
    private HashMap<Integer,node_data> graph;
    private HashMap<Integer,HashMap<Integer,edge_data>> edges;
    private HashMap<Integer,HashMap<Integer,edge_data>> endedges;
    private int nodesize,edgesize,mc;

    /**
     * basic directed weighted graph constructor
     */
    public DW_Graph()
    {
        this.graph=new HashMap<Integer,node_data>();
        this.edges=new HashMap<Integer,HashMap<Integer,edge_data>>();
        this.endedges=new HashMap<Integer,HashMap<Integer,edge_data>>();
        this.nodesize=0;
        this.edgesize=0;
        this.mc=0;
    }

    /**
     * deep copy constructor for directed weighted graph
     * @param g
     */
    public DW_Graph(directed_weighted_graph g)
    {
        this.edges=new HashMap<Integer,HashMap<Integer,edge_data>>();
        this.endedges=new HashMap<Integer,HashMap<Integer,edge_data>>();
        this.graph=new HashMap<Integer, node_data>();
        this.nodesize=0;
        this.edgesize=0;
        this.mc=0;
        Iterator<node_data> itr1=g.getV().iterator();
        while(itr1.hasNext())
        {
            node_data n=new Node_D(itr1.next());
            this.addNode(n);
        }
        Iterator<node_data>itr2=g.getV().iterator();
        while(itr2.hasNext())
        {
            node_data n=itr2.next();
            Iterator<edge_data> itr22=g.getE(n.getKey()).iterator();
            while(itr22.hasNext())
            {
                edge_data e=itr22.next();
                this.connect(e.getSrc(),e.getDest(),e.getWeight());
            }
        }

    }

    /**
     * returns the node from the hashmap that represent the graph by the node key
     * @param key - the node_id
     * @return
     */

    @Override
    public node_data getNode(int key)
    {
        if(null==graph.get(key))return null;
        return graph.get(key);
    }

    /**
     * returns the edge between node1 - node2.
     * if there no edge between them or if the graph doesnt contain both of them- this method return -1;
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest)
    {
        if(null==getNode(src)||null==getNode(dest)||src==dest)return null;
        return edges.get(src).get(dest);
    }

    /**
     * this method add this (given) node to the graph
     * and init for this node two new hashmaps(inside edges,endedges hashmaps) that represent the edges that start or end at this node
     * @param n
     */
    @Override
    public void addNode(node_data n)
    {
      if(null!=graph.get(n.getKey()))return;
      graph.put(n.getKey(),n);
      this.nodesize++;
      this.mc++;
      edges.put(n.getKey(),new HashMap<Integer, edge_data>());
      endedges.put(n.getKey(),new HashMap<Integer, edge_data>());
    }

    /**
     * this method create new edge between node1-node2 with the value w.
     * if there is already edge between them- this method just update the value of this edge.
     * @param src
     * @param dest
     * @param w
     */
    @Override
    public void connect(int src, int dest, double w)
    {
        if(src==dest)return;
        if(w<0)return;
        if(null==getNode(src)||null==getNode(dest))return;
        if(null==edges.get(src).get(dest))this.edgesize++;
        edge_data e=new Edge_D(src,dest,w);
        edges.get(src).put(dest,e);
        endedges.get(dest).put(src,e);
        this.mc++;
    }

    /**
     * this method returns a collection with all nodes of the graph
     * @return
     */
    @Override
    public Collection<node_data> getV()
    {
        return this.graph.values();
    }

    /**
     * this method returns a collection with all edges that start at this node(given)
     * @param node_id
     * @return
     */
    @Override
    public Collection<edge_data> getE(int node_id)
    {
        if(null==getNode(node_id))return null;
        return edges.get(node_id).values();
    }


    /**
     * this method removes the given node from the graph, and removes all the edges that start or end at this node
     * by iterate over all this edges from "edges" and "endedges" hashmaps
     * @param key
     * @return
     */
    @Override
    public node_data removeNode(int key)
    {
        if(null==graph.get(key))return null;
        node_data keyy=getNode(key);
        Collection<edge_data> c=getE(key);
        Collection<edge_data> cc=endedges.get(key).values();
        Iterator<edge_data> itr=cc.iterator();
        while(itr.hasNext())
        {
            int tmp=itr.next().getSrc();
            endedges.get(tmp).remove(key);
            edges.get(tmp).remove(key);
        }
        edges.remove(key);
        endedges.remove(key);
        this.edgesize-=c.size();
        this.edgesize-=cc.size();
        mc+=cc.size();
        mc+=c.size();
        graph.remove(key);
        mc++;
        nodesize--;
        return keyy;
    }

    /**
     * this method removes the edge between src->dest nodes from the graph
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data removeEdge(int src, int dest)
    {
        if(src==dest||null==getNode(src)||null==getNode(dest)||null==edges.get(src).get(dest))return null;
        edge_data e=edges.get(src).get(dest);
        edges.get(src).remove(dest);
        endedges.get(dest).remove(src);
        edgesize--;
        mc++;
        return e;
    }

    @Override
    public int nodeSize()
    {
        return this.nodesize;
    }

    @Override
    public int edgeSize()
    {
        return this.edgesize;
    }

    @Override
    public int getMC()
    {
        return this.mc;
    }

    public static class deserializer implements JsonDeserializer<DW_Graph>
    {

        /**
         * this method defines how to convert json file that represent directed weighted graph to real object
         */
        @Override
        public DW_Graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject j= jsonElement.getAsJsonObject();
           DW_Graph g=new DW_Graph();

           JsonArray nodes=j.get("Nodes").getAsJsonArray();
            JsonArray edges=j.get("Edges").getAsJsonArray();
          // JsonObject endedges=j.get("endedges").getAsJsonObject();

           for(JsonElement jj : nodes)
           {
              JsonElement node=jj;
               int key=node.getAsJsonObject().get("id").getAsInt();
               //int tag=node.getAsJsonObject().get("tag").getAsInt();
               //String info=node.getAsJsonObject().get("info").getAsString();
               //double weight=node.getAsJsonObject().get("weight").getAsDouble();
               String s= node.getAsJsonObject().get("pos").getAsString();
               String [] arr=s.split(",");
               double x=Double.parseDouble(arr[0]);
               double y=Double.parseDouble(arr[1]);
               double z=Double.parseDouble(arr[2]);

               Point3D p=new Point3D(x,y,z);
               geo_location gl=new Geo_l(p);
               node_data n=new Node_D(key,gl);
               g.addNode(n);
           }

           for(JsonElement ee : edges)
           {
               JsonElement edge=ee;
               int src=edge.getAsJsonObject().get("src").getAsInt();
               int dest=edge.getAsJsonObject().get("dest").getAsInt();
               double weight=edge.getAsJsonObject().get("w").getAsDouble();
               g.connect(src,dest,weight);

           }
          return g;
        }
    }

    @Override
    public boolean equals(Object obj) {
        DW_Graph ng= (DW_Graph) obj;
        if(this.nodesize!=ng.nodesize||this.edgesize!=ng.edgesize)return false;
        Iterator<node_data> itr=ng.getV().iterator();
        while(itr.hasNext())
        {
            node_data n=itr.next();
            if(!this.graph.containsValue(n))return false;
            Collection<edge_data> c=ng.getE(n.getKey());
            Collection<edge_data> cc=this.getE(n.getKey());
            Iterator<edge_data>  itr2=c.iterator();
            Iterator<edge_data>  itr22=cc.iterator();
            while(itr2.hasNext())
            {
                if(!c.contains(itr22.next())||!cc.contains(itr2.next()))return false;
            }
        }
        return true;
    }
}

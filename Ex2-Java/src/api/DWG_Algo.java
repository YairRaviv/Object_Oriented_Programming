package api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class DWG_Algo implements dw_graph_algorithms
{
    private directed_weighted_graph graph;

    /**
     * create new directed weighted graph algorithms
     */
    public DWG_Algo()
    {
        this.graph=new DW_Graph();
    }
    /**
     * "set" operation,init g to be this graph
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g)
    {
        this.graph=g;
    }

    @Override
    public directed_weighted_graph getGraph()
    {
        return this.graph;
    }

    /**
     * this method create deep copy of this graph by using deep copy constructor of directed weighted graph class
     * @return
     */
    @Override
    public directed_weighted_graph copy()
    {
        directed_weighted_graph g=new DW_Graph(this.graph);
        return g;
    }

    /**
     * this method returns true if there is a way from each node at the graph-to every others nodes at the graph
     * this method using "shortest path dist" algorithm to check if one of the distance is -1, -1 means - there is no way between
     * the two current ndoes
     * @return
     */
    @Override
    public boolean isConnected()
    {
        Iterator<node_data> itr1=this.graph.getV().iterator();
        while(itr1.hasNext())
        {
            node_data n= itr1.next();
            Iterator<node_data> itr2=this.graph.getV().iterator();
            while(itr2.hasNext())
            {
                if(this.shortestPathDist(n.getKey(),itr2.next().getKey())==-1)return false;
            }
        }
        return true;
    }
    /**
     * this method get list of nodes(using shortest path algorithm) that represent the shortest path from src to dest.
     * and and returns the sum of the weights at this path.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest)
    {
        if(src==dest)return 0;
        if(null==shortestPath(src,dest))return -1;
        List<node_data> path=shortestPath(src,dest);
        if(path.size()==0)return -1;
        double length=0;
        Iterator<node_data> itr =path.iterator();
        node_data tmp=itr.next();
        while(itr.hasNext())
        {
            node_data tmp1= itr.next();
            length+=getGraph().getEdge(tmp.getKey(), tmp1.getKey()).getWeight();
            tmp=tmp1;
        }
        return length;
    }

    /**
     * this method return list of nodes that represent the shortest path(by weights) from src to dest.
     * its using "Dijkstra's" algorithm.
     * the algorithm start from src and iterate over the graph by "BFS" algorithm
     * and for every node-update the shortest distance from src to this node,
     * in parallel,the algorithm update the path hashmap,that contain for every key-the node that represent
     * the "father" of that node with the shortest path.
     * finally the algorithm create a list and iterate in reverse at the hashmap from dest to src to restore the nodes
     * that represent the shortest path from dest to src,and then flip this list
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest)
    {
        HashMap<Integer,Double> dis=new HashMap<>();
        HashMap<Integer,Boolean> vis=new HashMap<>();
        Queue<Integer> q=new LinkedList<>();
        HashMap<Integer, node_data> Path =new HashMap<>();
        ArrayList<node_data> rev=new ArrayList<>();
        ArrayList<node_data> ans =new ArrayList<>();
        if(src==dest||null==graph.getNode(src)||null==graph.getNode(dest))return null;

        Iterator<node_data> itr1=this.graph.getV().iterator();
        while(itr1.hasNext())
        {
            node_data tmp1= itr1.next();
            if(tmp1.getKey()!=src)
            {
                dis.put(tmp1.getKey(),Double.MAX_VALUE);
            }
            else dis.put(tmp1.getKey(),0.0);
        }
        q.add(src);
        while(!q.isEmpty())
        {
            node_data curr=graph.getNode(q.poll());
            if(null==vis.get(curr.getKey()))
            {
                vis.put(curr.getKey(),true);
                Iterator<edge_data> itr2=graph.getE(curr.getKey()).iterator();
                while(itr2.hasNext())
                {
                    edge_data e=itr2.next();
                    node_data tmp2=graph.getNode(e.getDest());
                    if(null==vis.get(tmp2.getKey()))q.add(tmp2.getKey());
                    double d=graph.getEdge(curr.getKey(), tmp2.getKey()).getWeight();
                    if(d+dis.get(curr.getKey())< dis.get(tmp2.getKey()))
                    {
                        dis.put(tmp2.getKey(),d+dis.get(curr.getKey()));
                        Path.put(tmp2.getKey(),curr);
                    }
                }

            }
        }
        if(null==Path.get(dest))return null;
        rev.add(graph.getNode(dest));
        int tmp=dest;
        while(tmp!=src)
        {
            rev.add(Path.get(tmp));
            tmp=Path.get(tmp).getKey();
        }

        if(rev.size()<=1)return rev;
        int tmp2= rev.size()-1;
        while (tmp2>=0)
        {
            ans.add(rev.get(tmp2));
            tmp2--;
        }
        return ans;
    }


    /**
     * this method serializes directed weighted graph to given file at json format
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file)
    {
       Gson gson=new GsonBuilder().setPrettyPrinting().create();
       String MyJson=gson.toJson(getGraph());
       try
       {
           PrintWriter p=new PrintWriter(file);
           p.write(MyJson);
           p.close();
           return true;
       }
       catch (FileNotFoundException e)
       {
           e.printStackTrace();
       }
        // serialize();
        return false;
    }

    /**
     * this method deserialze directed weighted graph from json text and init this graph
     * @param file - file name of JSON file
     * @return
     */
    @Override
    public boolean load(String file)
    {
        boolean flag=false;

        GsonBuilder b=new GsonBuilder();
        b.registerTypeAdapter(DW_Graph.class,new DW_Graph.deserializer());
        Gson gg=b.create();

        try
        {
            DW_Graph g=gg.fromJson(file,DW_Graph.class);
            init(g);
            flag= true;
        } catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }

        if(!flag)
        {
            try
            {
                FileReader r = new FileReader(file);
                DW_Graph g=gg.fromJson(r,DW_Graph.class);
                init(g);
                flag= true;
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        return flag;
    }




}

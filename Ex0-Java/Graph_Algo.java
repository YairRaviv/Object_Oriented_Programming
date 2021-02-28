package ex0;

import java.util.*;

public class Graph_Algo implements graph_algorithms
{
    private graph graph;

    /**
     * create new graph_algo(and new graph)
     */
    public Graph_Algo()
    {
        this.graph=new Graph_DS();
    }

    /**
     * create new graph_algo(and new graph)
     * @param g
     */
    @Override
    public void init(graph g)
    {
        this.graph=g;
    }

    /**
     * return deep copy graph of this.graph(by use deep copy contructor function)
     * @return
     */
    @Override
    public graph copy()
    {
        graph g=new Graph_DS((Graph_DS) this.graph);
        return g;
    }

    /**
     * this method return true if there a path from every node in the graph to any other node in th graph
     * this method using "BFS" algorithm,its start with the first node at the graph(at the hash map colletion of the graph)
     * and count about how many nodes in the graph he manages to pass,
     * if he passed about all the nodes=> the graph is connected
     * @return
     */
    @Override
    public boolean isConnected()
    {
        if(this.graph.nodeSize()<=1)return true;
        int counter=0;
        int size=this.graph.nodeSize();
        Queue<node_data> q=new LinkedList<>();


        Iterator<node_data> i=this.graph.getV().iterator();
        while(i.hasNext())
        {
            node_data nn= i.next();
            nn.setTag(0);
        }

        Iterator<node_data> it=this.graph.getV().iterator();
        node_data n=it.next();

        q.add(n);
        while(!q.isEmpty())
        {
            node_data curr=(q.poll());
            if(curr.getTag()!=1)
            {
                counter++;
                curr.setTag(1);
                Iterator<node_data> itr=curr.getNi().iterator();
                while(itr.hasNext())
                {
                    node_data t = itr.next();
                    q.add(t);
                }

            }

        }
        return(counter==size);
    }






    /**
     * return the number of nodes that represent the shortest path between "src" to "dest" nodes
     * by using "BFS" algorithm
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public int shortestPathDist(int src, int dest)
    {
        if(src==dest)return 0;
        int size=shortestPath(src,dest).size();
        if(size==0)return -1;
        return size-1;

    }

    /**
     * return ArrayList of nodes that represent the shortest path between src to dest nodes by using "BFS" algorithm
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest)
    {
        //HashMap<Integer,Boolean> vis=new HashMap<>();
        HashMap<Integer,node_data> path=new HashMap<>();
        ArrayList<node_data> rev=new ArrayList<>();
        ArrayList<node_data> ans =new ArrayList<>();
        Queue<node_data> q=new LinkedList<>();
        if(src==dest||null==graph.getNode(src)||null==graph.getNode(dest))return ans;

        Iterator<node_data> i=this.graph.getV().iterator();
        while(i.hasNext())
        {
            i.next().setTag(0);
        }
        q.add(graph.getNode(src));
        while(!q.isEmpty()&&null==path.get(dest))
        {
            node_data curr=q.poll();
            if(curr.getTag()==0)
            {

                curr.setTag(1);
                for(node_data y: curr.getNi())
                {
                    if(y.getTag()==0)
                    {
                        q.add(y);
                        if(null==path.get(y.getKey()))
                        {
                            path.put(y.getKey(),curr);
                        }
                    }
                }
            }
        }
        if(null==path.get(dest))return ans;
        rev.add(graph.getNode(dest));
        int tmp=dest;
        while(tmp!=src)
        {
            rev.add(path.get(tmp));
            tmp=path.get(tmp).getKey();
        }

        if(rev.size()<=1)return rev;
        int tmp2= rev.size()-1;
        while (tmp2>=0)
        {
            ans.add(rev.get(tmp2));
            tmp2--;
        }


        /*
        node_data[]arr=new node_data[tmp2];
        Iterator<node_data> o= rev.iterator();
        int pos=0;
        while(o.hasNext())
        {
            arr[pos]=o.next();
            pos++;
        }
        tmp2--;
        while(tmp2>=0)
        {
            ans.add(arr[tmp2]);
            tmp2--;
        }
*/
        return ans;

    }
}

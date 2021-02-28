package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements Serializable, weighted_graph_algorithms
{
    private weighted_graph WGraph;


    public WGraph_Algo()
    {
        this.WGraph=new WGraph_DS();
    }
    @Override
    public void init(weighted_graph g)
    {
        this.WGraph=g;
    }

    @Override
    public weighted_graph getGraph()
    {
        return this.WGraph;
    }

    @Override
    public weighted_graph copy() {
        weighted_graph wg=new WGraph_DS((WGraph_DS) this.WGraph);
        return wg;
    }

    @Override
    public boolean isConnected()
    {
        if(this.WGraph.nodeSize()<=1)return true;
        int counter=0;
        int size=this.WGraph.nodeSize();
        Queue<node_info> q=new LinkedList<>();


        Iterator<node_info> i=this.WGraph.getV().iterator();
        while(i.hasNext())
        {
            node_info nn= i.next();
            nn.setTag(0);
        }

        Iterator<node_info> it=this.WGraph.getV().iterator();
        node_info n=it.next();

        q.add(n);
        while(!q.isEmpty())
        {
            node_info curr=(q.poll());
            if(curr.getTag()!=1)
            {
                counter++;
                curr.setTag(1);
                Iterator<node_info> itr=WGraph.getV(curr.getKey()).iterator();
                while(itr.hasNext())
                {
                    node_info t = itr.next();
                    q.add(t);
                }

            }

        }
        return(counter==size);
    }

    @Override
    public double shortestPathDist(int src, int dest)
    {
        if(src==dest)return 0;
        List<node_info> path=shortestPath(src,dest);
        if(path.size()==0)return -1;
        double length=0;
        Iterator<node_info> itr =path.iterator();
        node_info tmp=itr.next();
        while(itr.hasNext())
        {
            node_info tmp1= itr.next();
            length+=getGraph().getEdge(tmp.getKey(), tmp1.getKey());
            tmp=tmp1;
        }
        return length;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest)
    {
        HashMap<Integer,Boolean> vis=new HashMap<>();
        Queue<Integer> q=new LinkedList<>();
        HashMap<Integer,node_info> Path =new HashMap<>();
        ArrayList<node_info> rev=new ArrayList<>();
        ArrayList<node_info> ans =new ArrayList<>();
        if(src==dest||null==WGraph.getNode(src)||null==WGraph.getNode(dest))return ans;

        Iterator<node_info> itr1=this.WGraph.getV().iterator();
        while(itr1.hasNext())
        {
            node_info tmp1= itr1.next();
            if(tmp1.getKey()!=src)
            {
                tmp1.setTag(Integer.MAX_VALUE);
            }
            else tmp1.setTag(0);
        }
        q.add(src);
        while(!q.isEmpty())
        {
            node_info curr=WGraph.getNode(q.poll());
            if(null==vis.get(curr.getKey()))
            {
                vis.put(curr.getKey(),true);
                Iterator<node_info> itr2=WGraph.getV(curr.getKey()).iterator();
                while(itr2.hasNext())
                {
                    node_info tmp2=itr2.next();
                    if(null==vis.get(tmp2.getKey()))q.add(tmp2.getKey());
                    double d=WGraph.getEdge(curr.getKey(), tmp2.getKey());
                    if(d+curr.getTag()< tmp2.getTag())
                    {
                        tmp2.setTag(d+curr.getTag());
                        Path.put(tmp2.getKey(),curr);
                    }
                }

            }
        }
        if(null==Path.get(dest))return ans;
        rev.add(WGraph.getNode(dest));
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

    @Override
    public boolean save(String file)
    {
        boolean flag=false;
        try
        {
            FileOutputStream f=new FileOutputStream(file,true);
            ObjectOutputStream o=new ObjectOutputStream(f);
            o.writeObject(this.WGraph);
            flag=true;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean load(String file)
    {
        try
        {
            FileInputStream is=new FileInputStream(file);
            ObjectInputStream ois=new ObjectInputStream(is);
            WGraph_DS read=(WGraph_DS) ois.readObject();
            this.WGraph=read;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}

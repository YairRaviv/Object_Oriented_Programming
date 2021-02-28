package ex1;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class WGraph_DS implements Serializable,weighted_graph
{
    private HashMap<Integer,HashMap<Integer,node_info>> Neighbors;
    private HashMap<Integer,node_info> wgraph;
    private HashMap<String,Double> distance;
   // private static HashMap<Integer,node_info> AllNodes;
    private int nodesize,edgesize,mc;
    private static int counter=0;

    public WGraph_DS()
    {
        this.Neighbors=new HashMap<Integer, HashMap<Integer, node_info>>();
        this.wgraph=new HashMap<Integer, node_info>();
        this.distance=new HashMap<String,Double>();
        this.nodesize=this.edgesize=this.mc=0;
    }

    //copy constructor
    public WGraph_DS(WGraph_DS G)
    {
        this.Neighbors=new HashMap<Integer, HashMap<Integer, node_info>>();
        this.wgraph=new HashMap<Integer, node_info>();
        this.distance=new HashMap<String,Double>();
        //ndoes copy

        Iterator<node_info> itr=G.getV().iterator();
        while(itr.hasNext())
        {
            this.addNode(itr.next().getKey());
        }

        //neighbors copy
        Iterator<node_info> itr2=G.getV().iterator();
        while(itr2.hasNext())
        {
            node_info a=itr2.next();
            node_info b=this.getNode(a.getKey());

            Iterator<node_info> itr3=G.getV(a.getKey()).iterator();
            while(itr3.hasNext())
            {

                node_info c=itr3.next();
                String s=GetString(b.getKey(),c.getKey());
                Double d=G.distance.get(s);
                this.connect(b.getKey(),c.getKey(),d);
            }
        }


    }


    @Override
    public node_info getNode(int key)
    {
        if(null==wgraph.get(key))return null;
        return wgraph.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2)
    {
        if(null==distance.get(GetString(node1,node2)))return false;
        return true;
    }

    @Override
    public double getEdge(int node1, int node2)
    {
        if(null==distance.get(GetString(node1,node2)))return -1;
        return distance.get(GetString(node1,node2));
    }

    @Override
    public void addNode(int key)
    {
        if(null!=wgraph.get(key))return;
        node_info n=new NodeInfo(key);
        wgraph.put(key,n);
        Neighbors.put(key,new HashMap<>());
        this.nodesize++;
        this.mc++;

    }

    @Override
    public void connect(int node1, int node2, double w)
    {
        if(node1==node2)return;
        if(w<0)return;
        if(null==getNode(node1)||null==getNode(node2))return;
        node_info a=getNode(node1);
        node_info b=getNode(node2);

        String A=GetString(node1,node2);
        String B=GetString(node2,node1);
        if(null==distance.get(A))
        {
            this.edgesize++;
            Neighbors.get(node1).put(node2,b);
            Neighbors.get(node2).put(node1,a);
        }
        distance.put(A,w);
        distance.put(B,w);
        this.mc++;
    }

    @Override
    public Collection<node_info> getV()
    {
        return wgraph.values();
    }

    @Override
    public Collection<node_info> getV(int node_id)
    {
        if(null==Neighbors.get(node_id))return null;
        return Neighbors.get(node_id).values();
    }

    @Override
    public node_info removeNode(int key)
    {

        if(null==wgraph.get(key))
        {
            node_info n=new NodeInfo(key);
            return n;
        }
        node_info y=wgraph.get(key);


            Iterator<node_info> itr=Neighbors.get(key).values().iterator();
            while(itr.hasNext())
            {
                node_info tmp=itr.next();
                Neighbors.get(tmp.getKey()).remove(key);
                Neighbors.get(key).remove(tmp);
                String a=GetString(key, tmp.getKey());
                String b=GetString(tmp.getKey(), key);
                distance.remove(a);
                distance.remove(b);
                this.edgesize--;
                this.mc--;
            }
            wgraph.remove(key);
            this.nodesize--;

        return y;
    }

    @Override
    public void removeEdge(int node1, int node2)
    {
        Neighbors.get(node1).remove(node2);
        Neighbors.get(node2).remove(node1);
        String a=GetString(node1, node2);
        String b=GetString(node2, node1);
        distance.remove(a);
        distance.remove(b);
        this.edgesize--;
        this.mc--;
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

    public String GetString(int a,int b)
    {
        node_info aa=getNode(a);
        node_info bb=getNode(b);
        String s=aa.getInfo()+'-'+bb.getInfo();
        return s;
    }





    ///////////////////////////////
    @Override
    public boolean equals(Object a) {
        WGraph_DS A=(WGraph_DS) (a);
        if(A.nodeSize()!=this.nodeSize()||A.edgesize!=this.edgeSize())return false;
        Iterator<node_info> Aitr=A.getV().iterator();
        while(Aitr.hasNext())
        {
            if(!this.wgraph.containsKey(Aitr.next().getKey()))return false;
        }
        Iterator<Double> Aedges=A.distance.values().iterator();
        Iterator<Double> Bedges=this.distance.values().iterator();
        while(Aedges.hasNext())
        {
           if(!this.distance.containsValue(Aedges.next()))return true;
        }
        return true;
    }


    public boolean Equals(node_info a, node_info b)
    {
        if(a.getKey()==b.getKey()&&a.getInfo()==b.getInfo())return true;
        return false;
    }






//===============================================InnerClass==============================================================



    public static class NodeInfo implements Serializable,node_info
    {
        private int key;
        private String info;
        private double tag;

        public NodeInfo(int k)
        {
            this.key=k;
            this.tag=0;
        }

        public NodeInfo()
        {
            this.key=counter;
            this.tag=0;
            counter++;
        }
        public NodeInfo(NodeInfo n)
        {
            this.key=n.key;
            this.info=n.info;
            this.tag=n.tag;
        }

        @Override
        public int getKey()
        {
            return this.key;
        }

        @Override
        public String getInfo()
        {
            return this.info;
        }

        @Override
        public void setInfo(String s)
        {
            this.info=s;
        }

        @Override
        public double getTag()
        {
            return this.tag;
        }

        @Override
        public double setTag(double t)
        {
            this.tag=t;
            return tag;
        }
        private void setKey(int k) {
            this.key = k;
        }



        @Override
        public String toString() {
            String s="the ID is:"+this.key+"    "+"the info is:"+this.info+"     "+"the tag is"+this.tag+"";
            return s;
        }

    }
}

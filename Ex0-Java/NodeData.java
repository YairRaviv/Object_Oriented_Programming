package ex0;

import java.util.Collection;
import java.util.HashMap;

public class NodeData implements node_data
{
    static int counter=0;
    private int key;
    private int tag;
    private String info;
    private HashMap<Integer,node_data> Neighbors;


    /**
     * create new node data(constructor)
     */
    public NodeData()
    {
        this.key=counter;
        this.Neighbors=new HashMap<>();
        counter++;
        this.tag=0;
    }

    /**
     * deep copy constructor for Node_Data
     * @param n
     */
    public NodeData(NodeData n)
    {
        this.key=n.key;
        this.info=n.info;
        this.tag=n.tag;
        this.Neighbors=new HashMap<>();
    }

    /**
     * return the id of this node
     * @return
     */
    @Override
    public int getKey()
    {
        return this.key;
    }

    /**
     * return collection of nodes that presenting the neighbors of this node
     * @return
     */
    @Override
    public Collection<node_data> getNi()
    {
        return Neighbors.values();
    }

    /**
     * return true if node 1 (this) and node 2 (key) are neighbors
     * @param key
     * @return
     */
    @Override
    public boolean hasNi(int key)
    {
     return (null!=this.Neighbors.get(key));
    }

    /**
     * make this node and t neighbors
     * @param t
     */
    @Override
    public void addNi(node_data t)
    {
        if(null==t)return;
      if(null==this.Neighbors.get(t.getKey()))this.Neighbors.put(t.getKey(),t);

    }

    /**
     * remove node from the neighbors collection of this node
     * @param node
     */
    @Override
    public void removeNode(node_data node)
    {
      if(null!=node&&null!=this.Neighbors.get(node.getKey()))this.Neighbors.remove(node.getKey(),node);
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
    public int getTag()
    {
        return this.tag;
    }

    @Override
    public void setTag(int t)
    {
      this.tag=t;
    }
}

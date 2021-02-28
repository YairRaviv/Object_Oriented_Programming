package api;
public class Node_D implements node_data
{

    private int key,tag;
    private String info;
    private double weight;
    private geo_location gl;
    private static int counter=1;

    /**
     * basic node constructor
     */
    public Node_D()
    {
        this.key=key;
        this.tag=0;
        this.weight=0;
        this.info = "";
        this.key=counter++;

    }
    /**
     * create new node_info with given key
     * @param key
     */
    public Node_D(node_data key)
    {
        this.key=key.getKey();
        this.tag=key.getTag();
        this.info=key.getInfo();
        this.weight=key.getWeight();
        this.gl=key.getLocation();
    }

    /**
     * create new node node with given id and location
     * @param key
     * @param g
     */
    public Node_D(int key,geo_location g)
    {
        this.key=key;
        this.tag=tag;
        this.info=info;
        this.weight=weight;
        this.gl=g;
    }
    @Override

    public int getKey()
    {
        return this.key;
    }

    public void setKey(int keyy)
    {
        this.key=keyy;
    }

    @Override
    public geo_location getLocation()
    {
        return this.gl;
    }

    @Override
    public void setLocation(geo_location p)
    {
        this.gl=p;
    }

    @Override
    public double getWeight()
    {
        return this.weight;
    }

    @Override
    public void setWeight(double w)
    {
        this.weight=w;
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
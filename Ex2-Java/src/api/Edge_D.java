package api;

import com.google.gson.*;

import java.lang.reflect.Type;

public class Edge_D implements edge_data
{
    private int src,dest,tag;
    private double weight;
    private String info;

    /**
     * basic edge constructor
     * @param src
     * @param dest
     * @param weight
     */
    public Edge_D(int src,int dest,double weight)
    {
        this.dest=dest;
        this.info="";
        this.src=src;
        this.tag=0;
        this.weight=weight;
    }

    /**
     * edge constructor with given parameters
     * @param src
     * @param dest
     * @param tag
     * @param weight
     * @param info
     */
    public Edge_D(int src,int dest,int tag,double weight,String info)
    {
        this.info=info;
        this.tag=tag;
        this.weight=weight;
        this.src=src;
        this.dest=dest;
    }
    @Override
    public int getSrc()
    {
        return this.src;
    }

    @Override
    public int getDest()
    {
        return this.dest;
    }

    @Override
    public double getWeight()
    {
        return this.weight;
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

    /**
     * this method defines how to convert json file that represent edge to real object
     */
    public class deserializer implements JsonDeserializer<Edge_D>
    {

        @Override
        public Edge_D deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
        {
            JsonObject j= jsonElement.getAsJsonObject();
            int src,dest,tag;
            double weight;
            String info;
            src=j.get("src").getAsInt();
            dest=j.get("dest").getAsInt();
            tag=j.get("tag").getAsInt();
            weight=j.get("weight").getAsDouble();
            info=j.get("info").getAsString();
            Edge_D e=new Edge_D(src,dest,weight);
            e.tag=tag;
            e.info=info;
            return e;

        }
    }
}

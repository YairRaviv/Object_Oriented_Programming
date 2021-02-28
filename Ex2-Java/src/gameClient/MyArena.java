package gameClient;

import api.*;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a multi Agents Arena which move on a graph - grabs Pokemons and avoid the Zombies.
 * @author boaz.benmoshe
 *
 */
public class MyArena
{

    public static final double EPS1 = 0.001, EPS2=EPS1*EPS1, EPS=EPS2;
    private directed_weighted_graph _gg;
    private List<CL_Agent> _agents;
    private List<CL_Pokemon> _pokemons;
    private List<String> _info;
    private static Point3D MIN = new Point3D(0, 100,0);
    private static Point3D MAX = new Point3D(0, 100,0);
    public long time;
    public static HashMap<Integer,Integer> moves_to_eat;


    /**
     * basic arena constructor
     */
    public MyArena()
    {
        _info = new ArrayList<String>();
    }

    /**
     * arena constructor with given grpah,pokemons,and agents
     * @param g
     * @param r
     * @param p
     */
    private MyArena(directed_weighted_graph g, List<CL_Agent> r, List<CL_Pokemon> p)
    {
        _gg = g;
        this.setAgents(r);
        this.setPokemons(p);
    }
    public void setPokemons(List<CL_Pokemon> f) {
        this._pokemons = f;
    }
    public void setAgents(List<CL_Agent> f) {
        this._agents = f;
    }
    public void setGraph(directed_weighted_graph g) {this._gg =g;}


    public void setTime(long tim)
    {
        this.time=tim;
    }
    public long getTime()
    {
        return this.time;
    }

    /**
     * this method check the min and max points of nodes at the graph to create a "range" for this level
     */

    private void init( )
    {
        MIN=null; MAX=null;
        double x0=0,x1=0,y0=0,y1=0;
        Iterator<node_data> iter = _gg.getV().iterator();
        while(iter.hasNext())
        {
            geo_location c = iter.next().getLocation();
            if(MIN==null) {x0 = c.x(); y0=c.y(); x1=x0;y1=y0;MIN = new Point3D(x0,y0);}
            if(c.x() < x0) {x0=c.x();}
            if(c.y() < y0) {y0=c.y();}
            if(c.x() > x1) {x1=c.x();}
            if(c.y() > y1) {y1=c.y();}
        }
        double dx = x1-x0, dy = y1-y0;
        MIN = new Point3D(x0-dx/10,y0-dy/10);
        MAX = new Point3D(x1+dx/10,y1+dy/10);

    }


    public List<CL_Agent> getAgents()
    {
        return _agents;
    }
    public List<CL_Pokemon> getPokemons()
    {
        return _pokemons;
    }


    public directed_weighted_graph getGraph()
    {
        return _gg;
    }
    public List<String> get_info()
    {
        return _info;
    }
    public void set_info(List<String> _info)
    {
        this._info = _info;
    }

    /**
     * this method create a agents list by deserialze it from json text that accepted from the server
     * @param aa
     * @param gg
     * @return
     */
    public static List<CL_Agent> getAgents(String aa, directed_weighted_graph gg)
    {
        ArrayList<CL_Agent> ans = new ArrayList<CL_Agent>();
        try
        {
            JSONObject ttt = new JSONObject(aa);
            JSONArray ags = ttt.getJSONArray("Agents");
            for(int i=0;i<ags.length();i++)
            {
                CL_Agent c = new CL_Agent(gg,0);
                c.update(ags.get(i).toString());
                ans.add(c);
            }
            //= getJSONArray("Agents");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return ans;
    }
    /**
     * this method create a pokemons list by deserialze it from json text that accepted from the server
     * @param fs
     * @return
     */
    public static ArrayList<CL_Pokemon> json2Pokemons(String fs)
    {

        ArrayList<CL_Pokemon> ans = new  ArrayList<CL_Pokemon>();
        try
        {
            JSONObject ttt = new JSONObject(fs);
            JSONArray ags = ttt.getJSONArray("Pokemons");
            for(int i=0;i<ags.length();i++)
            {
                JSONObject pp = ags.getJSONObject(i);
                JSONObject pk = pp.getJSONObject("Pokemon");
                int t = pk.getInt("type");
                double v = pk.getDouble("value");
                //double s = pk.getDouble("speed");
                String p = pk.getString("pos");
                CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null);
                ans.add(f);
            }
        }
        catch (JSONException e) {e.printStackTrace();}
        return ans;
    }

    /**
     * this method update to given pokemon the appropriate edge at the graph
     * @param fr
     * @param g
     */
    public static void updateEdge(CL_Pokemon fr, directed_weighted_graph g)
    {
        //	oop_edge_data ans = null;
        Iterator<node_data> itr = g.getV().iterator();
        while(itr.hasNext())
        {
            node_data v = itr.next();
            Iterator<edge_data> iter = g.getE(v.getKey()).iterator();
            while(iter.hasNext())
            {
                edge_data e = iter.next();
                boolean f =isOnEdge(fr.getLocation(), e,fr.getType(), g);
                if(f) {fr.set_edge(e);}
            }
        }
    }

    /**
     * this methods gets a location,"type" of pokemon and edge at the graph and returns true if this location is on
     * this edge
     * @param p
     * @param src
     * @param dest
     * @return
     */
    public static boolean isOnEdge(geo_location p, geo_location src, geo_location dest)
    {

        boolean ans = false;
        double dist = src.distance(dest);
        double d1 = src.distance(p) + p.distance(dest);
        if(dist>d1-EPS2) {ans = true;}
        return ans;
    }
    public static boolean isOnEdge(geo_location p, int s, int d, directed_weighted_graph g)
    {
        geo_location src = g.getNode(s).getLocation();
        geo_location dest = g.getNode(d).getLocation();
        return isOnEdge(p,src,dest);
    }
    public static boolean isOnEdge(geo_location p, edge_data e, int type, directed_weighted_graph g)
    {
        int src = e.getSrc();
        int dest = e.getDest();
        if(type<0 && dest>src) {return false;}
        if(type>0 && src>dest) {return false;}
        return isOnEdge(p,src, dest, g);
    }

    /**
     * this methods create a range to the graph for the graphics interface
     * @param g
     * @return
     */
    private static Range2D GraphRange(directed_weighted_graph g)
    {
        Iterator<node_data> itr = g.getV().iterator();
        double x0=0,x1=0,y0=0,y1=0;
        boolean first = true;
        while(itr.hasNext())
        {
            geo_location p = itr.next().getLocation();
            if(first)
            {
                x0=p.x(); x1=x0;
                y0=p.y(); y1=y0;
                first = false;
            }
            else
            {
                if(p.x()<x0) {x0=p.x();}
                if(p.x()>x1) {x1=p.x();}
                if(p.y()<y0) {y0=p.y();}
                if(p.y()>y1) {y1=p.y();}
            }
        }
        Range xr = new Range(x0,x1);
        Range yr = new Range(y0,y1);
        return new Range2D(xr,yr);
    }
    public static Range2Range w2f(directed_weighted_graph g, Range2D frame)
    {
        Range2D world = GraphRange(g);
        Range2Range ans = new Range2Range(world, frame);
        return ans;
    }


    /**
     * this is the main algorithm of the game,this algorithm decide for each agent-where he should go.
     * first of all get sorted list of the agents by speed
     * iterate over all agents , for each one of the iterate over all pokemons
     * and check who is the best pokemons for this agent
     * need to improve-several agents can go for one pokemon
     * DWGAlgo= object that contain a graph and perform the algorithms on this graph
     * get_pos= function that get a pokemon and return the edge of this pokemon
     * shortestpath= return list of nodes that represent the shortest path from node src to node dest
     * @param game
     * @param gg
     */
    public void moveAgants(game_service game, directed_weighted_graph gg)
    {
        String lg = game.move();
        List<CL_Agent> agents_list = sort_agents(MyArena.getAgents(lg, gg));
        String fs =  game.getPokemons();
        List<CL_Pokemon> pokemons_list = sort_pokemons(MyArena.json2Pokemons(fs));
        this.setPokemons(pokemons_list);
        this.setAgents(agents_list);



        for(CL_Pokemon p:pokemons_list)
        {
            p.setMin_ro(0);
        }



        for(int i=0;i<agents_list.size();i++)
        {
            CL_Agent agent = agents_list.get(i);
            int id = agent.getID();
            int dest = agent.getNextNode();
            int src = agent.getSrcNode();
            double v = agent.getValue();
            double min_space= Integer.MAX_VALUE;
            int next_node=0;
            double distt=0;
            int counter=0;
            DWG_Algo dwga=new DWG_Algo();
            dwga.init(gg);
            if(dest==-1)
            {
                for(int j=0;j<pokemons_list.size();j++)
                {
                    CL_Pokemon tmp = pokemons_list.get(j);

                    if(tmp.getMin_ro()==1)continue;
                    edge_data e = get_pos(tmp.getLocation(), tmp.getType());
                    int dest_of_pokemon = e.getDest();
                    int src_of_pokemon = e.getSrc();
                    double dist=dwga.shortestPathDist(src, src_of_pokemon);
                    if(dist<0)continue;
                    if ((dist / agent.getSpeed()) < min_space)
                    {
                        counter=j;
                        List<node_data> l = dwga.shortestPath(src, src_of_pokemon);
                        if (src == src_of_pokemon)
                        {
                            next_node = dest_of_pokemon;
                            min_space = (dist / agent.getSpeed());
                        }
                        else
                        {
                            next_node = l.get(1).getKey();
                            min_space = (dist / agent.getSpeed());
                        }

                    }
                }
                pokemons_list.get(counter).setMin_ro(1);
                game.chooseNextEdge(agent.getID(), next_node);
                System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+next_node);
            }
        }

    }



    //============================================help functions====================================================//

    /**
     * this method gets a location and tipe of pokemon and return the edge that the pokemon is on
     * @param p
     * @param type
     * @return
     */
    public edge_data get_pos(Point3D p,int type)
    {
        Iterator<node_data> itr1=_gg.getV().iterator();
        while (itr1.hasNext())
        {
            Iterator<edge_data> itr2=_gg.getE(itr1.next().getKey()).iterator();
            while(itr2.hasNext())
            {
                edge_data tmp=itr2.next();
                if(isOnEdge(p,tmp,type,_gg))return tmp;
            }
        }
        return null;
    }

    /**
     * sort pokemons list by the value
     * @param game_pokemons
     * @return
     */
    private List<CL_Pokemon> sort_pokemons(ArrayList<CL_Pokemon> game_pokemons)
    {
        CL_Pokemon [] arr=new CL_Pokemon[game_pokemons.size()];
        for(int i=0;i<arr.length;i++)
        {
            arr[i]=game_pokemons.get(i);
        }

        for(int i=0;i< arr.length;i++)
        {
            for(int j=i+1;j<arr.length-1;j++)
            {
                if(arr[j].getValue()>arr[i].getValue())
                {
                    CL_Pokemon tmp=arr[j];
                    arr[j]=arr[i];
                    arr[i]=tmp;
                }
            }
        }
        List<CL_Pokemon> ans=new ArrayList<>();
        for(int i=0;i<arr.length;i++)
        {
            ans.add(arr[i]);
        }
        return ans;
    }


    /**
     * sort agents list by speed
     * @param list
     * @return
     */
    public List<CL_Agent> sort_agents(List<CL_Agent> list)
    {
        CL_Agent[] arr=new CL_Agent[list.size()];
        for(int i=0;i<arr.length;i++)
        {
            arr[i]=list.get(i);
        }

        for(int i=0;i< arr.length;i++)
        {
            for(int j=0;j<arr.length;j++)
            {
                if(arr[j].getSpeed()>arr[i].getSpeed())
                {
                    CL_Agent tmp=arr[j];
                    arr[j]=arr[i];
                    arr[i]=tmp;
                }
            }
        }
        List<CL_Agent> ans=new ArrayList<>();
        for(int i=0;i<arr.length;i++)
        {
            ans.add(arr[i]);
        }
        return ans;
    }

}




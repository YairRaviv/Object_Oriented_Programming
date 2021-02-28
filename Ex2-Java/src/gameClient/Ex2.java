package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ex2 implements Runnable
{
    private static MyFrame myFrame;
    private static MyArena myArena;
    private static int num;
    private static boolean flag=true;
    private static int id;
    private static int pai=0;
    private static directed_weighted_graph current_graph;

    public static void main(String[] a) throws IOException {

           if(a.length>=2)
           {
               id=Integer.parseInt(a[0]);
               num=Integer.parseInt(a[1]);
           }
           else
               {
               Frame f = new Frame(800, 1000);

               while (flag)
               {

                   f.repaint();
                   num = f.panel.num;
                   id = f.panel.id;
                   if (f.panel.flag)
                   {
                       flag = false;
                       f.dispose();
                   }

                   if(pai==0)
                   {
                       f.setVisible(true);
                       f.panel.setVisible(true);
                       pai++;
                   }
               }
           }

       Thread client = new Thread(new gameClient.Ex2());
       client.start();

    }

    /**
     * this method control at client thread,this method first of all init the level board with agents and
     * then manage this thread to run the game with specified sleeps times ,and update the games arena to repaint the current situation
     */
    @Override
    public void run()
    {

        game_service game = Game_Server_Ex2.getServer(num);
        game.login(id);
        dw_graph_algorithms dwga=new DWG_Algo();
        dwga.load(game.getGraph());
        current_graph= dwga.getGraph();
        try
        {
            init(game);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        game.startGame();
        int ind=0;
        long dt=250;
        long time=game.timeToEnd();

        while(game.isRunning())
        {
            myArena.moveAgants(game, current_graph);
            try
            {
                    myFrame.repaint();
                    myArena.setTime(game.timeToEnd());
                if((double)(4*(time- game.timeToEnd()))>=time)dt=110;
                if((double)(2*(time- game.timeToEnd()))>=time)dt=80;
               if((double)((1.3)*(time- game.timeToEnd()))>=time)dt=60;
                Thread.sleep(dt);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }


    /**
     * this method response to init the game,
     * mainly by
     * 1)create the board of this current level(arena) and load it with information from the game server
     * 2)create the graphics interface that showing the game at run
     * 3)init the agents at the appropriate location in the graph(as close as possible to the pokemons with highest values
     * @param game
     * @throws IOException
     */
    public static void init(game_service game) throws IOException
    {
        //String String_game_graph= game.getGraph();
        String String_game_pokemons = game.getPokemons();

        myArena = new MyArena();
        myArena.setGraph(current_graph);
        myArena.setPokemons(MyArena.json2Pokemons(String_game_pokemons));
        myFrame = new MyFrame("test Ex2");
        myFrame.setSize(1000, 700);
        myFrame.update(myArena);
        myFrame.setVisible(true);

        String info = game.toString();
        JSONObject json_of_game;
        try
        {
            json_of_game = new JSONObject(info);
            JSONObject json_game_server = json_of_game.getJSONObject("GameServer");
            int agents_num = json_game_server.getInt("agents");
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> game_pokemons = MyArena.json2Pokemons(String_game_pokemons);

            for(int a = 0;a<game_pokemons.size();a++)
            {
                MyArena.updateEdge(game_pokemons.get(a),current_graph);
            }


            /**
             * first of all,get a sorted list of pokemons(by values)
             * and a sorted list of agents(by speed)
             */
            List<CL_Pokemon> pokemons=sort_pokemons(game_pokemons);
            int i=0;



            /**
             * init the first set of agents ,one per pokemon
             * the highest pokemon get the fastest agent
             */
            for(int a = 0;a<agents_num;a++)
            {
                int pokemons_num = pokemons.size();
                CL_Pokemon c = pokemons.get(a);
                int init_pos = c.get_edge().getDest();
                if(c.getType()<0 ) {init_pos = c.get_edge().getSrc();}
                game.addAgent(init_pos);
                i=a;
                if(a==pokemons_num)break;
            }



            /**
             * init the rest of the agents
             * every agent get the max distance from the other agents
             */
            /*
            dw_graph_algorithms dwgaa=new DWG_Algo();
            dwgaa.init(current_graph);
            while(i<agents_num)
            {
                int max=0;
                double max_distance=0;
                for(int j=0;j<i;j++)
                {

                    if(sorted_agents.size()==0)break;
                    CL_Agent tmp=sorted_agents.get(j);
                    Iterator<node_data> itr=current_graph.getV().iterator();
                    while(itr.hasNext())
                    {
                        int node=itr.next().getKey();
                        if(dwgaa.shortestPathDist(tmp.getSrcNode(),node)/tmp.getSpeed()>max_distance)
                        {
                            max=node;
                            max_distance=dwgaa.shortestPathDist(tmp.getSrcNode(),node)/tmp.getSpeed();
                        }
                    }

                }
                game.addAgent(max);
                i++;
            }
*/
        }
        catch (JSONException e) {e.printStackTrace();}
    }


    /**
     * sort pokemons list by the value
     * @param game_pokemons
     * @return
     */
    public static List<CL_Pokemon> sort_pokemons(ArrayList<CL_Pokemon> game_pokemons)
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
    public static List<CL_Agent> sort_agents(List<CL_Agent> list)
    {
        CL_Agent[] arr=new CL_Agent[list.size()];
        for(int i=0;i<arr.length;i++)
        {
            arr[i]=list.get(i);
        }

        for(int i=0;i< arr.length;i++)
        {
            for(int j=i+1;j<arr.length;j++)
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




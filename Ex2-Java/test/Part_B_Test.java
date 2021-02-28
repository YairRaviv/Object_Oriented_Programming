import Server.Game_Server_Ex2;
import api.*;
import gameClient.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Part_B_Test
{
    @Test
    void sortpok() throws JSONException {
        for(int j=0;j<24;j++)
        {
            game_service game2 = Game_Server_Ex2.getServer(j);
            dw_graph_algorithms dwga = new DWG_Algo();
            dwga.load(game2.getGraph());
            directed_weighted_graph current_graph = dwga.getGraph();
            String info = game2.toString();
            JSONObject json_of_game;
            json_of_game = new JSONObject(info);
            JSONObject json_game_server = json_of_game.getJSONObject("GameServer");
            int agents_num = json_game_server.getInt("agents");
            String String_game_pokemons = game2.getPokemons();
            ArrayList<CL_Pokemon> list=MyArena.json2Pokemons(String_game_pokemons);
            ArrayList<CL_Pokemon> list2= (ArrayList<CL_Pokemon>) sort_pokemons(list);
            MyArena myArena=new MyArena();
            for(int a = 0;a<list2.size();a++)
            {
                MyArena.updateEdge(list2.get(a),current_graph);
            }
            for(int a = 0;a<agents_num;a++)
            {
                int pokemons_num = list2.size();
                CL_Pokemon c = list2.get(a);
                int init_pos = c.get_edge().getDest();
                if(c.getType()<0 ) {init_pos = c.get_edge().getSrc();}
                game2.addAgent(init_pos);
                if(a==pokemons_num)break;
            }
            List<CL_Agent> lista=sort_agents(myArena.getAgents(game2.getAgents(),current_graph));
            for(int i=0;i<lista.size()-1;i++)
            {
                assertTrue(lista.get(i).getSpeed()>=lista.get(i+1).getSpeed());
            }
            ArrayList<CL_Pokemon> listt=MyArena.json2Pokemons(String_game_pokemons);
            ArrayList<CL_Pokemon> list22= (ArrayList<CL_Pokemon>) sort_pokemons(list);
            for(int i=0;i<list2.size()-1;i++)
            {
                assertTrue(list22.get(i).getValue()>=list22.get(i+1).getValue());
            }
        }

    }

    @Test
    void moveagents()
    {
        game_service game = Game_Server_Ex2.getServer(1);
        dw_graph_algorithms dwga=new DWG_Algo();
        dwga.load(game.getGraph());
        directed_weighted_graph current_graph= dwga.getGraph();

            //String String_game_graph= game.getGraph();
            String String_game_pokemons = game.getPokemons();

            MyArena myArena = new MyArena();
            myArena.setGraph(current_graph);
            myArena.setPokemons(MyArena.json2Pokemons(String_game_pokemons));

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


            }
            catch (JSONException e) {e.printStackTrace();}
            game.startGame();
            myArena.moveAgants(game,current_graph);
            List<CL_Agent> list=myArena.getAgents(game.getAgents(),current_graph);
            assertTrue(list.get(0).getNextNode()==3);
    }







































    //===============================================sort fuctions=============================================
    public static List<CL_Pokemon> sort_pokemons(ArrayList<CL_Pokemon> game_pokemons)
    {
        CL_Pokemon [] arr=new CL_Pokemon[game_pokemons.size()];
        for(int i=0;i<arr.length;i++)
        {
            arr[i]=game_pokemons.get(i);
        }

        for(int i=0;i< arr.length;i++)
        {
            for(int j=i+1;j<arr.length;j++)
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

    public static List<CL_Agent> sort_agents(List<CL_Agent> list)
    {
        CL_Agent[] arr=new CL_Agent[list.size()];
        for(int i=0;i<arr.length;i++)
        {
            arr[i]=list.get(i);
        }

        for(int i=0;i< arr.length;i++)
        {
            for(int j=i+1;j<arr.length-1;j++)
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

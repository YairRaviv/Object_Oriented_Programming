package ex1;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void connect()
    {
        weighted_graph g=new WGraph_DS();
        for(int i=0;i<11;i++)
        {
            g.addNode(i);
        }
        g.connect(0,1,1);
        g.connect(1,2,1);
        g.connect(2,3,1);
        g.connect(3,4,1);
        g.connect(5,4,1);
        g.connect(0,6,1);
        g.connect(6,7,1);
        g.connect(7,8,1);
        g.connect(8,9,1);
        g.connect(9,10,1);
        g.connect(0,10,1);

        weighted_graph_algorithms wga=new WGraph_Algo();
        wga.init(g);
        assertTrue(wga.isConnected());

        g.removeNode(0);
        assertFalse(wga.isConnected());
        g.connect(1,9,5);

        assertTrue(wga.isConnected());
        g.removeNode(1);
        assertFalse(wga.isConnected());
        g.connect(2,8,3);
        assertTrue(wga.isConnected());
    }


    @Test
    void copy()
    {
        weighted_graph g=new WGraph_DS();
        for(int i=0;i<11;i++)
        {
            g.addNode(i);
        }
        g.connect(0,1,2);
        g.connect(0,2,3);
        g.connect(0,3,4);
        g.connect(0,4,2);
        g.connect(0,5,5);
        g.connect(1,2,6);
        g.connect(1,5,4);
        g.connect(1,10,7);
        g.connect(2,10,6);
        g.connect(2,6,3);
        g.connect(2,3,9);
        g.connect(2,3,9);
        g.connect(2,3,9);
        g.connect(2,3,9);
        g.connect(2,3,9);
        g.connect(2,3,9);
        g.connect(2,3,9);
        g.connect(2,3,9);



        weighted_graph_algorithms wga=new WGraph_Algo();
        wga.init(g);
        weighted_graph g1=wga.copy();

        assertEquals(g,g1);

        g.removeNode(0);

        assertNotEquals(g,g1);

    }
    @Test
    void shortestPathDist() {

        weighted_graph g=new WGraph_DS();
        for(int i=0;i<11;i++)
        {
            g.addNode(i);
        }

        g.connect(0,1,2);
        g.connect(0,2,3);
        g.connect(0,3,4);
        g.connect(0,4,6);
        g.connect(0,5,5);
        g.connect(1,2,6);
        g.connect(1,5,4);
        g.connect(1,10,7);
        g.connect(2,10,6);
        g.connect(2,6,3);
        g.connect(2,3,9);
        g.connect(3,4,11);
        g.connect(3,6,4);
        g.connect(3,7,2);
        g.connect(4,5,3);
        g.connect(4,7,15);
        g.connect(4,8,10);
        g.connect(5,10,20);
        g.connect(5,8,8);
        g.connect(5,9,9);
        g.connect(6,7,1);
        g.connect(6,10,11.5);
        g.connect(7,8,7.5);
        g.connect(8,9,9);
        g.connect(9,10,11);

        weighted_graph_algorithms wga=new WGraph_Algo();
        wga.init(g);
        double a=wga.shortestPathDist(0,10);
        double b=wga.shortestPathDist(0,8);
        double c=wga.shortestPathDist(2,9);
        double d=wga.shortestPathDist(3,8);
        double e=wga.shortestPathDist(6,9);

        assertEquals(a,9);
        assertEquals(b,13);
        assertEquals(c,17);
        assertEquals(d,9.5);
        assertEquals(e,17.5);

        g.removeNode(7);

        double f=wga.shortestPathDist(6,9);
        assertEquals(f,20);

    }

    @Test
    void shortestPath()
    {
        weighted_graph g=new WGraph_DS();
        for(int i=0;i<11;i++)
        {
            g.addNode(i);
        }

        g.connect(0,1,2);
        g.connect(0,2,3);
        g.connect(0,3,4);
        g.connect(0,4,6);
        g.connect(0,5,5);
        g.connect(1,2,6);
        g.connect(1,5,4);
        g.connect(1,10,7);
        g.connect(2,10,6);
        g.connect(2,6,3);
        g.connect(2,3,9);
        g.connect(3,4,11);
        g.connect(3,6,4);
        g.connect(3,7,2);
        g.connect(4,5,3);
        g.connect(4,7,15);
        g.connect(4,8,10);
        g.connect(5,10,20);
        g.connect(5,8,8);
        g.connect(5,9,9);
        g.connect(6,7,1);
        g.connect(6,10,11.5);
        g.connect(7,8,7.5);
        g.connect(8,9,9);
        g.connect(9,10,11);

    }


    @Test
    void save_load() {
        weighted_graph g0 = WGraph_DSTest.graph_creator(10,30,1);
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0.obj";
        ag0.save(str);
        weighted_graph g1 = WGraph_DSTest.graph_creator(10,30,1);
        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);
    }







    ////////////////////////////////////////////////////



    public static void PrintNi(weighted_graph wg,int key)
    {
        if(null==wg.getNode(key))return;
        Iterator<node_info> itr=wg.getV(key).iterator();
        while(itr.hasNext())
        {
            System.out.println(itr.next().getKey());
        }

    }

    public static void PrintWgraph(weighted_graph wg)
    {
        Iterator<node_info> itr=wg.getV().iterator();
        while(itr.hasNext())
        {
            node_info tmp= itr.next();
            System.out.println("the key is :"+tmp.getKey());
            System.out.println("the neighbors is :");
            PrintNi(wg,tmp.getKey());
        }
    }

}
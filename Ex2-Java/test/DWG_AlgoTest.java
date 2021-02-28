import api.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DWG_AlgoTest
{

    @Test
    void copy()
    {
        directed_weighted_graph g=small_grapg_creator();
        dw_graph_algorithms dwga=new DWG_Algo();
        dwga.init(g);
        directed_weighted_graph gg= dwga.copy();
        boolean b1=equals(g,gg);
        assertTrue(b1);
        g.removeNode(1);
        boolean b2=equals(g,gg);
        assertFalse(b2);
    }

    @Test
    void isconnected1()
    {
        directed_weighted_graph g=small_grapg_creator();
        dw_graph_algorithms dwga=new DWG_Algo();
        dwga.init(g);
        assertFalse(dwga.isConnected());

        g.connect(4,5,1);
        g.connect(5,3,1);

        assertTrue(dwga.isConnected());

        directed_weighted_graph gg=new DW_Graph();
        dwga.init(gg);
        assertTrue(dwga.isConnected());

    }

    @Test
    void isconnected2()
    {
        directed_weighted_graph g=my_graph_creator();
        dw_graph_algorithms dwga=new DWG_Algo();
        dwga.init(g);

        assertFalse(dwga.isConnected());
        g.connect(7,8,1);
        assertTrue(dwga.isConnected());
    }

    @Test
    void shortestpathdist1()
    {
        directed_weighted_graph g=small_grapg_creator();
        g.connect(4,5,1);
        g.connect(5,3,1);
        g.connect(5,2,12.4);
        g.connect(2,4,9.1);
        g.connect(1,3,9);
        dw_graph_algorithms dwga=new DWG_Algo();
        dwga.init(g);

        double a=dwga.shortestPathDist(5,2);
        double b=dwga.shortestPathDist(2,3);
        double c=dwga.shortestPathDist(1,3);
        double d=dwga.shortestPathDist(3,4);
        g.removeNode(5);
        double e=dwga.shortestPathDist(1,3);
        double f=dwga.shortestPathDist(4,1);
        double gg= dwga.shortestPathDist(4,5);
        assertEquals(a,10);
        assertEquals(b,11);
        assertEquals(c,8);
        assertEquals(d,7);
        assertEquals(e,9);
        assertEquals(f,gg);
        assertEquals(f,-1);
    }

    @Test
    void shortestpathdist2()
    {
        directed_weighted_graph g=my_graph_creator();
        dw_graph_algorithms dwga=new DWG_Algo();
        dwga.init(g);

        double a=dwga.shortestPathDist(3,1);
        double b=dwga.shortestPathDist(8,7);
        double c=dwga.shortestPathDist(4,11);
        double d=dwga.shortestPathDist(3,5);
        double e=dwga.shortestPathDist(3,7);
        double f=dwga.shortestPathDist(7,13);

        assertEquals(a,5.1);
        assertEquals(b,3.1);
        assertEquals(c,15);
        assertEquals(d,6.5);
        assertEquals(e,7.1);
        g.removeNode(6);
        g.removeNode(8);
        assertEquals(-1,dwga.shortestPathDist(3,7));
        assertEquals(-1,f);

    }

@Test
void shortestspath()
{
    directed_weighted_graph g=my_graph_creator();
    dw_graph_algorithms dwga=new DWG_Algo();
    dwga.init(g);

    int [] arr1={3,8,6,1};
    int [] arr2= {4,5,11};
    int [] arr3= {12,8,6,1};


    int counter=0;
    List<node_data> l1=dwga.shortestPath(3,1);
    Iterator<node_data> itr1=l1.iterator();
    while(itr1.hasNext())
    {
        assertEquals(arr1[counter++], itr1.next().getKey());
    }
    counter=0;

    List<node_data> l2=dwga.shortestPath(4,11);
    Iterator<node_data> itr2=l2.iterator();
    while(itr2.hasNext())
    {
        assertEquals(arr2[counter++],itr2.next().getKey());
    }
    counter=0;

    List<node_data> l3=dwga.shortestPath(12,1);
    Iterator<node_data> itr3=l3.iterator();
    while(itr3.hasNext())
    {
        assertEquals(arr3[counter++],itr3.next().getKey());
    }

    List<node_data> l4=dwga.shortestPath(7,3);
    assertNull(l4);

    List<node_data> l5=dwga.shortestPath(15,1);
    assertNull(l5);
}

@Test
void save_load()
{
    directed_weighted_graph g=my_graph_creator();
    dw_graph_algorithms dwga=new DWG_Algo();
    dwga.init(g);
    assertTrue(dwga.save("gra.json"));

}

@Test
void load()
{
    dw_graph_algorithms dwga=new DWG_Algo();
    assertTrue(dwga.load("data/A0"));

}
















































    //===============================================help fuctions=====================================================

    public static directed_weighted_graph small_grapg_creator()
    {
        directed_weighted_graph g=new DW_Graph();
        for(int i=1;i<6;i++)
        {
            Node_D n=new Node_D();
            n.setKey(i);
            g.addNode(n);
        }

        g.connect(1,2,4);
        g.connect(1,5,7);
        g.connect(2,1,3);
        g.connect(3,2,9);
        g.connect(3,4,7);

        return g;
    }


    public static directed_weighted_graph my_graph_creator()
    {

        directed_weighted_graph g=new DW_Graph();
        for(int i=1;i<14;i++)
        {
            Node_D n=new Node_D();
            n.setKey(i);
            g.addNode(n);
        }
        g.connect(1,2,4);
        g.connect(1,5,7);
        g.connect(2,1,3);
        g.connect(3,2,9);
        g.connect(3,4,7);
        g.connect(3,13,9);
        g.connect(3,8,4);
        g.connect(4,2,5);
        g.connect(4,5,4);
        g.connect(4,3,6);
        g.connect(4,10,9);
        g.connect(5,10,5);
        g.connect(5,11,11);
        g.connect(6,1,1);
        g.connect(6,12,1);
        g.connect(6,9,1);
        g.connect(8,12,0.5);
        g.connect(8,6,0.1);
        g.connect(8,7,20);
        g.connect(9,3,5);
        g.connect(9,6,2);
        g.connect(9,7,2);
        g.connect(10,4,1);
        g.connect(10,11,7);
        g.connect(11,12,4);
        g.connect(12,5,2);
        g.connect(12,8,3);
        g.connect(13,1,2);
        g.connect(13,3,3);

        return g;
    }



    public static void PrintNi(directed_weighted_graph wg,int key)
    {
        if(null==wg.getNode(key))return;
        Iterator<edge_data> itr=wg.getE(key).iterator();
        while(itr.hasNext())
        {
            System.out.println(itr.next().getDest());
        }

    }

    public static void PrintWgraph(directed_weighted_graph wg)
    {
        Iterator<node_data> itr=wg.getV().iterator();
        while(itr.hasNext())
        {
            node_data tmp= itr.next();
            System.out.println("the key is :"+tmp.getKey());
            System.out.println("the neighbors is :");
            PrintNi(wg,tmp.getKey());
            // PrintNi(wg,tmp.getKey());
        }
    }



    public boolean equals(directed_weighted_graph g1 ,directed_weighted_graph g2) {
        if(g1.nodeSize()!=g2.nodeSize()||g1.edgeSize()!= g2.edgeSize())return false;
        Iterator<node_data> itr=g2.getV().iterator();
        Iterator<node_data> itrr=g1.getV().iterator();
        while(itr.hasNext())
        {
            node_data n1=itr.next();
            node_data n2= itrr.next();
            if(n1.getKey()!=n2.getKey())return false;
            Collection<edge_data> c=g2.getE(n1.getKey());
            Collection<edge_data> cc=g1.getE(n2.getKey());
            Iterator<edge_data>  itr2=c.iterator();
            Iterator<edge_data>  itr22=cc.iterator();
            while(itr2.hasNext())
            {
                edge_data e1=itr2.next();
                edge_data e2=itr22.next();
                if(e1.getSrc()!=e2.getSrc()&&e1.getDest()!=e2.getDest()&&e1.getTag()!=e2.getTag()&&e1.getWeight()!=e2.getWeight())return false;
            }
        }
        return true;
    }


}

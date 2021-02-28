import api.*;
import org.junit.jupiter.api.Test;;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
public class DW_GraphTest
{


    @Test
    void nodeSize() {
        directed_weighted_graph g = new DW_Graph();
        for(int i=1;i<6;i++)
        {
            Node_D n=new Node_D();
            n.setKey(i);
            g.addNode(n);
        }
        assertEquals(g.nodeSize(),5);
        assertEquals(g.getMC(),5);

        g.removeNode(5);
        g.removeNode(5);
        assertEquals(g.getMC(),6);
        assertEquals(g.nodeSize(),4);


    }

    @Test
    void nodes2()
    {
        directed_weighted_graph g=my_graph_creator();
        assertEquals(g.nodeSize(),13);
        assertNull(g.getNode(14));

        //PrintNi(g,6);
        Collection<edge_data> itr=g.getE(6);
        assertEquals(itr.size(),3);
        int mc1=g.getMC();
        g.removeEdge(6,1);
        int mc2=g.getMC();
        assertNotEquals(mc1,mc2);


        for(int i=1;i<14;i++)
        {
            assertNotNull(g.getNode(i));
        }


    }

    @Test
    void edgeSize() {
        directed_weighted_graph g = new DW_Graph();
        for(int i=1;i<6;i++)
        {
            Node_D n=new Node_D();
            n.setKey(i);
            g.addNode(n);
        }
        g.connect(1,2,3);
        g.connect(1,3,4);
        g.connect(2,1,4.7);
        g.connect(3,0,5.2);
        g.connect(4,5,9.5);
        assertEquals(g.edgeSize(),4);
        g.removeEdge(1,3);
        g.removeEdge(3,1);
        int e_size =  g.edgeSize();
        assertEquals(3, e_size);
        double d=g.getEdge(1,2).getWeight();
        assertEquals(3,d);
        double d2=g.getEdge(2,1).getWeight();
        assertEquals(4.7,d2);

    }

    @Test
    void edges2()
    {
        directed_weighted_graph g=my_graph_creator();
        assertEquals(g.edgeSize(),29);
        assertNull(g.getEdge(8,9));
        assertNull(g.getEdge(10,10));
        assertEquals(g.getEdge(3,2).getWeight(),9);
        assertEquals(g.getMC(),42);
        g.removeNode(8);
        assertEquals(g.edgeSize(),24);
        assertNull(g.getEdge(8,6));
        assertEquals(9,g.getEdge(4,10).getWeight());
        assertEquals(g.getMC(),48);
    }





    @Test
    void getV() {
        directed_weighted_graph g = new DW_Graph();
        node_data n1=new Node_D();
        node_data n2=new Node_D();
        node_data n3=new Node_D();
        node_data n4=new Node_D();
        node_data n5=new Node_D();
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        g.connect(4,5,3.14);
        g.removeNode(4);
        assertNull(g.getEdge(4,5));
        Collection<node_data> v = g.getV();
        Iterator<node_data> iter = v.iterator();
        int counter=0;
        while (iter.hasNext()) {
            node_data n = iter.next();
            assertNotNull(n);
            counter++;
        }
        assertEquals(counter,g.nodeSize());
        assertTrue(v.contains(n1));
        assertTrue(v.contains(n2));
        assertTrue(v.contains(n3));
        assertTrue(v.contains(n5));
    }

    @Test
    void nodes()
    {
        directed_weighted_graph g=my_graph_creator();
        Collection<node_data> c=g.getV();
        assertEquals(c.size(),13);
        Collection<Integer> cc=new HashSet<>();

        Iterator<node_data> itr=c.iterator();
        while(itr.hasNext())
        {
            cc.add(itr.next().getKey());
        }

        for(int i=1;i<14;i++)
        {
            assertTrue(cc.contains(i));
        }
    }



    @Test
    void hasEdge()
    {
        directed_weighted_graph g=my_graph_creator();
        assertNotNull(g.getEdge(1,2));
        assertNotNull(g.getEdge(2,1));
        assertNotNull(g.getEdge(3,4));
        assertNotNull(g.getEdge(10,11));
        assertNotNull(g.getEdge(8,7));
        assertNull(g.getEdge(7,8));
        assertNull(g.getEdge(6,10));


    }

    @Test
    void getV_Ni()
    {
        directed_weighted_graph g =my_graph_creator();
        Collection<edge_data> c1=g.getE(8);
        Collection<edge_data> c2=g.getE(4);
        Collection<edge_data> c3=g.getE(10);
        assertTrue(c1.contains(g.getEdge(8,7)));
        assertTrue(c1.contains(g.getEdge(8,6)));
        assertTrue(c1.contains(g.getEdge(8,12)));
        assertTrue(c2.contains(g.getEdge(4,3)));
        assertTrue(c2.contains(g.getEdge(4,5)));
        assertTrue(c2.contains(g.getEdge(4,2)));
        assertTrue(c2.contains(g.getEdge(4,10)));
        assertTrue(c3.contains(g.getEdge(10,4)));
        assertTrue(c3.contains(g.getEdge(10,11)));
    }



    @Test
    void runtimeTest()
    {


        long start = new Date().getTime();
        directed_weighted_graph g=new DW_Graph();
        int nodes=1000000;
        int edges=10000000;

        for(int i=0;i<nodes;i++)
        {
            Node_D n=new Node_D();
            n.setKey(i);
            g.addNode(n);
        }


        int a=1;
        int b=2;
        for(int j=0;j<edges;j++)
        {
            g.connect(a,b,(j/1000));
            b++;
            if(b==nodes)
            {
                a++;b=a+1;
            }
        }


        long end = new Date().getTime();
        double d = (end-start)/1000.0;
        assertTrue(d<=10);
        assertEquals(g.nodeSize(),1000000);
        assertEquals(g.edgeSize(),10000000);

    }



    @Test
    void combineTest()
    {
        int nodes=0;
        int edges=0;
        directed_weighted_graph g=new DW_Graph();
        for (int i=1;i<9;i++)
        {
            Node_D n=new Node_D();
            n.setKey(i);
            g.addNode(n);
            nodes++;
        }
        g.connect(1,4,2);
        edges++;
        g.connect(1,2,2);
        edges++;
        g.connect(2,8,10);
        edges++;
        g.connect(8,5,7);
        edges++;
        g.connect(5,3,1);
        edges++;
        g.connect(5,6,4);
        edges++;
        g.connect(3,7,2);
        edges++;

        g.removeNode(2);
        g.removeNode(1);
        edges-=3;
        nodes-=2;

        assertEquals(edges,g.edgeSize());
        assertEquals(nodes,g.nodeSize());


        Collection<edge_data> C=g.getE(5);
        assertTrue(C.contains(g.getEdge(5,3)));
        assertTrue(C.contains(g.getEdge(5,6)));

    }


    //================================================help functions======================================================

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
        g.connect(6,9,23);
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



}



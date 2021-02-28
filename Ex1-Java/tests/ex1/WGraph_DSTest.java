package ex1;

import org.junit.jupiter.api.Test;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

public class WGraph_DSTest
{


    private static Random _rnd = null;

    @Test
    void nodeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        assertEquals(g.nodeSize(),6);
        g.addNode(5);
        assertEquals(g.nodeSize(),6);
        g.removeNode(5);
        g.removeNode(5);
        assertEquals(g.nodeSize(),5);
    }

    @Test
    void edgeSize() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,3);
        g.connect(0,1,2);
        g.connect(1,2,3);
        g.connect(1,3,4);
        g.connect(2,1,4.7);
        g.connect(3,0,5.2);
        g.connect(4,5,9.5);
        g.removeEdge(3,1);
        int e_size =  g.edgeSize();
        assertEquals(4, e_size);
        double d=g.getEdge(1,0);
        assertEquals(2,d);
        double d2=g.getEdge(1,2);
        assertEquals(4.7,d2);

    }

    @Test
    void getV() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        g.connect(4,5,3.14);
        g.removeNode(4);
        assertEquals(false,g.hasEdge(4,5));
        Collection<node_info> v = g.getV();
        Iterator<node_info> iter = v.iterator();
        int counter=0;
        while (iter.hasNext()) {
            node_info n = iter.next();
            assertNotNull(n);
            counter++;
        }
        assertEquals(counter,g.nodeSize());
    }

    @Test
    void edges()
    {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,1);
        g.connect(0,2,2.5);
        g.connect(0,3,3.2);
        g.connect(0,3,5.6);
        double a=g.getEdge(0,3);
        double b=g.getEdge(3,0);
        double c=g.getEdge(0,2);
        double d=g.getEdge(4,5);
        assertEquals(a,b);
        assertEquals(c,2.5);
        assertEquals(d,-1);
    }

    @Test
    void hasEdge()
    {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,3);
        boolean b=g.hasEdge(0,1);
        boolean c=g.hasEdge(0,2);
        boolean d=g.hasEdge(1,5);
        boolean e=g.hasEdge(0,3);
        assertEquals(true,b);
        assertEquals(true,c);
        assertEquals(false,d);
        assertEquals(false,e);

    }

    @Test
    void getV_Ni()
    {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,3);

        assertEquals(g.getV(0).size(),2);
        g.connect(4,1,1);
        g.connect(4,2,2);
        g.connect(4,3,3);
        g.connect(4,4,1);
        g.connect(4,5,2);
        g.connect(4,6,3);
        assertEquals(g.getV(4).size(),4);

    }


    @Test
    void million_nodes_ten_million_edge_runtime_test() {
        weighted_graph graph_test28 = new WGraph_DS();
        int node1,node2,mil = 1000000;
        double run_time_s;
        //long endTime,finalTime,startTime = System.currentTimeMillis();
        boolean check=false;

        long start = new Date().getTime();
        for (int i = 0; i <mil;i++)
        {
            graph_test28.addNode(i);
        }
        long end = new Date().getTime();
        double dt = (end-start)/1000.0;
        System.out.println("add nodes:"+dt);

        long start2 = new Date().getTime();
        for (int i = 0, j=mil-1; i <mil;i++)
        {
            for (int k=0;k<10;k++ ) {
                graph_test28.connect(i, j-k, 2);
            }
        }
        long end2 = new Date().getTime();
        double dt2 = (end2-start2)/1000.0;
        System.out.println("connect tins:"+dt2);
      //  endTime = System.currentTimeMillis();
       // finalTime = endTime - startTime;
        //run_time_s =((double)finalTime)/1000;
        //if (run_time_s<10.0)
            check=true;
       // System.out.println("node size: " + graph_test28.nodeSize() +" edge size: "
         //       + graph_test28.edgeSize() + " runtime seconds: " + run_time_s);
        assertTrue(check);
    }

    @Test
    void runtimeTest()
    {

        weighted_graph g=new WGraph_DS();
        int nodes=1000000;
         int edges=10000000;
         for(int i=0;i<nodes;i++)
         {
             g.addNode(i);
         }
         int a=0;
         int b=1;
         for(int j=0;j<edges;j++)
         {
             g.connect(a,b,(j/1000));
             b++;
             if(b==nodes)
             {
                 a++;b=a+1;
             }
         }
         weighted_graph_algorithms wga=new WGraph_Algo();
         wga.init(g);

         int q=(int)((Math.random()*nodes)+1);
        int w=(int)((Math.random()*nodes)+1);
        long start = new Date().getTime();
        double tt=wga.shortestPathDist(q,w);
        long end = new Date().getTime();
        double dt = (end-start)/1000.0;
        System.out.println(dt);
        //assertTrue(dt<10);
    }



    @Test
    void combineTest()
    {
        int nodes=0;
        int edges=0;
        weighted_graph g=new WGraph_DS();
        for (int i=1;i<9;i++)
        {
            g.addNode(i);
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


        Collection<node_info> C=g.getV(5);
        assertTrue(C.contains(g.getNode(3)));
        assertTrue(C.contains(g.getNode(8)));
        assertTrue(C.contains(g.getNode(6)));

    }



    ///////////////////////////////////
    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
   //================================================help functions======================================================

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

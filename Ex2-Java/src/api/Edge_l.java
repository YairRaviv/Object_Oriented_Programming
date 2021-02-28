package api;

public class Edge_l implements edge_location
{
    private edge_data e;
    private geo_location gl;
    private directed_weighted_graph gr;

    public Edge_l(edge_data ee,geo_location g,directed_weighted_graph gr)
    {
        this.e=ee;
        this.gl=g;
        this.gr=gr;
    }

    @Override
    public edge_data getEdge() {
        return this.e;
    }

    @Override
    public double getRatio()
    {
        double src_dest=gr.getNode(e.getSrc()).getLocation().distance(gr.getNode(e.getDest()).getLocation());
        return((gr.getNode(e.getSrc()).getLocation().distance(this.gl))/src_dest);
    }
}

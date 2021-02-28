package api;

import gameClient.util.Point3D;

public class Geo_l implements geo_location
{
    public Point3D p;

    public Geo_l(Point3D pp)
    {
        this.p=pp;
    }

    @Override
    public double x() {
        return p.x();
    }

    @Override
    public double y() {
        return p.y();
    }

    @Override
    public double z() {
        return p.z();
    }

    @Override
    public double distance(geo_location g)
    {
        return this.p.distance(g);
    }
}

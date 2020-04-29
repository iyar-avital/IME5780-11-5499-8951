package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable {

    private List<Intersectable> geo;

    public Geometries()
    {
        geo = new ArrayList<Intersectable>();
    }

    public Geometries(Intersectable ... geometries)
    {
        for(int i = 0; i< geometries.length; i++)
        {
            geo.add(geometries[i]);
        }
    }

    public void add(Intersectable ... geometries)
    {
        for(int i = 0; i< geometries.length; i++)
        {
            geo.add(geometries[i]);
        }
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if(geo.size() == 0)
           return null;
        else
        {
            List<Point3D> point3DS = new ArrayList<Point3D>();
            for(int i = 0; i< geo.size(); i++) {
                var points = geo.get(i).findIntersections(ray);
                if(points != null) {
                    for (int j = 0; j < points.size(); j++) {
                        point3DS.add(points.get(j));
                    }
                }
            }
            return point3DS.size() == 0 ? null : point3DS;
        }
    }
}

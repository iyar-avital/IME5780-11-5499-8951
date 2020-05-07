package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geometries implements Intersectable {

    private List<Intersectable> _geo;

    public Geometries()
    {
        _geo = new ArrayList<>();
    }

    public Geometries(Intersectable ... _geometries)
    {
        _geo = new ArrayList<>();
        add(_geometries);
    }

    public void add(Intersectable ... geometries)
    {
        for (Intersectable g : geometries) {
            _geo.add(g);
        }
      //  geo.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if(_geo.size() == 0)
           return null;
        else
        {
            List<Point3D> point3DS = new ArrayList<Point3D>();
            for(int i = 0; i< _geo.size(); i++) {
                var points = _geo.get(i).findIntersections(ray);
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

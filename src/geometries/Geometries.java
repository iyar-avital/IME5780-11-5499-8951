package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Geometries class, have one field with a list of geometries
 */
public class Geometries implements Intersectable {

    /**
     * the list of geometries
     */
    private List<Intersectable> _geo;

    /**
     * empty constructor
     */
    public Geometries()
    {
        _geo = new ArrayList<>();
    }

    /**
     * Geometries constructor
     * receive geometries and add its to the list
     * @param _geometries all the geometries to add to list
     */
    public Geometries(Intersectable ... _geometries)
    {
        _geo = new ArrayList<>();
        add(_geometries);
    }

    /**
     * receive geometries and add its to the list
     * @param geometries all the geometries to add to list
     */
    public void add(Intersectable ... geometries)
    {
        for (Intersectable g : geometries) {
            _geo.add(g);
        }
      //  geo.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if(_geo.size() == 0)
           return null;
        else
        {
            List<GeoPoint> point3DS = new ArrayList<GeoPoint>();
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

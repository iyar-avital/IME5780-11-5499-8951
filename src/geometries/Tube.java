package geometries;

import primitives.*;

import java.awt.event.MouseAdapter;
import java.util.List;

import static primitives.Util.isZero;
//צינור

/**
 * tube class
 */
public class Tube extends RadialGeometry {

    protected Ray _axisRay;

    /**
     * tube constructor
     * receiving color, material, double radius and ray
     * @param color tube color
     * @param material tube material
     * @param radius double radius
     * @param axisRay ray
     */
    public Tube(Color color, Material material, double radius, Ray axisRay) {
        super(color, material, radius);
        _axisRay = new Ray(axisRay);
    }

    /**
     * tube constructor
     * receiving color, double radius and ray
     * call the another constructor with material, (0,0,0)
     * @param color tube color
     * @param radius double radius
     * @param axisRay ray
     */
    public Tube(Color color, double radius, Ray axisRay) {
        this(color, new Material(0,0,0), radius, axisRay);
    }

    /**
     * tube constructor
     * receiving double radius and ray
     * call the another constructor with color, BLACK
     * @param radius double radius
     * @param axisRay ray
     */
    public Tube(double radius, Ray axisRay) { this(Color.BLACK, radius, axisRay); }

    /**
     * tube constructor
     * receiving color, material, double radius and ray
     * @param color tube color
     * @param material tube material
     * @param _radial Radial Geometry radius
     * @param axisRay ray
     */
    public Tube(Color color, Material material, RadialGeometry _radial, Ray axisRay) {
        super(color, material, _radial);
        _axisRay = new Ray(axisRay);
    }

    /**
     * tube constructor
     * receiving color, double radius and ray
     * call the another constructor with material, (0,0,0)
     * @param color tube color
     * @param _radial Radial Geometry radius
     * @param axisRay ray
     */
    public Tube(Color color, RadialGeometry _radial, Ray axisRay) {
        this(color, new Material(0,0,0), _radial, axisRay);
    }

    /**
     * tube constructor
     * receiving double radius and ray
     * call the another constructor with color, BLACK
     * @param radius Radial Geometry radius
     * @param axisRay ray
     */
    public Tube(RadialGeometry radius, Ray axisRay) { this(Color.BLACK, radius, axisRay); }

    /**
    *function that return the ray 
    */
    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }

    @Override
    public String toString() {
        return super.toString() +
                "_axisRay=" + _axisRay;
    }

    @Override
    public Vector getNormal(Point3D point) {

        Point3D o = _axisRay.get_poo();
        Vector v= _axisRay.get_direction();
        double t= point.subtract(o).dotProduct(v);
        if (!isZero(t))
            o= o.add(v.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }
}

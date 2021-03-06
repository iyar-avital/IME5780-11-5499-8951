package geometries;

import primitives.*;

import java.awt.event.MouseAdapter;
import java.util.List;

import static primitives.Util.alignZero;

public class Sphere extends RadialGeometry {

    /**
     * center point in the sphere
     */
    private Point3D _center;

    /**
     * getter- center
     * @return center point in the sphere
     */
    public Point3D get_center() {
        return new Point3D(_center);
    }

    /**
     * Sphere constructor
     * receiving color, material, double radius and point
     * @param color sphere color
     * @param material sphere material
     * @param radius double radius
     * @param point center sphere point
     */
    public Sphere(Color color, Material material, double radius, Point3D point) {
        super(color, material, radius);
        _center = new Point3D(point);
    }

    /**
     * Sphere constructor
     * receiving color, double radius and point
     * call the another constructor with material, (0,0,0)
     * @param color sphere color
     * @param radius double radius
     * @param point center sphere point
     */
    public Sphere(Color color, double radius, Point3D point) {
        this(color, new Material(0,0,0), radius, point);
    }

    /**
     * Sphere constructor
     * receiving double radius and point
     * call the another constructor with color, BLACK
     * @param radius double radius
     * @param point center sphere point
     */
    public Sphere(double radius, Point3D point) { this(Color.BLACK, radius, point);}

    /**
     * Sphere constructor
     * receiving color, material, double radius and point
     * @param color sphere color
     * @param material sphere material
     * @param _radial double radius
     * @param point3D center sphere point
     */
    public Sphere(Color color, Material material, RadialGeometry _radial, Point3D point3D) {
        super(color, material, _radial);
        _center = new Point3D(point3D);
    }

    /**
     * Sphere constructor
     * receiving color, double radius and point
     * call the another constructor with material, (0,0,0)
     * @param color sphere color
     * @param _radial Radial Geometry radius
     * @param point3D center sphere point
     */
    public Sphere(Color color, RadialGeometry _radial, Point3D point3D) {
        this(color, new Material(0,0,0), _radial, point3D);
    }

    /**
     * Sphere constructor
     * receiving double radius and point
     * call the another constructor with color, BLACK
     * @param radius Radial Geometry radius
     * @param point center sphere point
     */
    public Sphere(RadialGeometry radius, Point3D point) { this(Color.BLACK, radius, point);}

    @Override
    public Vector getNormal(Point3D point) {
         return point.subtract(_center).normalize();
    }

    @Override
    public String toString() {
        return super.toString() +
                "_center=" + _center;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        Point3D p0 = ray.get_poo();
        Vector v = ray.get_direction();
        Vector u;
        try {
            u = _center.subtract(p0);   // p0 == _center
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this, ray.getTargetPoint(_radius)));
        }
        
        double tm = alignZero(v.dotProduct(u));
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(_radius * _radius - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) return List.of(new GeoPoint(this, ray.getTargetPoint(t1)), new GeoPoint(this, ray.getTargetPoint(t2))); //P1 , P2
        if (t1 > 0)
            return List.of(new GeoPoint (this, ray.getTargetPoint(t1)));
        else
            return List.of(new GeoPoint(this, ray.getTargetPoint(t2)));
    }
}

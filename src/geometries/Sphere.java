package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere extends RadialGeometry {

    private Point3D _center;

    public Point3D get_center() {
        return new Point3D(_center);
    }

    public Sphere(double radius, Point3D point) {
        super(radius);
        _center = new Point3D(point);
    }

    public Sphere(RadialGeometry _radial, Point3D point3D) {
        super(_radial);
        _center = new Point3D(point3D);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() +
                "_center=" + _center;
    }

}

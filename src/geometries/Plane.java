package geometries;

import primitives.*;

import java.awt.event.MouseAdapter;
import java.util.List;

public class Plane extends Geometry {
    private Point3D _point;
    private Vector _normal;

    public Point3D get_point() {
        return new Point3D(_point);
    }

    public Vector getNormal() {
        return new Vector(_normal);
    }

    public Plane(Color color, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(color, material);
        Vector U = p1.subtract(p2);
        Vector V = p1.subtract(p3);
        Vector N = U.crossProduct(V);
        N.normalize();
        this._normal = N.scale(-1);
        this._point = p1;
    }

    public Plane(Color color, Point3D p1, Point3D p2, Point3D p3)
    {
        this(Color.BLACK, new Material(0,0,0), p1,p2,p3);
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3)
    {
        this(Color.BLACK,p1,p2,p3);
    }

    public Plane(Color color, Material material, Point3D point, Vector normal) {
        super(color, material);
        this._point = new Point3D(point);
        this._normal = new Vector(normal.normalized());
    }

    public Plane(Color color, Point3D point, Vector normal) {
        this(Color.BLACK, new Material(0,0,0), point, normal);
    }

    public Plane(Point3D point, Vector normal) {
        this(Color.BLACK, point, normal);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }

    @Override
    public String toString() {
        return "_point=" + _point +
                ", _normal=" + _normal;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        double nv = _normal.dotProduct(ray.get_direction());

        if (Util.isZero(nv)) // ray is parallel to the plane - no intersections
            return null;

        Vector Qp;
        try {
          Qp = _point.subtract(ray.get_poo());

        } catch (Exception i) { return null; }

        double t = Util.alignZero(_normal.dotProduct(Qp) / nv);
        return t <= 0 ? null : List.of(new GeoPoint(this, ray.getTargetPoint(t)));
    }
}

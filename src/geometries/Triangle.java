package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

/**
 * triangle class
 */
public class Triangle extends Polygon {

    public Triangle(Color color, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(color, material, new Point3D[]{p1, p2, p3});
    }

    public Triangle(Color color, Point3D p1, Point3D p2, Point3D p3) {
        super(color, new Point3D[]{p1, p2, p3});
    }

    /**
     * triangle constructor
     * @param p1 - first vertex
     * @param p2 - second vertex
     * @param p3 - third vertex
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(new Point3D[]{p1, p2, p3});
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = _plane.findIntersections(ray);
        if (intersections == null) return null;

        Point3D p0 = ray.get_poo();
        Vector v = ray.get_direction();

        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2).normalize());
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3).normalize());
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v1).normalize());
        if (isZero(s3)) return null;
        List<GeoPoint> triangleIntersections = List.of(new GeoPoint(this, intersections.get(0).getPoint()));
        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? triangleIntersections : null;

    }
}

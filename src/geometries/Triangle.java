package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * triangle class
 */
public class Triangle extends Polygon {

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
    public List<Point3D> findIntersections(Ray ray) {

        //if the ray point begin on a vertex in the triangle
        if(this._vertices.get(0).equals(ray.get_poo()) || this._vertices.get(1).equals(ray.get_poo()) || this._vertices.get(2).equals(ray.get_poo()))
            return null;

        try {
            Vector v1 = this._vertices.get(0).subtract(ray.get_poo());
            Vector v2 = this._vertices.get(1).subtract(ray.get_poo());
            Vector v3 = this._vertices.get(2).subtract(ray.get_poo());
            System.out.print(v1 + "-v1       \n" + v2 + "-v2      \n" + v3 + "-v3      \n");

            if(v1.isParallel(v2) || v1.isParallel(v3) || v2.isParallel(v3))
                return null;
            Vector N1 = v1.crossProduct(v2);
            Vector N2 = v2.crossProduct(v3);
            Vector N3 = v1.crossProduct(v3);
            System.out.print(N1 + "-N1       \n" + N2 + "-N2      \n" + N3 + "-N3      \n");


        double n1 = ray.get_direction().dotProduct(N1);
        double n2 = ray.get_direction().dotProduct(N2);
        double n3 = ray.get_direction().dotProduct(N3);
        System.out.print(ray.get_direction() + "\n");
            System.out.print(n1 + "-n1       \n" + n2 + "-n2      \n" + n3 + "-n3      \n");
        if (Util.isZero(n1) || Util.isZero(n2) || Util.isZero(n3))
            return null;

        if (n1 > 0 && n2 > 0 && n3 > 0)
            return this._plane.findIntersections(ray);

        if (n1 < 0 && n2 < 0 && n3 < 0)
            return this._plane.findIntersections(ray);
        return null;

        } catch (IllegalArgumentException i) { return null; }
    }
}

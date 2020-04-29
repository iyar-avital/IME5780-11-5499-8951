package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * the interface that all the geometries implement
 */
public interface Intersectable {
    /**
     *
     * @param ray the ray to find Intersections between it and geometry
     * @return all the Intersections between the ray and geometry
     */
    List<Point3D> findIntersections(Ray ray);
}

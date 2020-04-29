package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * the interface of all geometries implement from it
 */
public interface Geometry extends Intersectable {

    /**
     *
     * @param point - the point to find the normal
     * @return normal vector in point 'point'
     */
    public Vector getNormal(Point3D point);
}

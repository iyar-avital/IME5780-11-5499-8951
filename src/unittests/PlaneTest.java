package unittests;

import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static org.junit.Assert.*;

public class PlaneTest extends Object {

    @Test
    public void getNormalTest() {
        Point3D a1 = new Point3D(5,6,7);
        Point3D a2 = new Point3D(0,0,1);
        Point3D a3 = new Point3D(5,5,5);
        Vector v1 = a1.subtract(a2);
        Vector v2 = a1.subtract(a3);
        Plane p = new Plane(a1,a2,a3);

        Vector n = p.getNormal(a1);
        assertTrue(Util.isZero(v1.dotProduct(n)));
        assertTrue(Util.isZero(v2.dotProduct(n)));

    }


    @Test
    public void findIntersectionsTest() {
        Plane plane = new Plane(new Point3D(1,1,0), new Point3D(0,0,1), new Point3D(1,0,1));

        // ============ Equivalence Partitions Tests ==============
        //Ray intersects the plane
        Ray ray1 = new Ray(new Point3D(5,5,5), new Vector(1,2,-3));
        assertEquals("Ray intersects the plane",plane.findIntersections(ray1).size(), 1);

        //Ray does not intersect the plane
        Ray ray2 = new Ray(new Point3D(1,2,3), new Vector(1,0,2));
        assertNull("Ray does not intersects the plane",plane.findIntersections(ray2));

        // =============== Boundary Values Tests ==================
        // Ray is parallel to the plane - the ray included in the plane
        Ray ray3 = new Ray(new Point3D(2,1,0), new Vector(0,0,1));
        assertNull("Ray is parallel to the plane - the ray included in the plane",plane.findIntersections(ray3));

        // Ray is parallel to the plane - the ray not included in the plane
        Ray ray4 = new Ray(new Point3D(2,2,0), new Vector(2,1,1));
        assertNull("Ray is parallel to the plane - the ray not included in the plane",plane.findIntersections(ray4));

        //Ray is orthogonal to the plane - before
        Ray ray5 = new Ray(new Point3D(0,0,0), new Vector(0,1,1));
        assertEquals("Ray is orthogonal to the plane - before",plane.findIntersections(ray5).size(), 1);

        //Ray is orthogonal to the plane - in
        Ray ray6 = new Ray(new Point3D(0,0.5,0.5), new Vector(0,1,1));
        assertNull("Ray is orthogonal to the plane - in",plane.findIntersections(ray6));

        //Ray is orthogonal to the plane - after
        Ray ray7 = new Ray(new Point3D(0,0,0), new Vector(0,-1,-1));
        assertNull("Ray is orthogonal to the plane - after",plane.findIntersections(ray7));

        //Ray is neither orthogonal nor parallel to and begins at the plane
        Ray ray8 = new Ray(new Point3D(0,0.5,0.5), new Vector(0,2,1));
        assertNull("Ray is neither orthogonal nor parallel to and begins at the plane",plane.findIntersections(ray8));

        //Ray is neither orthogonal nor parallel to and begins at the plane - start point is A
        Ray ray9 = new Ray(new Point3D(1,1,0), new Vector(3,2,5));
        assertNull("Ray is neither orthogonal nor parallel to and begins at the plane",plane.findIntersections(ray9));
    }
}
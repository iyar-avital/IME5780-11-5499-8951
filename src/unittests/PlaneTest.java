package unittests;

import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
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
}
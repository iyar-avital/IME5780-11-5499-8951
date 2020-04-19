package unittests;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class Point3DTest extends Object {

    @Test
    public void testSubtract() {
        Point3D p1 = new Point3D(2,5,6);
        Point3D p2 = new Point3D(-1, 6, 9);

        assertEquals(new Vector(3,-1,-3), p1.subtract(p2));
    }

    @Test
    public void testAdd() {
        Point3D p1 = new Point3D(2,5,6);
        Vector v2 = new Vector(-1, 6, -6);

        assertEquals(new Point3D(1,11,0), p1.add(v2));
    }

    @Test
    public void testDistanceSquared() {
        Point3D p1 = new Point3D(2,-3,6);
        Point3D p2 = new Point3D(-1,7,5);

        assertEquals(110, p1.distanceSquared(p2), 0.001);
    }

    @Test
    public void testDistance() {
        Point3D p1 = new Point3D(2,-3,6);
        Point3D p2 = new Point3D(-1,7,5);

        assertEquals(10.488, p1.distance(p2), 0.001);
    }
}
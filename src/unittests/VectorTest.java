package unittests;

import primitives.Vector;

import static org.junit.Assert.*;

        /**
        * Testing Vectors
        */
public class VectorTest {

    /**
     * Unit test for primitives.Vector class
     */
    @org.junit.Test
    public void testSubtract() {

        //============Equivalence Partitions Tests============
        Vector v1 = new Vector(2,3,5);
        Vector v2 = new Vector(1,3,7);
        Vector v3 = new Vector(-2,-3,-9);

        //simple test, + +
        assertEquals(new Vector(1,0,-2), v1.subtract(v2));

        //simple test, + -
        assertEquals(new Vector(4,6,14), v1.subtract(v3));

        //simple test - +
        assertEquals(new Vector(-3,-6,-16), v3.subtract(v2));

        //same vector
        try {
            v1.subtract(v1);
            fail("must throw excption");
        } catch (IllegalArgumentException i) {}

    }

    @org.junit.Test
    public void testAdd() {
        //============Equivalence Partitions Tests============
        Vector v1 = new Vector(2,3,6);
        Vector v2 = new Vector(1,4,7);
        Vector v3 = new Vector(-2,-3,-6);
        Vector v4 = new Vector(-6, -2, -1);
        //simple test, + +
        assertEquals(new Vector(3,7,13), v1.add(v2));

        //simple test, + -
        assertEquals(new Vector(-4,1,5), v1.add(v4));

        //simple test - -
        assertEquals(new Vector(-8,-5,-7), v3.add(v4));

        //Vector against me (חחחח)
        try {
            v1.add(v3);
            fail("must throw excption");
        } catch (IllegalArgumentException i) {}

    }

    @org.junit.Test
    public void testScale() {

            Vector v1 = new Vector(1,1,1);

            Vector v1test = v1.scale(1);
            assertEquals(new Vector(1,1,1),v1test);

            Vector v2test = v1test.scale(2);
            assertEquals(new Vector(2,2,2),v2test);

            Vector v3test = v2test.scale(-2);
            assertEquals(new Vector(-4,-4,-4),v3test);
    }

    @org.junit.Test
    public void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);

       // ============ Equivalence Partitions Tests ==============
       // TC01: Simple dotProduct test
       Vector v2 = new Vector(-2, -4, -6);
       assertEquals("dotProduct() wrong value", -28d, v1.dotProduct(v2), 0.00001);

       // =============== Boundary Values Tests =================
        //  TC11: dotProduct for orthogonal vectors
        Vector v3 = new Vector(0, 3, -2);
        assertEquals("dotProduct() for orthogonal vectors is not zero", 0d, v1.dotProduct(v3), 0.00001);
    }

    @org.junit.Test
    public void testCrossProduct() {

        Vector v1 = new Vector(3.5, -5.0, 10.0);
        Vector v2 = new Vector(2.5,7,0.5);
        Vector v3 = v1.crossProduct(v2);

        assertEquals( 0, v3.dotProduct(v2), 1e-10);
        assertEquals( 0, v3.dotProduct(v1), 1e-10);

        Vector v4 = v2.crossProduct(v1);

        System.out.println(v3.toString());
        System.out.println(v4.toString());

        try {
            v3.add(v4);
            fail("Vector (0,0,0) not valid");
        }
        catch  (IllegalArgumentException e)
        {
            assertTrue(e.getMessage()!= null);
        }
//        assertTrue(v3.length() >84);
        assertEquals(84,v3.length(),0.659);
    }

    @org.junit.Test
    public void testLengthSquared() {
        Vector v1 = new Vector(3.6, -5.0, 10.0);
        Vector v2 = new Vector(2.8,7,0.5);

        assertEquals(v1.lengthSquared(), 137.96, 1e-10);
        assertEquals(v2.lengthSquared(), 57.09, 1e-10);
    }

    @org.junit.Test
    public void testLength() {
        Vector v1 = new Vector(3.6, -5.0, 10.0);
        Vector v2 = new Vector(2.8,7,0.5);

        assertEquals(v1.length(), 11.7456374, 0.001);
        assertEquals(v2.length(), 7.555792, 0.001);
    }

    @org.junit.Test
    public void testNormalize() {
        Vector v = new Vector(3.5, -5, 10);
        v.normalize();
        assertEquals(1, v.length(), 1e-10);

        try {
            Vector v1 = new Vector(0, 0, 0);
            v.normalize();
            fail("Didn't throw divide by zero exception!");
        } catch (IllegalArgumentException ex) {
            assertEquals("Point3D(0.0,0.0,0.0) not valid for vector head", ex.getMessage());
        }
        assertTrue(true);
    }
}
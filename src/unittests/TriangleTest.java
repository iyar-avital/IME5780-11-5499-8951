package unittests;

import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TriangleTest extends Object {

    @Test
    public void findIntersectionsTest() {
        Triangle triangle = new Triangle(new Point3D(1,0,2),new Point3D(0,0,2),new Point3D(1,1,1));
        // ============ Equivalence Partitions Tests ==============
        //Inside triangle
        Ray ray = new Ray(new Point3D(0,1,-2), new Vector(0.5,0,0.86876));
        assertEquals("Inside triangle", triangle.findIntersections(ray).size(), 1);

        /**Ray ray = new Ray(new Point3D(0,0,0), new Vector(5,3,25));
        assertEquals("Inside triangle", triangle.findIntersections(ray).size(), 1);*/

        //Outside against edge
        Ray ray1 = new Ray(new Point3D(1,1,0), new Vector(3,1,5));
        assertNull("Outside against edge", triangle.findIntersections(ray1));

        /**Ray ray2 = new Ray(new Point3D(0,0,0), new Vector(3,7,5));
        assertNotNull("Outside against edge", triangle.findIntersections(ray2));*/

        //Outside against vertex
        Ray ray3 = new Ray(new Point3D(1,0,0), new Vector(1,5,4));
        assertNull("Outside against vertex", triangle.findIntersections(ray3));

        /**Ray ray3 = new Ray(new Point3D(0,0,0), new Vector(9,11,5));
        assertNotNull("Outside against vertex", triangle.findIntersections(ray3));*/

        // =============== Boundary Values Tests ==================
        //On edge
        Ray ray4 = new Ray(new Point3D(0.5,0,-3), new Vector(2,1,11));
        assertNull("On edge", triangle.findIntersections(ray4));

        /**Ray ray4 = new Ray(new Point3D(0,0,0), new Vector(1,1,2));
        assertNotNull("On edge", triangle.findIntersections(ray4));
        Ray ray5 = new Ray(new Point3D(0,0,0), new Vector(7,4,10));
        assertNotNull("On edge", triangle.findIntersections(ray5));*/

        //In vertex
        Ray ray6 = new Ray(new Point3D(1,0,0), new Vector(1,5,5));
        assertNull("In vertex", triangle.findIntersections(ray6));

        Ray ray7 = new Ray(new Point3D(1,0,0.5), new Vector(1,7,4));
        assertNull("In vertex", triangle.findIntersections(ray7));

        /**Ray ray6 = new Ray(new Point3D(0,0,0), new Vector(0,0,5));
        assertNotNull("In vertex", triangle.findIntersections(ray6));*/

        //On edge's continuation
        Ray ray8 = new Ray(new Point3D(-0.5,0,-3), new Vector(1.9,-2,7));
        assertNull("On edge's continuation", triangle.findIntersections(ray8));

        /**Ray ray8 = new Ray(new Point3D(0,0,0), new Vector(3,-4,10));
        assertNotNull("In vertex", triangle.findIntersections(ray8 ));*/
    }

}
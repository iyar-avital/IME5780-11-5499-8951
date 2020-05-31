package unittests.geometries;

import geometries.Intersectable;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

public class TriangleTest extends Object {

    @Test
    public void findIntersectionsTest() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is Inside triangle (1 points)
        Triangle triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        Ray ray = new Ray(new Point3D(0,0.5,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is Inside triangle", 1 ,triangle.findIntersections(ray).size());

        // TC02: Ray's line is Outside against edge (0 points)
        triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(2,0.5,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is Outside against edge", null ,triangle.findIntersections(ray));

        // TC03:  Ray's line is Outside against vertex (0 points)
        triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(0,2,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is Outside against vertex", null ,triangle.findIntersections(ray));

        // =============== Boundary Values Tests ==================

        // TC01: Ray's line is On edge (0 points)
        triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(0.5,0,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is On edge", null ,triangle.findIntersections(ray));

        // TC02: Ray's line is In vertex (0 points)
        triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(0,1,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is In vertex", null ,triangle.findIntersections(ray));

        // TC03: Ray's line is On edge's continuation (0 points)
        triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(2,0,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is On edge's continuation", null ,triangle.findIntersections(ray));
    }
}
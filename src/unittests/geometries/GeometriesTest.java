package unittests.geometries;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

public class GeometriesTest extends Object {

    @Test
    public void findIntersections() {
        Geometries ListOfGeometries = new Geometries();
        Ray ray = new Ray(new Point3D(0,0.5,-1), new Vector(0,0,1));

        // TC01: Empty lis
        assertNull("empty list", ListOfGeometries.findIntersections(ray));

        //TC02: no geometry with intersects
        Triangle triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        Plane plane = new Plane(new Point3D(-20,-1,8), new Point3D(0,9,-20), new Point3D(-13,21,6));
        ray = new Ray(new Point3D(2,0.5,-1), new Vector(0,0,1));
        ListOfGeometries.add(triangle,plane);
        assertNull("no geometry with intersects", ListOfGeometries.findIntersections(ray));

        // TC03: just one intersect
        plane = new Plane(new Point3D(-20,-1,8), new Point3D(0,9,-20), new Point3D(1.3,1,6));
        Sphere sphere = new Sphere(1.2, new Point3D(0,1,0));
        Geometries ListOfGeometries2 = new Geometries(sphere, plane, triangle);
        assertEquals("just one intersect", ListOfGeometries2.findIntersections(ray).size(), 1);

        // TC04: any geometries but not all have a intersect with the ray
        sphere = new Sphere(4, new Point3D(0,1,0));
        ListOfGeometries2.add(sphere);
        assertEquals("any geometries but not all have an intersect with the ray", ListOfGeometries2.findIntersections(ray).size(), 2);

        // TC05: all the geometries have a intersect with the ray
        Geometries ListOfGeometries3 = new Geometries(sphere, plane);
        assertEquals("all the geometries have an intersect with the ray", ListOfGeometries3.findIntersections(ray).size(), 2);
    }
}
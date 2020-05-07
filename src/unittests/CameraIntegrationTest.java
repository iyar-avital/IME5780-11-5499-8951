package unittests;

import elements.Camera;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CameraIntegrationTest extends Object {

    Camera camera;
    Sphere sphere;
    Plane plane;
    Ray ray;
    Triangle triangle;
    List<Point3D> results = new ArrayList<>();

    int nY = 3;
    int nX = 3;

    /**
     * Test helper function to count the intersections and compare with expected value
     *
     * @param cam - camera for the test
     * @param geo - 3D body to test the integration of the camera with
     * @return expected value
     */
    private int assertCountIntersections(Camera cam, Intersectable geo) {
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                ray = cam.constructRayThroughPixel(nX,nY,j,i,1,3,3);
                var v = geo.findIntersections(ray);
                if(v != null)
                    results.addAll(v);
            }
        }
        return results.size();
    }

    /**
     * test sphere 1: constructRayThroughPixel(int,int,int,int,double,double,double) + findIntersections (ray)
     * the central pixel send ray that create 2 intersect points
     */
    @Test
    public void constructRayThroughPixelSphereTest1()
    {
        // TC01 construct Ray Through Pixel With Sphere
        camera = new Camera(new Point3D(0,0,0), new Vector(0,0,1), new Vector(0,-1,0));
        sphere= new Sphere(1, new Point3D(0,0,3));
        results.clear();
        assertEquals(assertCountIntersections(camera, sphere),2);
    }

    /**
     * test sphere 2: constructRayThroughPixel(int,int,int,int,double,double,double) + findIntersections (ray)
     * every pixel send ray that create 2 intersect points - 2*9 = 18
     */
    @Test
    public void constructRayThroughPixelSphereTest2() {
        // TC02 construct Ray Through Pixel With Sphere
        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        sphere = new Sphere(2.5, new Point3D(0, 0, 2.5));
        results.clear();
        assertEquals(assertCountIntersections(camera, sphere), 18);
    }

    /**
     * test sphere 3: constructRayThroughPixel(int,int,int,int,double,double,double) + findIntersections (ray)
     * 5 pixels (like cross in the grid) send ray that create 2 intersect points - 2*5 = 10
     */
    @Test
    public void constructRayThroughPixelSphereTest3() {
        // TC03 construct Ray Through Pixel With Sphere
        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        sphere = new Sphere(2, new Point3D(0, 0, 2));
        results.clear();
        assertEquals(assertCountIntersections(camera,sphere), 10);
    }

    /**
     * test sphere 4: constructRayThroughPixel(int,int,int,int,double,double,double) + findIntersections (ray)
     * every pixel send ray that create 1 intersect point, because of the sphere included the plane view - 9*1 = 9
     */
    @Test
    public void constructRayThroughPixelSphereTest4() {
        // TC04 construct Ray Through Pixel With Sphere
        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        sphere = new Sphere(4, new Point3D(0, 0, 2));
        results.clear();
        assertEquals(assertCountIntersections(camera,sphere), 9);
    }

    /**
     * test sphere 5: constructRayThroughPixel(int,int,int,int,double,double,double) + findIntersections (ray)
     * the sphere is behind the view plane - no intersects
     */
    @Test
    public void constructRayThroughPixelSphereTest5() {
        // TC05 construct Ray Through Pixel With Sphere
        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        sphere = new Sphere(0.5, new Point3D(0, 0, -1));
        results.clear();
        assertEquals(assertCountIntersections(camera, sphere), 0);
    }

    /**
     * test plane 1: constructRayThroughPixel(int,int,int,int,double,double,double) + findIntersections (ray)
     * every pixel send ray that create intersect point - 1*9 = 9
     * the plane is orthogonal to the view plane
     */
    @Test
    public void constructRayThroughPixelPlaneTest1() {
        // TC01 construct Ray Through Pixel With plane
        camera = new Camera(new Point3D(0,0,0), new Vector(0,0,1), new Vector(0,-1,0));
        plane = new Plane(new Point3D(10,-6,7), new Vector(0,0,1));
        results.clear();
        assertEquals(assertCountIntersections(camera, plane), 9);
    }

    /**
     * test plane 2: constructRayThroughPixel(int,int,int,int,double,double,double) + findIntersections (ray)
     * every pixel send ray that create intersect point - 1*9 = 9
     */
    @Test
    public void constructRayThroughPixelPlaneTest2() {
        // TC02 construct Ray Through Pixel With plane
        camera = new Camera(new Point3D(0,0,0), new Vector(0,0,1), new Vector(0,-1,0));
        plane = new Plane(new Point3D(10,-6,7), new Vector(0.5,0,1));
        results.clear();
        assertEquals(assertCountIntersections(camera, plane), 9);
    }

    /**
     * test plane 3: constructRayThroughPixel(int,int,int,int,double,double,double) + findIntersections (ray)
     * from two rows: pixel send ray that create intersect point - 1*6 = 9
     */
    @Test
    public void constructRayThroughPixelPlaneTest3() {
        // TC03 construct Ray Through Pixel With plane
        camera = new Camera(new Point3D(0,0,0), new Vector(0,0,1), new Vector(0,-1,0));
        plane = new Plane(new Point3D(10,-6,7), new Vector(2,0,1));
        results.clear();
        assertEquals(assertCountIntersections(camera, plane), 6);
    }

    /**
     * test triangle 1: constructRayThroughPixel(int,int,int,int,double,double,double) + findIntersections (ray)
     * the central pixel send ray that create 1 intersect point
     */
    @Test
    public void constructRayThroughPixelTriangleTest1() {
        // TC01 construct Ray Through Pixel With triangle
        camera = new Camera(new Point3D(0,0,0), new Vector(0,0,1), new Vector(0,-1,0));
        triangle = new Triangle(new Point3D(0,-1,2), new Point3D(1,1,2), new Point3D(-1,1,2));
        results.clear();
        assertEquals(assertCountIntersections(camera, triangle), 1);
    }

    /**
     * test triangle 2: constructRayThroughPixel(int,int,int,int,double,double,double) + findIntersections (ray)
     * the central pixel and the pixel above it send ray that create 1 intersect point - 1*2 = 2
     */
    @Test
    public void constructRayThroughPixelTriangleTest2() {
        // TC02 construct Ray Through Pixel With triangle
        camera = new Camera(new Point3D(0,0,0), new Vector(0,0,1), new Vector(0,-1,0));
        triangle = new Triangle(new Point3D(0,-20,2), new Point3D(1,1,2), new Point3D(-1,1,2));
        results.clear();
        assertEquals(assertCountIntersections(camera, triangle), 2);
    }
}
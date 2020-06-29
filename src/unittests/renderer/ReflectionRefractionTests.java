package unittests.renderer;

import geometries.Plane;

import geometries.Polygon;
import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 *
 */
public class ReflectionRefractionTests {

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
                        new Point3D(0, 0, 50)),
                new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                0.0004, 0.0000006));

        ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(10000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), 400, new Point3D(-950, 900, 1000)),
                new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(-750, 750, 150),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     *  producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void regular()
    {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(9000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(0, 120, 256), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 128, 69), new Material(0.8, 0.9, 200,0,0.7), 450, new Point3D(-1020, 0, 1100)),
                new Sphere(new Color(63, 150, 20), new Material(0.8, 0.25, 120, 0, 0.7), 500, new Point3D(1000, -150, 1100)),
                new Sphere(new Color (51, 200, 51) ,new Material(0.85, 0.25, 700, 0, 0.7), 600, new Point3D(-100, -700, 1600)),
                new Plane(new Color(java.awt.Color.black), new Material(0.4, 0.3, 20000, 0, 0.4), new Point3D(1500, 1500, 0),
                        new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(0, 300, -400),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new SpotLight(new Color(20, 40, 0),  new Point3D(800, 100, -300),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new SpotLight(new Color(1020, 400, 400),  new Point3D(-800, 100, -300),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new DirectionalLight(new Color(java.awt.Color.darkGray),   new Vector(-0.5, 0.5, 0)) );

        ImageWriter imageWriter = new ImageWriter("regular", 2500, 2500, 500, 500);
        Render render = new Render(imageWriter, scene, Render.whatToRun.regular);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void miniProject1()
    {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(9000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(0, 120, 256), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 128, 69), new Material(0.8, 0.9, 200,0,0.7), 450, new Point3D(-1020, 0, 1100)),
                new Sphere(new Color(63, 150, 20), new Material(0.8, 0.25, 120, 0, 0.7), 500, new Point3D(1000, -150, 1100)),
                new Sphere(new Color (51, 200, 51) ,new Material(0.85, 0.25, 700, 0, 0.7), 600, new Point3D(-100, -700, 1600)),
                new Plane(new Color(java.awt.Color.black), new Material(0.4, 0.3, 20000, 0, 0.4), new Point3D(1500, 1500, 0),
                        new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(0, 300, -400),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new SpotLight(new Color(20, 40, 0),  new Point3D(800, 100, -300),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new SpotLight(new Color(1020, 400, 400),  new Point3D(-800, 100, -300),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new DirectionalLight(new Color(java.awt.Color.darkGray),   new Vector(-0.5, 0.5, 0)) );

        ImageWriter imageWriter = new ImageWriter("miniProject1", 2500, 2500, 500, 500);
        Render render = new Render(imageWriter, scene, Render.whatToRun.nimiProject1);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void miniProject2()
    {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(9000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(0, 120, 256), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 128, 69), new Material(0.8, 0.9, 200,0,0.7), 450, new Point3D(-1020, 0, 1100)),
                new Sphere(new Color(63, 150, 20), new Material(0.8, 0.25, 120, 0, 0.7), 500, new Point3D(1000, -150, 1100)),
                new Sphere(new Color (51, 200, 51) ,new Material(0.85, 0.25, 700, 0, 0.7), 600, new Point3D(-100, -700, 1600)),
                new Plane(new Color(java.awt.Color.black), new Material(0.4, 0.3, 20000, 0, 0.4), new Point3D(1500, 1500, 0),
                        new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(0, 300, -400),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new SpotLight(new Color(20, 40, 0),  new Point3D(800, 100, -300),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new SpotLight(new Color(1020, 400, 400),  new Point3D(-800, 100, -300),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new DirectionalLight(new Color(java.awt.Color.darkGray),   new Vector(-0.5, 0.5, 0)) );

        ImageWriter imageWriter = new ImageWriter("miniProject2", 2500, 2500, 500, 500);
        Render render = new Render(imageWriter, scene, Render.whatToRun.miniProject2);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void myAmazingImage3()
    {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(9000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(0, 120, 256), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 0, 128), new Material(0.8, 0.9, 200,0,0.7), 450, new Point3D(-1020, 0, 1100)),
                new Sphere(new Color(0, 128, 0), new Material(0.8, 0.9, 200,0,0.7), 450, new Point3D(1020, 0, 1100)),
                new Sphere(new Color(128, 0, 0), new Material(0.8, 0.9, 200,0,0.7), 450, new Point3D(0, 0, 1100)),

                //height
              //  new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -1100, 1100), new Point3D(-1800, -1200, 1100), new Point3D(-1800, -1100, 1100)),
              //  new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -1200, 1100), new Point3D(-1800, -1200, 1100), new Point3D(-1700, -1100, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -1100, 1100), new Point3D(-1800, -1000, 1100), new Point3D(-1700, -1000, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -1100, 1100), new Point3D(-1800, -1100, 1100), new Point3D(-1800, -1000, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -900, 1100), new Point3D(-1800, -1000, 1100), new Point3D(-1800, -900, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -1000, 1100), new Point3D(-1800, -1000, 1100), new Point3D(-1700, -900, 1100)),

                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -900, 1100), new Point3D(-1800, -800, 1100), new Point3D(-1700, -800, 1100)),
                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -900, 1100), new Point3D(-1800, -900, 1100), new Point3D(-1800, -800, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -700, 1100), new Point3D(-1800, -800, 1100), new Point3D(-1800, -700, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -800, 1100), new Point3D(-1800, -800, 1100), new Point3D(-1700, -700, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -700, 1100), new Point3D(-1800, -600, 1100), new Point3D(-1700, -600, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -700, 1100), new Point3D(-1800, -700, 1100), new Point3D(-1800, -600, 1100)),

                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -500, 1100), new Point3D(-1800, -600, 1100), new Point3D(-1800, -500, 1100)),
                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -600, 1100), new Point3D(-1800, -600, 1100), new Point3D(-1700, -500, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -500, 1100), new Point3D(-1800, -400, 1100), new Point3D(-1700, -400, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -500, 1100), new Point3D(-1800, -500, 1100), new Point3D(-1800, -400, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -300, 1100), new Point3D(-1800, -400, 1100), new Point3D(-1800, -300, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -400, 1100), new Point3D(-1800, -400, 1100), new Point3D(-1700, -300, 1100)),

                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -300, 1100), new Point3D(-1800, -200, 1100), new Point3D(-1700, -200, 1100)),
                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -300, 1100), new Point3D(-1800, -300, 1100), new Point3D(-1800, -200, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -100, 1100), new Point3D(-1800, -200, 1100), new Point3D(-1800, -100, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -200, 1100), new Point3D(-1800, -200, 1100), new Point3D(-1700, -100, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -100, 1100), new Point3D(-1800, 0, 1100), new Point3D(-1700, 0, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, -100, 1100), new Point3D(-1800, -100, 1100), new Point3D(-1800, 0, 1100)),

                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 100, 1100), new Point3D(-1800, 0, 1100), new Point3D(-1800, 100, 1100)),
                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 0, 1100), new Point3D(-1800, 0, 1100), new Point3D(-1700, 100, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 100, 1100), new Point3D(-1800, 200, 1100), new Point3D(-1700, 200, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 100, 1100), new Point3D(-1800, 100, 1100), new Point3D(-1800, 200, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 300, 1100), new Point3D(-1800, 200, 1100), new Point3D(-1800, 300, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 200, 1100), new Point3D(-1800, 200, 1100), new Point3D(-1700, 300, 1100)),

                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 300, 1100), new Point3D(-1800, 400, 1100), new Point3D(-1700, 400, 1100)),
                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 300, 1100), new Point3D(-1800, 300, 1100), new Point3D(-1800, 400, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 500, 1100), new Point3D(-1800, 400, 1100), new Point3D(-1800, 500, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 400, 1100), new Point3D(-1800, 400, 1100), new Point3D(-1700, 500, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 500, 1100), new Point3D(-1800, 600, 1100), new Point3D(-1700, 600, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1700, 500, 1100), new Point3D(-1800, 500, 1100), new Point3D(-1800, 600, 1100)),

                //width
                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1600, -1100, 1100), new Point3D(-1700, -1200, 1100), new Point3D(-1700, -1100, 1100)),
                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1600, -1200, 1100), new Point3D(-1700, -1200, 1100), new Point3D(-1600, -1100, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1500, -1100, 1100), new Point3D(-1600, -1000, 1100), new Point3D(-1500, -1000, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1500, -1100, 1100), new Point3D(-1600, -1100, 1100), new Point3D(-1600, -1000, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1400, -1000, 1100), new Point3D(-1500, -1100, 1100), new Point3D(-1500, -1000, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1400, -1100, 1100), new Point3D(-1500, -1100, 1100), new Point3D(-1400, -1000, 1100)),

                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1300, -1100, 1100), new Point3D(-1400, -1000, 1100), new Point3D(-1300, -1000, 1100)),
                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1300, -1100, 1100), new Point3D(-1400, -1100, 1100), new Point3D(-1400, -1000, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1200, -1000, 1100), new Point3D(-1300, -1100, 1100), new Point3D(-1300, -1000, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1200, -1100, 1100), new Point3D(-1300, -1100, 1100), new Point3D(-1200, -1000, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1100, -1100, 1100), new Point3D(-1200, -1000, 1100), new Point3D(-1100, -1000, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1100, -1100, 1100), new Point3D(-1200, -1100, 1100), new Point3D(-1200, -1000, 1100)),

                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1000, -1000, 1100), new Point3D(-1100, -1100, 1100), new Point3D(-1100, -1000, 1100)),
                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-1000, -1100, 1100), new Point3D(-1100, -1100, 1100), new Point3D(-1000, -1000, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-900, -1100, 1100), new Point3D(-1000, -1000, 1100), new Point3D(-900, -1000, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-900, -1100, 1100), new Point3D(-1000, -1100, 1100), new Point3D(-1000, -1000, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-800, -1000, 1100), new Point3D(-900, -1100, 1100), new Point3D(-900, -1000, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-800, -1100, 1100), new Point3D(-900, -1100, 1100), new Point3D(-800, -1000, 1100)),

                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-700, -1100, 1100), new Point3D(-800, -1000, 1100), new Point3D(-700, -1000, 1100)),
                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-700, -1100, 1100), new Point3D(-800, -1100, 1100), new Point3D(-800, -1000, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-600, -1000, 1100), new Point3D(-700, -1100, 1100), new Point3D(-700, -1000, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-600, -1100, 1100), new Point3D(-700, -1100, 1100), new Point3D(-600, -1000, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-500, -1100, 1100), new Point3D(-600, -1000, 1100), new Point3D(-500, -1000, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-500, -1100, 1100), new Point3D(-600, -1100, 1100), new Point3D(-600, -1000, 1100)),

                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-400, -1000, 1100), new Point3D(-500, -1100, 1100), new Point3D(-500, -1000, 1100)),
                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-400, -1100, 1100), new Point3D(-500, -1100, 1100), new Point3D(-400, -1000, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-300, -1100, 1100), new Point3D(-400, -1000, 1100), new Point3D(-300, -1000, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-300, -1100, 1100), new Point3D(-400, -1100, 1100), new Point3D(-400, -1000, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-200, -1000, 1100), new Point3D(-300, -1100, 1100), new Point3D(-300, -1000, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-200, -1100, 1100), new Point3D(-300, -1100, 1100), new Point3D(-200, -1000, 1100)),

                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-100, -1100, 1100), new Point3D(-200, -1000, 1100), new Point3D(-100, -1000, 1100)),
                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(-100, -1100, 1100), new Point3D(-200, -1100, 1100), new Point3D(-200, -1000, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(0, -1000, 1100), new Point3D(-100, -1100, 1100), new Point3D(-100, -1000, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(0, -1100, 1100), new Point3D(-100, -1100, 1100), new Point3D(0, -1000, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(100, -1100, 1100), new Point3D(0, -1000, 1100), new Point3D(100, -1000, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(100, -1100, 1100), new Point3D(0, -1100, 1100), new Point3D(0, -1000, 1100)),

                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(200, -1000, 1100), new Point3D(100, -1100, 1100), new Point3D(100, -1000, 1100)),
                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(200, -1100, 1100), new Point3D(100, -1100, 1100), new Point3D(200, -1000, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(300, -1100, 1100), new Point3D(200, -1000, 1100), new Point3D(300, -1000, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(300, -1100, 1100), new Point3D(200, -1100, 1100), new Point3D(200, -1000, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(400, -1000, 1100), new Point3D(300, -1100, 1100), new Point3D(300, -1000, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(400, -1100, 1100), new Point3D(300, -1100, 1100), new Point3D(400, -1000, 1100)),

                new Triangle(new Color(250, 50, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(500, -1100, 1100), new Point3D(400, -1000, 1100), new Point3D(500, -1000, 1100)),
                new Triangle(new Color(250, 100, 90), new Material(0.8, 0.9, 200,0,0.7), new Point3D(500, -1100, 1100), new Point3D(400, -1100, 1100), new Point3D(400, -1000, 1100)),
                new Triangle(new Color(160, 250, 140), new Material(0.8, 0.9, 200,0,0.7), new Point3D(600, -1000, 1100), new Point3D(500, -1100, 1100), new Point3D(500, -1000, 1100)),
                new Triangle(new Color(50, 250, 25), new Material(0.8, 0.9, 200,0,0.7), new Point3D(600, -1100, 1100), new Point3D(500, -1100, 1100), new Point3D(600, -1000, 1100)),
                new Triangle(new Color(45, 45, 250), new Material(0.8, 0.9, 200,0,0.7), new Point3D(700, -1100, 1100), new Point3D(600, -1000, 1100), new Point3D(700, -1000, 1100)),
                new Triangle(new Color(140, 180, 255), new Material(0.8, 0.9, 200,0,0.7), new Point3D(700, -1100, 1100), new Point3D(600, -1100, 1100), new Point3D(600, -1000, 1100)),


                new Plane(new Color(java.awt.Color.black), new Material(0.4, 0.3, 20000, 0, 0.4), new Point3D(1500, 1500, 0),
                        new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0)));



        scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(0, 300, -400),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new SpotLight(new Color(20, 40, 0),  new Point3D(800, 100, -300),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new SpotLight(new Color(1020, 400, 400),  new Point3D(-800, 100, -300),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005),new DirectionalLight(new Color(java.awt.Color.darkGray),   new Vector(-0.5, 0.5, 0)) );

        ImageWriter imageWriter = new ImageWriter("myAmazingImage3", 3000, 2000, 600, 400);
        Render render = new Render(imageWriter, scene, Render.whatToRun.miniProject2);

        render.renderImage();
        render.writeToImage();
    }
}




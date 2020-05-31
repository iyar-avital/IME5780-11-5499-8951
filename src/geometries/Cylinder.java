package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Ray;

import java.awt.event.MouseAdapter;

public class Cylinder extends Tube {

    private double _height;

    public double get_height() {
        return _height;
    }

    public Cylinder(Color color, Material material, double radius, Ray axisRay, double height) {
        super(color, material, radius, axisRay);
        _height = height;
    }

    public Cylinder(Color color, double radius, Ray axisRay, double height) {
        this(color, new Material(0,0,0), radius, axisRay, height);
    }

    /**
     * constructor
     * receiving double radius, ray and height
     */
    public Cylinder(double radius, Ray axisRay, double height) { this(Color.BLACK, radius, axisRay, height); }

    public Cylinder(Color color, Material material, RadialGeometry _radial, Ray axisRay, double height) {
        super(color, material, _radial, axisRay);
        _height = height;
    }

    public Cylinder(Color color, RadialGeometry _radial, Ray axisRay, double height) {
        this(color, new Material(0,0,0), _radial, axisRay, height);
    }

        /**
         * constructor
         * receiving radialGeometry radius, ray and height
         */
    public Cylinder(RadialGeometry radius, Ray axisRay, double height) { this(Color.BLACK, radius, axisRay, height); }
}

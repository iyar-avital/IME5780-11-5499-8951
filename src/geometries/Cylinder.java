package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Ray;

import java.awt.event.MouseAdapter;

/**
 * Cylinder class. extend tube and have a final height
 */
public class Cylinder extends Tube {
    /**
     * the cylinder height
     */
    private double _height;

    /**
     * @return the cylinder height
     */
    public double get_height() {
        return _height;
    }

    /**
     * cylinder constructor
     * receiving color, material, double radius, ray and height
     * @param color cylinder color
     * @param material cylinder material
     * @param radius double radius
     * @param axisRay ray axisRay
     * @param height height
     */
    public Cylinder(Color color, Material material, double radius, Ray axisRay, double height) {
        super(color, material, radius, axisRay);
        _height = height;
    }

    /**
     * cylinder constructor
     * receiving color, double radius, ray and height
     * call the another constructor with material, (0,0,0)
     * @param color cylinder color
     * @param radius double radius
     * @param axisRay ray axisRay
     * @param height height
     */
    public Cylinder(Color color, double radius, Ray axisRay, double height) {
        this(color, new Material(0,0,0), radius, axisRay, height);
    }

    /**
     * cylinder constructor
     * receiving double radius, ray and height
     * call the another constructor with color, BLACK
     * @param radius double radius
     * @param axisRay ray axisRay
     * @param height height
     */
    public Cylinder(double radius, Ray axisRay, double height) { this(Color.BLACK, radius, axisRay, height); }

    /**
     * cylinder constructor
     * receiving color, material, double radius, ray and height
     * @param color cylinder color
     * @param material cylinder material
     * @param _radial Radial Geometry radius
     * @param axisRay ray axisRay
     * @param height height
     */
    public Cylinder(Color color, Material material, RadialGeometry _radial, Ray axisRay, double height) {
        super(color, material, _radial, axisRay);
        _height = height;
    }

    /**
     * cylinder constructor
     * receiving color, double radius, ray and height
     * call the another constructor with material, (0,0,0)
     * @param color cylinder color
     * @param _radial Radial Geometry radius
     * @param axisRay ray axisRay
     * @param height height
     */
    public Cylinder(Color color, RadialGeometry _radial, Ray axisRay, double height) {
        this(color, new Material(0,0,0), _radial, axisRay, height);
    }

    /**
     * cylinder constructor
     * receiving double radius, ray and height
     * call the another constructor with color, BLACK
     * @param radius Radial Geometry radius
     * @param axisRay ray axisRay
     * @param height height
     */
    public Cylinder(RadialGeometry radius, Ray axisRay, double height) { this(Color.BLACK, radius, axisRay, height); }
}

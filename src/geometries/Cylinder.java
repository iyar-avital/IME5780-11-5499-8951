package geometries;

import primitives.Ray;

public class Cylinder extends Tube {

    private double _height;

    public double get_height() {
        return _height;
    }

    /**
    * constructor
    * receiving double radius, ray and height
    */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        _height = height;
    }
    
    /**
    * constructor
    * receiving radialGeometry radius, ray and height
    */
    public Cylinder(RadialGeometry _radial, Ray axisRay, double height) {
        super(_radial, axisRay);
        _height = height;
    }
}

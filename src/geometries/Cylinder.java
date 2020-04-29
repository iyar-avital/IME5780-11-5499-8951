package geometries;

import primitives.Ray;

public class Cylinder extends Tube {

    private double _height;

    public double get_height() {
        return _height;
    }

    /**
    *constructor
    *reciving double radius, ray and hight
    */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        _height = height;
    }
    
    /**
    *constructor
    *reciving radialGeometry radius, ray and hight
    */
    public Cylinder(RadialGeometry _radial, Ray axisRay, double height) {
        super(_radial, axisRay);
        _height = height;
    }
}

package geometries;

import primitives.Ray;

public class Cylinder extends Tube {

    private double _height;

    public double get_height() {
        return _height;
    }

    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        _height = height;
    }

    public Cylinder(RadialGeometry _radial, Ray axisRay, double height) {
        super(_radial, axisRay);
        _height = height;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", _height=" + _height;

    }
}

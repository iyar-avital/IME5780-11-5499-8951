package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {

    protected Ray _axisRay;

    public Tube(double radius, Ray axisRay) {
        super(radius);
        _axisRay = new Ray(axisRay);
    }

    public Tube(RadialGeometry _radial, Ray axisRay) {
        super(_radial);
        _axisRay = new Ray(axisRay);
    }

    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }

    @Override
    public String toString() {
        return super.toString() +
                "_axisRay=" + _axisRay;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}

package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

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

        Point3D o = _axisRay.get_poo();
        Vector v= _axisRay.get_direction();
        double t= point.subtract(o).dotProduct(v);
        if (!isZero(t))
            o= o.add(v.scale(t));
        return point.subtract(o).normalize();
    }
}

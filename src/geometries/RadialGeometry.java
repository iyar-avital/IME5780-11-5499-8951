package geometries;

/**
 * the abstract class all
 *
 */
public abstract class RadialGeometry implements Geometry {

    protected double _radius;

    public RadialGeometry(double radius) {
        this._radius = radius;
    }

    public RadialGeometry(RadialGeometry _radial) {
        this._radius = _radial._radius;
    }

    public double get_radius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "_radius=" + _radius;
    }
}

package geometries;

/**
 * the abstract class that all classes that have radius extend
 *
 */
public abstract class RadialGeometry implements Geometry {

    /**
     * the radius
     */
    protected double _radius;

    /**
     * radial geometry constructor
     * @param radius - radius in double
     */
    public RadialGeometry(double radius) {
        this._radius = radius;
    }

    /**
     * radial geometry constructor
     * @param _radial - radius in RadialGeometry
     */
    public RadialGeometry(RadialGeometry _radial) {
        this._radius = _radial._radius;
    }

    /**
     *
     * @return parameter radius
     */
    public double get_radius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "_radius=" + _radius;
    }
}

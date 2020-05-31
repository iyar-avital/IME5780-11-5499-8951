package geometries;

import primitives.Color;
import primitives.Material;

import java.awt.event.MouseAdapter;

/**
 * the abstract class that all classes that have radius extend
 *
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * the radius
     */
    protected double _radius;

    /**
     * radial geometry constructor
     * @param material emission material
     * @param color emission color
     * @param radius radius in double
     */
    public RadialGeometry(Color color, Material material, double radius) {
        super(color, material);
        this._radius = radius;
    }

    /**
     * radial geometry constructor
     * call the another constructor with material, (0,0,0)
     * @param color emission color
     * @param radius radius in double
     */
    public RadialGeometry(Color color, double radius) {
        this(color, new Material(0,0,0), radius);
    }

    /**
     * radial geometry constructor
     * call the another constructor with color, BLACK
     * @param radius radius in double
     */
    public RadialGeometry(double radius) {
        this(Color.BLACK, radius);
    }

    /**
     * radial geometry constructor
     * @param material emission material
     * @param color emission color
     * @param _radial radius in RadialGeometry
     */
    public RadialGeometry(Color color, Material material, RadialGeometry _radial) {
        super(color, material);
        this._radius = _radial._radius;
    }

    /**
     * radial geometry constructor
     * call the another constructor with material, (0,0,0)
     * @param color emission color
     * @param _radial radius in RadialGeometry
     */
    public RadialGeometry(Color color, RadialGeometry _radial) {
        this(color, new Material(0,0,0), _radial);
    }

    /**
     * radial geometry constructor
     * call the another constructor with color, BLACK
     * @param _radial radius in RadialGeometry
     */
    public RadialGeometry(RadialGeometry _radial) { this(Color.BLACK, _radial); }

    /**
     * getter- radius
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

package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * the interface of all geometries implement from it
 */
public abstract class Geometry implements Intersectable {

    /**
     * the shape color
     */
    protected Color _emission;

    protected Material _material;

    /**
     * Geometry constructor gets color and material of geometry
     * @param color of geometry
     * @param material of specific geometry
     */
    public Geometry(Color color, Material material) { _emission = new Color(color); _material = material; }

    /**
     * Geometry constructor gets a color of geometry
     * @param emission the shapes color
     */
    public Geometry(Color emission) {
        this(emission, new Material(0,0,0));
    }

    /**
     * default Geometry constructor
     */
    public Geometry() { this(Color.BLACK, new Material(0,0,0)); }

    /**
     * get emission
     * @return the emission of the shape
     */
    public Color get_emission() {
        return _emission;
    }

    /**
     * get material
     * @return the material of the shape
     */
    public Material get_material() { return _material; }

    /**
     * @param point - the point to find the normal
     * @return normal vector in point 'point'
     */
    public Vector getNormal(Point3D point) {
        return null;
    }
}

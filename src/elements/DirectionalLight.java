package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.ListIterator;

/**
 * Directional Light class
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * the direction vector
     */
    private Vector _direction;

    /**
     * Directional Light constructor
     * @param i the color of the light
     * @param direction the direction vector of the light
     */
    public DirectionalLight(Color i, Vector direction) {
        super(i);
        this._direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return get_intensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return new Vector(_direction);
    }
}

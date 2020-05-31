package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.ListIterator;

public class DirectionalLight extends Light implements LightSource {
    private Vector _direction;

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

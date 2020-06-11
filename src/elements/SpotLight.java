package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

import java.security.PublicKey;

/**
 * spot Light class. extend from point light, have a direction + all the parameter from the point light class
 */
public class SpotLight extends PointLight {
    /**
     * the direction vector
     */
    private Vector _direction;

    /**
     * spot Light constructor
     * @param i the color of the light
     * @param _position the start light point
     * @param d the direction vector
     * @param _kC it is simple kc
     * @param _kL it is simple kc
     * @param _kQ it is simple kc
     */
    public SpotLight(Color i, Point3D _position, Vector d, double _kC, double _kL, double _kQ)
    {
        super(i,_position,_kC,_kL,_kQ);
        _direction = d.normalized();
    }

    @Override
    public Color get_intensity() {
        return super.get_intensity();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double projection = _direction.dotProduct(getL(p));

        if (Util.isZero(projection)) {
            return Color.BLACK;
        }
        double factor = Math.max(0, projection);
        Color pointlightIntensity = super.getIntensity(p);

        return (pointlightIntensity.scale(factor));
    }
}

package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

import java.security.PublicKey;

public class SpotLight extends PointLight {
    private Vector _direction;

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

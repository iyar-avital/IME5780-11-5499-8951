package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    protected Point3D _position;
    protected double _kC, _kL, _kQ;

    public PointLight(Color i, Point3D _position, double _kC, double _kL, double _kQ) {
        super(i);
        this._position = new Point3D(_position);
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }

    @Override
    public Color get_intensity() {
        return super.get_intensity();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double d2 = p.distanceSquared(_position);
        double d = p.distance(_position);
        return (_intensity.reduce(_kC + _kL*d + _kQ*d2));
    }

    @Override
    public Vector getL(Point3D p) {
        if (p.equals(_position)) {
            return null;
        }
        return p.subtract(_position).normalize();
    }
}

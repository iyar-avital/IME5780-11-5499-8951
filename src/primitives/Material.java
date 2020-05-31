package primitives;

import org.w3c.dom.ls.LSOutput;

public class Material {
   private double _kD;
   private double _kS;
   private int _nShininess;

    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    public double get_kD() {
        return _kD;
    }

    public double get_kS() {
        return _kS;
    }

    public int get_nShininess() {
        return _nShininess;
    }
}

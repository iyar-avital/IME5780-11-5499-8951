package primitives;

import org.w3c.dom.ls.LSOutput;

public class Material {
   private double _kD;
   private double _kS;
   private int _nShininess;
   private double _kT;
   private double _kR;

    public Material(double kD, double kS, int nShininess) {
        this(kD, kS, nShininess, 0, 0);
    }

    public Material(double kD, double kS, int nShininess, double kt, double kr) {
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
        this._kT = kt;
        this._kR = kr;
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

    public double get_kT() { return _kT; }

    public double get_kR() { return _kR; }
}

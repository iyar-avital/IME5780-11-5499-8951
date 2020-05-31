package elements;

import primitives.Color;

public class AmbientLight extends Light {

    public AmbientLight(Color i, double k) { super(i.scale(k)); }
}

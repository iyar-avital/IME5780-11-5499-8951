package elements;

import primitives.Color;

public class AmbientLight {
    Color _intensity;

/**
constructor
receiving Color i and double k
*/
    public AmbientLight(Color i, double k)
    {
        _intensity = i.scale(k);
    }

/**
function thaet return intensity
*/
    public Color get_intensity() {
        return _intensity;
    }
}

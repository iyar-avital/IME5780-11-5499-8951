package elements;

import primitives.Color;

public class AmbientLight {
    Color _intensity;

    public AmbientLight(Color i, double k)
    {
        _intensity = i.scale(k);
    }

    public Color get_intensity() {
        return _intensity;
    }
}

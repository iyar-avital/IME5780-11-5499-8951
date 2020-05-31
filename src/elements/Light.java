package elements;

import primitives.Color;

abstract class Light {
    protected Color _intensity;

    public Light(Color color) { _intensity = color; }

    public Color get_intensity() {
        return new Color(_intensity);
    }
}

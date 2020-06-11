package elements;

import primitives.Color;

/**
 * light class - the class that all the light extended from her
 */
abstract class Light {
    /**
     * the light color
     */
    protected Color _intensity;

    /**
     * light constructor
     * @param color the light color
     */
    public Light(Color color) { _intensity = new Color(color); }

    /**
     * @return the color - 'intensity' of the light
     */
    public Color get_intensity() {
        return new Color(_intensity);
    }
}

package elements;

import primitives.Color;

public class AmbientLight  extends  Light{

    /**
     * AmbientLight constructor
     * @param ia the color
     * @param ka ka
     */
    public AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }
}
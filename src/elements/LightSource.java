package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * the interface light source- all the lights that have a direction implement it
 */
public interface LightSource {

    /**
     *
     * @param p the point we want get the color it
     * @return the color in specific point
     */
    public Color getIntensity(Point3D p);

    /**
     * a function gets a point and calculate the vector from the light source to the point
     * @param p the point we want get the vector in it
     * @return the direction vector in specific point
     */
    public Vector getL(Point3D p);

    /**
     * calculate the distance between the light source and the point
     * @param point the intersect point
     * @return the distance
     */
    double getDistance(Point3D point);
}

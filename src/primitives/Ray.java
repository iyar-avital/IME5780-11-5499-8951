package primitives;

import java.util.Objects;

import static primitives.Util.isZero;

/**
 * Class Ray is the basic class representing a ray
 */

public final class Ray
{
    private static final double DELTA = 0.1;
    /**
     * A Ray is represented by a vector and point.
     * the vector is the direction of the ray
     */
    private Vector _direction;
    private Point3D _poo;

    /**
     * Ray constructor receiving direction vector and point
     * @param _direction - the direction vector
     * @param _poo - the start of the ray point
     */
    public Ray(Vector _direction, Point3D _poo) {
        _direction.normalize();
        this._direction = new Vector(_direction);
        this._poo = new Point3D(_poo);
    }

    /**
     * Ray constructor receiving direction vector and point
     * @param _poo - the start of the ray point
     * @param _direction - the direction vector
     */
    public Ray(Point3D _poo, Vector _direction) {
        _direction.normalize();
        this._direction = new Vector(_direction);
        this._poo = new Point3D(_poo);
    }

    /**
     * Ray copy constructor receiving a Ray and build
     * a new Ray with identical direction vector and point
     * @param ray - the ray to copy it
     */
    public Ray(Ray ray) {
        this._direction = new Vector(ray._direction);
        this._poo = new Point3D(ray._poo);
    }

    public Ray(Point3D point, Vector direction, Vector normal) {
        //point + normal.scale(±DELTA)
        _direction = new Vector(direction).normalized();

        double nv = normal.dotProduct(direction);

        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        _poo = point.add(normalDelta);
    }

    /**
     * @return new Vector(_direction) - Copy of the value _direction
     */
    public Vector get_direction() { return new Vector(_direction); }

    /**
     * @return new Point3D(_poo) - Copy of the value poo
     */
    public Point3D get_poo() { return new Point3D(_poo); }

    /**
     * @param length the length
     * @return new Point3D
     */
    public Point3D getTargetPoint(double length) {
        return isZero(length) ? _poo : _poo.add(_direction.scale(length));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(_direction, ray._direction) &&
                Objects.equals(_poo, ray._poo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_direction, _poo);
    }

    @Override
    public String toString() {
        return "_direction=" + _direction +
                ", _poo=" + _poo;
    }
}

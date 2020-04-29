package primitives;

import java.util.Objects;

/**
 * Class Point3D is the basic class representing a point
 */
public final class Point3D
{
    /**
     * the point consists of 3 Coordinates For 3 axes
     * x axis, y axis ant z axis
     */
    private Coordinate _x;
    private Coordinate _y;
    private Coordinate _z;
    public static final Point3D ZERO = new Point3D(0,0,0);
    /**
     * Point3D constructor receiving 3 coordinate values
     * @param x
     * @param y
     * @param z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
        this._z = new Coordinate(z);
    }

    /**
     * Point3D constructor receiving 3 double - coordinate values
     * @param x -
     * @param y -
     * @param z -
     */
    public Point3D(double x, double y, double z) {
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
        this._z = new Coordinate(z);
    }

    /**
     * Point3D copy constructor receiving a point and build new point
     * with identical values
     * @param point - the point to copy
     */
    public Point3D(Point3D point) {
        this._x = new Coordinate(point._x);
        this._y = new Coordinate(point._y);
        this._z = new Coordinate(point._z);
    }

    /**
     * @return new Coordinate(_x) - Copy of the value _x
     */
    public Coordinate get_x() { return new Coordinate(_x); }

    /**
     * @return new Coordinate(_y) - Copy of the value _y
     */
    public Coordinate get_y() { return new Coordinate(_y); }

    /**
     * @return new Coordinate(_z) - Copy of the value _z
     */
    public Coordinate get_z() { return new Coordinate(_z); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return Objects.equals(_x, point3D._x) &&
                Objects.equals(_y, point3D._y) &&
                Objects.equals(_z, point3D._z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_x, _y, _z);
    }

    @Override
    public String toString() {
        return "_x=" + _x + ", _y=" + _y + ", _z=" + _z;
    }

    public Vector subtract(Point3D other) {
        return new Vector(new Point3D(this._x.get() - other._x.get(),
                this._y.get() - other._y.get(),
                this._z.get() - other._z.get()));
    }

    public Point3D add(Vector other) {
        return new Point3D(this._x.get() + other.get_head()._x.get(),
                this._y.get() + other.get_head()._y.get(),
                this._z.get() + other.get_head()._z.get());
    }

    public double distanceSquared(Point3D other) {
        return this.subtract(other).lengthSquared();
    }

    public double distance(Point3D other) {
        return Math.sqrt(this.distanceSquared(other));
    }

}

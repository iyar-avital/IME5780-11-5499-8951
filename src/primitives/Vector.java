package primitives;
import java.util.Objects;

/**
 * Class Vector is the basic class representing a vector
 */
public final class Vector
{
    /**
     * A vector is represented by a single point, the and of a vector.
     * The vector start at "reshit hatzirim"
     */
    private Point3D _head;

    /**
     *  Vector constructor receiving 3 coordinate of the head point values
     * @param _x
     * @param _y
     * @param _z
     */
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._head = new Point3D(_x, _y, _z);
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
    }

    /**
     *  Vector constructor receiving 3 double - coordinate of the head point values
     * @param _x
     * @param _y
     * @param _z
     */
    public Vector(double _x, double _y, double _z) {
        this._head = new Point3D(_x, _y, _z);
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
    }

    /**
     * Vector constructor receiving a head point
     * @param _head
     */
    public Vector(Point3D _head) {
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
        this._head = new Point3D(_head);
    }

    /**
     * Vector copy constructor receiving a vector and build
     * a new vector  with identical head point
     * @param vector
     */
    public Vector(Vector vector) {
        this._head = new Point3D(vector._head);
    }

    /**
     * @return new Point3D(_head) - copy of the head point
     */
    public Point3D get_head() { return new Point3D(_head); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(_head, vector._head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_head);
    }

    @Override
    public String toString() {
        return "_head=" + _head;
    }

    public Vector subtract(Vector other) {
        return this._head.subtract(other._head);
    }

    public Vector add(Vector other) {
        return new Vector(this._head.add(other));
    }

    public Vector scale(double number) {
        return new Vector(new Point3D(this._head.get_x().get()*number,
                this._head.get_y().get()*number,
                this._head.get_z().get()*number));
    }

    public double dotProduct(Vector other) {
        return this._head.get_x().get()*other._head.get_x().get() +
                this._head.get_y().get()*other._head.get_y().get() +
                this._head.get_z().get()*other._head.get_z().get();
    }

    public Vector crossProduct(Vector other) {
        return new Vector(new Point3D(this._head.get_y().get()*other._head.get_z().get() - this._head.get_z().get()*other._head.get_y().get(),
                this._head.get_z().get()*other._head.get_x().get() - this._head.get_x().get()*other._head.get_z().get(),
                this._head.get_x().get()*other._head.get_y().get() - this._head.get_y().get()*other._head.get_x().get()));
    }

    public double lengthSquared() {
        return this._head.get_x().get()*this._head.get_x().get() +
                this._head.get_y().get()*this._head.get_y().get() +
                this._head.get_z().get()*this._head.get_z().get();
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize() {
        if (this.length() == 0)
            throw new ArithmeticException("divide by Zero");

        this._head = this.scale(1/this.length())._head;
        return this;
    }

    public Vector normalized() {
        Vector other = new Vector(this);
        other.normalize();
        return other;
    }
}

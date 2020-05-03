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
     * @param _x - value on x axis
     * @param _y - value on y axis
     * @param _z - value on z axis
     */
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._head = new Point3D(_x, _y, _z);
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Point3D(0.0,0.0,0.0) not valid for vector head");
    }

    /**
     *  Vector constructor receiving 3 double - coordinate of the head point values
     * @param _x - value on x axis
     * @param _y - value on y axis
     * @param _z - value on z axis
     */
    public Vector(double _x, double _y, double _z) {
        this._head = new Point3D(_x, _y, _z);
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Point3D(0.0,0.0,0.0) not valid for vector head");
    }

    /**
     * Vector constructor receiving a head point
     * @param _head - vector end point
     */
    public Vector(Point3D _head) {
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Point3D(0.0,0.0,0.0) not valid for vector head");
        this._head = new Point3D(_head);
    }

    /**
     * Vector copy constructor receiving a vector and build
     * a new vector  with identical head point
     * @param vector the vector to copy it
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

    /**
     * the function received vector and calculate the vector between this vector to it
     * @param other the second vector
     * @return vector between two final vector points
     */
    public Vector subtract(Vector other) {
        return this._head.subtract(other._head);
    }

    /**
     * the function received vector and calculate the vector between this vector to it
     * @param other  the second vector
     * @return vector between two start vector points
     */
    public Vector add(Vector other) {
        return new Vector(this._head.add(other));
    }

    /**
    * getting number and return the multiplication with all coordinate of the vector
    */
    public Vector scale(double number) {
        return new Vector(new Point3D(this._head.get_x().get()*number,
                this._head.get_y().get()*number,
                this._head.get_z().get()*number));
    }

    /**
    * return scalar multiplication
    */
    public double dotProduct(Vector other) {
        return (this._head.get_x().get()*other._head.get_x().get()) +
                (this._head.get_y().get()*other._head.get_y().get()) +
                (this._head.get_z().get()*other._head.get_z().get());
    }

    /**
    * return vector multiplication
    */
    public Vector crossProduct(Vector other) {
        double w1 = this._head.get_y().get() * other._head.get_z().get() - this._head.get_z().get() * other.get_head().get_y().get();
        double w2 = this._head.get_z().get() * other._head.get_x().get() - this._head.get_x().get() * other.get_head().get_z().get();
        double w3 = this._head.get_x().get() * other._head.get_y().get() - this._head.get_y().get() * other.get_head().get_x().get();

        return new Vector(new Point3D(w1, w2, w3));
    }

    /**
     * the function gets a vector and checks if it parallel to this vector
     * @param other the vector to parallelism test
     * @return true if the vectors parallel
     */
    public boolean isParallel(Vector other) {
        if(this.crossProduct(other).get_head().equals(Point3D.ZERO)) {
            return true;
        }
        return false;
    }

    /**
     * the function calculate the length of this vector
     * @return this vector length squared
     */
    public double lengthSquared() {
        return this._head.get_x().get()*this._head.get_x().get() +
                this._head.get_y().get()*this._head.get_y().get() +
                this._head.get_z().get()*this._head.get_z().get();
    }

    /**
     * the function calculate the length of this vector
     * @return this vector length
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * the function calculate the length of this vector, and
     * normalizes it
     * @return this vector after the normalization
     */
    public Vector normalize() {
        if (this.length() == 0)
            throw new ArithmeticException("divide by Zero");

        this._head = this.scale(1/this.length())._head;
        return this;
    }

    /**
     * the function calculate the length of this vector, and
     * create a new vector - this normalizes vector
     * @return new vector equals to this vector after the normalization
     */
    public Vector normalized() {
        Vector other = new Vector(this);
        other.normalize();
        return other;
    }
}

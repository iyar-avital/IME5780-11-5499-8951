package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * the interface that all the geometries implement
 */
public interface Intersectable {
    /**
     *
     * @param ray the ray to find Intersections between it and geometry
     * @return all the Intersections between the ray and geometry
     */
    List<GeoPoint> findIntersections(Ray ray);

    /**
     *
     */
    public static class GeoPoint {
        private Geometry geometry;
        private Point3D point;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(getGeometry(), geoPoint.getGeometry()) &&
                    Objects.equals(getPoint(), geoPoint.getPoint());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getGeometry(), getPoint());
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public Point3D getPoint() {
            return new Point3D(point);
        }

        public GeoPoint(Geometry g, Point3D p)
        {
          geometry = g;
          point = p;
        }
    }
}

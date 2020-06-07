package renderer;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.List;
import java.util.Scanner;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Render {
    /**
     * a const that we raise the ray with it, in order to tha ray dosent cut itself
     */
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    ImageWriter _imageWriter;
    Scene _scene;

    public Render(Scene _scene) {
        this._scene = _scene;
    }

    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
    }

    public Scene get_scene() {
        return _scene;
    }

    /**
     * Filling the buffer according to the geometries that are in the scene.
     * This function does not creating the picture file, but create the buffer pf pixels
     */
    public void renderImage() {
        java.awt.Color background = _scene.get_background().getColor();
        Camera camera = _scene.get_camera();
        Intersectable geometries = _scene.get_geometries();
        double distance = _scene.get_distance();

        //width and height are the number of pixels in the rows
        //and columns of the view plane
        int width = (int) _imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();

        //Nx and Ny are the width and height of the image.
        int Nx = _imageWriter.getNx(); //columns
        int Ny = _imageWriter.getNy(); //rows
        //pixels grid
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                Ray ray = camera.constructRayThroughPixel(Nx, Ny, j, i, distance, width, height);
                List<Intersectable.GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                GeoPoint closestPoint = findCLosestIntersection(ray);
                _imageWriter.writePixel(j, i, closestPoint == null ? _scene.get_background().getColor():
                calcColor(closestPoint, ray).getColor());
            }
        }
    }

    /**
     * Printing the grid with a fixed interval between lines
     *
     * @param interval The interval between the lines.
     * @param colorsep color
     */
    public void printGrid(int interval, java.awt.Color colorsep) {
        double rows = this._imageWriter.getNy();
        double collumns = _imageWriter.getNx();
        //Writing the lines.
        for (int row = 0; row < rows; ++row) {
            for (int collumn = 0; collumn < collumns; ++collumn) {
                if (collumn % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(collumn, row, colorsep);
                }
            }
        }
    }

    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * Finding the closest point to the P0 of the camera.
     *
     * @param intersectionPoints list of points, the function should find from
     *                           this list the closet point to P0 of the camera in the scene.
     * @return the closest point to the camera
     */
    private Intersectable.GeoPoint getClosestPoint(List<Intersectable.GeoPoint> intersectionPoints) {
        Intersectable.GeoPoint result = null;
        double minDistance = Double.MAX_VALUE;

        Point3D p0 = this._scene.get_camera().getP0();

        for (Intersectable.GeoPoint geo : intersectionPoints) {
            Intersectable.GeoPoint pt = geo;
            double distance = p0.distance(pt.getPoint());
            if (distance < minDistance) {
                minDistance = distance;
                result = geo;
            }
        }
        return result;
    }

    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.get_ambientLight().get_intensity());
    }

    /**
     * Calculate the color intensity in a point
     *
     * @param intersection intersection the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(GeoPoint intersection, Ray inRay, int level, double k) {
        if (level == 0 || k < MIN_CALC_COLOR_K) return Color.BLACK;
        Color color = intersection.getGeometry().get_emission(); // remove Ambient Light
        Vector v = intersection.getPoint().subtract(_scene.get_camera().getP0()).normalize();
        Vector n = intersection.getGeometry().getNormal(intersection.getPoint());
        Material material = intersection.getGeometry().get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        for (LightSource lightSource : _scene.get_lights()) {
            Vector l = lightSource.getL(intersection.getPoint());
            double nl = alignZero(n.dotProduct(l));
            double nv = alignZero(n.dotProduct(v));
            if (sign(nl) == sign(nv)) {
                if (unshaded(lightSource, l, n, intersection)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.getPoint());
                    color = color.add(calcDiffusive(kd, nl, lightIntensity),
                            calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                }
            }
        }
        if (level == 1) return Color.BLACK;
        double kr = intersection.getGeometry().get_material().get_kR(), kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(intersection.getPoint(), inRay, n);
            GeoPoint reflectedPoint = findCLosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));
            double kt = intersection.getGeometry().get_material().get_kT(), kkt = k * kt;
            if (kkt > MIN_CALC_COLOR_K) {
                Ray refractedRay = constructRefractedRay(intersection.getPoint(), inRay,n) ;
                GeoPoint refractedPoint = findCLosestIntersection(refractedRay);
                if (refractedPoint != null)
                    color = color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
            }
        }
        return color;
    }

    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd diffusive component coef
     * @param nl dot-product n*l
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * <p>
     * The diffuse component is that dot product n•L. It approximates light, originally from light source L,
     * reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossysurface is paper.
     * In general, you'll also want this to have a non-gray color value,
     * so this term would in general be a color defined as: [rd,gd,bd](n•L)
     * </p>
     */
    private Color calcDiffusive(double kd, double nl, Color ip) {
        return ip.scale(Math.abs(nl) * kd);
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param nl         dot-product n*l
     * @param V          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point
     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     * </p>
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector V, int nShininess, Color ip) {
        double p = nShininess;
        if (isZero(nl)) {
            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
        }
        Vector R = l.add(n.scale(-2 * nl)).normalize(); // nl must not be zero!
        double VR = alignZero(V.dotProduct(R));
        if (VR >= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        // [rs,gs,bs]ks(-V.R)^p
        return ip.scale(ks * Math.pow(-1d * VR, p));
    }

    /**
     * checks if something is in between the intersect point and the source light
     *
     * @param l  the vector from intersect point to light source
     * @param n  the normal
     * @param gp intersect point
     * @return
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1);
        Ray lightRay = new Ray(gp.getPoint(), lightDirection, n);
        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(gp.getPoint());
        for (GeoPoint intersect : intersections) {
            if (alignZero(intersect.getPoint().distance(gp.getPoint()) - lightDistance) <= 0 &&
                    gp.getGeometry().get_material().get_kT() == 0)
                return false;
        }
        return true;
    }

    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo, inRay.get_direction(), n);
    }

    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n) {
        //𝒓=𝒗 −𝟐∙(𝒗∙𝒏)∙𝒏
        Vector v = inRay.get_direction();
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }

    private GeoPoint findCLosestIntersection(Ray ray){
        if(ray == null)
            return null;
        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(ray);
        if (intersections == null)
            return null;
        return getClosestPoint(intersections);

    }
}

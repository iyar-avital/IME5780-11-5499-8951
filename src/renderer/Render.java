package renderer;
import com.sun.jdi.connect.spi.TransportService;
import elements.LightSource;
import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Render {

    public enum whatToRun { regular, nimiProject1, miniProject2 };
    private whatToRun whoNow = whatToRun.regular;
    /**
     * numOfRaysForSuperSampling - number of rays for super sampling
     */
    private int maxRaysForSuperSampling = 81;

    /**
     * numOfRays - setNumOfRays
     * set the number of rays in each pixel
     */
    public void setMaxRaysForSuperSampling(int maxRaysForSuperSampling) {
        this.maxRaysForSuperSampling = maxRaysForSuperSampling;
    }

    /**
     * a const that we raise the ray with it, in order to tha ray dosent cut itself
     */
    private static final double DELTA = 0.1;

    /**
     * MAX_CALC_COLOR_LEVEL - maximum level in the recursion three
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * MIN_CALC_COLOR_K - stopping calculate color at this value of k
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    private ImageWriter _imageWriter;
    private Scene _scene;

    /**
     * constructor received scene
     * @param _scene the scene
     */
    public Render(Scene _scene) {
        this._scene = _scene;
    }

    /**
     * constructor received scene and imageWriter
     * @param imageWriter the imageWriter
     * @param scene the scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this(imageWriter, scene, whatToRun.miniProject2);
    }

    /**
     *
     * @param imageWriter the imagewriter
     * @param scene the scene
     * @param w who run now (regular/ miniproject1/ mini project2)
     */
    public Render(ImageWriter imageWriter, Scene scene, whatToRun w) {
       this(imageWriter, scene, w, 81);
    }

    /**
     *
     * @param imageWriter the imagewriter
     * @param scene the scene
     * @param numOfPixel the max number of pixel
     */
    public Render(ImageWriter imageWriter, Scene scene, int numOfPixel) {
        this(imageWriter, scene, whatToRun.miniProject2, numOfPixel);
    }

    /**
     *
     * @param imageWriter the imagewriter
     * @param scene the scene
     * @param w who run now (regular/ miniproject1/ mini project2)
     * @param numOfRays the max number of pixel
     */
    public Render(ImageWriter imageWriter, Scene scene, whatToRun w, int numOfRays) {
        this._imageWriter = imageWriter;
        this._scene = scene;
        this.whoNow = w;
        if (numOfRays > 500)
            this.maxRaysForSuperSampling = 81;
        else
            this.maxRaysForSuperSampling = numOfRays;
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
        final Pixel thePixel = new Pixel(Ny, Nx); // Main pixel management object
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) { // create all threads
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel(); // Auxiliary thread’s pixel object
                while (thePixel.nextPixel(pixel)) {
                    switch (whoNow)
                    {
                        case regular:
                            Ray rays = camera.constructRayThroughPixel(Nx, Ny, pixel.col, pixel.row, distance, width, height);
                            GeoPoint closestPoint = findCLosestIntersection(rays);
                            _imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background : calcColor(closestPoint, rays).getColor()); break;
                        case nimiProject1:
                            List<Ray> ray = camera.constructRayThroughPixelMINI1(Nx, Ny, pixel.col, pixel.row, distance, width, height, (int)Math.sqrt(maxRaysForSuperSampling));
                            _imageWriter.writePixel(pixel.col, pixel.row, calcColorMINI1(ray).getColor()); break;
                        case miniProject2:
                            try {
                                Color adaptiveColor = AdaptiveSuperSampling(Nx, Ny, pixel.col, pixel.row, distance, width, height, maxRaysForSuperSampling);
                                _imageWriter.writePixel(pixel.col, pixel.row, adaptiveColor.getColor()); break;
                            } catch (Exception e) {}
                        default: break;
                    }
                }});
        }
        for (Thread thread : threads) thread.start(); // Start all the threads
        // Wait for all threads to finish
        for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
        if (_print) System.out.printf("\r100%%\n"); // Print 100%
    }

    private synchronized void printMessage(String msg)
    {
        synchronized (System.out) {
            System.out.print(msg);
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
        if (intersectionPoints == null) {
            return null;
        }

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

    private GeoPoint findCLosestIntersection(Ray ray){
        if (ray == null) {
            return null;
        }
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.get_poo();

        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.getPoint());
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = geoPoint;
            }
        }
        return closestPoint;
    }

    /**
     *
     * @param rays list of ray to find closes intersection from this rays
     * @return the average color from all the get closes points color
     */
    private Color calcColorMINI1(List<Ray> rays)
    {
        Color x = Color.BLACK;
        for(Ray r:rays){
            GeoPoint p = findCLosestIntersection(r);
            if(p == null)
                x = x.add(_scene.get_background());
            else
                x = x.add(calcColor(p,r));
        }
        x = x.reduce(rays.size());
        return x;
    }

    /**
     * Calculate the color intensity in a point
     *
     * @param geopoint intersection the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.get_ambientLight().get_intensity());
    }

    /**
     * Calculate the color intensity in a point
     * @param intersection the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(GeoPoint intersection, Ray inRay, int level, double k) {
        if (level == 0 || k < MIN_CALC_COLOR_K) return Color.BLACK;
        if (level == 1) return Color.BLACK;
        Color color = intersection.getGeometry().get_emission(); // remove Ambient Light
        //וקטור מהמצלמה אל נקודת החיתוך
        Vector v = intersection.getPoint().subtract(_scene.get_camera().getP0()).normalize();
        //הנורמל לנקודת החיתוך
        Vector n = intersection.getGeometry().getNormal(intersection.getPoint());
        Material material = intersection.getGeometry().get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        for (LightSource lightSource : _scene.get_lights()) {
            //וקטור ממקור התאורה אל נק החיתוך
            Vector l = lightSource.getL(intersection.getPoint());
            double nl = alignZero(n.dotProduct(l));
            double nv = alignZero(n.dotProduct(v));
            if (sign(nl) == sign(nv)) {
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.getPoint()).scale(ktr);
                    color = color.add(calcDiffusive(kd, nl, lightIntensity),
                            calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                }
            }
        }

        double kr = intersection.getGeometry().get_material().get_kR(), kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(intersection.getPoint(), inRay, n);
            GeoPoint reflectedPoint = findCLosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }
        double kt = intersection.getGeometry().get_material().get_kT(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
                Ray refractedRay = constructRefractedRay(intersection.getPoint(), inRay,n) ;
                GeoPoint refractedPoint = findCLosestIntersection(refractedRay);
                if (refractedPoint != null)
                    color = color.add(calcColor(refractedPoint, refractedRay, level-1, kkt).scale(kt));
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
     * @param ls the light source
     * @param geopoint the intersected point
     * @return the value of the trancperacy
     */


    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geopoint)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n);
        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(lightRay);
        if (intersections == null) return 1.0;
        double lightDistance = ls.getDistance(geopoint.getPoint());
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.getPoint().distance(geopoint.getPoint()) - lightDistance) <= 0) {
                ktr *= gp.getGeometry().get_material().get_kT();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
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

    private int _threads = 1;
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean _print = false; // printing progress percentage
    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less SPARE (2) is taken
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0) throw new IllegalArgumentException("Multithreading must be 0 or higher");
        if (threads != 0) _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            _threads = cores <= 2 ? 1 : cores;
        }
        return this;
    }
    /**
     * Set debug printing on
     * @return the Render object itself
     */
    public Render setDebugPrint() { _print = true; return this; }
    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     */
    private class Pixel {
        private long _maxRows = 0; // Ny
        private long _maxCols = 0; // Nx
        private long _pixels = 0; // Total number of pixels: Nx*Ny
        public volatile int row = 0; // Last processed row (j)
        public volatile int col = -1; // Last processed column (i)
        private long _counter = 0; // Total number of pixels processed
        private int _percents = 0; // Percent of pixels processed
        private long _nextCounter = 0; // Next amount of processed pixels for percent progress

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (_print && percents > 0) System.out.printf("\r %02d%%", percents);
            if (percents >= 0) return true;
            if (_print) System.out.printf("\r %02d%%", 100);
            return false;
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_print && _counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_print && _counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }
    }

    /**
     *
     * @param nX number of pixel in axis x
     * @param nY number of pixel in axis y
     * @param j location of current pixel in the row
     * @param i location of current pixel in the column
     * @param screenDistance the distance between the camera and view plane
     * @param screenWidth the width plane in cm
     * @param screenHeight the height plane in cm
     * @param numOfRays max rays we can create from one pixel
     * @return the pixel color
     * @throws Exception
     */
    public primitives.Color AdaptiveSuperSampling(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight, int numOfRays) throws Exception {
        Camera camera = _scene.get_camera();
        Vector Vright = camera.getV_right();
        Vector Vup = camera.getV_up();
        //start point in camera
        Point3D cameraLoc = camera.getP0();
        int numOfRaysInRowCol = (int)Math.ceil(Math.sqrt(numOfRays));
        //if the max ray == 1, call to constructRayThroughPixel that received one ray, and send this ray to calcolor
        if(numOfRaysInRowCol == 1)
            return calcColor(camera.constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth,screenHeight));
        Point3D Pc;
        //we want point of center pixel in the view plane
        if (screenDistance != 0)
            Pc = cameraLoc.add(camera.getV_to().scale(screenDistance));
        else
            Pc = cameraLoc;
        Point3D Pij = Pc;
        //the length of the height for ont pixel
        double Ry = screenHeight/nY;
        //the length of the width for ont pixel
        double Rx = screenWidth/nX;

        double Yi= (i - (nY/2d))*Ry + Ry/2d;
        double Xj= (j - (nX/2d))*Rx + Rx/2d;

        if(Xj != 0) Pij = Pij.add(Vright.scale(-Xj)) ;
        if(Yi != 0) Pij = Pij.add(Vup.scale(-Yi));

        double minRy = Ry/numOfRaysInRowCol;
        double minRx = Rx/numOfRaysInRowCol;
        return AdaptiveSuperSamplingRec(Pij, Rx, Ry, minRx, minRy,cameraLoc,Vright, Vup);
    }

    /**
     *
     * @param centerP the point in (j,i), the center point in the pixel
     * @param Width the width of pixel
     * @param Height the height of pixel
     * @param minWidth the min pixel width, when width < min width we need to stop the recursion
     * @param minHeight the min pixel height, when width < min height we need to stop the recursion
     * @param cameraLoc the camera point
     * @param Vright vector v- right
     * @param Vup vector v- up
     * @return the "tat" pixel color
     * @throws Exception
     */
    private primitives.Color AdaptiveSuperSamplingRec(Point3D centerP, double Width, double Height, double minWidth, double minHeight, Point3D cameraLoc,Vector Vright,Vector Vup) throws Exception {
        //4 point "קצוות" of the pixel quarters
        Point3D corner1 = centerP.add(Vright.scale(Width / 2)).add(Vup.scale(-Height / 2)),
                corner2 = centerP.add(Vright.scale(Width / 2)).add(Vup.scale(Height / 2)),
                corner3 = centerP.add(Vright.scale(-Width / 2)).add(Vup.scale(-Height / 2)),
                corner4 = centerP.add(Vright.scale(-Width / 2)).add(Vup.scale(Height / 2));

        //create a ray from the camera point, the direction is the vector from the center of quarter to camera point
        Ray     ray1 = new Ray(cameraLoc, corner1.subtract(cameraLoc)),
                ray2 = new Ray(cameraLoc, corner2.subtract(cameraLoc)),
                ray3 = new Ray(cameraLoc, corner3.subtract(cameraLoc)),
                ray4 = new Ray(cameraLoc, corner4.subtract(cameraLoc));

        //calculate the color of all the ray
        primitives.Color color1 = calcColor(ray1),
                color2 = calcColor(ray2),
                color3 = calcColor(ray3),
                color4 = calcColor(ray4);

        //checks the Recursion stop conditions, if the recursion is to be stopped we will return an average of the colors
        if(Width <= minWidth || Height <= minHeight)
            return color1.add(color2, color3, color4).reduce(4);

        //Checks if the 4 rays on the edge have a same color. if the answer is true- we will return an average of the colors
        //Why don't we return one of the colors? that is our secret
        if (color1.same(color2)&& color1.same(color3)&& color1.same(color4))
            return color1.add(color2).add(color3).add(color4).reduce(4);

        //if the color of rays are not same- we calculate the center quarters point
        Point3D centerP1 = centerP.add(Vright.scale(Width / 4)).add(Vup.scale(-Height / 4)),
                centerP2 = centerP.add(Vright.scale(Width / 4)).add(Vup.scale(Height / 4)),
                centerP3 = centerP.add(Vright.scale(-Width / 4)).add(Vup.scale(-Height / 4)),
                centerP4 = centerP.add(Vright.scale(-Width / 4)).add(Vup.scale(Height / 4));

        return AdaptiveSuperSamplingRec(centerP1, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup).add(
                AdaptiveSuperSamplingRec(centerP2, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup),
                AdaptiveSuperSamplingRec(centerP3, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup),
                AdaptiveSuperSamplingRec(centerP4, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup)
        ).reduce(4);

    }

    private primitives.Color calcColor(Ray ray) throws Exception {
        GeoPoint gp;
        gp = findCLosestIntersection(ray);
        if(gp == null)
            return this._scene.get_background();
        else
            return calcColor(gp, ray);
    }


}

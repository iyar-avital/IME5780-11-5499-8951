package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import java.util.Scanner;

public class Render {
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
                List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null)
                   _imageWriter.writePixel(j, i, background);
                else {
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
                }
            }
        }
    }

    /**
     * Printing the grid with a fixed interval between lines
     *
     * @param interval The interval between the lines.
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
    private Point3D getClosestPoint(List<Point3D> intersectionPoints) {
        Point3D result = null;
        double minDistance = Double.MAX_VALUE;

        Point3D p0 = this._scene.get_camera().getP0();

        for (Point3D geo : intersectionPoints) {
            Point3D pt = geo;
            double distance = p0.distance(pt);
            if (distance < minDistance) {
                minDistance = distance;
                result = geo;
            }
        }
        return result;
    }

    /**
     * Calculate the color intensity in a point
     *
     * @param point intersection the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(Point3D point) {
        return _scene.get_ambientLight().get_intensity();
    }
}

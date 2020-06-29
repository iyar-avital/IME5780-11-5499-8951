package elements;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * camera class
 */
public class Camera {
    /**
     * the camera starting point
     */
    private Point3D _p0;
    /**
     * vector v_To, in the Z axis direction
     */
    private Vector _vTo;
    /**
     * vector v_Up, in the Y axis direction
     */
    private Vector _vUp;
    /**
     * vector v_Right, in the X axis direction
     */
    private Vector _vRight;

    /**
     * return p0
     * @return Point3D p0 - the camera starting point
     */
    public Point3D getP0() {
        return new Point3D(_p0);
    }

    /**
     * return v To
     * @return Vector vTo
     */
    public Vector getV_to() {
        return new Vector(_vTo);
    }

    /**
     * return v Up
     * @return Vector vUp
     */
    public Vector getV_up() {
        return new Vector(_vUp);
    }

    /**
     * return v Right
     * @return Vector vRight
     */
    public Vector getV_right() {
        return new Vector(_vRight);
    }

    /**
     * camera constructor- received p0, vto, vup
     * calculate vright - the cross product between vto and vup
     * @param p0 the camera starting point
     * @param vTo one vector
     * @param vUp the second vector
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp)
    {
        if(vTo.dotProduct(vUp) != 0)
            throw new IllegalArgumentException("the vector must be vertical");

        _p0 = new Point3D(p0);
        _vUp = vUp.normalized();
        _vTo = vTo.normalized();
        _vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * calculate the ray from the camera starting point to current pixel (j,i)
     * @param nX number of pixels in x axis
     * @param nY number of pixels in y axis
     * @param j location of current pixel in the row
     * @param i location of current pixel in the column
     * @param screenDistance the distance between the camera and view plane
     * @param screenWidth the width plane in cm
     * @param screenHeight the height plane in cm
     * @return the ray from the starting point to current pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY,
                                        double j, double i, double screenDistance,
                                        double screenWidth, double screenHeight)
    {
        Point3D Pc = _p0.add(_vTo.scale(screenDistance));

        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;

        double yi = ((i-nY/2.0) * Ry + Ry/2);
        double xj = (j-nX/2.0) * Rx + Rx/2;

        Point3D pIJ = new Point3D(Pc);

        if (xj != 0) {
            pIJ = pIJ.add(_vRight.scale(xj));
        }

        if (yi != 0) {
            pIJ = pIJ.add(_vUp.scale(-yi));
        }
        Vector vIJ = pIJ.subtract(_p0);

        return new Ray(_p0, vIJ);
    }


    /**
     * calculate all the rays from the camera starting point to current pixel (j,i)
     * in (i,j) we "create" a grid 9X9 and calculate all the 81 rays from this one pixel!
     * the difference between this function to constructRayThroughPixel:
     * in this function we have 2 iterators that create the grid,
     * build a ray for all 1/9*1/9 pixel, and return all the rays.
     * @param nX number of pixels in x axis
     * @param nY number of pixels in y axis
     * @param j location of current pixel in the row
     * @param i location of current pixel in the column
     * @param screenDistance the distance between the camera and view plane
     * @param screenWidth the width plane in cm
     * @param screenHeight the height plane in cm
     * @return list of ray from the camera to one pixel
     *
     */
    public List<Ray> constructRayThroughPixelMINI1(int nX, int nY,
                                              int j, int i, double screenDistance,
                                              double screenWidth, double screenHeight, int numOfRay)
    {
        Point3D Pc = _p0.add(_vTo.scale(screenDistance));
        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;

        double yi;
        double xj;

        Vector vIJ;
        Ray myRay;
        List<Ray> allTheRays = new ArrayList<>();

        for(double rows = j; rows < j + 1; rows = rows + (1d/numOfRay)) {
            for (double columns = i; columns < i + 1; columns = columns + (1d/numOfRay)) {
                Point3D pIJ = new Point3D(Pc);

                yi = ((columns - nY / 2.0) * Ry + Ry / 2);
                xj = (rows - nX / 2.0) * Rx + Rx / 2;


                if (!isZero(xj)) {
                    pIJ = pIJ.add(_vRight.scale(xj));
                }

                if (!isZero(yi)) {
                    pIJ = pIJ.add(_vUp.scale(-yi));
                }
                vIJ = pIJ.subtract(_p0);
                myRay = new Ray(_p0, vIJ);
                allTheRays.add(myRay);
            }
        }
        return allTheRays;
    }
}

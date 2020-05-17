package unittests.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

public class TubeTest extends Object {

    @Test
    public void getNormal() {
        Ray r = new Ray(new Vector(0,0,1), new Point3D(0,0,0));
        Tube t = new Tube(1,r);
        Point3D p = new Point3D(1,0,1);
        Vector n = t.getNormal(p);
        assertTrue("bad normal to tube",isZero(r.get_direction().dotProduct(n)));
    }
}
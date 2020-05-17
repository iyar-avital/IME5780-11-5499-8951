package unittests.primitives;

import org.junit.Test;
import primitives.Util;

import static org.junit.Assert.*;

public class UtilTest extends Object {

    @Test
    public void isZero() {
        assertTrue(Util.isZero(0.00000000000006));
    }

    @Test
    public void alignZero() {
        assertEquals(Util.alignZero(0.0000000000000000003), 0.0, 1e-10);
        assertEquals(Util.alignZero(25), 25, 1e-10);
    }
}
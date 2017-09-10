import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Tetiana_Prynda
 *         Created on 9/6/2017.
 */
public class PointSETTest {
    @Test
    public void testBasicMethods() throws Exception {
        PointSET set = new PointSET();
        assertTrue(set.isEmpty());
        assertEquals(0, set.size());
        assertNotNull(set.range(new RectHV(0, 0, 1, 1)));
        final Point2D zeroPoint = new Point2D(0, 0);
        assertNull(set.nearest(zeroPoint));
        final Point2D p = new Point2D(0.5, 0.5);
        set.insert(p);
        assertFalse(set.isEmpty());
        assertEquals(1, set.size());
        assertEquals(p, set.nearest(zeroPoint));
    }
}
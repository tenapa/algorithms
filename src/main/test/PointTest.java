import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by tetianaprynda on 26.08.17.
 */
public class PointTest {
    private static final double PRECISION = 0.000001;

    @Test
    public void pointTest_1(){
        Point p = new Point(355, 436);
        Point q = new Point(160, 427);
        Point r = new Point(290, 433);
        assertEquals(0, p.slopeOrder().compare(q, r));
        assertEquals(0.046153846153846156, p.slopeTo(q), PRECISION);
        assertEquals(0.046153846153846156, p.slopeTo(r), PRECISION);
    }

    @Test
    public void pointTest_2(){
        Point p = new Point(5, 4);
        Point q = new Point(5, 8);
        Point r = new Point(5, 9);
        assertEquals(0, p.slopeOrder().compare(q, r));
        assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(q), PRECISION);
        assertEquals(Double.POSITIVE_INFINITY, p.slopeTo(r), PRECISION);
    }

    @Test
    public void pointTest_4(){

        Point p = new Point(3, 6);
        Point q = new Point(2, 6);
        Point r = new Point(3, 6);
        assertEquals(1, p.slopeOrder().compare(q, r));
        assertEquals(0.0, p.slopeTo(q), PRECISION);
        assertEquals(Double.NEGATIVE_INFINITY, p.slopeTo(r), PRECISION);
    }

//    @Test
    public void pointTest_3(){
        Point p = new Point(488, 13);
        Point q = new Point(434, 1);
        Point r = new Point(488, 35);
        Point s = new Point(161, 167);
        assertEquals(-1, p.slopeOrder().compare(q, r));
        assertEquals(-1, p.slopeOrder().compare(r, s));
        assertEquals(-1, p.slopeOrder().compare(q, s));
    }
}

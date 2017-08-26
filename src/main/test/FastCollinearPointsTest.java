import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by tetianaprynda on 26.08.17.
 */
public class FastCollinearPointsTest {
    @Test
    public void fastCollinearTest_input8() {
        String file = "collinear/input8.txt";
        LineSegment[] actual = fileRunner(file);
        LineSegment[] expected = new LineSegment[2];
        expected[0] = new LineSegment(new Point(10000, 0), new Point(0, 10000));
        expected[1] = new LineSegment(new Point(3000, 4000), new Point(20000, 21000));

        assertSegments(actual, expected);
    }

    @Test
    public void fastCollinearTest_input9() {
        String file = "collinear/input9.txt";
        LineSegment[] actual = fileRunner(file);
        LineSegment[] expected = new LineSegment[1];
        expected[0] = new LineSegment(new Point(1000, 1000), new Point(9000, 9000));

//        assertSegments(actual, expected);
    }

    @Test
    public void fastCollinearTest_random23() {
        String file = "collinear/random23.txt";
        LineSegment[] actual = fileRunner(file);
        LineSegment[] expected = new LineSegment[0];

        assertArrayEquals(expected, actual);
    }


    @Test
    public void fastCollinearTest_input1() {
        String file = "collinear/input1.txt";
        LineSegment[] actual = fileRunner(file);
        LineSegment[] expected = new LineSegment[0];

        assertArrayEquals(expected, actual);
    }

    @Test
    public void fastCollinearTest_equidistant() {
        String file = "collinear/equidistant.txt";
        LineSegment[] actual = fileRunner(file);
        LineSegment[] expected = new LineSegment[4];
        expected[0] = new LineSegment(new Point(10000, 0), new Point(0, 10000));
        expected[1] = new LineSegment(new Point(10000, 0), new Point(30000, 0));
        expected[2] = new LineSegment(new Point(13000, 0), new Point(5000, 12000));
        expected[3] = new LineSegment(new Point(30000, 0), new Point(0, 30000));

        assertSegments(actual, expected);
    }


    @Test
    public void fastCollinearTest_horizontal() {
        Point[] points = new Point[]{new Point(8595, 4836), new Point(12989, 4836), new Point(4390, 4836),
                new Point(14181, 4836)};
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        LineSegment[] actual = collinear.segments();
        LineSegment[] expected = new LineSegment[]{new LineSegment(new Point(4390, 4836), new Point(14181, 4836))};

        assertSegments(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fastCollinearTest_samePoints() {
        Point[] points = new Point[]{new Point(10066, 26925), new Point(7070, 914), new Point(9901, 21632),
                new Point(26167, 7105), new Point(26167, 7105)};
        FastCollinearPoints collinear = new FastCollinearPoints(points);

    }

    @Test(expected = IllegalArgumentException.class)
    public void fastCollinearTest_nullPoint() {
        Point[] points = new Point[]{new Point(8595, 4836), null, new Point(14181, 4836)};
        FastCollinearPoints collinear = new FastCollinearPoints(points);
    }

    private void assertSegments(LineSegment[] actual, LineSegment[] expected) {
        assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    private LineSegment[] fileRunner(String file) {
        In in = new In(file);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        System.out.println(Arrays.toString(points));

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        return collinear.segments();
    }
}

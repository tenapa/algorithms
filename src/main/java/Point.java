import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * @author Tetiana_Prynda
 * Created on 8/25/2017.
 */
public class Point implements Comparable<Point> {
    private static final double PRECISION = 0.00001;

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
//        Point p1 = new Point(0, 0);
//        Point p2 = new Point(0, 1);
//        Point p3 = new Point(1, 0);
//        Point p4 = new Point(1, 1);
//        final Point[] points = {p1, p2, p3, p4};
//        System.out.println(Arrays.toString(points));
//        Arrays.sort(points, p1.slopeOrder());
//        System.out.println(Arrays.toString(points));
//        System.out.println(p1 + " " + p2);
//        System.out.println(p1.compareTo(p2));
//        System.out.println(p1.slopeTo(p2));
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (x == that.x && y == that.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else if (y == that.y) {
            return +0.0;
        } else {
            return (double) (that.y - y) / (that.x - x);
        }
        /* YOUR CODE HERE */
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (that.y == y) {
            return compare(x, that.x);
        } else {
            return compare(y, that.y);
        }
    }

    private int compare(int val1, int val2) {
        if (val1 == val2) return 0;
        return val1 < val2 ? -1 : 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                final double slope1 = Point.this.slopeTo(o1);
                final double slope2 = Point.this.slopeTo(o2);
                if (slope2 > slope1 && slope2 - slope1 > PRECISION) return -1;
                if (slope2 < slope1 && slope1 - slope2 > PRECISION) return 1;
                return 0;
            }
        };
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
}
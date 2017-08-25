import java.util.Arrays;

/**
 * @author Tetiana_Prynda
 * Created on 8/25/2017.
 */
public class FastCollinearPoints {
    private static final double PRECISION = 0.00001;
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkNull(points, "Points can not be null");
        LineSegment[] seg = new LineSegment[2];
        int nextIndex = 0;
        for (int i = 0; i < points.length; i++) {
            final Point p = points[i];
            checkNull(p, "Point can not ne null");
            final Point[] subset = Arrays.copyOfRange(points, i + 1, points.length);
            if (subset.length < 3) break;
            Arrays.sort(subset, p.slopeOrder());
            Point q = subset[0];
            double slope = p.slopeTo(q);
            int count = 1;
            for (int j = 0; j < subset.length; j++) {
                q = subset[j];
                checkSame(p, q);
                final double currentSlope = p.slopeTo(q);
                if (currentSlope > slope ? currentSlope - slope < PRECISION : slope - currentSlope < PRECISION) {
                    count++;
                } else {
                    if (count >= 4) {
                        Point[] inSegment = getPointsInSegment(p, subset, count, j);
                        final LineSegment lineSegment = makeSegment(inSegment);
                        addSegment(seg, nextIndex++, lineSegment);
                    }
                    slope = currentSlope;
                    count = 2;
                }
            }
            // treat last segment
            if (count >= 4) {
                final LineSegment lineSegment = makeSegment(getPointsInSegment(p, subset, count, subset.length));
                addSegment(seg, nextIndex++, lineSegment);
            }
        }
        this.segments = Arrays.copyOf(seg, nextIndex);
    }

    public static void main(String[] args) {
        Point[] points = new Point[]{new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3),
                new Point(2, 3), new Point(4, 3), new Point(1, 3)};
        final FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
        System.out.println(Arrays.toString(collinearPoints.segments()));
    }

    private Point[] getPointsInSegment(Point p, Point[] subset, int count, int j) {
        Point[] inSegment = new Point[count];
        inSegment[0] = p;
        for (int k = 1; k < count; k++) {
            inSegment[k] = subset[j - count + k];
        }
        return inSegment;
    }

    private LineSegment makeSegment(Point[] points) {
        Point min = null, max = null;
        for (Point point : points) {
            if ((min != null ? min.compareTo(point) : 1) > 0) {
                min = point;
            }
            if ((max != null ? max.compareTo(point) : -1) < 0) {
                max = point;
            }
        }
        return new LineSegment(min, max);
    }

    private void addSegment(LineSegment[] segments, int nextIndex, LineSegment lineSegment) {
        segments = Arrays.copyOf(segments, segments.length * 2);
        segments[nextIndex] = lineSegment;
    }

    private void checkSame(Point p, Point r) {
        if (p.compareTo(r) == 0) throw new IllegalArgumentException("Points cannot be same");
    }

    private void checkNull(Object object, String message) {
        if (object == null) throw new IllegalArgumentException(message);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }
}

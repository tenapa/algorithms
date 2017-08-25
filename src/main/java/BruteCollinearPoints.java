import java.util.Arrays;

/**
 * @author Tetiana_Prynda
 * Created on 8/25/2017.
 */
public class BruteCollinearPoints {
    private static final double PRECISION = 0.00001;
    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkNull(points, "Points should be provided");
        LineSegment[] seg = new LineSegment[2];
        int count = 0;
        for (int pi = 0; pi < points.length; pi++) {
            for (int qi = pi + 1; qi < points.length; qi++) {
                for (int ri = qi + 1; ri < points.length; ri++) {
                    for (int si = ri + 1; si < points.length; si++) {
                        Point p = points[pi];
                        checkNull(p, "Point can not be null");
                        Point q = points[qi];
                        checkNull(q, "Point can not be null");
                        Point r = points[ri];
                        checkNull(r, "Point can not be null");
                        Point s = points[si];
                        checkNull(s, "Point can not be null");

                        checkSame(p, q);
                        checkSame(p, r);
                        checkSame(p, s);

                        final double pq = p.slopeTo(q);
                        final double pr = p.slopeTo(r);
                        final double ps = p.slopeTo(s);

                        if ((pq > pr ? pq - pr < PRECISION : pr - pq < PRECISION)
                                && (pq > ps ? pq - ps < PRECISION : ps - pq < PRECISION)) {
                            // points are collinear, get bottom and top point
                            Point[] collinearPoints = {p, q, r, s};
                            Arrays.sort(collinearPoints, p.slopeOrder());
                            final LineSegment lineSegment = new LineSegment(collinearPoints[0], collinearPoints[3]);
                            addSegment(seg, count++, lineSegment);
                        }
                    }
                }
            }
        }
        this.segments = Arrays.copyOf(seg, count);
    }

    public static void main(String[] args) {
        Point[] points = new Point[]{new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3),
                new Point(2, 3), new Point(4, 3), new Point(1, 3)};
        final BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
        System.out.println(Arrays.toString(collinearPoints.segments()));
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

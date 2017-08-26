import java.util.Arrays;

/**
 * @author Tetiana_Prynda
 *         Created on 8/25/2017.
 */
public class BruteCollinearPoints {
    private final LineSegment[] segments;
    private LineSegment[] segmentsTmp;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkNull(points, "Points should be provided");
        segmentsTmp = new LineSegment[2];
        int count = 0;
        for (int pi = 0; pi < points.length; pi++) {
            Point p = points[pi];
            checkNull(p, "Point can not be null");
            for (int qi = pi + 1; qi < points.length; qi++) {
                Point q = points[qi];
                checkNull(q, "Point can not be null");
                checkSame(p, q);
                for (int ri = qi + 1; ri < points.length; ri++) {
                    Point r = points[ri];
                    checkSame(p, r);
                    checkNull(r, "Point can not be null");
                    for (int si = ri + 1; si < points.length; si++) {
                        Point s = points[si];
                        checkNull(s, "Point can not be null");
                        checkSame(p, s);

                        final double pq = p.slopeTo(q);
                        final double pr = p.slopeTo(r);
                        final double ps = p.slopeTo(s);

                        if (pq == pr && pq == ps) {
                            // points are collinear, get bottom and top point
                            Point[] collinearPoints = {p, q, r, s};
                            Arrays.sort(collinearPoints);
                            Arrays.sort(collinearPoints, p.slopeOrder());
                            addSegment(count++, new LineSegment(collinearPoints[0], collinearPoints[3]));
                        }
                    }
                }
            }
        }
        this.segments = Arrays.copyOf(segmentsTmp, count);
    }

    public static void main(String[] args) {
        Point[] points = new Point[]{
//                new Point(10000, 0),
//                new Point(0, 10000),
//                new Point(3000, 7000),
//                new Point(7000, 3000)
//                ,
                new Point(20000, 21000),
                new Point(3000, 4000),
                new Point(14000, 15000),
                new Point(6000, 7000)

        };
        final BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);

        System.out.println(Arrays.toString(collinearPoints.segments()));
    }

    private void addSegment(int nextIndex, LineSegment lineSegment) {
        if (this.segmentsTmp.length == nextIndex) {
            LineSegment[] newSegments = new LineSegment[this.segmentsTmp.length * 2];
            for (int i = 0; i < this.segmentsTmp.length; i++) {
                newSegments[i] = this.segmentsTmp[i];
            }
            this.segmentsTmp = newSegments;
        }
//        System.out.println("Inserting line segment "+lineSegment+" into index "+nextIndex+ " of array "+this.segmentsTmp.length);
        this.segmentsTmp[nextIndex] = lineSegment;
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

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.*;

/**
 * @author Tetiana_Prynda
 * Created on 9/6/2017.
 */
public class PointSET {

    private Set<Point2D> pointsOnSquare;

    // construct an empty set of points
    public PointSET() {
        pointsOnSquare = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointsOnSquare.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointsOnSquare.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        pointsOnSquare.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can not check null Point");
        return pointsOnSquare.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point2D : pointsOnSquare) {
            point2D.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Can not check null Rect");
        if (pointsOnSquare.isEmpty()) return null;
        Collection<Point2D> insidePoints = new LinkedList<Point2D>();
        for (Point2D point2D : pointsOnSquare) {
            if (rect.contains(point2D)) {
                insidePoints.add(point2D);
            }
        }
        return insidePoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can not check null Point");
        final Iterator<Point2D> pointsIterator = pointsOnSquare.iterator();
        if (pointsIterator.hasNext()) {
            Point2D nearest = pointsIterator.next();
            double minDistance = p.distanceTo(nearest);
            while (pointsIterator.hasNext()) {
                final Point2D currentPoint = pointsIterator.next();
                final double currentDistance = p.distanceTo(currentPoint);
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    nearest = currentPoint;
                }
            }
            return nearest;
        }
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}

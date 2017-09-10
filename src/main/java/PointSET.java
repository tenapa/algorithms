import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author Tetiana_Prynda
 * Created on 9/6/2017.
 */
public class PointSET {

    private final Set<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can not check null Point");
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point2D : points) {
            point2D.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Can not check null Rect");
        if (points.isEmpty()) return null;
        Collection<Point2D> insidePoints = new LinkedList<Point2D>();
        for (Point2D point2D : points) {
            if (rect.contains(point2D)) {
                insidePoints.add(point2D);
            }
        }
        return insidePoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can not check null Point");
        final Iterator<Point2D> pointsIterator = points.iterator();
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
//
    }
}

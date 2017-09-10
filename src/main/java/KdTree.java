import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;


/**
 * @author Tetiana_Prynda
 *         Created on 9/6/2017.
 */
public class KdTree {
    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can not insert null point into the tree.");
        if (root == null) {
            root = new Node(p, NodeType.X, null, null, null);
            size++;
        } else {
            Node parent = findParent(p, root);
            if (parent.getPoint().equals(p)) {
                // point already exists in the set
                return;
            } else if (isSmaller(p, parent)) {
                Node newNode = new Node(p, parent.getType() == NodeType.X ? NodeType.Y : NodeType.X, parent, null, null);
                parent.setLeftChild(newNode);
                size++;
            } else {
                Node newNode = new Node(p, parent.getType() == NodeType.X ? NodeType.Y : NodeType.X, parent, null, null);
                parent.setRightChild(newNode);
                size++;
            }
        }
    }

    private Node findParent(Point2D p, Node node) {
        Node parent = node;
        if (p.equals(node.getPoint())) return node;
        if (isSmaller(p, node) && node.getLeftChild() != null) parent = findParent(p, node.getLeftChild());
        else if (isGreater(p, node) && node.getRightChild() != null) parent = findParent(p, node.getRightChild());
        return parent;
    }

    private boolean isSmaller(Point2D p, Node node) {
        return (node.getType() == NodeType.X && p.x() < node.getPoint().x())
                || (node.getType() == NodeType.Y && p.y() < node.getPoint().y());
    }

    private boolean isGreater(Point2D p, Node node) {
        return (node.getType() == NodeType.X && node.getPoint().x() <= p.x())
                || (node.getType() == NodeType.Y && node.getPoint().y() <= p.y());
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can not check null Point");
        if (root == null) return false;
        Node parent = findParent(p, root);
        return parent.getPoint().equals(p);
    }

    // draw all points to standard draw
    public void draw() {
        drawNode(root, null, null);
    }

    private void drawNode(Node node, Node lowerLimit, Node upperLimit) {
        if (node == null) return;
        StdDraw.setPenColor(Color.BLACK);
        node.getPoint().draw();
        Point2D lineStartPoint = getIntersection(node, lowerLimit, 0.0);
        Point2D lineEndPoint = getIntersection(node, upperLimit, 1.0);
        if (node.getType() == NodeType.X) {
            StdDraw.setPenColor(Color.RED);
        } else {
            StdDraw.setPenColor(Color.BLUE);
        }
        StdDraw.line(lineStartPoint.x(), lineStartPoint.y(), lineEndPoint.x(), lineEndPoint.y());
        drawNode(node.getLeftChild(), lowerLimit, node);
        drawNode(node.getRightChild(), node, upperLimit);
    }

    private Point2D getIntersection(Node node, Node limit, double defaultValue) {
        if (node.getType() == NodeType.X) {
            // line will be vertical
            if (limit == null || limit.getType() == NodeType.X) {
                return new Point2D(node.getPoint().x(), defaultValue);
            } else {
                return new Point2D(node.getPoint().x(), limit.point.y());
            }
        } else {
            // line will be horizontal
            if (limit == null || limit.getType() == NodeType.Y) {
                return new Point2D(defaultValue, node.getPoint().y());
            } else {
                return new Point2D(limit.getPoint().x(), node.getPoint().y());
            }
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Can not search null rect");
        Collection<Point2D> inRange = new LinkedList<Point2D>();
        range(rect, root, inRange);
        return inRange;
    }

    private void range(RectHV rect, Node node, Collection<Point2D> inRange) {
        if (node == null) return;
        Point2D point = node.getPoint();
        if (rect.contains(point)) {
            inRange.add(point);
        }
        if (node.getType() == NodeType.X && rect.xmax() < point.x()) {
            range(rect, node.getLeftChild(), inRange);
        } else if (node.getType() == NodeType.X && point.x() <= rect.xmin()) {
            range(rect, node.getRightChild(), inRange);
        } else if (node.getType() == NodeType.X) {
            range(rect, node.getLeftChild(), inRange);
            range(rect, node.getRightChild(), inRange);
        } else if (node.getType() == NodeType.Y && rect.ymax() < point.y()) {
            range(rect, node.getLeftChild(), inRange);
        } else if (node.getType() == NodeType.Y && point.y() <= rect.ymin()) {
            range(rect, node.getRightChild(), inRange);
        } else if (node.getType() == NodeType.Y) {
            range(rect, node.getLeftChild(), inRange);
            range(rect, node.getRightChild(), inRange);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Can not search null point");
        if (isEmpty()) return null;
        return getBest(root, root.getPoint(), p);
    }

    private Point2D getBest(Node currentNode, Point2D best, Point2D p) {
        if (currentNode == null) return best;
        double currentDistance = getDistance(currentNode.getPoint(), p);
        if (currentDistance < getDistance(best, p)) {
            best = currentNode.getPoint();
        }
        if (isSmaller(p, currentNode)) {
            best = getBest(currentNode.getLeftChild(), best, p);
            if (canBeBetter(currentNode, getDistance(best, p), p)) {
                best = getBest(currentNode.getRightChild(), best, p);
            }
        } else {
            best = getBest(currentNode.getRightChild(), best, p);
            if (canBeBetter(currentNode, getDistance(best, p), p)) {
                best = getBest(currentNode.getLeftChild(), best, p);
            }
        }
        return best;
    }

    private double getDistance(Point2D original, Point2D target) {
        return original.distanceTo(target);
    }

    private boolean canBeBetter(Node currentNode, double bestDistance, Point2D p) {
        Point2D bestPossible = currentNode.getType() == NodeType.X ? new Point2D(currentNode.getPoint().x(), p.y())
                : new Point2D(p.x(), currentNode.getPoint().y());
        return getDistance(bestPossible, p) < bestDistance;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // no tests here
    }

    private enum NodeType {
        X, Y;
    }

    private static class Node {
        private final Point2D point;
        private final NodeType type;
        private final Node parent;
        private Node leftChild;
        private Node rightChild;

        public Node(Point2D point, NodeType type, Node parent, Node leftChild, Node rightChild) {
            this.point = point;
            this.type = type;
            this.parent = parent;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public Point2D getPoint() {
            return point;
        }

        public NodeType getType() {
            return type;
        }

        public Node getParent() {
            return parent;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }
    }
}

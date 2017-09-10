import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * @author Tetiana_Prynda
 *         Created on 9/6/2017.
 */
public class KdTreeTest {

    @Test
    public void testBasicMethods() throws Exception {
        KdTree tree = new KdTree();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        final Point2D rootPoint = new Point2D(1, 1);
        tree.insert(rootPoint);
        assertFalse(tree.isEmpty());
        assertEquals(1, tree.size());
        final Point2D p = new Point2D(0.5, 0.5);
        tree.insert(p);
        assertFalse(tree.isEmpty());
        assertEquals(2, tree.size());
    }

    @Test
    public void testTreeInsert() throws Exception {
        KdTree tree = new KdTree();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        Point2D p = new Point2D(0.9, 0.6);
        tree.insert(p);

        assertFalse(tree.isEmpty());
        assertEquals(5, tree.size());
        tree.insert(p);
        assertEquals(5, tree.size());
        assertTrue(tree.contains(p));
        assertFalse(tree.contains(new Point2D(.2, .2)));

        tree.insert(new Point2D(0.6, 0.5));
        visualize(tree);
    }

    @Test
    public void testTree_Contains1() throws Exception {
        KdTree tree = new KdTree();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        Point2D point = new Point2D(0.2, 0.3);
        tree.insert(point);
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));

        assertFalse(tree.isEmpty());
        assertEquals(5, tree.size());

        RectHV rect = new RectHV(.1, .1, .4, .3);
        Iterable<Point2D> range = tree.range(rect);
        Iterator<Point2D> iterator = range.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(point, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testTree_Contains3() throws Exception {
        KdTree tree = new KdTree();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        Point2D p1 = new Point2D(0.7, 0.2);
        tree.insert(p1);
        Point2D p2 = new Point2D(0.5, 0.4);
        tree.insert(p2);
        Point2D p3 = new Point2D(0.2, 0.3);
        tree.insert(p3);
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));

        assertFalse(tree.isEmpty());
        assertEquals(5, tree.size());

        RectHV rect = new RectHV(.1, .1, .9, .4);
        Iterable<Point2D> range = tree.range(rect);
        Iterator<Point2D> iterator = range.iterator();
        Collection<Point2D> expected = new ArrayList<Point2D>(3);
        expected.add(p1);
        expected.add(p2);
        expected.add(p3);
        assertTrue(iterator.hasNext());
        Point2D next = iterator.next();
        assertTrue(expected.contains(next));
        expected.remove(next);
        assertTrue(iterator.hasNext());
        next = iterator.next();
        assertTrue(expected.contains(next));
        expected.remove(next);
        next = iterator.next();
        assertTrue(expected.contains(next));
        expected.remove(next);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testTree_nearest() throws Exception {
        KdTree tree = new KdTree();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        Point2D nearPoint = new Point2D(.1, .5);
        assertNull(tree.nearest(nearPoint));
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        Point2D p = new Point2D(0.2, 0.3);
        tree.insert(p);
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));

        assertFalse(tree.isEmpty());
        assertEquals(5, tree.size());
        Point2D nearest = tree.nearest(nearPoint);
        assertNotNull(nearest);
        assertEquals(p, nearest);
    }

    @Test
    public void testTree_nearest2() throws Exception {
        KdTree tree = new KdTree();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        Point2D nearPoint = new Point2D(.8, .4);
        assertNull(tree.nearest(nearPoint));
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        Point2D p1 = new Point2D(0.9, 0.6);
        tree.insert(p1);

        assertFalse(tree.isEmpty());
        assertEquals(5, tree.size());
        Point2D nearest = tree.nearest(nearPoint);
        assertNotNull(nearest);
        assertEquals(p1, nearest);
    }

    public void visualize(KdTree tree) throws Exception {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.setPenColor(Color.GREEN);
        rect.draw();
        tree.draw();
        while (!StdDraw.isMousePressed()) {
            StdDraw.pause(50);
        }
    }
}
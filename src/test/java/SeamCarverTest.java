import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by tetianaprynda on 01.11.17.
 */
public class SeamCarverTest {

    @Test
    public void constructorMutation() throws Exception {
        Picture pic = new Picture(2, 2);
        SeamCarver carver = new SeamCarver(pic);
        pic.set(0, 0, Color.BLUE);
        assertFalse(carver.picture().get(0, 0).equals(Color.BLUE));
    }

    @Test
    public void energy_3x4_1_1() throws Exception {
        SeamCarver carver = new SeamCarver(new Picture("/seam/3x4.png"));
        assertEquals(228.53, carver.energy(1, 1), 0.01);
    }

    @Test
    public void energy_3x4_1_2() throws Exception {
        SeamCarver carver = new SeamCarver(new Picture("/seam/3x4.png"));
        assertEquals(228.09, carver.energy(1, 2), 0.01);
    }

    @Test
    public void energy_7x3_1_1() throws Exception {
        SeamCarver carver = new SeamCarver(new Picture("/seam/7x3.png"));
        assertEquals(237.12, carver.energy(1, 1), 0.01);
    }

    @Test
    public void vert_seam_3x4() throws Exception {
        SeamCarver carver = new SeamCarver(new Picture("/seam/3x4.png"));
        Picture originalPicture = carver.picture();
        int[] verticalSeam = carver.findVerticalSeam();
        assertArrayEquals(new int[]{0,1,1,0}, verticalSeam);
        carver.removeVerticalSeam(verticalSeam);
        Picture updatedPicture = carver.picture();
        assertEquals(originalPicture.get(1,0), updatedPicture.get(0,0));
        assertEquals(originalPicture.get(2,0), updatedPicture.get(1,0));
        assertEquals(originalPicture.get(0,1), updatedPicture.get(0,1));
        assertEquals(originalPicture.get(2,1), updatedPicture.get(1,1));
        assertEquals(originalPicture.get(0,2), updatedPicture.get(0,2));
        assertEquals(originalPicture.get(2,2), updatedPicture.get(1,2));
        assertEquals(originalPicture.get(1,3), updatedPicture.get(0,3));
        assertEquals(originalPicture.get(2,3), updatedPicture.get(1,3));
    }

    @Test
    public void hor_seam_3x4() throws Exception {
        SeamCarver carver = new SeamCarver(new Picture("/seam/3x4.png"));
        Picture originalPicture = carver.picture();
        int[] horizontalSeam = carver.findHorizontalSeam();
        assertArrayEquals(new int[]{1,2,1}, horizontalSeam);
        carver.removeHorizontalSeam(horizontalSeam);
        Picture updatedPicture = carver.picture();
        assertEquals(originalPicture.get(0,0), updatedPicture.get(0,0));
        assertEquals(originalPicture.get(1,0), updatedPicture.get(1,0));
        assertEquals(originalPicture.get(2,0), updatedPicture.get(2,0));
        assertEquals(originalPicture.get(0,2), updatedPicture.get(0,1));
        assertEquals(originalPicture.get(1,1), updatedPicture.get(1,1));
        assertEquals(originalPicture.get(2,2), updatedPicture.get(2,1));
        assertEquals(originalPicture.get(0,3), updatedPicture.get(0,2));
        assertEquals(originalPicture.get(1,3), updatedPicture.get(1,2));
        assertEquals(originalPicture.get(2,3), updatedPicture.get(2,2));
    }
}
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
}
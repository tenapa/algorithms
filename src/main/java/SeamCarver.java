import edu.princeton.cs.algs4.Picture;

import java.awt.*;

/**
 * Created by tetianaprynda on 01.11.17.
 */
public class SeamCarver {
    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        checkArgument(picture);
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        checkIndex(x, y);
        if (x == 0 || y == 0 || x == picture.width() - 1 || y == picture.height() - 1)
            return 1000;

        Color colorL = picture.get(x - 1, y);
        Color colorR = picture.get(x + 1, y);
        Color colorU = picture.get(x, y - 1);
        Color colorD = picture.get(x, y + 1);
        int xR = colorL.getRed() - colorR.getRed();
        int xG = colorL.getGreen() - colorR.getGreen();
        int xB = colorL.getBlue() - colorR.getBlue();
        int yR = colorU.getRed() - colorD.getRed();
        int yG = colorU.getGreen() - colorD.getGreen();
        int yB = colorU.getBlue() - colorD.getBlue();


        int gradientX = xR * xR + xB * xB + xG * xG;
        int gradientY = yR * yR + yB * yB + yG * yG;

        return Math.sqrt(gradientX + gradientY);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        checkArgument(seam);
        checkSeam(seam, false);

    }

    private <T> void checkArgument(T object) {
        if (object == null) throw new IllegalArgumentException("Argument can not be null");
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        checkArgument(seam);
        checkSeam(seam, true);

    }

    private void checkIndex(int x, int y) {
        if (x < 0 || x > picture.width() - 1 || y < 0 || y > picture.height() - 1)
            throw new IllegalArgumentException("Indexes must be within bounds");
    }

    private void checkSeam(int[] seam, boolean isVertical) {
        if (seam.length != (isVertical ? picture.height() : picture.width()))
            throw new IllegalArgumentException("Not a valid seam");
        int previous = seam[0];
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(previous - seam[i]) > 1)
                throw new IllegalArgumentException("Not a valid seam");

        }
    }

}

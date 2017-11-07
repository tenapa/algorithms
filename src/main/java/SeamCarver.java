import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tetianaprynda on 01.11.17.
 */
public class SeamCarver {
    private int height;
    private int width;
    private double[][] energies;
    private Color[][] picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture pic) {
        checkArgument(pic);
        this.height = pic.height();
        this.width = pic.width();
        energies = new double[width][height];
        picture = new Color[width][height];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
                    energies[i][j] = 1000;
                } else {
                    Color colorL = pic.get(i - 1, j);
                    Color colorR = pic.get(i + 1, j);
                    Color colorU = pic.get(i, j - 1);
                    Color colorD = pic.get(i, j + 1);
                    energies[i][i] = getEnergy(colorL, colorR, colorU, colorD);
                }
                picture[i][j] = pic.get(i, j);
            }
        }
    }

    // current picture
    public Picture picture() {
        Picture pic = new Picture(width, height);
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                pic.set(i, j, picture[i][j]);
            }
        }
        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        checkIndex(x, y);
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return 1000;
        Color colorL = picture[x - 1][y];
        Color colorR = picture[x + 1][y];
        Color colorU = picture[x][y - 1];
        Color colorD = picture[x][y + 1];
        return getEnergy(colorL, colorR, colorU, colorD);
    }

    private double getEnergy(Color colorL, Color colorR, Color colorU, Color colorD) {
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
        transpose();
        int[] verticalSeam = findVerticalSeam();
        transpose();
        return verticalSeam;

    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        BestSeam best = new BestSeam(Double.MAX_VALUE, new int[0]);
        HashMap<Coord, BestSeam> markedCoordinates = new HashMap<>();
        for (int i = 0; i < width; i++) {
            BestSeam currentBest = findBestFromStart(0, i, new BestSeam(0, new int[0]), markedCoordinates);
            if (best.getEnergy() > currentBest.getEnergy()) {
                best = currentBest;
            }
        }
        int[] result = new int[height];
        int i = best.getSeam().length - 1;
        for (int j = 0; j < best.getSeam().length; j++) {
            result[j] = best.getSeam()[i--];
        }
        return result;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        checkArgument(seam);
        checkSeam(seam, false);
        int k = 0;
        for (int x = 0; x < width; x++) {
            removeElemHorizontal(x, seam[k++]);
        }
        height = height - 1;
        k = 0;
        for (int x = 0; x < width; x++) {
            int y = seam[k++];
            if (y > 0) {
                energies[x][y - 1] = energy(x, y - 1);
            }
            if (y < height - 1) {
                energies[x][y + 1] = energy(x, y + 1);
            }
            energies[x][y] = energy(x, y);
        }

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        checkArgument(seam);
        checkSeam(seam, true);
        int j = 0;
        for (int i = 0; i < height; i++) {
            removeElemVertical(seam[j++], i);
        }
        width = width - 1;
        j = 0;
        for (int y = 0; y < height; y++) {
            int x = seam[j++];
            if (x > 0) {
                energies[x - 1][y] = energy(x - 1, y);
            }
            if (x < width - 1) {
                energies[x + 1][y] = energy(x + 1, y);
            }
            energies[x][y] = energy(x, y);
        }

    }

    private void removeElemVertical(int x, int y) {
        for (int i = x; i < width - 1; i++) {
            picture[i][y] = picture[i + 1][y];
        }
    }

    private void removeElemHorizontal(int x, int y) {
        for (int i = y; i < height - 1; i++) {
            picture[x][i] = picture[x][i + 1];
        }
    }

    private BestSeam merge(BestSeam path, int position, double energy) {
        int[] seam = Arrays.copyOf(path.getSeam(), path.getSeam().length + 1);
        seam[path.getSeam().length] = position;
        return new BestSeam(path.getEnergy() + energy, seam);
    }

    private BestSeam findBestFromStart(int heightStart, int widthStart, BestSeam parent, Map<Coord, BestSeam> bestSeams) {
        if (widthStart < 0 || widthStart >= width) {
            return new BestSeam(Double.MAX_VALUE, new int[0]);
        }
        if (heightStart < 0 || heightStart >= height) {
            return parent;
        }
        Coord coord = new Coord(widthStart, heightStart);
        if (bestSeams.containsKey(coord)) {
            return bestSeams.get(coord);
        }

        BestSeam leftPath = findBestFromStart(heightStart + 1, widthStart - 1, new BestSeam(0, new int[0]), bestSeams);
        BestSeam downPath = findBestFromStart(heightStart + 1, widthStart, new BestSeam(0, new int[0]), bestSeams);
        BestSeam rightPath = findBestFromStart(heightStart + 1, widthStart + 1, new BestSeam(0, new int[0]), bestSeams);

        double energy = parent.getEnergy() + energy(widthStart, heightStart);
        BestSeam current;
        if (leftPath.getEnergy() <= rightPath.getEnergy() && leftPath.getEnergy() <= downPath.getEnergy()) {
            current = merge(leftPath, widthStart, energy);

        } else if (rightPath.getEnergy() <= downPath.getEnergy()) {
            current = merge(rightPath, widthStart, energy);

        } else {
            current = merge(downPath, widthStart, energy);
        }
        bestSeams.put(coord, current);
        return current;
    }

    private void transpose() {
        int newHeight = width;
        this.width = height;
        this.height = newHeight;
        double[][] newEnergies = new double[width][height];
        Color[][] newPicture = new Color[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newEnergies[i][j] = energies[j][i];
                newPicture[i][j] = picture[j][i];
            }
        }
        this.energies = newEnergies;
        this.picture = newPicture;
    }

    private <T> void checkArgument(T object) {
        if (object == null) throw new IllegalArgumentException("Argument can not be null");
    }

    private void checkIndex(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1)
            throw new IllegalArgumentException("Indexes must be within bounds");
    }

    private void checkSeam(int[] seam, boolean isVertical) {
        if (seam.length != (isVertical ? height : width))
            throw new IllegalArgumentException("Not a valid seam");
        int previous = seam[0];
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(previous - seam[i]) > 1)
                throw new IllegalArgumentException("Not a valid seam");

        }
    }

    private static class Coord {
        int x;
        int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Coord coord = (Coord) obj;

            if (x != coord.x) return false;
            return y == coord.y;

        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private static class BestSeam {
        double energy;
        int[] seam;

        public BestSeam(double energy, int[] seam) {
            this.energy = energy;
            this.seam = seam;
        }

        public double getEnergy() {
            return energy;
        }

        public int[] getSeam() {
            return seam;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            BestSeam bestSeam = (BestSeam) obj;

            if (Double.compare(bestSeam.energy, energy) != 0) return false;
            return Arrays.equals(seam, bestSeam.seam);

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            temp = Double.doubleToLongBits(energy);
            result = (int) (temp ^ (temp >>> 32));
            result = 31 * result + Arrays.hashCode(seam);
            return result;
        }

        @Override
        public String toString() {
            return "BestSeam{" +
                    "energy=" + energy +
                    ", seam=" + Arrays.toString(seam) +
                    '}';
        }
    }
}

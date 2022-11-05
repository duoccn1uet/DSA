import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.PriorityQueue;

public class SeamCarver {
    private static class Pair implements Comparable<Pair> {
        public int vertex;
        public double distance;
        public Pair(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(Pair o) {
            return Double.compare(distance, o.distance);
        }
    }
    private static final double BORDER_ENERGY = 1000;
    private Picture picture;
    private int w;
    private int h;
    private double[][] pixelEnergy;
    private double[] dist;
    private int[] trace;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("null picture passed in constructor SeamCarver(Picture)");
        this.picture = new Picture(picture);
        w = picture.width();
        h = picture.height();
        pixelEnergy = new double[h][w];
        dist = new double[w*h];
        trace = new int[w*h];
        /**for (int x = 0; x < h; ++x)
            for (int y = 0; y < w; ++y)
                */
        /**for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y)
                System.out.print(energy(x, y) + " ");
            System.out.println();
        }*/
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return w;
    }

    // height of current picture
    public int height() {
        return h;
    }

    private boolean isValidPixel(int x, int y) {
        return 0 <= x && x < w && 0 <= y && y < h;
    }
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!isValidPixel(x, y))
            throw new IllegalArgumentException("Index out of bounds in function SeamCarver::energy(int,int)");
        if (x == 0 || x == w-1 || y == 0 || y == h-1)
            return BORDER_ENERGY;
        if (pixelEnergy[y][x] != 0)
            return pixelEnergy[y][x];
        int res = 0;

        Color adjust1 = picture.get(x-1, y);
        Color adjust2 = picture.get(x+1, y);
        int redDiff = adjust1.getRed() - adjust2.getRed();
        int greenDiff = adjust1.getGreen() - adjust2.getGreen();
        int blueDiff = adjust1.getBlue() - adjust2.getBlue();
        res += redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;

        adjust1 = picture.get(x, y - 1);
        adjust2 = picture.get(x, y + 1);
        redDiff = adjust1.getRed() - adjust2.getRed();
        greenDiff = adjust1.getGreen() - adjust2.getGreen();
        blueDiff = adjust1.getBlue() - adjust2.getBlue();
        res += redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;

        return Math.sqrt(res);
    }

    private int to1D(int x, int y) {
        return y * w + x;
    }

    private int toX(int n) {
        return n % w;
    }

    private int toY(int n) {
        return n / w;
    }

    private void push(int u, int v, PriorityQueue<Pair> q) {
        if (dist[v] > dist[u] + energy(toX(v), toY(v))) {
            dist[v] = dist[u] + energy(toX(v), toY(v));
            trace[v] = u;
            q.add(new Pair(v, dist[v]));
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        for (int i = 0; i < w*h; ++i) {
            trace[i] = -1;
            dist[i] = Integer.MAX_VALUE;
        }
        PriorityQueue<Pair> q = new PriorityQueue<>();
        for (int y = 0; y < h; ++y) {
            q.add(new Pair(to1D(0, y), 0));
            dist[to1D(0, y)] = 0;
        }
        while (!q.isEmpty()) {
            int u = q.peek().vertex;
            double du = q.peek().distance;
            q.poll();
            if (du != dist[u])
                continue;
            int x = toX(u);
            int y = toY(u);
            ///System.out.println(x + " " + y + " " + du);
            if (x == w-1) {
                int[] path = new int[w];
                int i = w - 1;
                int v = u;
                while (v != -1) {
                    ///System.out.println(toX(v) + " " + toY(v));
                    path[i] = toY(v);
                    v = trace[v];
                    --i;
                }
                return path;
            }
            if (y-1 >= 0)
                push(u, to1D(x+1, y-1), q);
            push(u, to1D(x+1, y), q);
            if (y+1 < h)
                push(u, to1D(x+1, y+1), q);
        }
        return new int[0];
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        for (int i = 0; i < w*h; ++i) {
            trace[i] = -1;
            dist[i] = Integer.MAX_VALUE;
        }
        PriorityQueue<Pair> q = new PriorityQueue<>();
        for (int x = 0; x < w; ++x) {
            q.add(new Pair(to1D(x, 0), 0));
            dist[to1D(x, 0)] = 0;
        }
        while (!q.isEmpty()) {
            int u = q.peek().vertex;
            double du = q.peek().distance;
            q.poll();
            if (du != dist[u])
                continue;
            int x = toX(u);
            int y = toY(u);
            ///System.out.println(x + " " + y + " " + du);
            if (y == h-1) {
                int[] path = new int[h];
                int i = h - 1;
                int v = u;
                while (v != -1) {
                    ///System.out.println(toX(v) + " " + toY(v));
                    path[i] = toX(v);
                    v = trace[v];
                    --i;
                }
                return path;
            }
            if (x-1 >= 0)
                push(u, to1D(x-1, y+1), q);
            push(u, to1D(x, y+1), q);
            if (x+1 < w)
                push(u, to1D(x+1, y+1), q);
        }
        return new int[0];
    }

    private void checkValidSeam(int[] seam, int n) {
        if (seam == null)
            throw new IllegalArgumentException("null seam passed");
        if (seam.length != n)
            throw new IllegalArgumentException("invalid seam length");
        for (int i = 0; i < n; ++i)
            if (n == w) {
                if (!isValidPixel(i, seam[i]))
                    throw new IllegalArgumentException("out of bound in seam");
                if (i > 0 && Math.abs(seam[i] - seam[i-1]) > 1)
                    throw new IllegalArgumentException("distance > 1 in seam");
            } else {
                if (!isValidPixel(seam[i], i))
                    throw new IllegalArgumentException("out of bound in seam");
                if (i > 0 && Math.abs(seam[i] - seam[i-1]) > 1)
                    throw new IllegalArgumentException("distance > 1 in seam");
            }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (h <= 1)
            throw new IllegalArgumentException("height <= 1");
        checkValidSeam(seam, w);
        Picture newPic = new Picture(w, h-1);
        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < seam[x]; ++y)
                newPic.set(x, y, picture.get(x, y));
            for (int y = seam[x]; y < h-1; ++y)
                newPic.set(x, y, picture.get(x, y+1));
        }
        picture = newPic;
        --h;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (w <= 1)
            throw new IllegalArgumentException("width <= 1");
        checkValidSeam(seam, h);
        Picture newPic = new Picture(w-1, h);
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < seam[y]; ++x)
                newPic.set(x, y, picture.get(x, y));
            for (int x = seam[y]; x < w-1; ++x)
                newPic.set(x, y, picture.get(x+1, y));
        }
        picture = newPic;
        --w;
    }
//
//    public static void main(String[] args) {
//        SeamCarver s = new SeamCarver(new Picture("C:\\Users\\LENOVO\\OneDrive - vnu.edu.vn\\Code_Java\\DSA\\Coursera\\Seam Carving\\src\\6x5.png"));
//        int[] a = s.findVerticalSeam();
//        for (int x : a)
//            System.out.print(x + " ");
//    }
}

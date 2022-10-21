
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point other) {
        StdDraw.line(this.x, this.y, other.x, other.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point other) {
        if (y != other.y)
            return Integer.compare(y, other.y);
        return Integer.compare(x, other.x);
    }
    public double slopeTo(Point other) {
        if (x == other.x) {
            return y == other.y ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        }
        if (y == other.y)
            return 0;
        return ((double) (other.y - y) / (other.x - x));
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(slopeTo(o1), slopeTo(o2));
            }
        };
    }

}
    
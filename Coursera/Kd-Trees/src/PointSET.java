import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    private final Set<Point2D> points = new TreeSet<>();

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("null pram provided in function PointSET.insert");
        if (!contains(p)) {
            points.add(p);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("null pram provided in function PointSET.contains");
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("null pram provided in function PointSET.range");
        ArrayList<Point2D> pointsInRect = new ArrayList<>();
        for (Point2D p : points)
            if (rect.contains(p))
                pointsInRect.add(p);
        return pointsInRect;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("null pram provided in function PointSET.nearest");
        if (isEmpty())
            return null;
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D res = p;
        for (Point2D point : points) {
            if (p.distanceSquaredTo(point) < minDistance) {
                minDistance = p.distanceSquaredTo(point);
                res = point;
            }
        }
        return res;
    }
}
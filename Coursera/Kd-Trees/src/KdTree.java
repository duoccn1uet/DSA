import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Comparator;

public class KdTree {

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private int level;

        public Node(Point2D point, Node left, Node right, int level) {
            this.point = point;
            this.left = left;
            this.right = right;
            this.level = level;
        }
    }

    private Node root = null;
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void insert(Node cur, Point2D p) {
        Comparator<Point2D> cmp = (cur.level % 2 == 1 ? Point2D.X_ORDER : Point2D.Y_ORDER);
        if (cmp.compare(cur.point, p) > 0) {
            if (cur.left == null) {
                cur.left = new Node(p, null, null, cur.level + 1);
            } else {
                insert(cur.left, p);
            }
        } else {
            if (cur.right == null) {
                cur.right = new Node(p, null, null, cur.level + 1);
            } else {
                insert(cur.right, p);
            }
        }
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("null pram provided in function KdTree.insert");
        ///p = new Point2D(p.x()*10, p.y()*10);
        if (root == null) {
            root = new Node(p, null, null, 1);
            ++size;
        } else if (!contains(p)) {
            insert(root, p);
            ++size;
        }
    }

    private boolean contains(Node cur, Point2D p) {
        Comparator<Point2D> cmp = cur.level % 2 == 1 ? Point2D.X_ORDER : Point2D.Y_ORDER;
        ///System.out.println(cur.right == null);
        if (cur.point.equals(p))
            return true;
        if (cmp.compare(cur.point, p) > 0)
            return cur.left != null && contains(cur.left, p);
        return cur.right != null && contains(cur.right, p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("null pram provided in function KdTree.contains");
        if (isEmpty())
            return false;
        return contains(root, p);
    }

    private void draw(Node cur, RectHV rectHV) {
        if (cur == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        cur.point.draw();

        StdDraw.setPenRadius(0.005);
        if (cur.level % 2 == 1) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(cur.point.x(), rectHV.ymin(), cur.point.x(), rectHV.ymax());
            draw(cur.left, new RectHV(rectHV.xmin(), rectHV.ymin(), cur.point.x(), rectHV.ymax()));
            draw(cur.right, new RectHV(cur.point.x(), rectHV.ymin(), rectHV.xmax(), rectHV.ymax()));
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rectHV.xmin(), cur.point.y(), rectHV.xmax(), cur.point.y());
            draw(cur.left, new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), cur.point.y()));
            draw(cur.right, new RectHV(rectHV.xmin(), cur.point.y(), rectHV.xmax(), rectHV.ymax()));
        }
    }

    public void draw() {
        if (isEmpty()) return;
        draw(root, new RectHV(0, 0, 1, 1));
    }

    private void range(Node cur, RectHV point, ArrayList<Point2D> pointsInRange) {
        if (cur == null) {
            return;
        }
        if (point.contains(cur.point)) {
            pointsInRange.add(cur.point);
        }
        double midLine;
        double lowBound;
        double highBound;
        if (cur.level % 2 == 1) {
            midLine = cur.point.x();
            lowBound = point.xmin();
            highBound = point.xmax();
        } else {
            midLine = cur.point.y();
            lowBound = point.ymin();
            highBound = point.ymax();
        }
        if (lowBound <= midLine && midLine <= highBound) {
            range(cur.left, point, pointsInRange);
            range(cur.right, point, pointsInRange);
        } else if (lowBound > midLine) {
            range(cur.right, point, pointsInRange);
        } else {
            range(cur.left, point, pointsInRange);
        }
    }

    public Iterable<Point2D> range(RectHV point) {
        if (point == null)
            throw new IllegalArgumentException("null pram provided in function KdTree.range");
        ArrayList<Point2D> pointsInRange = new ArrayList<>();
        range(root, point, pointsInRange);
        return pointsInRange;
    }

    private Point2D nearest(Node cur, Point2D query, Point2D best, RectHV curRect) {
        if (cur == null)
            return best;
        if (cur.point.distanceSquaredTo(query) < best.distanceSquaredTo(query))
            best = cur.point;
        double midLine;
        double highBound;
        double cx = cur.point.x();
        double cy = cur.point.y();
        RectHV rRect;
        RectHV lRect;
        if (cur.level % 2 == 1) {
            midLine = cur.point.x();
            highBound = query.x();
            rRect = new RectHV(cx, curRect.ymin(), curRect.xmax(), curRect.ymax());
            lRect = new RectHV(curRect.xmin(), curRect.ymin(), cx, curRect.ymax());
        } else {
            midLine = cur.point.y();
            highBound = query.y();
            rRect = new RectHV(curRect.xmin(), cy, curRect.xmax(), curRect.ymax());
            lRect = new RectHV(curRect.xmin(), curRect.ymin(), curRect.xmax(), cy);
        }
        if (midLine <= highBound) {
            best = nearest(cur.right, query, best, rRect);
            if (cur.left != null
                    && best.distanceSquaredTo(query) > lRect.distanceSquaredTo(query))
                best = nearest(cur.left, query, best, lRect);
        } else {
            best = nearest(cur.left, query, best, lRect);
            if (cur.right != null
                    && best.distanceSquaredTo(query) > rRect.distanceSquaredTo(query))
                best = nearest(cur.right, query, best, rRect);
        }
        return best;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("null pram provided in function KdTree.nearest");
        if (isEmpty())
            return null;
        return nearest(root, p, root.point, new RectHV(0, 0, 1, 1));
    }

    public static void main(String[] args) {
        KdTree t = new KdTree();
        t.insert(new Point2D(0.7, 0.2));
        t.insert(new Point2D(0.5, 0.4));
        t.insert(new Point2D(0.2, 0.3));
        t.insert(new Point2D(0.4, 0.7));
        t.insert(new Point2D(0.9, 0.6));
        ///t.draw();
        ///t.insert(new Point2D(1, 1));
        System.out.println(t.nearest(new Point2D(0.097, 0.556)));
        ///System.out.println(Point2D.X_ORDER.compare(new Point2D(1, 0), new Point2D(1, 1)));
        ///System.out.println(t.contains(new Point2D(1, 1)));
    }
}
public class LineSegment {
    private final Point p; /// start point
    private final Point q; /// end point

    public LineSegment(Point p, Point q) {
        if (p == null || q == null)
            throw new NullPointerException("point argument is null");
        this.p = p;
        this.q = q;
    }

    public void draw() {
        p.drawTo(q);
    }

    public String toString() {
        return p + " -> " + q;
    }

    public static void main(String[] args) {
        System.out.println(new LineSegment(new Point(1, 2), new Point(3, 5)).toString());
    }
}
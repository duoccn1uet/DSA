import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("List points is null");
        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException("List points contains null point");
        }

        Point[] tempPoints = points.clone();

        Arrays.sort(tempPoints);
        int n = tempPoints.length;
        for (int i = 1; i < n; ++i) {
            if (tempPoints[i].compareTo(tempPoints[i - 1]) == 0)
                throw new IllegalArgumentException("List points contains duplicate point.");
        }

        ArrayList<LineSegment> tempSegmentList = new ArrayList<LineSegment>();
        if (n > 3) {
            for (int i = 0; i < n; ++i)
                for (int j = i+1; j < n; ++j) {
                    double x1 = tempPoints[i].slopeTo(tempPoints[j]);
                    for (int u = j+1; u < n; ++u) {
                        double x2 = tempPoints[i].slopeTo(tempPoints[u]);
                        if (x1 == x2) {
                            for (int v = u+1; v < n; ++v) {
                                double x3 = tempPoints[i].slopeTo(tempPoints[v]);
                                if (x1 == x3) {
                                    tempSegmentList.add(new LineSegment(tempPoints[i], tempPoints[v]));
                                    ///System.out.println(new LineSegment(tempPoints[i], tempPoints[v]));
                                }
                            }
                        }
                    }
                }

        }
        segments = tempSegmentList.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return segments.length;
    }
    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}
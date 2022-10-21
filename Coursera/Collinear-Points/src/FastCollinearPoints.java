import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("List points is null");
        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException("List points contains null point");
        }

        Point[] tempPoints = points.clone();

        int n = tempPoints.length;
        Arrays.sort(tempPoints);
        for (int i = 1; i < n; ++i) {
            if (tempPoints[i].compareTo(tempPoints[i - 1]) == 0)
                throw new IllegalArgumentException("List points contains duplicate point.");
        }

        ArrayList<LineSegment> tempSegmentList = new ArrayList<>();
        if (n > 3) {
            Point[] temp = tempPoints.clone();
            for (Point point : tempPoints) {
                Arrays.sort(temp, point.slopeOrder());
                for (int j = 1; j < n;) {
                    double slope = point.slopeTo(temp[j]);
                    int k = j;
                    while (k < n && point.slopeTo(temp[k]) == slope)
                        ++k;
                    if (k-j >= 3) {
                        ArrayList<Point> dcm = new ArrayList<>();
                        dcm.add(point);
                        for (int i = j; i < k; ++i)
                            dcm.add(temp[i]);
                        Collections.sort(dcm);
                        if (dcm.get(0).compareTo(point) == 0)
                            tempSegmentList.add(new LineSegment(point, dcm.get(dcm.size()-1)));
                    }
                    j = k;
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
        /**try {
            FileReader inp = new FileReader("C:\\Users\\LENOVO\\OneDrive\\Desktop\\.inp");
            BufferedReader in = new BufferedReader(inp);
            int n = Integer.parseInt(in.readLine());
            System.out.println(n);
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                String[] s = in.readLine().split(" ");
                int x = Integer.parseInt(s[0]);
                int y = Integer.parseInt(s[1]);
                points[i] = new Point(x, y);
                ///System.out.println(points[i]);
            }

            // print and draw the line segments
            FastCollinearPoints collinear = new FastCollinearPoints(points);

            for (LineSegment segment : collinear.segments()) {
                System.out.println(segment);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }

}
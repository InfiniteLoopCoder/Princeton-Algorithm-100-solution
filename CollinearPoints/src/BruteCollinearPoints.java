import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {
    private LinkedList<LineSegment> segs;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        segs = new LinkedList<LineSegment>();
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
                for (int k = j + 1; k < points.length; k++) {
                    if (points[k] == null || points[i].compareTo(points[k]) == 0 || points[j].compareTo(points[k]) == 0) {
                        throw new IllegalArgumentException();
                    }
                    for (int m = k + 1; m < points.length; m++) {
                        if (points[m] == null || points[i].compareTo(points[m]) == 0 || points[j].compareTo(points[m]) == 0 || points[k].compareTo(points[m]) == 0) {
                            throw new IllegalArgumentException();
                        }
                        if ((points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) && (points[i].slopeTo(points[j]) == points[i].slopeTo(points[m]))) {
                            Point[] collinearPoints = {points[i], points[j], points[k], points[m]};
                            Arrays.sort(collinearPoints); // Sort the points to find the endpoints
                            segs.add(new LineSegment(collinearPoints[0], collinearPoints[3]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segs.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segs.toArray(new LineSegment[0]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
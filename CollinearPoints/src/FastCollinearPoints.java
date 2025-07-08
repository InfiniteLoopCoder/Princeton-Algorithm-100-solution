import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private LinkedList<LineSegment> segs;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] process = points.clone();
        segs = new LinkedList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(process, points[i].slopeOrder());
            if (points.length > 1) {
                if (points[i].slopeTo(process[1]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
            }
            int beginPosition = 0;
            int endPosition = 1;
            while (beginPosition < points.length) {
                // When while quit, the endPosition refer to the first different slope point
                while (endPosition < points.length && points[i].slopeTo(process[beginPosition]) == points[i].slopeTo(process[endPosition])) {
                    endPosition++;
                }
                if (endPosition - beginPosition >= 3) {
                    Point[] collinearPoints = new Point[endPosition - beginPosition + 1];
                    collinearPoints[0] = points[i];
                    for (int j = 0; j < endPosition - beginPosition; j++) {
                        collinearPoints[j + 1] = process[beginPosition + j];
                    }
                    Arrays.sort(collinearPoints); // Sort the points to find the endpoints
                    // for an identical segment, every point's relative position is always same,
                    // but the point providing slope comparator can be different, exactly one position at a time
                    // thus we can select only one situation to avoid examining if a segment is already exist
                    if (collinearPoints[0].compareTo(points[i]) == 0) { // can be [0, endPosition - beginPosition]
                        segs.add(new LineSegment(collinearPoints[0], collinearPoints[endPosition - beginPosition]));
                    }
                }
                beginPosition = endPosition;
                endPosition++;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
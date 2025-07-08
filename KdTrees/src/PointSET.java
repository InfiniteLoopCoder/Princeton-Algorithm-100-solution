import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {

    private SET<Point2D> pointSet;

    public PointSET() {
        pointSet = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return pointSet.contains(p);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        ArrayList<Point2D> result = new ArrayList<Point2D>();

        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }

        return result;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Point2D result = null;

        // we use square distance to reduce computation
        double minDistance = Double.POSITIVE_INFINITY;

        for (Point2D current : pointSet) {
            double currDistance = p.distanceSquaredTo(current);
            if (currDistance < minDistance) {
                result = current;
                minDistance = currDistance;
            }
        }

        return result;
    }

    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    public static void main(String[] args) {

    }

}
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


import java.util.ArrayList;

public class KdTree {

    private static class Node {
        private final Point2D p;
        private final RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D p, RectHV rect) {
            // use this to distinguish members and parameters
            this.p = p;
            this.rect = rect;
            // automatically null for rest members。
        }
    }

    private Node root;
    private int nodeNums;

    public KdTree() {
        root = null;
        nodeNums = 0;
    }

    public boolean isEmpty() {
        return nodeNums == 0;
    }

    public int size() {
        return nodeNums;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        // corner case: empty
        if (root == null) {
            root = new Node(p, new RectHV(0, 0, 1, 1));
            nodeNums++;
            return;
        }

        // 1 for x-coordinate, 0 for y-coordinate
        boolean compareX = true;
        // for insertion logic
        Node lastNode = null;

        Node currentNode = root;


        // find the empty position to insert our new Node
        while (true) {

            if (currentNode.p.equals(p)) {
                return;
            }

            lastNode = currentNode;
            double newRectXMin = lastNode.rect.xmin();
            double newRectXMax = lastNode.rect.xmax();
            double newRectYMin = lastNode.rect.ymin();
            double newRectYMax = lastNode.rect.ymax();

            if (compareX) {
                if (p.x() < currentNode.p.x()) {
                    currentNode = currentNode.left;

                    if (currentNode == null) {
                        newRectXMax = lastNode.p.x();
                        lastNode.left = new Node(p, new RectHV(newRectXMin, newRectYMin, newRectXMax, newRectYMax));
                        nodeNums++;
                        break;
                    }

                } else {
                    currentNode = currentNode.right;

                    if (currentNode == null) {
                        newRectXMin = lastNode.p.x();
                        lastNode.right = new Node(p, new RectHV(newRectXMin, newRectYMin, newRectXMax, newRectYMax));
                        nodeNums++;
                        break;
                    }
                }
            } else {
                if (p.y() < currentNode.p.y()) {
                    currentNode = currentNode.left;

                    if (currentNode == null) {
                        newRectYMax = lastNode.p.y();
                        lastNode.left = new Node(p, new RectHV(newRectXMin, newRectYMin, newRectXMax, newRectYMax));
                        nodeNums++;
                        break;
                    }

                } else {
                    currentNode = currentNode.right;

                    if (currentNode == null) {
                        newRectYMin = lastNode.p.y();
                        lastNode.right = new Node(p, new RectHV(newRectXMin, newRectYMin, newRectXMax, newRectYMax));
                        nodeNums++;
                        break;
                    }
                }
            }

            // maintain critical variables
            compareX = !compareX;

        }

    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        // corner case: empty
        if (root == null) {
            return false;
        }

        // 1 for x-coordinate, 0 for y-coordinate
        boolean compareX = true;

        Node currentNode = root;


        // find the empty position to insert our new Node
        while (currentNode != null) {
            if (currentNode.p.equals(p)) {
                return true;
            }

            if (compareX) {
                if (p.x() < currentNode.p.x()) {
                    currentNode = currentNode.left;

                } else {
                    currentNode = currentNode.right;
                }
            } else {
                if (p.y() < currentNode.p.y()) {
                    currentNode = currentNode.left;
                } else {
                    currentNode = currentNode.right;
                }
            }

            // maintain critical variables
            compareX = !compareX;
        }
        return false;
    }

    private void rangeHelper(RectHV rect, Node root, ArrayList<Point2D> result) {
        if (rect == null) throw new IllegalArgumentException();

        if (root == null) {
            return;
        }

        if (!root.rect.intersects(rect)) {
            return;
        }

        if (rect.contains(root.p)) {
            result.add(root.p);
        }

        rangeHelper(rect, root.left, result);
        rangeHelper(rect, root.right, result);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> result = new ArrayList<Point2D>();

        rangeHelper(rect, root, result);

        return result;
    }

    private Point2D nearestHelper(Point2D p, Node root, Point2D currentBest, boolean compareX) {
        if (root == null) {
            return currentBest;
        }

        // we use squared distance to reduce computation
        double distance = p.distanceSquaredTo(root.p);
        if (distance < p.distanceSquaredTo(currentBest)) {
            currentBest = root.p;
        }

        boolean leftSide = false;
        // judge which side p is
        if (compareX) {
            if (p.x() < root.p.x()) { // left
                leftSide = true;
            }
        } else {
            if (p.y() < root.p.y()) { // left
                leftSide = true;
            }
        }
        Node firstSubTree;
        Node secondSubTree;
        if (leftSide) {
            firstSubTree = root.left;
            secondSubTree = root.right;
        } else {
            firstSubTree = root.right;
            secondSubTree = root.left;
        }

        currentBest = nearestHelper(p, firstSubTree, currentBest, !compareX);

        // conduct pruning
        if (secondSubTree != null) {
            double possibleDistance = secondSubTree.rect.distanceSquaredTo(p);
            if (possibleDistance < p.distanceSquaredTo(currentBest)) {
                currentBest = nearestHelper(p, secondSubTree, currentBest, !compareX);
            }
        }

        return currentBest;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (root == null) {
            return null;
        }
        return nearestHelper(p, root, root.p, true);
    }


    private void drawHelper(Node node, boolean isVertical) {

        if (node == null) {
            return;
        }

        // draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        // draw segment
        StdDraw.setPenRadius();

        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }

        // recursion
        drawHelper(node.left, !isVertical);
        drawHelper(node.right, !isVertical);
    }

    public void draw() {
        StdDraw.setScale(0, 1);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        StdDraw.square(0.5, 0.5, 0.5);

        drawHelper(root, true);
    }

    public static void main(String[] args) {

    }
}

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private static final int SEGMENT_MIN_SIZE = 4;
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] pointsOrigin) {
        if (pointsOrigin == null) throw new IllegalArgumentException();

        for (Point p : pointsOrigin) {
            if (p == null) throw new IllegalArgumentException();
        }

        Point[] points = pointsOrigin.clone();
        Arrays.sort(points);

        for (int i = 1; i < points.length; i += 1) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        if (points.length < SEGMENT_MIN_SIZE) {
            segments = new LineSegment[0];
            return;
        }

        ArrayList<LineSegment> lineSegments = new ArrayList<>();

        for (int i = 0; i < points.length; i += 1) {
            Point p = points[i];

            brute:
            for (int j = i + 1; j < points.length; j += 1) {
                Point q = points[j];
                double pqSlope = p.slopeTo(q);

                for (int m = j + 1; m < points.length; m += 1) {
                    Point r = points[m];
                    double prSlope = p.slopeTo(r);
                    if (prSlope != pqSlope) continue;

                    for (int n = m + 1; n < points.length; n += 1) {
                        Point s = points[n];
                        double psSlope = p.slopeTo(s);
                        if (psSlope != pqSlope) continue;

                        lineSegments.add(new LineSegment(p, s));
                        continue brute;
                    }
                }
            }
        }

        segments = lineSegments.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i += 1) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

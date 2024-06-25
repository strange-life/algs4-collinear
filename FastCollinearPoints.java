import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private static final int SEGMENT_MIN_SIZE = 4;
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] pointsOrigin) {
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

        for (Point p : points) {
            Point[] sorted = points.clone();
            Arrays.sort(sorted, p.slopeOrder());

            for (int start = 1, end = 1, i = 1; i < sorted.length; i += 1) {
                if (p.slopeTo(sorted[start]) == p.slopeTo(sorted[i])) {
                    end = i;
                    if (i + 1 < sorted.length) continue;
                }

                if (end - start + 2 >= SEGMENT_MIN_SIZE && p.compareTo(sorted[start]) < 0) {
                    lineSegments.add(new LineSegment(p, sorted[end]));
                }

                start = i;
                end = i;
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

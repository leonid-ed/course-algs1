import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
  private final Point[] points;
  private final LineSegment[] segs;

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] initialPoints) {
    if (initialPoints == null) {
      throw new NullPointerException("argument is null");
    }

    points = initialPoints.clone();

    ArrayList<LineSegment> segList = new ArrayList<LineSegment>();

    double slope = 0.0;
    Arrays.sort(points);

    // find repeated points
    for (int i = 0; i < points.length-1; i++) {
      if (points[i].compareTo(points[i+1]) == 0)
        throw new IllegalArgumentException("repeated points");
    }

    for (int i = 0; i < points.length-3; i++) {
      boolean br = false;

      for (int j = i+1; j < points.length-2 && !br; j++) {

        slope = points[i].slopeTo(points[j]);

        for (int k = j+1; k < points.length-1 && !br; k++) {

          if (slope != points[i].slopeTo(points[k]))
            continue;

          for (int v = k+1; v < points.length && !br; v++) {

            if (slope != points[i].slopeTo(points[v]))
              continue;

            LineSegment seg = new LineSegment(points[i], points[v]);
            segList.add(seg);
            // br = true;

            // System.out.println(points[i] + ", " + points[j] + ", " +
            //                    points[k] + ", " + points[v]);
          }
        }
      }
    }

    segs = new LineSegment[segList.size()];
    int i = 0;
    for (LineSegment seg : segList) {
      segs[i] = seg;
      i++;
    }
  }

  // the number of line segments
  public int numberOfSegments()
  {
    return segs.length;
  }

  // the line segments
  public LineSegment[] segments()
  {
    return segs;
  }

  /**
   * Unit tests the BruteCollinearPoints data type.
   */
  public static void main(String[] args)
  {
    // read the N points from a file
    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    for (int i = 0; i < N; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point((short) x, (short) y);
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
      // try {
      //   System.in.read();
      // }
      // catch (java.io.IOException e) {
      //   ;
      // }
    }
  }
}
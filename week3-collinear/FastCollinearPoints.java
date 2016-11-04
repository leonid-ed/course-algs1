import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
// import java.io.InputStream;

public class FastCollinearPoints {
  private static final int F_INIT = 0;
  private static final int F_COLL = 1;
  private static final int F_SEG = 2;
  private static final int F_FF = 3;

  private final Point[] points;
  private final LineSegment[] segs;

  public FastCollinearPoints(Point[] initialPoints) {
    if (initialPoints == null) {
      throw new NullPointerException("argument is null");
    }

    points = initialPoints.clone();

    ArrayList<SegmentPoint> segList = new ArrayList<SegmentPoint>();
    Arrays.sort(points);

    // find repeated points
    for (int i = 0; i < points.length-1; i++) {
      if (points[i].compareTo(points[i+1]) == 0)
        throw new IllegalArgumentException("repeated points");
    }

    if (points.length < 4) {
      segs = new LineSegment[0];
      return;
    }

    int i = 0;
    Point pNext = null;
    Point pO = points[i];
    // System.out.println("start pO is " + pO);

    while (true) {
      i = 0;

      Point pA = pO;
      Point pB = pO;

      Arrays.sort(points, pO.slopeOrder());

      // System.out.print("points: ");
      // for (Point p : points) {
      //   System.out.print(p + " ");
      // }
      // System.out.println();

      double preSlope = pO.slopeTo(points[i]);
      double newSlope;
      double thisSlope;
      double nextSlope;
      int collinear_count = 1;
      int mode = F_INIT;

      for (i = 1; i < points.length; i++) {
        // determine pNext
        /* if pO < points[i] */
        if (pO.compareTo(points[i]) == -1) {
          if (pNext == null || pNext.compareTo(points[i]) == 1) {
            pNext = points[i];
          }
        }

        if (mode == F_FF)
          continue;

        if (i == points.length-1) {
          if (mode == F_COLL) {
            mode = F_SEG;
          }
        }
        else {
          thisSlope = pO.slopeTo(points[i]);
          nextSlope = pO.slopeTo(points[i+1]);

          if (thisSlope == nextSlope) {
            mode = F_COLL;
          }
          else {
            if (mode == F_COLL) {
              mode = F_SEG;
            }
            else {
              mode = F_INIT;
            }
          }
        }

        if (mode == F_INIT) {
          collinear_count = 1;
        }
        else if (mode == F_COLL || mode == F_SEG) {
          collinear_count++;

          // find new ends of potential segment
          if (pA.compareTo(points[i]) == 1) {
            // System.out.println(points[i] + " is a new pA = " + pA);

            pA = points[i];
          }

          if (pB.compareTo(points[i]) == -1) {
            // System.out.println(points[i] + " is a new pB = " + pB);

            pB = points[i];
          }

          if (mode == F_SEG) {
            if (collinear_count > 3) {
              if (addSegment(segList, pA, pB)) {
                // System.out.println("[" + pA + ", " + pB + "] added " +
                //                    collinear_count + " points segment");
                mode = F_FF;
              }
              else {
                collinear_count = 1;
                mode = F_INIT;
                pA = pO;
                pB = pO;
              }
            }
            else {
              collinear_count = 1;
              mode = F_INIT;
              pA = pO;
              pB = pO;
            }
          }
        }
      }

      if (pNext == null)
        break;

      pO = pNext;
      pNext = null;
      // System.out.println("new pO is " + pO);
    }

    segs = new LineSegment[segList.size()];
    i = 0;
    for (SegmentPoint seg : segList) {
      segs[i] = seg.toLineSegment();
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

  private boolean addSegment(ArrayList<SegmentPoint> list, Point a, Point b)
  {
    SegmentPoint seg = new SegmentPoint(a, b);
    if (!segmentExists(list, seg)) {
      list.add(seg);
      return true;
    }

    return false;
  }


  private boolean segmentExists(ArrayList<SegmentPoint> list, SegmentPoint seg)
  {
    for (SegmentPoint s : list) {
      if (s.equalsTo(seg))
        return true;
    }
    return false;
  }

  private class SegmentPoint
  {
    private final Point p;
    private final Point q;

    public SegmentPoint(Point p, Point q)
    {
      if (p == null || q == null) {
        throw new NullPointerException("argument is null");
      }
      this.p = p;
      this.q = q;
    }

    public boolean equalsTo(SegmentPoint that)
    {
      if (p.compareTo(that.p) == 0 && q.compareTo(that.q) == 0)
        return true;

      if (q.compareTo(that.p) == 0 && p.compareTo(that.q) == 0)
        return true;

      return false;
    }

    public LineSegment toLineSegment()
    {
      return new LineSegment(p, q);
    }
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

    FastCollinearPoints collinear = new FastCollinearPoints(points);
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
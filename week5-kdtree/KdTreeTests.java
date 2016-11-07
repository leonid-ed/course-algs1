import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class KdTreeTests
{
  /* [ HELPFUL METHODS ] */

  private static void vprintf(boolean verbose, String format, Object ... arguments)
  {
    if (verbose) {
      StdOut.printf(format, arguments);
    }
  }

  private static int printTestFinish(boolean rs)
  {
    StdOut.printf("... %s\n", (rs == true ? "OK" : "Failed :("));
    return (rs == true ? 0 : -1);
  }

  private static void printTestBegin(String tname, boolean verbose)
  {
    StdOut.printf("test function '%s' %s",
                  tname, (verbose ? "... \n" : ""));
  }

  /* [ TEST PUBLIC METHODS ] */

  private static int testIsEmpty(boolean verbose)
  {
    boolean rs = true;
    boolean r = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    KdTree kdtree = new KdTree();
    r = kdtree.isEmpty();
    rs &= (r == true);
    if (verbose) {
      StdOut.printf("check KdTree contains no points: isEmpty() == %b\n", r);
    }

    kdtree.insert(new Point2D(0.420313, 0.103516));
    r = kdtree.isEmpty();
    rs &= (r == false);
    if (verbose) {
      StdOut.printf("check KdTree contains 1 point: isEmpty() == %b\n", r);
    }

    kdtree.insert(new Point2D(0.500000, 1.000000));
    r = kdtree.isEmpty();
    rs &= (r == false);
    if (verbose) {
      StdOut.printf("check KdTree contains 2 points: isEmpty() == %b\n", r);
    }

    return printTestFinish(rs);
  }

  private static int testContains(boolean verbose)
  {
    boolean rs = true;
    boolean r = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    KdTree kdtree = new KdTree();
    Point2D p = new Point2D(0.0, 0.0);

    r = kdtree.contains(p);
    rs &= (r == false);
    if (verbose) {
      StdOut.printf("check KdTree contains no points: contains() == %b\n", r);
    }

    kdtree.insert(p);
    r = kdtree.contains(p);
    rs &= (r == true);
    if (verbose) {
      StdOut.printf("check KdTree contains the point: contains() == %b\n", r);
    }

    return printTestFinish(rs);
  }

  private static int testContains_grid1by1(boolean verbose)
  {
    boolean rs = true;
    boolean r = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    KdTree kdtree = new KdTree();
    Point2D p = new Point2D(1.0, 1.0);
    kdtree.insert(p);

    r = kdtree.contains(p);
    rs &= (r == true);
    vprintf(verbose, "check1 KdTree contains the points: contains() == %b\n", r);

    r = kdtree.contains(p);
    rs &= (r == true);
    vprintf(verbose, "check2 KdTree contains the points: contains() == %b\n", r);

    p = new Point2D(1.0, 1.0);
    r = kdtree.contains(p);
    rs &= (r == true);
    vprintf(verbose, "check KdTree doesn't contain given point: contains() == %b\n", r);

    return printTestFinish(rs);
  }

  private static int testSize(boolean verbose)
  {
    boolean rs = true;
    boolean r = true;
    int v = 0;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    KdTree kdtree = new KdTree();
    Point2D p = new Point2D(0.500000, 1.000000);
    Point2D q = new Point2D(0.600000, 1.000000);

    v = kdtree.size();
    rs &= (v == 0);
    vprintf(verbose, "check KdTree contains no points: size() == %d\n", v);

    kdtree.insert(p);
    v = kdtree.size();
    rs &= (v == 1);
    vprintf(verbose, "check KdTree contains 1 points: size() == %d\n", v);

    kdtree.insert(q);
    v = kdtree.size();
    rs &= (v == 2);
    vprintf(verbose, "check KdTree contains 2 points: size() == %d\n", v);

    kdtree.insert(q);
    v = kdtree.size();
    rs &= (v == 2);
    vprintf(verbose, "check KdTree after adding one point twice: size() == %d\n", v);

    return printTestFinish(rs);
  }

  private static int testSize_input100K(boolean verbose)
  {
    boolean rs = true;
    boolean r = true;
    int v = 0;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    String filename = "input100K.txt";
    In in = new In(filename);
    KdTree kdtree = new KdTree();
    int count = 0;
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      kdtree.insert(p);
      count++;

      v = kdtree.size();
      rs = (v == count);
      if (rs == false) {
        vprintf(verbose, "incorrect size() result: size() == %d\n", v);
        vprintf(verbose, "reference is %d\n", count);
        break;
      }
    }

    return printTestFinish(rs);
  }

  private static int testContains_input100K(boolean verbose)
  {
    boolean rs = true;
    boolean r = true;
    int v = 0;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    String filename = "input100K.txt";
    In in = new In(filename);
    KdTree kdtree = new KdTree();
    int count = 0;
    Queue<Point2D> queue = new Queue<Point2D>();
    int num = StdRandom.uniform(1, 100000);
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      kdtree.insert(p);
      count++;

      if (StdRandom.bernoulli(0.34)) {
        queue.enqueue(p);
      }

      v = kdtree.size();
      rs = (v == count);
      if (rs == false) {
        vprintf(verbose, "incorrect size() result: size() == %d\n", v);
        vprintf(verbose, "reference is %d\n", count);
        break;
      }
    }

    vprintf(verbose, "KdTree size() == %d\n", kdtree.size());
    vprintf(verbose, "Picked %d random points\n", queue.size());

    count = 0;
    for (Point2D p : queue) {
      r = kdtree.contains(p);
      if (!r)   count++;

    }

    rs &= (count == 0);
    if (!rs) {
      vprintf(verbose, "%d points are missed\n", count);
    }

    return printTestFinish(rs);
  }

  private static int testContains_random100K(boolean verbose)
  {
    boolean rs = true;
    boolean r = true;
    int v = 0;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    KdTree kdtree = new KdTree();
    int count = 100000;
    Queue<Point2D> queue = new Queue<Point2D>();
    int num = StdRandom.uniform(1, 100000);
    for (int i = 0; i < count; i++) {
      double x = StdRandom.uniform(0.0, 1.0);
      double y = StdRandom.uniform(0.0, 1.0);
      Point2D p = new Point2D(x, y);
      kdtree.insert(p);

      if (StdRandom.bernoulli(0.34)) {
        queue.enqueue(p);
      }

      v = kdtree.size();
      rs = (v == i+1);
      if (rs == false) {
        vprintf(verbose, "incorrect size() result: size() == %d\n", v);
        vprintf(verbose, "reference is %d\n", i+1);
        break;
      }
    }

    vprintf(verbose, "KdTree size() == %d\n", kdtree.size());
    vprintf(verbose, "Picked %d random points\n", queue.size());

    count = 0;
    for (Point2D p : queue) {
      r = kdtree.contains(p);
      if (!r)   count++;
    }

    rs &= (count == 0);
    if (verbose) {
      if (!rs) {
        vprintf(verbose, "%d points are missed\n", count);
      }
    }

    return printTestFinish(rs);
  }

  public static void main(String[] args)
    throws Exception
  {
    testIsEmpty(false);
    testContains(true);
    testContains_grid1by1(false);
    testSize(true);
    testSize_input100K(true);
    testContains_input100K(true);
    testContains_random100K(true);
  }
}
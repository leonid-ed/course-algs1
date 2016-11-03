import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

import java.util.TreeSet;


public class PointSET
{
  private TreeSet<Point2D> mSet;

  // construct an empty set of points
  public PointSET()
  {
    mSet = new TreeSet<Point2D>();
  }

  // is the set empty?
  public boolean isEmpty()
  {
    return mSet.isEmpty();
  }

  // number of points in the set
  public int size()
  {
    return mSet.size();
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p)
  {
    if (p == null)  throw new NullPointerException();
    mSet.add(p);
  }

  // does the set contain point p?
  public boolean contains(Point2D p)
  {
    if (p == null)  throw new NullPointerException();

    return mSet.contains(p);
  }

  // draw all points to standard draw
  public void draw()
  {
    for (Point2D p : mSet) {
      p.draw();
    }
  }

  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect)
  {
    if (rect == null)  throw new NullPointerException();

    Queue<Point2D> queue = new Queue<Point2D>();
    for (Point2D p : mSet) {
      if (rect.contains(p))
        queue.enqueue(p);
    }

    return queue;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D point)
  {
    if (point == null)  throw new NullPointerException();
    if (mSet.size() == 0) return null;

    Point2D lowestP = null;
    double dist = Double.POSITIVE_INFINITY;
    for (Point2D p : mSet) {
      Double d = point.distanceTo(p);
      if (dist > d) {
        dist = d;
        lowestP = p;
      }
    }

    return lowestP;
  }

  // unit testing of the methods (optional)
  public static void main(String[] args)
  {
    RectHV rect = new RectHV(0.4,0.3, 0.8,0.6);
    StdOut.printf("%s\n", rect);
    // rect.draw();
  }
}



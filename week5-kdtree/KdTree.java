import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class KdTree
{
  private class Node
  {
    private Point2D p;  // the point
    private RectHV rect;// the rectangle
    Node lb;            // the left/bottom subtree
    Node rt;            // the right/top subtree
    // debug
    int num;
    // boolean even;

    public Node(double x0, double y0, double x1, double y1)
    {
      rect = new RectHV(x0,y0, x1,y1);
      // StdOut.printf("created %s\n", rect);
      p = null;
      lb = null;
      rt = null;
    }

    private void setPoint(Point2D point, boolean even)
    {
      if (point == null)  throw new NullPointerException();

      p = point;
      if (even) {
        lb = new Node(rect.xmin(), rect.ymin(), point.x(), rect.ymax());
        rt = new Node(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
      }
      else {
        lb = new Node(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
        rt = new Node(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
      }
    }
  }

  private Node mRoot;
  private int mSize;
  private boolean mFindEvenLevel;
  private Node mNearestNode;
  private double mNearestDist;

  // construct an empty set of points
  public KdTree()
  {
    mSize = 0;
    mRoot = new Node(0.0, 0.0, 1.0, 1.0);
    mFindEvenLevel = true;
    mNearestDist = Double.POSITIVE_INFINITY;
    mNearestNode = null;
  }

  // is the set empty?
  public boolean isEmpty()
  {
    return mSize == 0;
  }

  // number of points in the set
  public int size()
  {
    return mSize;
  }

  private Node find(Node root, Point2D point)
  {
    if (point == null)  throw new NullPointerException();

    if (root == mRoot) {
      mFindEvenLevel = true;
    }

    if (root.p == null)   return root;

    if (root.p == point)
      return root;

    Node next = null;
    if (mFindEvenLevel) {
      if (point.x() < root.p.x())
        next = root.lb;
      else
        next = root.rt;
    }
    else {
      if (point.y() < root.p.y())
        next = root.lb;
      else
        next = root.rt;
    }

    mFindEvenLevel = !mFindEvenLevel;
    return find(next, point);
  }

  private void drawPoint(Node node, boolean even)
  {
    if (node.rect == null)  return;

    if (node.rt != null)  drawPoint(node.rt, !even);
    if (node.lb != null)  drawPoint(node.lb, !even);

    if (node.p != null) {
      StdDraw.setPenRadius(0.01);
      StdDraw.setPenColor(StdDraw.BLACK);
      node.p.draw();
    }
    StdDraw.setPenRadius(0.002);
    if (even) {
      StdDraw.setPenColor(StdDraw.RED);
    }
    else {
      StdDraw.setPenColor(StdDraw.BLUE);
    }

    if (node != mRoot)
      node.rect.draw();
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D point)
  {
    if (point == null)  throw new NullPointerException();

    Node node = find(mRoot, point);
    if (node.p == null) {
      node.setPoint(point, mFindEvenLevel);
      mSize++;

      node.num = mSize;

      // StdOut.printf("%s\n", point);
    }
  }

  private void print(Node root, int tabsize, boolean even)
  {
    if (root == null)  throw new NullPointerException();

    if (root.rt != null) {
      print(root.rt, tabsize+1, !even);
    }

    for (int i = 0; i < tabsize; i++)
      StdOut.printf("  ");

    if (even)
      StdOut.printf("-");
    else
      StdOut.printf("|");

    if (root.p != null) {
      StdOut.printf("%d: %s\n", root.num, root.p);
    }
    else {
      StdOut.printf("(nil)\n");
    }

    if (root.lb != null) {
      print(root.lb, tabsize+1, !even);
    }
  }

  public void printDebug()
  {
    print(mRoot, 0, true);
  }

  // does the set contain point p?
  public boolean contains(Point2D p)
  {
    if (p == null)  throw new NullPointerException();
    Node n = find(mRoot, p);
    return (n.p != null);
  }

  // draw all points to standard draw
  public void draw()
  {
    drawPoint(mRoot, false);
  }

  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect)
  {
    if (rect == null)  throw new NullPointerException();

    Queue<Point2D> queue = new Queue<Point2D>();

    return queue;
  }

  private void findNearest(Node node, Point2D point, Point2D potentPoint, boolean even)
  {
    if (node == mRoot) {
      mNearestDist = Double.POSITIVE_INFINITY;
      potentPoint = new Point2D(node.p.x(), point.y());
    }

    double dist = point.distanceTo(node.p);
    if (dist < mNearestDist) {
      mNearestNode = node;
      mNearestDist = dist;
    }

    // <-- debug output
    if (even)
       StdOut.printf("-");
     else
       StdOut.printf("|");
    StdOut.printf("%d   %s, potential point %s\n", node.num, node.p, potentPoint);
    // -->


    // regular search
    Node next = null;
    if (even) {
      if (point.x() < node.p.x()) {
        next = node.lb;
      }
      else {
        next = node.rt;
      }
    }
    else {
      if (point.y() < node.p.y()) {
        next = node.lb;
      }
      else {
        next = node.rt;
      }
    }

    Point2D nextPotentPoint = null;
    if (next.p != null) {
      // make up a next potential point
      if (even) {
        nextPotentPoint = new Point2D(point.x(), next.p.y());
      }
      else {
        nextPotentPoint = new Point2D(next.p.x(), point.y());
      }
      findNearest(next, point, nextPotentPoint, !even);
    }
    else {
      StdOut.printf("  %d   no any point in next half\n", node.num);
    }

    // check potential point
    if (point.distanceTo(potentPoint) < mNearestDist)
    {
      if (next == node.lb)
        next = node.rt;
      else
        next = node.lb;

      if (next.p != null) {
        // make up a new next potential point
        if (mFindEvenLevel) {
          nextPotentPoint = new Point2D(node.p.x(), next.p.y());
        }
        else {
          nextPotentPoint = new Point2D(next.p.x(), node.p.y());
        }

        findNearest(next, point, nextPotentPoint, !even);
      }
      else {
        StdOut.printf("  %d   no any point in alt half\n", node.num);
      }
    }
    else {
      StdOut.printf("  %d   no nearest point in alt half\n", node.num);
    }
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D point)
  {
    if (point == null)   throw new NullPointerException();
    if (isEmpty())   return null;

    findNearest(mRoot, point, null, true);
    return mNearestNode.p;
  }

  // unit testing of the methods (optional)
  public static void main(String[] args)
  {
    String filename = "ex1.txt";
    In in = new In(filename);
    KdTree kdtree = new KdTree();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      kdtree.insert(p);
    }

    StdOut.printf("KdTree:\n");
    kdtree.printDebug();

    Point2D q = new Point2D(0.042734, 0.523672);
    StdOut.printf("Goal point %s\n", q);

    Point2D p = kdtree.nearest(q);
    StdOut.printf("Nearest point: %s\n", p);
  }
}
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
  private Queue<Point2D> mRanged;

  // construct an empty set of points
  public KdTree()
  {
    mSize = 0;
    mRoot = new Node(0.0, 0.0, 1.0, 1.0);
    mFindEvenLevel = true;
    mNearestDist = Double.POSITIVE_INFINITY;
    mNearestNode = null;
    mRanged = null;
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

  private void findRanged(Node node, RectHV rect, boolean even)
  {
    boolean lbInter = rect.intersects(node.lb.rect);
    boolean rtInter = rect.intersects(node.rt.rect);

    // <-- debug output
    if (even)
       StdOut.printf("-");
     else
       StdOut.printf("|");
    StdOut.printf("%d   %s", node.num, node.p);
    if (lbInter)
      StdOut.printf(" (lb intersected)");
    if (rtInter)
      StdOut.printf(" (rt intersected)");
    StdOut.printf("\n");
    // -->

    if (rect.contains(node.p)) {
      StdOut.printf("  %d point is in the given rect\n", node.num);
      mRanged.enqueue(node.p);
    }

    Node next = null;

    if (lbInter) {
      if (node.lb.p != null) {
        next = node.lb;
        findRanged(next, rect, !even);
      }
      else {
        StdOut.printf("  %d no points in left/bottom half\n", node.num);
      }
    }

    if (rtInter) {
      if (node.rt.p != null) {
        next = node.rt;
        findRanged(next, rect, !even);
      }
      else {
        StdOut.printf("  %d no points in right/top half\n", node.num);
      }
    }
  }

  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect)
  {
    if (rect == null)  throw new NullPointerException();
    if (isEmpty())   return null;

    mRanged = new Queue<Point2D>();
    findRanged(mRoot, rect, true);

    return mRanged;
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
      StdOut.printf("  %d   no any points in next half\n", node.num);
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
        StdOut.printf("  %d   no any points in alt half\n", node.num);
      }
    }
    else {
      StdOut.printf("  %d   no nearest points in alt half\n", node.num);
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

    //
    // Point2D q = new Point2D(0.042734, 0.523672);
    // StdOut.printf("Goal point %s\n", q);
    // Point2D p = kdtree.nearest(q);
    // StdOut.printf("Nearest point: %s\n", p);

    RectHV rect = new RectHV(0.04, 0.3, 0.09, 0.67);
    StdOut.printf("Points are in rect %s\n", rect);
    for (Point2D p : kdtree.range(rect)) {
      StdOut.printf("%s\n", p);
    }
  }
}
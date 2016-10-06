import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private static byte maskIsOpen = 0x01;
  private static byte maskIsFull = 0x02;
  private static byte maskIsTop  = 0x04;
  private static byte maskIsLow  = 0x08;

  private byte[] mMap;
  private WeightedQuickUnionUF mModel;     // WQUF model
  private int mSize;
  private boolean mIsPercolatable;

  /* create N-by-N grid, with all sites blocked */
  public Percolation(int N)
  {
    if (N <= 0) {
      throw new java.lang.IllegalArgumentException();
    }

    mSize = N;
    mMap = new byte[N*N];
    mModel = new WeightedQuickUnionUF(N*N);
  }

  private int getIndex(int i, int j)
  {
    return mSize * (i - 1) + (j - 1);
  }

  private byte setComponentFlags(int i, int j, byte flags)
  {
    mMap[mModel.find(getIndex(i, j))] |= flags;
    return mMap[mModel.find(getIndex(i, j))];
  }

  private byte getComponentFlags(int i, int j)
  {
    return mMap[mModel.find(getIndex(i, j))];
  }

  /* open site (row i, column j) if it is not open already */
  public void open(int i, int j)
  {
    if (i < 1 || i > mSize || j < 1 || j > mSize)
      throw new java.lang.IndexOutOfBoundsException();

    if (isOpen(i, j))
      return;

    byte old_flags = getComponentFlags(i, j);
    byte flags = old_flags;
    mMap[getIndex(i, j)] |= maskIsOpen;
    flags |= maskIsOpen;

    byte ne_flags = 0;
    if (i > 1) {
      if (isOpen(i-1, j))
      {
        ne_flags = getComponentFlags(i-1, j);
        mModel.union(getIndex(i, j), getIndex(i-1, j));
        flags |= ne_flags;
      }
    }
    else {
      flags |= maskIsFull;
    }

    if (i < mSize) {
      if (isOpen(i+1, j) &&
          !mModel.connected(getIndex(i, j), getIndex(i+1, j)))
      {
        ne_flags = getComponentFlags(i+1, j);
        mModel.union(getIndex(i, j), getIndex(i+1, j));
        flags |= ne_flags;
      }
    }
    else {
      flags |= maskIsLow;
    }

    if (j > 1) {
      if (isOpen(i, j-1) &&
          !mModel.connected(getIndex(i, j), getIndex(i, j-1)))
      {
        ne_flags = getComponentFlags(i, j-1);
        mModel.union(getIndex(i, j), getIndex(i, j-1));
        flags |= ne_flags;
      }
    }

    if (j < mSize) {
      if (isOpen(i, j+1) &&
          !mModel.connected(getIndex(i, j), getIndex(i, j+1)))
      {
        ne_flags = getComponentFlags(i, j+1);
        mModel.union(getIndex(i, j), getIndex(i, j+1));
        flags |= ne_flags;
      }
    }

    if (!mIsPercolatable) {
      int check_perc = flags & (maskIsFull | maskIsLow);
      if (check_perc == (maskIsFull | maskIsLow))
        mIsPercolatable = true;
    }

    setComponentFlags(i, j, flags);
  }

  /* is site (row i, column j) open? */
  public boolean isOpen(int i, int j)
  {
    if (i < 1 || i > mSize || j < 1 || j > mSize)
      throw new java.lang.IndexOutOfBoundsException();

    int t = mMap[getIndex(i, j)] & maskIsOpen;
    if (t > 0)
      return true;

    return false;
  }

  /* is site (row i, column j) full? */
  public boolean isFull(int i, int j)
  {
    if (i < 1 || i > mSize || j < 1 || j > mSize)
      throw new java.lang.IndexOutOfBoundsException();

    int t = getComponentFlags(i, j) & maskIsFull;
    if (t > 0)
      return true;

    return false;
  }

  /* does the system percolate? */
  public boolean percolates()
  {
    return mIsPercolatable;
  }

  /* test client (optional) */
  public static void main(String[] args)
  {
    // System.out.println(args);
    Percolation perc = new Percolation(2);
    perc.open(2, 2);
    perc.open(2, 1);
    perc.open(1, 1);
    if (perc.percolates()) {
      System.out.println("percolates");
    }
    else {
      System.out.println("doesn't percolate");
    }
  }
}
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private double mMean;
  private double mStdDev;
  private int mT;

  // perform T independent experiments on an N-by-N grid
  public PercolationStats(int N, int T)
  {
    if (N <= 0 || T <= 0) {
      throw new java.lang.IllegalArgumentException();
    }

    mT = T;
    mMean = 0;
    mStdDev = 0;
    double[] x = new double[T];

    int row, col, opened_num;
    for (int t = 0; t < T; t++) {
      Percolation perc = new Percolation(N);
      opened_num = 0;
      do {
        do {
          row = StdRandom.uniform(1, N+1);
          col = StdRandom.uniform(1, N+1);
          // System.out.println("row = " + row + ", col = " + col);
        } while (perc.isOpen(row, col));

        perc.open(row, col);
        opened_num++;
      } while (!perc.percolates());

      x[t] = opened_num * 1.0 / (N*N);
      mMean += x[t] / T;
      // System.out.println("xt = " + xt);
    }

    mStdDev = StdStats.stddev(x);
  }

  // sample mean of percolation threshold
  public double mean()
  {
    return mMean;
  }

  // sample standard deviation of percolation threshold
  public double stddev()
  {
    return mStdDev;
  }

  // low  endpoint of 95% confidence interval
  public double confidenceLo()
  {
    return mMean - mStdDev * 1.96 / Math.sqrt(mT*1.0);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi()
  {
    return mMean + mStdDev * 1.96 / Math.sqrt(mT*1.0);
  }

  // test client (described below)
  public static void main(String[] args)
  {
    if (args.length < 2) {
      System.out.println("too few args");
      return;
    }

    int N = Integer.parseInt(args[0]);
    int T = Integer.parseInt(args[1]);
    System.out.println("N = " + N + ", T = " + T);
    PercolationStats percStats = new PercolationStats(N, T);

    System.out.println("mean = " + percStats.mean());
    System.out.println("stddev = " + percStats.stddev());
    System.out.println("95% confidence interval = " + percStats.confidenceLo()
                       + ", " + percStats.confidenceHi());
  }
}
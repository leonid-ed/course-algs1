import edu.princeton.cs.algs4.StdOut;

public class Board
{
  private final int[][] blocks;
  private final int size;

  // construct a board from an N-by-N array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] initBlocks)
  {
    blocks = initBlocks.clone();
    size = blocks[0].length;
  }

  /* [ TEST METHODS ] */

  private static int testGetBlockNumberXY(boolean verbose)
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    StdOut.printf("test function '%s' %s",
                  tname, (verbose ? "... \n" : ""));

    Board board = new Board(new int[3][3]);
    for (int i = 1; i < 10; ++i) {
      int[] vals = new int[2];
      board.getBlockNumberXY(i, vals);

      if ( (i == 1 && (vals[0] != 1 || vals[1] != 1)) ||
           (i == 2 && (vals[0] != 1 || vals[1] != 2)) ||
           (i == 3 && (vals[0] != 1 || vals[1] != 3)) ||
           (i == 4 && (vals[0] != 2 || vals[1] != 1)) ||
           (i == 5 && (vals[0] != 2 || vals[1] != 2)) ||
           (i == 6 && (vals[0] != 2 || vals[1] != 3)) ||
           (i == 7 && (vals[0] != 3 || vals[1] != 1)) ||
           (i == 8 && (vals[0] != 3 || vals[1] != 2)) ||
           (i == 9 && (vals[0] != 3 || vals[1] != 3)) )
      {
        rs = false;
      }

      if (verbose)
        StdOut.printf("%d : (%d, %d)\n", i, vals[0], vals[1]);
    }

    StdOut.printf("... %s\n", (rs == true ? "OK" : "Failed :("));
    return (rs == true ? 0 : -1);
  }

  private static int testGetBlockNumber(boolean verbose)
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    StdOut.printf("test function '%s' %s",
                  tname, (verbose ? "... \n" : ""));

    Board board = new Board(new int[3][3]);
    for (int i = 0; i < board.size; ++i) {
      for (int j = 0; j < board.size; ++j) {
        int v = board.getBlockNumber(i, j);
        if ( (i == 0 && j == 0 && (v != 1)) ||
             (i == 0 && j == 1 && (v != 2)) ||
             (i == 0 && j == 2 && (v != 3)) ||
             (i == 1 && j == 0 && (v != 4)) ||
             (i == 1 && j == 1 && (v != 5)) ||
             (i == 1 && j == 2 && (v != 6)) ||
             (i == 2 && j == 0 && (v != 7)) ||
             (i == 2 && j == 1 && (v != 8)) ||
             (i == 2 && j == 2 && (v != 9)) )
        {
          rs = false;
        }

        if (verbose)
          StdOut.printf("[%d,%d] : %d\n", i, j, v);
      }
    }

    StdOut.printf("... %s\n", (rs == true ? "OK" : "Failed :("));
    return (rs == true ? 0 : -1);
  }

  private static int testHamming(boolean verbose)
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    StdOut.printf("test function '%s' %s",
                  tname, (verbose ? "... \n" : ""));

    int initBlocks[][] = new int[3][3];
    initBlocks[0][0] = 8;
    initBlocks[0][1] = 1;
    initBlocks[0][2] = 3;
    initBlocks[1][0] = 4;
    initBlocks[1][1] = 0;
    initBlocks[1][2] = 2;
    initBlocks[2][0] = 7;
    initBlocks[2][1] = 6;
    initBlocks[2][2] = 5;
    Board board = new Board(initBlocks);

    int v = board.hamming();
    if (v != 5) rs = false;

    if (verbose) {
      StdOut.printf("%s\n", board.toString());
      StdOut.printf("board.hamming() = %d\n", v);
    }

    StdOut.printf("... %s\n", (rs == true ? "OK" : "Failed :("));
    return (rs == true ? 0 : -1);
  }

  /* [ PRIVATE METHODS ] */

  private int getBlockNumber(int i, int j)
  {
    return size * i + j + 1;
  }

  private int getBlockNumberXY(int num, int[] vals)
  {
    int m = num % size;
    vals[0] = num / size + (m > 0 ? 1 : 0);
    vals[1] = (m == 0 ? size : m);
    return 0;
  }

  /* [ PUBLIC METHODS ] */

  // board dimension N
  public int dimension()
  {
    return size;
  }

  // number of blocks out of place
  public int hamming()
  {
    int rs = 0;
    for (int i = 0; i < size; ++i) {
      for (int j = 0; j < size; ++j) {
        if (blocks[i][j] == 0) continue;
        if (blocks[i][j] != getBlockNumber(i, j))
          ++rs;
      }
    }
    return rs;
  }

  // sum of Manhattan distances between blocks and goal
  public int manhattan()
  {
    int rs = 0;
    int v1[] = new int[2];
    int v2[] = new int[2];

    for (int i = 0; i < size; ++i) {
      for (int j = 0; j < size; ++j) {
        if (blocks[i][j] == 0) continue;

        v1[0] = i + 1;
        v1[1] = j + 1;
        getBlockNumberXY(blocks[i][j], v2);
        rs += Math.abs(v2[0] - v1[0]) + Math.abs(v2[1] - v1[1]);
      }
    }
    return rs;
  }

  // is this board the goal board?
  public boolean isGoal()
  {
    return true;
  }

  // a board that is obtained by exchanging any pair of blocks
  public Board twin()
  {
    return new Board(blocks);
  }

  // does this board equal y?
  public boolean equals(Object y)
  {
    return true;
  }

  // all neighboring boards
  public Iterable<Board> neighbors()
  {
    return null;
  }

  // string representation of this board (in the output format specified below)
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append(size + "\n");
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        s.append(String.format("%2d ", blocks[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  // unit tests (not graded)
  public static void main(String[] args)
  {
    testGetBlockNumberXY(false);
    testGetBlockNumber(true);
    testHamming(true);

  }
}

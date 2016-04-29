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

  /* [ PRIVATE METHODS ] */

  /**
  * Clones the provided array
  *
  * @param src
  * @return a new clone of the provided array
  */
  public static int[][] cloneArray(int[][] src) {
    int length = src.length;
    int[][] target = new int[length][src[0].length];
    for (int i = 0; i < length; i++) {
      System.arraycopy(src[i], 0, target[i], 0, src[i].length);
    }
    return target;
  }

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
    int[] v1 = new int[2];
    int[] v2 = new int[2];

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
    for (int i = 0; i < size; ++i) {
      for (int j = 0; j < size-1; ++j) {
        if (blocks[i][j] != getBlockNumber(i, j))
          return false;
      }
    }
    return (0 == blocks[size-1][size-1]);
  }

  // a board that is obtained by exchanging any pair of blocks
  public Board twin()
  {
    int[] v1 = null;
    int[] v2 = null;
    for (int i = 0; i < size; ++i) {
      for (int j = 0; j < size; ++j) {
        if (blocks[i][j] != 0) {
          if (v1 == null) {
            v1 = new int[2];
            v1[0] = i;
            v1[1] = j;
          }
          else {
            v2 = new int[2];
            v2[0] = i;
            v2[1] = j;

            /* make a twin board */
            int[][] twinBlocks = cloneArray(blocks);
            int t = twinBlocks[v1[0]][v1[1]];
            twinBlocks[v1[0]][v1[1]] = twinBlocks[v2[0]][v2[1]];
            twinBlocks[v2[0]][v2[1]] = t;

            Board twin_board = new Board(twinBlocks);
            return twin_board;
          }
        }
      }
    }

    assert true : "twin() failed";
    return null;
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

  }
}

import java.lang.reflect.*;
import edu.princeton.cs.algs4.StdOut;

public class BoardTests
{
  /* [ HELPFUL METHODS ] */

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

  /* [ TEST PRIVATE METHODS ] */

  private static int testGetBlockNumberXY(boolean verbose)
    throws IllegalAccessException, NoSuchMethodException,
           InvocationTargetException
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    Method methGetBlockNumberXY =
      Board.class.getDeclaredMethod("getBlockNumberXY", int.class, int[].class);
    methGetBlockNumberXY.setAccessible(true);

    Board board = new Board(new int[3][3]);
    for (int i = 1; i < 10; ++i) {
      int[] vals = new int[2];
      methGetBlockNumberXY.invoke(board, i, vals);

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
      throws IllegalAccessException, NoSuchMethodException,
           InvocationTargetException
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    Method methGetBlockNumber =
      Board.class.getDeclaredMethod("getBlockNumber",  int.class, int.class);
    methGetBlockNumber.setAccessible(true);

    Board board = new Board(new int[3][3]);
    for (int i = 0; i < board.dimension(); ++i) {
      for (int j = 0; j < board.dimension(); ++j) {
        int v = (int)methGetBlockNumber.invoke(board, i, j);

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

    return printTestFinish(rs);
  }

  /* [ TEST PUBLIC METHODS ] */

  private static int testHamming(boolean verbose)
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

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
    rs = (5 == v);

    if (verbose) {
      StdOut.printf("%s\n", board.toString());
      StdOut.printf("board.hamming() = %d\n", v);
    }

    return printTestFinish(rs);
  }

  private static int testManhattan(boolean verbose)
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

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

    int v = board.manhattan();
    rs = (10 == v);

    if (verbose) {
      StdOut.printf("%s\n", board.toString());
      StdOut.printf("board.manhattan() = %d\n", v);
    }

    return printTestFinish(rs);
  }

  private static int testIsGoal(boolean verbose)
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    int initBlocks[][] = new int[3][3];
    /* it is not a goal board */
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

    if (verbose) {
      StdOut.printf("%s\n", board.toString());
      StdOut.printf("board.isGoal() = %b\n", board.isGoal());
    }

    rs = (false == board.isGoal());
    if (!rs) return printTestFinish(rs);

    /* it is a goal board */
    initBlocks[0][0] = 1;
    initBlocks[0][1] = 2;
    initBlocks[0][2] = 3;
    initBlocks[1][0] = 4;
    initBlocks[1][1] = 5;
    initBlocks[1][2] = 6;
    initBlocks[2][0] = 7;
    initBlocks[2][1] = 8;
    initBlocks[2][2] = 0;
    board = new Board(initBlocks);

    if (verbose) {
      StdOut.printf("%s\n", board.toString());
      StdOut.printf("board.isGoal() = %b\n", board.isGoal());
    }

    rs = (true == board.isGoal());
    if (!rs) printTestFinish(rs);

    /* it is not a goal board */
    initBlocks[0][0] = 1;
    initBlocks[0][1] = 2;
    initBlocks[0][2] = 3;
    initBlocks[1][0] = 4;
    initBlocks[1][1] = 5;
    initBlocks[1][2] = 6;
    initBlocks[2][0] = 7;
    initBlocks[2][1] = 8;
    initBlocks[2][2] = 9;
    board = new Board(initBlocks);

    if (verbose) {
      StdOut.printf("%s\n", board.toString());
      StdOut.printf("board.isGoal() = %b\n", board.isGoal());
    }

    return printTestFinish(rs);
  }

  private static int testTwin(boolean verbose)
    throws IllegalAccessException, NoSuchFieldException
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    Field f = Board.class.getDeclaredField("blocks");
    f.setAccessible(true);

    int initBlocks[][] = new int[3][3];
    initBlocks[0][0] = 1;
    initBlocks[0][1] = 2;
    initBlocks[0][2] = 3;
    initBlocks[1][0] = 4;
    initBlocks[1][1] = 5;
    initBlocks[1][2] = 6;
    initBlocks[2][0] = 7;
    initBlocks[2][1] = 8;
    initBlocks[2][2] = 0;
    Board board = new Board(initBlocks);

    int[][] blocks = cloneArray((int[][])f.get(board));
    /* twin manualy */
    int t = blocks[0][0];
    blocks[0][0] = blocks[0][1];
    blocks[0][1] = t;
    /* get twin board */
    Board twin_board = board.twin();

    if (verbose) {
      StdOut.printf("origin board:\n%s\n", board.toString());
      StdOut.printf("twin board:\n%s\n", twin_board.toString());
    }

    int[][] twin_blocks = (int[][])f.get(twin_board);
    /* compare */
    for (int i = 0; i < board.dimension(); i++) {
      for (int j = 0; j < board.dimension(); j++) {
        if (twin_blocks[i][j] != blocks[i][j]) {
          rs = false;
          return printTestFinish(rs);
        }
      }
    }

    /* check 2 */

    initBlocks[0][0] = 1;
    initBlocks[0][1] = 0;
    initBlocks[0][2] = 3;
    initBlocks[1][0] = 4;
    initBlocks[1][1] = 5;
    initBlocks[1][2] = 6;
    initBlocks[2][0] = 7;
    initBlocks[2][1] = 8;
    initBlocks[2][2] = 2;

    board = new Board(initBlocks);
    blocks = cloneArray((int[][])f.get(board));

    /* twin manualy */
    t = blocks[0][0];
    blocks[0][0] = blocks[0][2];
    blocks[0][2] = t;
    /* get twin board */
    twin_board = board.twin();

    if (verbose) {
      StdOut.printf("origin board:\n%s\n", board.toString());
      StdOut.printf("twin board:\n%s\n", twin_board.toString());
    }

    twin_blocks = (int[][])f.get(twin_board);
    /* compare */
    for (int i = 0; i < board.dimension(); i++) {
      for (int j = 0; j < board.dimension(); j++) {
        if (twin_blocks[i][j] != blocks[i][j]) {
          rs = false;
          return printTestFinish(rs);
        }
      }
    }

    return printTestFinish(rs);
  }

  private static int testEquals(boolean verbose)
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    int initBlocks[][] = new int[3][3];
    initBlocks[0][0] = 1;
    initBlocks[0][1] = 2;
    initBlocks[0][2] = 3;
    initBlocks[1][0] = 4;
    initBlocks[1][1] = 5;
    initBlocks[1][2] = 6;
    initBlocks[2][0] = 7;
    initBlocks[2][1] = 8;
    initBlocks[2][2] = 0;
    Board board1 = new Board(initBlocks);

    /* not equal board */
    initBlocks[2][1] = 0;
    initBlocks[2][2] = 8;
    Board board2 = new Board(initBlocks);

    rs = (false == board1.equals(board2));

    if (verbose) {
      StdOut.printf("board1:\n%s\n", board1.toString());
      StdOut.printf("board2:\n%s\n", board2.toString());
      StdOut.printf("board1.equals(board2) = %b\n", board1.equals(board2));
    }

    if (!rs) return printTestFinish(rs);

    /* equal board */
    initBlocks[2][1] = 8;
    initBlocks[2][2] = 0;
    board2 = new Board(initBlocks);

    rs = (true == board1.equals(board2));

    if (verbose) {
      StdOut.printf("board1:\n%s\n", board1.toString());
      StdOut.printf("board2:\n%s\n", board2.toString());
      StdOut.printf("board1.equals(board2) = %b\n", board1.equals(board2));
    }

    return printTestFinish(rs);
  }

  public static void main(String[] args)
    throws Exception
  {
    testGetBlockNumberXY(false);
    testGetBlockNumber(false);
    testHamming(false);
    testManhattan(false);
    testIsGoal(false);
    testTwin(false);
    testEquals(true);
  }
}

import java.lang.reflect.*;
import edu.princeton.cs.algs4.Queue;
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
    initBlocks[2][1] = 0;
    initBlocks[2][2] = 8;
    board = new Board(initBlocks);

    if (verbose) {
      StdOut.printf("%s\n", board.toString());
      StdOut.printf("board.isGoal() = %b\n", board.isGoal());
    }

    if (!rs) return printTestFinish(rs);

    /* 100x100 */
    initBlocks = new int[100][100];
    for (int i = 0; i < 100; ++i)
      for (int j = 0; j < 100; ++j)
        if (i == 99 && j == 99)
          continue;
        else
          initBlocks[i][j] = i * 100 + j + 1;

    board = new Board(initBlocks);

    if (verbose) {
      StdOut.printf("%s\n", board.toString());
      StdOut.printf("board.isGoal() = %b\n", board.isGoal());
    }

    rs = (true == board.isGoal());

    return printTestFinish(rs);
  }

  private static int testTwin(boolean verbose)
    throws IllegalAccessException, NoSuchMethodException,
           InvocationTargetException, NoSuchFieldException
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

    Method methCloneBoard =
      Board.class.getDeclaredMethod("cloneBoard", (Class<?>[]) null);
    methCloneBoard.setAccessible(true);

    Board boardCloned = (Board)methCloneBoard.invoke(board);
    char[] blocks = (char[])f.get(boardCloned);
    /* twin manualy */
    char t = blocks[0];
    blocks[0] = blocks[1];
    blocks[1] = t;
    /* get twin board */
    Board twin_board = board.twin();
    assert twin_board != null : "twin_board == null";

    if (verbose) {
      StdOut.printf("origin board:\n%s\n", board.toString());
      StdOut.printf("twin board:\n%s\n", twin_board.toString());
    }

    char[] twin_blocks = (char[])f.get(twin_board);
    /* compare */
    for (int i = 0; i < board.dimension()*board.dimension(); ++i) {
      if (twin_blocks[i] != blocks[i]) {
        rs = false;
        return printTestFinish(rs);
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

    boardCloned = (Board)methCloneBoard.invoke(board);
    blocks = (char[])f.get(boardCloned);
    /* twin manualy */
    t = blocks[0];
    blocks[0] = blocks[2];
    blocks[2] = t;
    /* get twin board */
    twin_board = board.twin();

    if (verbose) {
      StdOut.printf("origin board:\n%s\n", board.toString());
      StdOut.printf("twin board:\n%s\n", twin_board.toString());
    }

    twin_blocks = (char[])f.get(twin_board);
    /* compare */
    for (int i = 0; i < board.dimension()*board.dimension(); ++i) {
      if (twin_blocks[i] != blocks[i]) {
        rs = false;
        break;
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

  private static int testNeighbors(boolean verbose)
  {
    boolean rs = true;
    String tname = new Object(){}.getClass().getEnclosingMethod().getName();
    printTestBegin(tname, verbose);

    int initBlocks[][] = new int[3][3];
    initBlocks[0][0] = 0;
    initBlocks[0][1] = 1;
    initBlocks[0][2] = 3;
    initBlocks[1][0] = 4;
    initBlocks[1][1] = 2;
    initBlocks[1][2] = 5;
    initBlocks[2][0] = 7;
    initBlocks[2][1] = 8;
    initBlocks[2][2] = 6;
    Board board = new Board(initBlocks);
    Queue<Board> queue = (Queue<Board>)board.neighbors();

    if (verbose) {
      StdOut.printf("origin:\n%s\nneighbors:\n", board.toString());

      for (Board b: queue) {
        StdOut.printf("%s\n", b.toString());
      }
    }

    rs = (2 == queue.size());
    if (!rs) return printTestFinish(rs);

    /* check 2 */

    initBlocks[0][0] = 1;
    initBlocks[0][1] = 0;
    initBlocks[0][2] = 3;
    initBlocks[1][0] = 4;
    initBlocks[1][1] = 2;
    initBlocks[1][2] = 5;
    initBlocks[2][0] = 7;
    initBlocks[2][1] = 8;
    initBlocks[2][2] = 6;
    board = new Board(initBlocks);
    queue = (Queue<Board>)board.neighbors();

    if (verbose) {
      StdOut.printf("origin:\n%s\nneighbors:\n", board.toString());

      for (Board b: queue) {
        StdOut.printf("%s\n", b.toString());
      }
    }

    rs = (3 == queue.size());
    return printTestFinish(rs);
  }

  public static void main(String[] args)
    throws Exception
  {
    testHamming(true);
    testManhattan(false);
    testIsGoal(false);
    testTwin(false);
    testEquals(false);
    testNeighbors(false);
  }
}

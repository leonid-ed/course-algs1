import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board
{
  private final byte[][] blocks;
  private final byte size;
  private byte[] emptyBlock;

  // construct a board from an N-by-N array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] initBlocks)
  {
    blocks = cloneArray(initBlocks);
    size = (byte)blocks[0].length;
    emptyBlock = null;

    emptyBlockLoop:
    for (byte i = 0; i < size; ++i) {
      for (byte j = 0; j < size; ++j) {
        if (blocks[i][j] == 0) {
          emptyBlock = new byte[2];
          emptyBlock[0] = i;
          emptyBlock[1] = j;
          break emptyBlockLoop;
        }
      }
    }

    assert emptyBlock != null : "emptyBlock == null";

  }

  /* [ PRIVATE METHODS ] */

  /**
  * Clones the provided array
  *
  * @param src
  * @return a new clone of the provided array
  */
  private static  byte[][] cloneArray(int[][] src) {
    byte length = (byte) src.length;
    byte[][] target = new byte[length][src[0].length];
    for (byte i = 0; i < length; i++) {
      for (byte j = 0; j < length; j++) {
        target[i][j] = (byte) src[i][j];
      }
    }
    return target;
  }
  private static  int[][] cloneArray(byte[][] src) {
    int length = src.length;
    int[][] target = new int[length][src[0].length];
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length; j++) {
        target[i][j] = src[i][j];
      }
    }
    return target;
  }

  private static void exchangeBlocks(
    byte[][] blocks, byte i1, byte j1, byte i2, byte j2
  )
  {
    if (i1 > blocks.length-1 || i2 > blocks.length-1 ||
        j1 > blocks[0].length-1 || j2 > blocks[0].length-1)
      throw new IndexOutOfBoundsException();

    byte t = blocks[i1][j1];
    blocks[i1][j1] = blocks[i2][j2];
    blocks[i2][j2] = t;
  }

  private Board cloneBoard()
  {
    int[][] initBlocks = cloneArray(blocks);
    Board newBoard = new Board(initBlocks);
    return newBoard;
  }

  private void exchangeBlocks(byte i1, byte j1, byte i2, byte j2)
  {
    exchangeBlocks(blocks, i1, j1, i2, j2);
    if (blocks[i1][j1] == 0) {
      emptyBlock[0] = i1;
      emptyBlock[1] = j1;
    }
    else if (blocks[i2][j2] == 0) {
      emptyBlock[0] = i2;
      emptyBlock[1] = j2;
    }
  }

  private boolean emptyIsTop()
  {
    return (emptyBlock[0] == 0);
  }

  private boolean emptyIsBottom()
  {
    return (emptyBlock[0] == size-1);
  }

  private boolean emptyIsLeft()
  {
    return (emptyBlock[1] == 0);
  }

  private boolean emptyIsRight()
  {
    return (emptyBlock[1] == size-1);
  }

  private void emptyMoveDown()
  {
    if (emptyIsBottom())
      throw new IllegalStateException();

    exchangeBlocks(emptyBlock[0],emptyBlock[1], (byte)(emptyBlock[0]+1),emptyBlock[1]);
  }

  private void emptyMoveUp()
  {
    if (emptyIsTop())
      throw new IllegalStateException();

    exchangeBlocks(emptyBlock[0],emptyBlock[1], (byte)(emptyBlock[0]-1),emptyBlock[1]);
  }

  private void emptyMoveLeft()
  {
    if (emptyIsLeft())
      throw new IllegalStateException();

    exchangeBlocks(emptyBlock[0],emptyBlock[1], emptyBlock[0], (byte)(emptyBlock[1]-1));
  }

  private void emptyMoveRight()
  {
    if (emptyIsRight())
      throw new IllegalStateException();

    exchangeBlocks(emptyBlock[0],emptyBlock[1], emptyBlock[0], (byte)(emptyBlock[1]+1));
  }

  private int getBlockNumber(byte i, byte j)
  {
    return size * i + j + 1;
  }

  private int getBlockNumberXY(byte num, byte[] vals)
  {
    int m = num % size;
    vals[0] = (byte)(num / size + (m > 0 ? 1 : 0));
    vals[1] = (byte)((m == 0 ? size : m));
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
    for (byte i = 0; i < size; ++i) {
      for (byte j = 0; j < size; ++j) {
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
    byte[] v1 = new byte[2];
    byte[] v2 = new byte[2];

    for (byte i = 0; i < size; ++i) {
      for (byte j = 0; j < size; ++j) {
        if (blocks[i][j] == 0) continue;

        v1[0] = (byte)(i + 1);
        v1[1] = (byte)(j + 1);
        getBlockNumberXY(blocks[i][j], v2);
        rs += Math.abs(v2[0] - v1[0]) + Math.abs(v2[1] - v1[1]);
      }
    }
    return rs;
  }

  // is this board the goal board?
  public boolean isGoal()
  {
    for (byte i = 0; i < size; ++i) {
      for (byte j = 0; j < size; ++j) {
        if (i == size-1 && j == size-1) break;
        if (blocks[i][j] != getBlockNumber(i, j))
          return false;
      }
    }
    return (0 == blocks[size-1][size-1]);
  }

  // a board that is obtained by exchanging any pair of blocks
  public Board twin()
  {
    byte[] v1 = null;
    byte[] v2 = null;
    for (byte i = 0; i < size; ++i) {
      for (byte j = 0; j < size; ++j) {
        if (blocks[i][j] != 0) {
          if (v1 == null) {
            v1 = new byte[2];
            v1[0] = i;
            v1[1] = j;
          }
          else {
            v2 = new byte[2];
            v2[0] = i;
            v2[1] = j;

            /* make a twin board */
            Board twinBoard = cloneBoard();
            twinBoard.exchangeBlocks(v1[0], v1[1], v2[0], v2[1]);
            return twinBoard;
          }
        }
      }
    }

    assert true : "twin() failed";
    return null;
  }

  // does this board equal y?
  public boolean equals(Object other)
  {
    if (other == this) return true;
    if (other == null) return false;
    if (other.getClass() != this.getClass()) return false;

    Board that = (Board) other;
    if (this.size != that.size) return false;

    for (byte i = 0; i < size; ++i) {
      for (byte j = 0; j < size; ++j) {
        if (this.blocks[i][j] != that.blocks[i][j])
          return false;
      }
    }

    return true;
  }

  // all neighboring boards
  public Iterable<Board> neighbors()
  {
    Queue<Board> queue = new Queue<Board>();

    if (!emptyIsTop()) {
      Board board = cloneBoard();
      board.emptyMoveUp();
      queue.enqueue(board);
    }

    if (!emptyIsRight()) {
      Board board = cloneBoard();
      board.emptyMoveRight();
      queue.enqueue(board);
    }

    if (!emptyIsBottom()) {
      Board board = cloneBoard();
      board.emptyMoveDown();
      queue.enqueue(board);
    }

    if (!emptyIsLeft()) {
      Board board = cloneBoard();
      board.emptyMoveLeft();
      queue.enqueue(board);
    }

    return queue;
  }

  // string representation of this board (in the output format specified below)
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append(size + "\n");
    for (byte i = 0; i < size; i++) {
      for (byte j = 0; j < size; j++) {
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

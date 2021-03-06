import edu.princeton.cs.algs4.Queue;
// import edu.princeton.cs.algs4.StdOut;

public class Board
{
  private final char[] blocks;
  private final byte size;
  private char emptyBlock;

  // construct a board from an N-by-N array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] initBlocks)
  {
    size = (byte) initBlocks[0].length;
    blocks = new char[ size * size ];
    boolean emptyBlockIsSet = false;

    emptyBlockLoop:
    for (byte i = 0; i < size; ++i) {
      for (byte j = 0; j < size; ++j) {
        char index = getIndex(i, j);
        blocks[index] = (char) initBlocks[i][j];

        if (blocks[index] == 0) {
          String msg = "emptyBlock is already set! (emtyBlock = " + emptyBlock +
                       ", index = " + index + ")";
          assert !emptyBlockIsSet : msg;
          emptyBlock = index;
          emptyBlockIsSet = true;
        }
      }
    }

    assert emptyBlockIsSet : "emptyBlock is not set!";
  }

  private Board(byte initSize)
  {
    size = initSize;
    blocks = new char[size * size];
  }

  /* [ PRIVATE METHODS ] */

  private Board cloneBoard()
  {
    Board newBoard = new Board(size);
    newBoard.emptyBlock = emptyBlock;
    System.arraycopy(blocks, 0, newBoard.blocks, 0, blocks.length);

    return newBoard;
  }

  private static void exchangeBlocks(char[] blocks, int i1, int i2)
  {

    if (i1 > blocks.length-1 || i2 > blocks.length-1)
      throw new IndexOutOfBoundsException();

    char t = blocks[i1];
    blocks[i1] = blocks[i2];
    blocks[i2] = t;
  }

  private void exchangeBlocks(byte i1, byte j1, byte i2, byte j2)
  {
    char index1 = getIndex(i1, j1);
    char index2 = getIndex(i2, j2);

    exchangeBlocks(blocks, index1, index2);
    if (blocks[index1] == 0) {
      emptyBlock = index1;
    }
    else if (blocks[index2] == 0) {
      emptyBlock = index2;
    }
  }

  private boolean emptyIsTop()
  {
    int row = emptyBlock / size;
    return (row == 0);
  }

  private boolean emptyIsBottom()
  {
    int row = emptyBlock / size;
    return (row == size-1);
  }

  private boolean emptyIsLeft()
  {
    int col = emptyBlock % size;
    return (col == 0);
  }

  private boolean emptyIsRight()
  {
    int col = emptyBlock % size;
    return (col == size-1);
  }

  private void emptyMoveDown()
  {
    if (emptyIsBottom())
      throw new IllegalStateException();

    byte row = (byte) (emptyBlock / size);
    byte col = (byte) (emptyBlock % size);

    exchangeBlocks(row, col, (byte) (row+1), col);
  }

  private void emptyMoveUp()
  {
    if (emptyIsTop())
      throw new IllegalStateException();

    byte row = (byte) (emptyBlock / size);
    byte col = (byte) (emptyBlock % size);

    exchangeBlocks(row, col, (byte) (row-1), col);
  }

  private void emptyMoveLeft()
  {
    if (emptyIsLeft())
      throw new IllegalStateException();

    byte row = (byte)  (emptyBlock / size);
    byte col = (byte) (emptyBlock % size);

    exchangeBlocks(row, col, row, (byte) (col-1));
  }

  private void emptyMoveRight()
  {
    if (emptyIsRight())
      throw new IllegalStateException();

    byte row = (byte) (emptyBlock / size);
    byte col = (byte) (emptyBlock % size);

    exchangeBlocks(row, col, row, (byte) (col+1));
  }

  private int getBlockNumber(byte i, byte j)
  {
    return size * i + j + 1;
  }

  private char getIndex(int i, int j)
  {
    return (char) (i * size + j);
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
        if (blocks[getIndex(i, j)] == 0) continue;
        if (blocks[getIndex(i, j)] != getBlockNumber(i, j))
          ++rs;
      }
    }
    return rs;
  }

  // sum of Manhattan distances between blocks and goal
  public int manhattan()
  {
    int rs = 0;
    int m;
    byte i1;
    byte j1;
    byte i2;
    byte j2;

    for (int i = 0; i < size*size; ++i) {
      if (blocks[i] == 0) continue;

      i1 = (byte) (i / size + 1);
      j1 = (byte) (i % size + 1);

      m = blocks[i] % size;
      i2 = (byte) (blocks[i] / size);
      if (m > 0) ++i2;

      if (m == 0)
        j2 = size;
      else
        j2 = (byte) m;

      rs += Math.abs(i2 - i1) + Math.abs(j2 - j1);
    }
    return rs;
  }

  // is this board the goal board?
  public boolean isGoal()
  {
    for (int i = 0; i < size*size-1; ++i) {
      if (blocks[i] != i + 1)
        return false;
    }
    return (0 == blocks[size*size-1]);
  }

  // a board that is obtained by exchanging any pair of blocks
  public Board twin()
  {
    byte[] v1 = null;
    byte[] v2 = null;
    for (byte i = 0; i < size; ++i) {
      for (byte j = 0; j < size; ++j) {
        if (blocks[getIndex(i, j)] != 0) {
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

    assert false : "twin() failed!";
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

    for (int i = 0; i < size*size; ++i) {
      if (this.blocks[i] != that.blocks[i])
        return false;
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
        s.append(String.format("%2d ", (int) blocks[getIndex(i, j)]));
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

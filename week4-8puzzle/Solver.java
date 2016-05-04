import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class Solver
{
  private class Case implements Comparable<Case> {
    public final int num;
    public final int move;
    public final int priority;
    public final Board board;
    public final Board prevBoard;

    public Case(int initNum, int initMove, Board initBoard, Board initPrevBoard)
    {
      num = initNum;
      move = initMove;
      board = initBoard;
      prevBoard = initPrevBoard;
      int manh = board.manhattan();
      priority = manh + move;
    }

    public Iterable<Case> neighbors()
    {
      Queue<Case> queue = new Queue<Case>();
      int caseCounter = 0;
      for (Board b: board.neighbors()) {
        if (b.equals(prevBoard)) continue;
        Case c = new Case(caseCounter++, move+1, b, board);
        queue.enqueue(c);
      }
      return queue;
    }

    public int compareTo(Case that)
    {
      if (priority == that.priority) {
        if (num == that.num)
          return 0;
        else if (num > that.num) {
          return 1;
        }
        else {
          return -1;
        }
      }

      if (priority > that.priority)
        return 1;
      else
        return -1;
    }
  };

  private int move;
  private MinPQ<Case> pq;
  private Queue<Case> caseQueue;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial)
  {
    move = 0;
    caseQueue = new Queue<Case>();
    Case c = new Case(0, move, initial, null);
    pq = new MinPQ<Case>();
    pq.insert(c);
    compute();
  }

  private void compute()
  {
    int startPriority = -1;
    while(true) {
      Case c = pq.delMin();
      if (startPriority == -1)
        startPriority = c.priority;
      else {
        if (startPriority < c.priority) {
          move = -1;
          caseQueue = new Queue<Case>();
          return;
        }
      }
      // StdOut.printf("move: %d, priority: %d\n%s\n",
      // c.move, c.priority, c.board.toString());

      caseQueue.enqueue(c);
      if (c.board.isGoal()) {
        move = c.move;
        return;
      }

      // ++move;
      for (Case c1 : c.neighbors()) {
        pq.insert(c1);
      }

      // try {
      //   System.in.read();
      // }
      // catch (java.io.IOException e) {
      //   ;
      // }
    }
  }

  // is the initial board solvable?
  public boolean isSolvable()
  {
    return (-1 == move ? false : true);
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves()
  {
    return move;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution()
  {
    if (isSolvable()) {
      Queue<Board> queue = new Queue<Board>();
      for (Case c : caseQueue) {
        Board board = c.board;
        queue.enqueue(board);
        if (board.isGoal())
          return queue;
      }
    }
    return null;
  }

  // solve a slider puzzle (given below)
  public static void main(String[] args)
  {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}

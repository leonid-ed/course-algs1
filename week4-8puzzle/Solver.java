import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class Solver
{
  private class Case implements Comparable<Case> {
    public final int num;
    public final int move;
    public final int priority;
    public final int hamm;
    public final int manh;
    public final Board board;
    public final Case prevCase;

    public Case(int initNum, int initMove, Board initBoard, Case initPrevCase)
    {
      num = initNum;
      move = initMove;
      board = initBoard;
      prevCase = initPrevCase;
      manh = board.manhattan();
      hamm = board.hamming();
      priority = manh + move;
    }

    public Iterable<Case> neighbors()
    {
      Queue<Case> queue = new Queue<Case>();
      int caseCounter = 0;
      for (Board b: board.neighbors()) {
        if (prevCase != null && b.equals(prevCase.board)) continue;
        Case c = new Case(caseCounter++, move+1, b, this);
        queue.enqueue(c);
      }
      return queue;
    }

    public int compareTo(Case that)
    {
      if (priority == that.priority) {
        if (move == that.move) {
          if (num == that.num)
            return 0;
          else if (num > that.num) {
            return 1;
          }
          else {
            return -1;
          }
        }
        else if (move < that.move) {
          return 1;
        }
        else {
          return -1;
        }
      }
      else if (priority > that.priority)
        return 1;
      else
        return -1;
    }
  };

  private final boolean debug;
  private MinPQ<Case> pqPre;
  private MinPQ<Case> pqPro;
  private Case finalCase;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial)
  {
    debug = false;
    Case c = new Case(0, 0, initial, null);
    pqPre = new MinPQ<Case>();
    pqPre.insert(c);
    pqPro = new MinPQ<Case>();
    compute();
  }

  private void compute()
  {
    int startPriority = -1;
    while(true) {
      Case c = pqPre.delMin();

      if (debug) {
        StdOut.printf("step\n");
        StdOut.printf("move: %d\nmanhattan: %d\npriority: %d\n%s\n",
                      c.move, c.manh, c.priority, c.board.toString());
      }

      /* detect unsolved case */
      // if (startPriority == -1)
      //   startPriority = c.priority;
      // else {
      //   if (startPriority < c.priority) {
      //     move = -1;
      //     caseQueue = new Queue<Case>();
      //     return;
      //   }
      //   startPriority = c.priority;
      // }

      pqPro.insert(c);
      if (c.board.isGoal()) {
        finalCase = c;
        return;
      }

      if (debug) StdOut.printf("<-- neighbors :\n");
      for (Case c1 : c.neighbors()) {
        if (debug) {
          StdOut.printf("move: %d\nmanhattan: %d\npriority: %d\n%s\n",
                        c1.move, c1.manh, c1.priority, c1.board.toString());
        }

        pqPre.insert(c1);
      }
      if (debug) StdOut.printf("neighbors -->\n");

      /* step by step */
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
    return (finalCase != null);
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves()
  {
    return (finalCase != null ? finalCase.move : -1);
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution()
  {
    if (isSolvable()) {
      Stack<Board> stack = new Stack<Board>();
      Case c = finalCase;

      do {
        stack.push(c.board);
        c = c.prevCase;
      } while (c != null);

      return stack;
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

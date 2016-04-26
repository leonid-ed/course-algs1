import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {
  public static void main(String[] args)
  {
    if (args.length < 1) {
      StdOut.printf("Too few arguments\n");
      return;
    }

    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> queue = new RandomizedQueue<String>();

    String[] seq = StdIn.readAllStrings();
    int[] map = new int[seq.length];
    for (int i = 0; i < map.length; i++) {
       map[i] = i;
    }
    StdRandom.shuffle(map, 0, seq.length-1);

    for (int i = 0; i < k; i++) {
      queue.enqueue(seq[map[i]]);
    }

    for (int i = 0; i < k; i++) {
      String s = queue.dequeue();
      StdOut.printf("%s\n", s);
    }
  }
}
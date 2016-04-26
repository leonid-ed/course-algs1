public class RandomizedQueueTests
{
  private static boolean testEmpty()
  {
    System.out.println("testEmpty()");
    RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();
    boolean rs = deque.isEmpty() && deque.size() == 0;
    assert rs : deque.size();
    return rs;
  }

  private static boolean testDequeueFromEmpty()
  {
    System.out.println("testDequeueFromEmpty()");
    boolean rs = false;
    boolean caught = false;
    RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();
    try {
      deque.dequeue();
    }
    catch(java.util.NoSuchElementException e) {
      caught = true;
    }

    rs = caught;
    assert rs : caught;

    try {
      deque.sample();
    }
    catch(java.util.NoSuchElementException e) {
      caught = true;
    }

    rs = caught;
    assert rs : caught;

    return rs;
  }

  private static boolean testSize()
  {
    System.out.println("testSize()");
    boolean rs = false;
    RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();

    rs = deque.size() == 0;
    assert rs : deque.size();

    deque.enqueue(0);
    rs = deque.size() == 1;
    assert rs : deque.size();

    deque.enqueue(1);
    rs = deque.size() == 2;
    assert rs : deque.size();

    // deque.removeLast();
    // rs = deque.size() == 1;
    // assert rs : deque.size();

    // deque.removeFirst();
    // rs = deque.size() == 0;
    // assert rs : deque.size();

    return rs;
  }

  private static boolean testEnqueue()
  {
    System.out.println("testEnqueue()");
    boolean rs = false;
    RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();

    deque.enqueue(5);
    deque.enqueue(4);
    deque.enqueue(3);
    deque.enqueue(2);
    deque.enqueue(1);
    deque.enqueue(0);

    // rs = deque.removeLast() == 5;
    // assert rs : rs;

    // deque.addLast(5);
    // deque.addLast(6);
    // deque.addLast(7);
    // deque.addLast(8);
    // deque.addLast(9);

    // rs = deque.size() == 10;
    // assert rs : deque.size();

    for (int i : deque) {
      System.out.println("testEnqueue(): " + i);
    }

    return rs;
  }

  private static boolean testDequeue()
  {
    System.out.println("testDequeue()");
    boolean rs = false;
    RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();

    deque.enqueue(5);
    assert deque.size() == 1 : deque.size();

    int num = deque.dequeue();

    rs = num == 5;
    assert rs : num;

    assert deque.size() == 0 : deque.size();

    deque.enqueue(0);
    deque.enqueue(1);
    deque.enqueue(2);
    deque.enqueue(3);
    deque.enqueue(4);
    deque.enqueue(5);
    deque.enqueue(6);
    deque.enqueue(7);
    deque.enqueue(8);
    deque.enqueue(9);

    deque.dequeue();
    deque.dequeue();
    deque.dequeue();
    deque.dequeue();
    deque.dequeue();
    deque.dequeue();
    deque.dequeue();

    for (int i : deque) {
      System.out.println("testEnqueue(): " + i);
    }

    return rs;
  }

  private static boolean testEnqueueNull()
  {
    System.out.println("testEnqueueNull()");
    boolean rs = false;
    boolean caught = false;
    RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();
    try {
      deque.enqueue(null);
    }
    catch(java.lang.NullPointerException e) {
      caught = true;
    }
    rs = caught;
    assert rs : caught;

    return rs;
  }

  private static boolean testIterator()
  {
    System.out.println("testIterator()");
    boolean rs = false;

    RandomizedQueue<Integer> deque = new RandomizedQueue<Integer>();
    deque.enqueue(0);
    deque.enqueue(1);
    deque.enqueue(2);
    deque.enqueue(3);

    for (int i : deque) {
      System.out.println("testIterator(): foreach: " + i);
    }

    return rs;
  }

  // unit testing
  public static void main(String[] args)
  {
    testEmpty();
    testDequeueFromEmpty();
    testSize();
    testEnqueue();
    testEnqueueNull();
    testDequeue();
    testIterator();
  }
}
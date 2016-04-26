public class DequeTests
{
  private static boolean testEmpty()
  {
    Deque<Integer> deque = new Deque<Integer>();
    boolean rs = deque.isEmpty() && deque.size() == 0;
    assert rs : deque.size();
    return rs;
  }

  private static boolean testRemoveFromEmpty()
  {
    boolean rs = false;
    boolean caught = false;
    Deque<Integer> deque = new Deque<Integer>();
    try {
      deque.removeFirst();
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
    boolean rs = false;
    Deque<Integer> deque = new Deque<Integer>();

    rs = deque.size() == 0;
    assert rs : deque.size();

    deque.addFirst(0);
    rs = deque.size() == 1;
    assert rs : deque.size();

    deque.addLast(1);
    rs = deque.size() == 2;
    assert rs : deque.size();

    deque.removeLast();
    rs = deque.size() == 1;
    assert rs : deque.size();

    deque.removeFirst();
    rs = deque.size() == 0;
    assert rs : deque.size();

    return rs;
  }

  private static boolean testEnque()
  {
    boolean rs = false;
    Deque<Integer> deque = new Deque<Integer>();

    deque.addFirst(5);
    deque.addFirst(4);
    deque.addFirst(3);
    deque.addFirst(2);
    deque.addFirst(1);
    deque.addFirst(0);

    rs = deque.removeLast() == 5;
    assert rs : rs;

    deque.addLast(5);
    deque.addLast(6);
    deque.addLast(7);
    deque.addLast(8);
    deque.addLast(9);

    rs = deque.size() == 10;
    assert rs : deque.size();

    int j = 0;
    for (int i : deque) {
      rs = i == j;
      assert rs : i;
      j++;
    }

    return rs;
  }

  private static boolean testEnqueNull()
  {
    boolean rs = false;
    boolean caught = false;
    Deque<Integer> deque = new Deque<Integer>();
    try {
      deque.addFirst(null);
    }
    catch(java.lang.NullPointerException e) {
      caught = true;
    }
    rs = caught;
    assert rs : caught;

    caught = false;
    try {
      deque.addLast(null);
    }
    catch(java.lang.NullPointerException e) {
      caught = true;
    }
    rs = caught;
    assert rs : caught;

    return rs;
  }

  // unit testing
  public static void main(String[] args)
  {
    testEmpty();
    testRemoveFromEmpty();
    testSize();
    testEnque();
    testEnqueNull();
  }
}
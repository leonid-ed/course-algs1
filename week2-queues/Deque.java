import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
  private class Node {
    private Item item;
    private Node next;
    private Node prev;
  }

  private Node head;
  private Node tail;
  private int size;


  // construct an empty deque
  public Deque()
  {
    head = null;
    tail = null;
    size = 0;
  }

  // is the deque empty?
  public boolean isEmpty()
  {
    return size == 0;
  }

  // return the number of items on the deque
  public int size()
  {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item)
  {
    if (item == null)
      throw new java.lang.NullPointerException();

    Node node = new Node();
    node.item = item;
    node.next = head;
    node.prev = null;

    if (size == 0) {
      tail = node;
    }
    else {
      head.prev = node;
    }

    head = node;
    size++;

    assert head.prev == null;
    assert tail.next == null;
  }

  // add the item to the end
  public void addLast(Item item)
  {
    if (item == null)
      throw new java.lang.NullPointerException();

    Node node = new Node();
    node.item = item;
    node.next = null;
    node.prev = tail;

    if (size == 0) {
      head = node;
    }
    else {
      tail.next = node;
    }

    tail = node;
    size++;

    assert head.prev == null;
    assert tail.next == null;
  }

  // remove and return the item from the front
  public Item removeFirst()
  {
    if (isEmpty())
      throw new java.util.NoSuchElementException();

    assert head != null;

    Node node = head;
    if (head.next != null) {
      head = head.next;
      head.prev = null;
    }

    node.next = null;
    node.prev = null;
    Item item = node.item;
    node.item = null;
    size--;

    if (size == 0) {
      head = null;
      tail = null;
    }

    return item;
  }

  // remove and return the item from the end
  public Item removeLast()
  {
    if (isEmpty())
      throw new java.util.NoSuchElementException();

    assert tail != null;

    Node node = tail;
    if (tail.prev != null) {
      tail = node.prev;
      tail.next = null;
    }

    node.next = null;
    node.prev = null;
    Item item = node.item;
    node.item = null;
    size--;

    if (size == 0) {
      head = null;
      tail = null;
    }
    return item;
  }

  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() { return new DequeIterator(); }

  private class DequeIterator implements Iterator<Item>
  {
    private Node current = head;

    public boolean hasNext() { return current != null; }
    public void remove()     { throw new java.lang.UnsupportedOperationException(); }
    public Item next()
    {
      if (!hasNext())
        throw new java.util.NoSuchElementException();

      if (current == null)
        throw new java.lang.UnsupportedOperationException();

      Item item = current.item;
      current   = current.next;
      return item;
    }
  }

  // unit testing
  public static void main(String[] args)
  {
    Deque<Integer> deque = new Deque<Integer>();
    deque.addFirst(0);
    deque.removeLast();
    for (int i : deque) {
      System.out.println(i);
    }
  }
}
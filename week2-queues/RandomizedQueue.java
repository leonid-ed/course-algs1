import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private int size;
  private int realSize;
  private Item[] box;

  // construct an empty randomized queue
  public RandomizedQueue()
  {
    realSize = 2;
    box = (Item[]) new Object[realSize];
    size = 0;
  }

  private void realloc(int capacity)
  {
    Item[] new_box = (Item[]) new Object[capacity];
    for (int i = 0; i < size; i++) {
      new_box[i] = box[i];
    }

    // StdOut.printf("Memory is reallocated: from %d to %d elements (size=%d)\n", realSize, capacity, size);

    box = new_box;
    realSize = capacity;
  }

  // is the queue empty?
  public boolean isEmpty()
  {
    return size == 0;
  }

  // return the number of items on the queue
  public int size()
  {
    return size;
  }

  // add the item
  public void enqueue(Item item)
  {
    if (item == null)
      throw new java.lang.NullPointerException();

    if (size == realSize) {
      realloc(realSize * 2);
    }

    box[size] = item;
    size++;
  }

  // remove and return a random item
  public Item dequeue()
  {
    if (isEmpty())
      throw new java.util.NoSuchElementException();

    int i = StdRandom.uniform(size());

    Item item = box[i];
    box[i] = null;
    size--;

    if (i < size) {
      box[i] = box[size];
      box[size] = null;
    }

    if (realSize > 4  && size < realSize * 0.25) {
      realloc(realSize / 2);
    }

    return item;
  }

  // return (but do not remove) a random item
  public Item sample()
  {
    if (isEmpty())
      throw new java.util.NoSuchElementException();

    int i = StdRandom.uniform(size());
    return box[i];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

  private class RandomizedQueueIterator implements Iterator<Item>
  {
    private int[] map;
    private int index;
    private int mSize;

    public RandomizedQueueIterator()
    {
      mSize = size();
      index = 0;

      if (mSize == 0)
        return;

      map = new int[mSize];
      for (int i = 0; i < map.length; i++) {
        map[i] = i;
      }

      StdRandom.shuffle(map, 0, mSize-1);

      // for (int i = 0; i < map.length; i++) {
      //   System.out.println("map[" + i + "] = " + map[i]);
      // }
    }

    public boolean hasNext() { return index != mSize; }
    public void remove()     { throw new java.lang.UnsupportedOperationException(); }
    public Item next()
    {
      if (!hasNext())
        throw new java.util.NoSuchElementException();

      Item item = box[map[index]];
      index++;
      return item;
    }
  }

  // unit testing
//  public static void main(String[] args);
}
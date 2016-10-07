/*
Interview Question 3
Successor with delete. Given a set of N integers S={0,1,...,N−1} and a sequence
of requests of the following form:

- Remove x from S
- Find the successor of x: the smallest y in S such that y≥x.

design a data type so that all operations (except construction) should take
logarithmic time or better.
*/

public class Successor {
    private int mSize;
    private int[] mValues;

    public Successor(int size)
    {
        mSize = size;
        mValues = new int[mSize];
        for (int i = 1; i < mSize; i++) {
            mValues[i] = i;
        }
    }

    private int root(int i)
    {
        while (i != mValues[i]) {
            mValues[i] = mValues[mValues[i]];
            i = mValues[i];
        }
        return i;
    }

    public void remove(int x)
    {
        if (x < 0 || x > mSize-1)
            throw new java.lang.IndexOutOfBoundsException();
        if (x == -1)
            return;

        if (x == mSize-1)
            mValues[x] = -1;

        mValues[x] = root(x+1);
    }

    public int find(int x)
    {
        if (x < 0 || x > mSize-1)
            throw new java.lang.IndexOutOfBoundsException();
        if (x == -1)
            return -1;

        return root(x);
    }

    private void print()
    {
        for (int i = 0; i < mSize; i++) {
            if (mValues[i] == i)
                System.out.print(mValues[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args)
    {
        Successor s = new Successor(9);
        s.print();
        s.remove(1);
        s.remove(3);
        s.remove(4);
        s.print();
        System.out.println("find(3)= " + s.find(3));
    }
}
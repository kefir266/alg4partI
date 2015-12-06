/**
 * Created by dmitrij on 24.09.2015.
 */

import java.util.NoSuchElementException;
import java.util.Iterator;
//import edu.princeton.cs.algs4.StdIn;
//import edu.princeton.cs.algs4.StdRandom;

public class Deque<Item> implements Iterable<Item>  {

    private static final int INIT_SIZE = 10; // must be even

    private Item[] a;         // array of items
    //private int N;            // number of elements on stack
    private int nBegin;
    private int nEnd;



    public Deque() {

        nBegin = INIT_SIZE / 2;
        nEnd = nBegin + 1;
        resize(INIT_SIZE);
    }                           // construct an empty deque

    public boolean isEmpty() {
        return nBegin == nEnd - 1;
    }                // is the deque empty?

    public int size() {
        return nEnd - nBegin - 1;
    }                       // return the number of items on the deque

    public void addFirst(Item item) {

        if (item == null) throw new NullPointerException("Null");
        if (nBegin == 0) resize(2 * a.length);

        a[nBegin--] = item;
    }        // add the item to the front

    public void addLast(Item item) {

        if (item == null) throw new NullPointerException("Null");
        if (nEnd == a.length) resize(2 * a.length);    // double size of array if necessary
        a[nEnd++] = item;                            // add item
    }          // add the item to the end

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = a[++nBegin];

        return item;
    }                // remove and return the item from the front

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = a[--nEnd];


        //if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
    }                 // remove and return the item from the end

    public Iterator<Item> iterator() {

        return new ArrayIterator();

    }        // return an iterator over items in order from front to end


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= nEnd - nBegin;
        Item[] temp = (Item[]) new Object[capacity];

        int start = (capacity - (nEnd - nBegin - 1)) / 2;
        int j = 0;
        for (int i = nBegin + 1; i < nEnd; i++) {
            j++;
            temp[j + start] = a[i];

        }
        nBegin = start;
        nEnd = j + start + 1;
        a = temp;
    }

    private class ArrayIterator implements Iterator<Item> {

        private int i = nBegin + 1;

        @Override
        public boolean hasNext() {
            return i < nEnd;
        }

        @Override
        public Item next() {

            if (!hasNext()) throw new NoSuchElementException();
            return a[i++];
        }

        @Override
        public void remove() {

            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args) {

//        Deque<Integer> deque = new Deque<Integer>();
//        deque.size();
//        deque.addFirst(1);
//        deque.isEmpty();
//        deque.addLast(3);
//        deque.isEmpty();
//        deque.addLast(5);
//        deque.addLast(6);
//        //deque.removeFirst()
//        System.out.println(deque.removeFirst());
//        for (int i = 0; i < 1000; i++){
//            deque.addFirst(StdRandom.uniform(0, 100));
//            deque.addLast(StdRandom.uniform(0, 100));
//        }
//
//        System.out.println(deque.size());
    }   // unit testing


}

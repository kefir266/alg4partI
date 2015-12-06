/**
 * Created by dmitrij on 24.09.2015.
 */

import edu.princeton.cs.algs4.StdRandom;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INIT_SIZE = 10;
    private int N;          // size of the stack
    private Item[] a;
    private boolean isShuffled = false;

    // helper linked list class

    public RandomizedQueue() {

        a = (Item[]) new Object[INIT_SIZE];
        N = 0;
        //assert check();
    }                 // construct an empty randomized queue

    public boolean isEmpty() {
        return N == 0;

    }                 // is the queue empty?

    public int size() {

        return N;
    }                        // return the number of items on the queue

    public void enqueue(Item item) {

        if (item == null) throw new NullPointerException("Null");
        if (N == a.length) resize(2*a.length);    // double size of array if necessary
        a[N++] = item;
        isShuffled = false;
        //assert check();

    }          // add the item

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");

        if ( ! isShuffled) shuffle();
        Item item = a[--N];

        //assert check();
        return item;

    }                    // remove and return a random item

    public Item sample() {

        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        if ( ! isShuffled) shuffle();
        int j;
        if (N > 1) j = StdRandom.uniform(0 ,N - 1);
        else j = 0;
        Item item = a[j];
        return item;

    }                     // return (but do not remove) a random item

    public Iterator<Item> iterator() {
        return new ArrayIterator();

    }        // return an independent iterator over items in random order



    // check internal invariants
//    private boolean check() {
//        if (N == 0) {
//            if (first != null) return false;
//        }
//        else if (N == 1) {
//            if (first == null)      return false;
//            if (first.next != null) return false;
//        }
//        else {
//            if (first.next == null) return false;
//        }
//
//        // check internal consistency of instance variable N
//        int numberOfNodes = 0;
//        for (Node x = first; x != null; x = x.next) {
//            numberOfNodes++;
//        }
//        if (numberOfNodes != N) return false;
//
//        return true;
//    }


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    private void shuffle() {
        for (int i = 0; i < N; i++) {
            int j;
            if (N > 1) j = StdRandom.uniform(0 ,N - 1);
            else return;
            Item swp = a[j];
            a[j] = a[i];
            a[i] = swp;
        }
        isShuffled = true;
    }

    private class ArrayIterator implements Iterator<Item> {


        private int num = 0;
        public ArrayIterator() {
            shuffle();
        }


        @Override
        public boolean hasNext() {
            return num < N;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = a[num++];

            return item;
        }

        @Override
        public void remove() {

            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        rq.isEmpty();
        rq.enqueue(227);
        rq.size();
        System.out.println(rq.dequeue());

    }  // unit testing
}

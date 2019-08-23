import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private final static int INIT_LENGTH = 8;
    private int size = 0;
    private Item[] storage = (Item[]) new Object[INIT_LENGTH];

    // construct an empty randomized queue
    public RandomizedQueue() {}

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int newCapacity) {
        Item[] newStorage = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newStorage[i] = storage[i];
        }
        storage = newStorage;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null element");
        }
        if (size == storage.length) {
            resize(storage.length * 2);
        }

        storage[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("empty queue");
        }
        int indexToDelete = StdRandom.uniform(size);
        Item itemToReturn = storage[indexToDelete];
        storage[indexToDelete] = storage[size - 1];
        size--;
        if ((size >= INIT_LENGTH / 2) && (size <= storage.length / 4)) {
            resize(storage.length / 2);
        }
        return itemToReturn;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("empty queue");
        }
        return storage[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int index = 0;
        private int[] nextOrder = new int[size];

        public RandomizedQueueIterator()
        {
            for (int i = 0; i < size; ++i) {
                nextOrder[i] = i;
            }
            StdRandom.shuffle(nextOrder);
        }

        public boolean hasNext() {
            return index < nextOrder.length;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("unsupport");
        }

        public Item next()
        {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("no more elements");
            }
            index++;
            return storage[nextOrder[index-1]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
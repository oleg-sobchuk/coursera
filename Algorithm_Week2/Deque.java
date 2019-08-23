import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        //StdOut.println("Create empty deque");
    }

    // is the deque empty?
    public boolean isEmpty() {
        //StdOut.println("isEmpty: " + (size == 0));
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        //StdOut.println("size: " + size);
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null element");
        }
        if (size == 0) {
            initFirstAndLastNode(item);
            return;
        }
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;
        newFirst.prev = null;
        first.prev = newFirst;
        first = newFirst;
        size++;
        //StdOut.println("Add to head: " + item);
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null element");
        }
        if (size == 0) {
            initFirstAndLastNode(item);
            return;
        }
        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;
        newLast.prev = last;
        last.next = newLast;
        last = newLast;
        size++;
        //StdOut.println("Add to tail: " + item);
    }

    private void initFirstAndLastNode(Item item) {
        Node firstAndLast = new Node();
        firstAndLast.item = item;
        firstAndLast.next = null;
        firstAndLast.prev = null;
        first = firstAndLast;
        last = firstAndLast;
        size++;
        //StdOut.println("Init first node: " + item);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("empty deque");
        }
        if (size == 1) {
            return returnLastItem();
        }
        Node oldFirst = first;
        first = first.next;
        first.prev = null;
        size--;
        //StdOut.println("Take from head: " + oldFirst.item);
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("empty deque");
        }
        if (size == 1) {
            return returnLastItem();
        }
        Node oldLast = last;
        //last.prev.next = null;
        last = last.prev;
        last.next = null;
        size--;
        //StdOut.println("Take from tail: " + oldLast.item);
        return oldLast.item;
    }

    private Item returnLastItem() {
        Item item = first.item;
        first = null;
        last = null;
        size = 0;
        //StdOut.println("Deleted last node: " + item);
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /*public void printDeque() {
        StdOut.println("Print deque...");
        if (size == 0) {
            StdOut.println("NULL");
            return;
        }
        Iterator<Item> iterator = iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
    }*/

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("no more elements");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("unsupport");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.isEmpty();
        deque.addFirst("aa");
        deque.addFirst("newAA");
        deque.addLast("cc");
        deque.addLast("newCC");
        deque.size();
        deque.isEmpty();
        deque.removeFirst();
        //deque.printDeque();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeLast();
        //deque.printDeque();
    }

}
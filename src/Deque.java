
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mohsen
 */
public class Deque<Item> implements Iterable<Item> {

    public Item[] items;
    private int middle;
    private int firstCursor = 0;
    private int lastCursor = 0;

    // construct an empty deque
    public Deque() {
        this.items = (Item[]) new Object[10];
        this.middle = items.length / 2;
        this.firstCursor = middle - 1;
        this.lastCursor = middle;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return lastCursor - firstCursor == 1;
    }

    // return the number of items on the deque
    public int size() {
        return (middle - firstCursor - 1) + (lastCursor - middle);
    }

    private void resize(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];
        int curSize = (firstCursor + lastCursor);
        int curMiddle = curSize / 2;
        int newMiddle = newItems.length / 2;
        int j = 0;
        for (int i = firstCursor; i < lastCursor; i++) {
            newItems[newMiddle - curMiddle + (j++)] = items[i];
        }
        firstCursor = newMiddle - (curSize / 2);
        lastCursor = newMiddle + (curSize / 2) + 1;
        this.middle = newMiddle;
        items = newItems;
    }
    
    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (firstCursor == 0) {
            resize(2 * items.length);
        }
        items[firstCursor--] = item;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        items[lastCursor++] = item;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item result = items[++firstCursor];
        items[firstCursor] = null;
        return result;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item result = items[--lastCursor];
        items[lastCursor] = null;
        return result;
    }

    public class DequeIterator<Item> implements Iterable<Item> {

        private int i;
        public boolean hasNext() {
            return i == size();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return (Item)items[--i];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterator<Item> iterator() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public DequeIterator(Deque<Item> deque) {
            this.deque = deque;
        }

        private Deque<Item> deque;
    }

    // return an iterator over items in order from front to end
    /**
     *
     * @return
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(this).iterator();
    }

    private static void Test1() {
        Deque<Integer> deque = new Deque<Integer>();
        assert deque.isEmpty();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addLast(0);
        deque.addLast(-1);
        assert deque.size() == 7;
        assert deque.removeFirst() == 5;
        assert deque.removeFirst() == 4;
        assert deque.removeFirst() == 3;
        assert deque.size() == 4;
        assert deque.removeLast() == -1;
        assert deque.removeLast() == 0;
        assert deque.removeLast() == 1;
        assert deque.removeLast() == 2;
        assert deque.size() == 0;
        assert deque.isEmpty();
    }
    
    private static void Test2() {
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 0; i < 15; i++) {
            deque.addFirst(i);
        }
        assert deque.size() == 15;
        assert deque.removeFirst() == 14;
        assert deque.removeFirst() == 13;
        assert deque.removeFirst() == 12;
        assert deque.size() == 12;        
        assert deque.removeLast() == 0;
        assert deque.removeLast() == 1;
        assert deque.removeLast() == 2;
        assert deque.size() == 9;
    }

    // unit testing
    public static void main(String[] args) {
        Test1();
        Test2();
    }
}


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
        this.items = (Item[]) new Object[8];
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
        return lastCursor - firstCursor - 1;
    }

    private void resize(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];
        int curSize = size();
        int curMiddle = size() / 2;
        int newMiddle = newItems.length / 2;
        int newStartPoint = newMiddle - curMiddle;
        int j = 0;
        for (int i = firstCursor + 1; i < lastCursor; i++) {
            newItems[newStartPoint + j] = items[i];
            j++;
        }
        firstCursor = newMiddle - (curSize / 2) - 1;
        lastCursor = firstCursor + curSize + 1;
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
        if (lastCursor == items.length) {
            resize(2 * items.length);
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

    public class DequeIterator<Item> implements Iterator<Item> {

        private int i;
        @Override
        public boolean hasNext() {
            return i < size();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return (Item)items[firstCursor + (++i)];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public DequeIterator(Deque<Item> deque) {
            i = 0;
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
        return new DequeIterator<Item>(this);
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
        for (int i = 0; i < 5; i++) {
            deque.addFirst(i);
        }
        assert deque.size() == 5;
        assert deque.removeLast() == 0;
        for (int i = 0; i > -10; i--) {
            deque.addLast(i);
        }
        assert deque.size() == 14;
        assert deque.removeFirst() == 4;
        assert deque.removeFirst() == 3;
        assert deque.removeFirst() == 2;
        assert deque.size() == 11;
        assert deque.removeLast() == -9;
        assert deque.removeLast() == -8;
        assert deque.removeLast() == -7;
        assert deque.size() == 8;
    }

    private static void Test3() {
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 0; i < 5; i++) {
            deque.addFirst(i);
        }
        for (int i = -1; i > -5; i--) {
            deque.addLast(i);
        }
        assert deque.size() == 9;
        int i = 4;
        for(Integer item: deque) {
            assert item == i;
            i--;
        }
    }
    
    private static void Test4() {
        Deque<Integer> deque = new Deque<Integer>();
        for (int i = 0; i < 10000; i++) {
            deque.addFirst(i);
        }
        assert deque.size() == 10000;
    }
    
    // unit testing
    public static void main(String[] args) {
        Test1();
        Test2();
        Test3();
        Test4();
    }
}

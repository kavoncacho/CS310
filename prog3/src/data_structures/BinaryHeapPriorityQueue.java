/**
 *  Program 3
 *  One implementation of a priority queue, priority is FIFO
 *  CS310-1
 *  8APR2020
 *  @author  Kavon Cacho cssc1209
 */
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryHeapPriorityQueue <E extends Comparable<E>> implements PriorityQueue<E> {
    int size = 0;
    int entry;
    private Wrapper<E>[] binaryHeap;
    int modificationCounter;

    public BinaryHeapPriorityQueue() {
        binaryHeap = (Wrapper<E>[]) new Wrapper[DEFAULT_MAX_CAPACITY];
        entry = 0;
        modificationCounter = 0;
    }

    public BinaryHeapPriorityQueue(int maxCapacity) {
        binaryHeap = (Wrapper<E>[]) new Wrapper[maxCapacity];
        entry = 0;
        modificationCounter = 0;
    }

    protected class Wrapper<E> implements Comparable<Wrapper<E>> {
        long number;
        E data;
        public Wrapper(E d) {
            number = entry++;
            data = d;
        }
        public int compareTo (Wrapper<E> o) {
            if (((Comparable<E>)data).compareTo(o.data) == 0) {
                return (int) (number - o.number);
            }
            return ((Comparable<E>)data).compareTo(o.data);
        }
    }

    @Override
    public boolean insert(E object) {
        if (isFull()) {
            return false;
        }
        Wrapper<E> wrapper = new Wrapper<E>(object);    //put E object into the wrapper
        binaryHeap[size] = wrapper;     //insert it at the end of the array
        size++;
        modificationCounter++;
        trickleUp(size - 1);    //re-heapify
        return true;
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            return null;
        }
        Wrapper<E> temp = binaryHeap[0];    //log the highest priority item so it can be returned
        trickleDown(0);     //re-heapify starting at the beginning. this method automatically equals last element to this index
        modificationCounter++;
        size--;
        return temp.data;   //return E data
    }

    @Override
    public boolean delete(E obj) {
        if (isEmpty() || !contains(obj)) {
            return false;
        }
        for (int i = 0; i <= size; ++i) {
            if (binaryHeap[i].data.compareTo(obj) == 0) {   //if an instance if found
                if (binaryHeap[(i - 1) / 2].data.compareTo(binaryHeap[size - 1].data) < 0) {    //if the parent node is of a higher priority than the last element
                    trickleDown(i); //re-heapify starting at this index
                }
                else {  //if the parent node is of a lower priority than the last element
                    binaryHeap[i] = binaryHeap[size - 1];
                    trickleUp(i);   //re-heapify starting at this index
                }
                i = i - 1;  //just incase there are instances right next to eachother
                modificationCounter++;
                size--;
            }
        }
        return true;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return binaryHeap[0].data;  //return data at first element, highest priority
    }

    @Override
    public boolean contains(E obj) {
        if (isEmpty()) {
            return false;
        }
        E key = obj;
        //linear search
        for (int i = 0; i < size; i++) {
            if (binaryHeap[i].data.compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        modificationCounter++;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isFull() {
        if (size == binaryHeap.length) {
            return true;
        }
        else {
            return false;
        }
    }

    //i t e r a t o r
    @Override
    public Iterator<E> iterator() {
        return new IteratorHelper();
    }

    private class IteratorHelper implements Iterator<E> {
        int counter = 0;
        long modCounter;

        public IteratorHelper() {
            modCounter = modificationCounter;
        }
        public boolean hasNext() {
            if (modCounter !=modificationCounter) {
                throw new ConcurrentModificationException();
            }
            return size - 1 > counter;
        }
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return binaryHeap[counter++].data;
        }
        public void remove () {
            throw new UnsupportedOperationException();
        }

    }

    //trickles up at a given index, rather than automatically at the top
    //this method automatically equals the last element to the given index
    private void trickleUp(int index) {
        int newIndex = index;
        int parentIndex = (newIndex - 1) >> 1;
        Wrapper<E> newValue = binaryHeap[newIndex];
        while (parentIndex >= 0 && newValue.compareTo(binaryHeap[parentIndex]) < 0) {
            binaryHeap[newIndex] = binaryHeap[parentIndex];
            newIndex = parentIndex;
            parentIndex = (parentIndex - 1) >> 1;
        }
        binaryHeap[newIndex] = newValue;
    }

    //trickles down at a given index, rather than automatically at the top
    //this method automatically equals the last element to the given index
    private void trickleDown(int index) {
        int current = index;
        int child = getNextChild(current);
        while ((child != -1) && (binaryHeap[current].compareTo(binaryHeap[child]) < 0) && (binaryHeap[child].compareTo(binaryHeap[size - 1]) < 0)) {
            binaryHeap[current] = binaryHeap[child];
            current = child;
            child = getNextChild(current);
        }
        binaryHeap[current] = binaryHeap[size - 1];
    }

    //thank you riggins
    private int getNextChild(int current) {
        int left = (current << 1) + 1;
        int right = left + 1;
        if (right < size) {
            if (binaryHeap[left].compareTo(binaryHeap[right]) < 0) {
                return left;
            }
            return right;
        }
        if (left < size) {
            return left;
        }
        return -1;
    }

    public void testPrint() {
        for (int i = 0; i < size; ++i) {
            System.out.println(binaryHeap[i].data);
        }
    }
}

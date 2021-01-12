/**
 *  Program 2
 *  Two implementations of a priority queue, priority is FIFO
 *  CS310-1
 *  25FEB2020
 *  @author  Kavon Cacho cssc1209
 */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedLinkedListPriorityQueue <E extends Comparable<E>> implements PriorityQueue<E> {
    private E uSLL;
    private Node<E> head;
    private int size;
    private int modCounter;

    private class Node<T> {
        T data;
        Node<T> next;
        public Node (T data) {
            this.data = data;
            next = null;
        }
    }

    public UnorderedLinkedListPriorityQueue() {
        head = null;
        size = 0;
        modCounter = 0;
    }

    @Override
    public boolean insert(E object) {
        Node<E> newNode = new Node<E>(object);
        newNode.next = head;
        head = newNode;
        size++;
        modCounter++;
        return true;
    }

    @Override
    public E remove() {
        Node<E> previous = null;
        Node<E> current = head;
        Node<E> minPrevious = null;
        Node<E> minCurrent = head;
        if (isEmpty()) {
            return null;
        }
        while (current != null) {
            //when new minimum is found, log it with minCurrent and minPrevious
            if (minCurrent.data.compareTo(current.data) >= 0) {
                minCurrent = current;
                minPrevious = previous;
            }
            //traverse
            previous = current;
            current = current.next;
        }
        if (minCurrent == head) {   //if the element to be removed is at the head
            head = head.next;
        }
        else {  //if it is in the middle or end
            minPrevious.next = minCurrent.next;
        }
        size--;
        modCounter++;
        return minCurrent.data;
    }

    @Override
    public boolean delete(E obj) {
        Node<E> previous = null;
        Node<E> current = head;
        if (isEmpty()) {
            return false;
        }
        while (current != null) {
            if (current.data.compareTo(obj) == 0) { //if the key is found
                if (current == head) {  //if the key is at the head
                    head = head.next;
                }
                else {  //if it is in the middle or end
                    previous.next = current.next;
                }
                size--;
                modCounter++;
                current = current.next;     //check for duplicates that are side-by-side
            }
            else {      //traverse
                previous = current;
                current = current.next;
            }
        }
        return true;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        Node<E> current = head;
        Node<E> min = head;
        while (current != null) {
            if (current.data.compareTo(min.data) <= 0) {    //when a new minimum is found
                min = current;
            }
            current = current.next;     //traverse
        }
        return min.data;
    }

    @Override
    public boolean contains(E obj) {
        if (isEmpty()) {
            return false;
        }
        Node<E> current = head;
        while (current != null) {
            if (current.data.compareTo(obj) == 0) { //when key is found
                return true;
            }
            current = current.next; //traverse
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
        modCounter++;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new IteratorHelper();
    }

    //i t e r a t o r
    private class IteratorHelper implements Iterator<E> {
        Node<E> nodePointer;
        long modCounter2;

        public IteratorHelper() {
            nodePointer = head;
            modCounter2 = modCounter;
        }
        public boolean hasNext() {
            if (modCounter2 != modCounter) {
                throw new ConcurrentModificationException();
            }
            return nodePointer != null;
        }
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E tmp = nodePointer.data;
            nodePointer = nodePointer.next;
            return tmp;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

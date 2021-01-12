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

public class OrderedLinkedListPriorityQueue <E extends Comparable<E>> implements PriorityQueue<E> {
    private E oSLL;
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

    public OrderedLinkedListPriorityQueue() {
        head = null;
        size = 0;
        modCounter = 0;
    }

    @Override
    public boolean insert(E object) {   //inserted from highest priority -> least priority
        Node<E> newNode = new Node<E>(object);
        Node<E> previous = null;
        Node<E> current = head;
        while (current != null && ((Comparable<E>)object).compareTo(current.data) >= 0) {   //traverse the linked list
            previous = current;
            current = current.next;
        }
        if (previous == null) {     //if the index of insertion is at the beginning
            newNode.next = head;
            head = newNode;
        }
        else {  //if it is in the middle or end
            previous.next = newNode;
            newNode.next = current;
        }
        size++;
        modCounter++;
        return true;
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            return null;
        }
        Node<E> tmp = head;
        head = head.next;   //remove the first node
        size--;
        modCounter++;
        return tmp.data;
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
                else {  //if it is in the middle or the end
                    previous.next = current.next;
                }
                size--;
                modCounter++;
                current = current.next; //check for duplicates right next to each other
            }
            else {  //traverse
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
        return head.data;   //return data of first node
    }

    @Override
    public boolean contains(E obj) {
        if (isEmpty()) {
            return false;
        }
        Node<E> current = head;
        while (current != null) {
            if (current.data.compareTo(obj) == 0) { //if the key is found
                return true;
            }
            current = current.next;     //traverse
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

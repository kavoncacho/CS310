/**
       *  Program 1
       *  Two implementations of a priority queue, priority is FIFO
       *  CS310-1
       *  05FEB2020
       *  @author  Kavon Cacho cssc1209
       */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedArrayPriorityQueue<E extends Comparable <E>> implements PriorityQueue<E>{
	private E[] orderedArray;
	int size = 0;
	private Iterator<E> iterator;
	
	public OrderedArrayPriorityQueue() {
		orderedArray =  (E[]) new Comparable[DEFAULT_MAX_CAPACITY];
	}
	
	public OrderedArrayPriorityQueue(int queueCapacity) {
		orderedArray = (E[]) new Comparable[queueCapacity];
	}

	@Override
	public boolean insert(E object) {
		int mid = 0;
		int low = 0;
		int high = size - 1;
		if (isFull()) {
			return false;
		}
		int position = findPosition(low, high, object); //find the insertion point to insert
		rightShift(position); //when found, shift right to make room for new object
		orderedArray[position] = object;
		return true;
	}

	@Override
	public E remove() {
		if (isEmpty()) {	//returns null if remove is called on empty array
			return null;
		}
		return orderedArray[--size];
		//array is ordered from lowest priority -> highest, so return object at the end while also decrementing size
	}

	@Override
	public boolean delete(E obj) {
		if (!contains(obj)) {
			return false;
		}
		for (int i = 0; i < size; ++i) {
			if (((Comparable<E>)orderedArray[i]).compareTo(obj) == 0) {
				leftShift(i);
				i--;	
			}
		}
		return true;
	}

	@Override
	public E peek() {
		if (isEmpty()) {
			return null;
		}
		return orderedArray[size - 1];	//return element at end of the array
	}

	//binary search that is modified to search an array that is greatest -> least
	@Override
	public boolean contains(E obj) {
		int mid = 0;
		int low = 0;
		int high = size - 1;
		if (isEmpty()) {
			return false;
		}
		while (high >= low) {
			mid = (high + low) / 2;
			if (orderedArray[mid].compareTo(obj) < 0) {
				high = mid - 1;
			}
			else if (orderedArray[mid].compareTo(obj) > 0) {
				low = mid + 1;
			}
			else {
				return true;
			}
		}
		return false;
	}

	@Override
	public int size() {		//return size
		return size;
	}

	@Override
	public void clear() {		//set size to 0
		size = 0;
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
		if (size == orderedArray.length) {
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
		public boolean hasNext() {
			return counter < size;
		}

		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return (E) orderedArray[counter++];
		}
		public void remove () {

			throw new UnsupportedOperationException();
		}
		
	}
	
	private void leftShift(int position) {		//same left shift the unorderedArray class
		for (int i = position; i <= size - 2; ++i) {	
			orderedArray[i] = orderedArray[i + 1];
		}
		size--;
		
	}
	private void rightShift(int position) {		//shifts elements to the right, but runs through the array backwards
		for (int i = size - 1; i >= position; i--) {
			orderedArray[i + 1] = orderedArray[i];
		}
		size++;
	}
	private int findPosition (int low, int high, E objct) {
		if (high < low) {
			return low;
		}
		int mid = (low + high) >> 1;
		if (objct.compareTo(orderedArray[mid]) >= 0) {
			return findPosition(low, mid - 1, objct);
		}
		return findPosition(mid + 1, high, objct);
	}
}

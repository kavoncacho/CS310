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

public class UnorderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private E[] unorderedArray;
	int size = 0;
	private Iterator<E> iterator;
	
	
	public UnorderedArrayPriorityQueue() {
		unorderedArray =  (E[]) new Comparable[DEFAULT_MAX_CAPACITY];
	}

	public UnorderedArrayPriorityQueue(int queueCapacity) {
		unorderedArray = (E[]) new Comparable[queueCapacity];
	}
	
	@Override
	public boolean insert(E object) {
		if (isFull()) { //checks if array is full
			return false;
		}
		unorderedArray[size] = object; //insert object at next null index
		size++;		//increase size
		return true;
	}

	@Override
	public E remove() {
		if (isEmpty()) {	//checks if it is empty
			return null;
		}
		E min = unorderedArray[0];
		int minLocation1 = 0;
		for (int i = 0; i < size; ++i) {
			if (unorderedArray[i].compareTo(min) < 0){	//if current object is less than current min
				min = unorderedArray[i];
				minLocation1 = i;	//log the location of highest priority item
			}
		}
		leftShift(minLocation1);	//left shift to override current min
		return min;
	}

	@Override
	public boolean delete(E obj) {
		if (!contains(obj)) {
			return false;
		}
		int count = 0; //how many times we find instances of obj in array
		int i = 0;	//increase i to find next instance, only when an instance in found
		for (int k = 0; k < size; k++) {
			if (unorderedArray[k].compareTo(obj) == 0) {
				count++;
			}
			else {
				unorderedArray[i] = unorderedArray[i + count];
				i++;
			}
		}
		size = size - count;	//get new size
		return true;
	}

	@Override
	public E peek() {
		E min = unorderedArray[0];
		for (int i = 0; i < size; ++i) {
			if (unorderedArray[i].compareTo(min) < 0) {		//if current object is less than current min
				min = unorderedArray[i];
			}
		}
		return min;
	}

	@Override
	public boolean contains(E obj) {	//linear search method
		if (size == 0) {
			return false;
		}
		E key = obj;
		for (int i = 0; i < size; i++) {
			if (unorderedArray[i].compareTo(key) == 0) {
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
	public void clear() {	//clear array
		size = 0;
	}

	@Override
	public boolean isEmpty() {
		if (size == 0) {		//return true if actually empty
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isFull() {
		if (size == unorderedArray.length) {	//return true is full
			return true;
		}
		else {
			return false;
		}
	}

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
			return (E) unorderedArray[counter++];
		}
		public void remove () {
			throw new UnsupportedOperationException();
		}
		
	}
	
	//iterating through the array, the next element is equal to the current element
	//this overrides the element at int position, effectively deleting it
	private void leftShift(int position) {
		for (int i = position; i <= size - 1; i++) {
			if(i < size - 1)
				unorderedArray[i] = unorderedArray[i + 1];
		}
		size--;
		
	}

}

/**
 *  Program 4
 *  One implementation of a Dictionary
 *  CS310-1
 *  2MAY2020
 *  @author  Kavon Cacho cssc1209
 */
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Hashtable<K, V> implements DictionaryADT<K, V> {
    private ListADT<DictionaryNode<K, V>> [] list;
    int tableSize;
    int maxSize;
    int currentSize;
    int modCounter;

    public Hashtable (int n) {
        maxSize = n;
        tableSize = (int)(n*1.3f);
        currentSize = 0;
        modCounter = 0;
        list = new LinkedListDS[tableSize];
        for (int i = 0; i < tableSize; i++) {
            list[i] = new LinkedListDS<DictionaryNode<K, V>> ();
        }
    }

    private class DictionaryNode<K, V> implements Comparable<DictionaryNode<K, V>> {
        K key;
        V value;

        public DictionaryNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(DictionaryNode<K, V> node) {
            return ((Comparable<K>)key).compareTo((K)node.key);
        }
    }

    private int getHashCode(K key) {
        return (key.hashCode() & 0x7FFFFFFF)%tableSize;
    }

    @Override
    public boolean put(K key, V value) {
        if ((isFull() == true) || (contains(key) == true)) {
            return false;
        }
        else {
            list[getHashCode(key)].addFirst(new DictionaryNode<K, V>(key, value));
            currentSize++;
            modCounter++;
            return true;
        }
    }

    @Override
    public boolean delete(K key) {
        if ((isEmpty() == true) || (contains(key) == false)) {
            return false;
        }
        list[getHashCode(key)].remove(new DictionaryNode<K, V>(key, null));
        currentSize--;
        modCounter++;
        return true;
    }

    @Override
    public V get(K key) {
        if (((isEmpty()) == true) || (contains(key) == false)) {
            return null;
        }
        return list[getHashCode(key)].search(new DictionaryNode<K, V>(key, null)).value;
    }

    @Override
    public K getKey(V value) {
        Iterator<DictionaryNode<K, V>> listIterator;
        if (isEmpty() == true) {
            return null;
        }
        else {
            for (int i = 0; i < list.length; i++) {
                listIterator = list[i].iterator();
                while (listIterator.hasNext() == true) {
                    DictionaryNode<K, V> temp = listIterator.next();
                    if (((Comparable<V>)temp.value).compareTo(value)==0) {
                        return temp.key;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isFull() {
        if (currentSize == maxSize) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        if (currentSize == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < list.length; i++) {
            ListADT l = list[i];
            l.makeEmpty();
        }
        currentSize = 0;
        modCounter++;
    }

    abstract class IteratorHelper<E> implements Iterator<E> {
        protected DictionaryNode<K, V> [] nodes;
        protected int idx;
        protected long modCheck;

        public IteratorHelper() {
            nodes = new DictionaryNode[currentSize];
            idx = 0;
            int j = 0;
            modCheck = modCounter;
            for (int i = 0; i < tableSize; i++)
                for (DictionaryNode n : list[i])
                    nodes[j++] = n;
            nodes = (DictionaryNode<K, V>[]) insertionSort(nodes);
        }

        public boolean hasNext() {
            if (modCheck != modCounter)
                throw new ConcurrentModificationException();
            return idx < currentSize;
        }
        public abstract E next();

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<K> keys() {
        return new KeyIteratorHelper();
    }

    class KeyIteratorHelper<K> extends IteratorHelper<K> {
        public KeyIteratorHelper() {
            super();
        }
        public K next() {
            return (K) nodes[idx++].key;
        }
    }

    @Override
    public Iterator<V> values() {
        return new ValueIteratorHelper();
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V> {
        public ValueIteratorHelper() {
            super();
        }
        public V next() {
            return (V) nodes[idx++].value;
        }
    }

    //helper method
    public boolean contains(K key) {
        if (list[getHashCode(key)].contains(new DictionaryNode<K, V>(key, null)) == true) {
            return true;
        }
        else {
            return false;
        }
    }

    public DictionaryNode<K, V>[] insertionSort(DictionaryNode[] array) {
        DictionaryNode<K, V> [] n = array;
        int in, out;
        DictionaryNode<K, V> temp = null;
        for (out = 1; out < n.length; out++) {
            temp = n[out];
            in = out;
            while (in > 0 && n[in - 1].compareTo(temp) > 0) {
                n[in] = n[in - 1];
                in--;
                in--;
            }
            n[in] = temp;
        }
        return n;
    }

}

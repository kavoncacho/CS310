/**
 *  Program 4
 *  One implementation of a Dictionary
 *  CS310-1
 *  2MAY2020
 *  @author  Kavon Cacho cssc1209
 */
package data_structures;

import java.util.Iterator;
import java.util.TreeMap;

public class BalancedTreeDictionary<K, V> implements DictionaryADT<K, V>{
    K key;
    V value;
    TreeMap rbTree;

    public BalancedTreeDictionary() {
        rbTree = new TreeMap();
        key = null;
        value = null;
    }

    @Override
    public boolean put(K key, V value) {
        if (rbTree.containsKey(key) == true) {
            return false;
        }
        else {
            rbTree.put(key, value);
            return true;
        }
    }

    @Override
    public boolean delete(K key) {
        if (rbTree.containsKey(key) == false) {
            return false;
        }
        rbTree.remove(key);
        return true;
    }

    @Override
    public V get(K key) {
        if ((isEmpty() == true) || (rbTree.containsKey(key) == false)) {
            return null;
        }
        return (V) rbTree.get(key);
    }

    @Override
    public K getKey(V value) {
        Iterator<V> values = values();
        Iterator<K> keys = keys();
        while (values.hasNext()) {
            if(((Comparable<V>)values.next()).compareTo(value) == 0) {
                key = keys.next();
                return key;
            }
            keys.next();
        }
        return null;
    }

    @Override
    public int size() {
        return rbTree.size();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return rbTree.isEmpty();
    }

    @Override
    public void clear() {
        rbTree.clear();
    }

    @Override
    public Iterator<K> keys() {
        return rbTree.navigableKeySet().iterator();
    }

    @Override
    public Iterator<V> values() {
        return rbTree.values().iterator();
    }
}

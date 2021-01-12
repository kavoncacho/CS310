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
import java.util.NoSuchElementException;

public class BinarySearchTree<K, V> implements DictionaryADT<K, V> {
    private Node<K, V> root;
    private int currentSize;
    public int modCounter;

    private class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> leftChild;
        private Node<K, V> rightChild;

        public Node (K k, V v) {
            key = k;
            value = v;
            leftChild = rightChild = null;
        }
    }

    public BinarySearchTree() {
        root = null;
        currentSize = 0;
        modCounter = 0;
    }

    @Override
    public boolean put(K key, V value) {
        Node<K, V> newNode = new Node<K, V>(key, value);
        if (contains(key)) {
            return false;
        }
        if (root == null) {
            root = newNode;
        }
        else {
            Node current = root;
            Node parent;
            for (;;) {
                parent = current;
                if (((Comparable<K>)key).compareTo((K)current.key) < 0) {
                    current = current.leftChild;
                    if (current == null) {
                        parent.leftChild = newNode;
                        currentSize++;
                        return true;
                    }
                }
                else {
                    current = current.rightChild;
                    if (current == null) {
                        parent.rightChild = newNode;
                        currentSize++;
                        return true;
                    }
                }
            }
        }
        currentSize++;
        return true;
    }

    @Override
    public boolean delete(K key) {
        Node<K, V> current = root;
        Node<K, V> parent = null;
        if (isEmpty() == true) {
            return false;
        }
        if (current == null) { //empty dictionary
            return false;
        }

        //nodeToDelete(key, current, parent);
        preOrderDelete(current, parent, key);

        if ((current.leftChild == null) && (current.rightChild == null)) { //zero children
            if (parent.rightChild == current) {
                parent.rightChild = null;
            }
            if (parent.leftChild == current) {
                parent.leftChild = null;
            }
        }
        else if ((current == root)) {
            Node<K, V> temp = current;
            temp = temp.rightChild;
            while (temp.leftChild !=null) {
                temp = temp.leftChild;
            }
            current = temp.leftChild;
            temp.leftChild = null;
        }
        else if ((current.leftChild != null) && (current.rightChild != null)) {

        }
        else if ((current.leftChild != null) || (current.rightChild != null)){ //one child
            if (current.rightChild != null) {
                current.rightChild = current;
                current.rightChild = null;
            }
            if (current.leftChild != null) {
                current.leftChild = current;
                current.leftChild = null;
            }
        }
        else {
            root = null;
        }
        currentSize--;
        modCounter++;
        return true;
    }

    @Override
    public V get(K key) {
        Node<K, V> current = root;
        if (root == null) {
            return null;
        }
        else {
            while(((Comparable<K>) current.key).compareTo((K)key) != 0) {
                if (((Comparable<K>)key).compareTo((K)current.key) < 0) {
                    current = current.leftChild;
                }
                else {
                    current = current.rightChild;
                }
                if (current == null) {
                    return null;
                }
            }
        }
        return current.value;
    }

    @Override
    public K getKey(V value) {
        Node<K, V> current = root;
        if (root == null) {
            return null;
        }
        else {
            while(((Comparable<V>) current.value).compareTo((V)value) != 0) {
                if (((Comparable<V>)value).compareTo((V)current.value) < 0) {
                    current = current.leftChild;
                }
                else {
                    current = current.rightChild;
                }
                if (current == null) {
                    return null;
                }
            }
        }
        return current.key;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isFull() {
        return false;
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
        root = null;
        currentSize = 0;
        modCounter++;
    }

    abstract class IteratorHelper<E> implements Iterator<E>{
        protected int idx,index;
        protected long modCheck;
        protected Node<K,V> [] nodeArray;

        public IteratorHelper(){
            nodeArray=new Node[currentSize];
            index=idx=0;
            modCheck=modCounter;
            orderArray(root);
        }
        public boolean hasNext(){
            if(modCheck != modCounter)
                throw new ConcurrentModificationException();
            return idx < currentSize;
        }
        public abstract E next();

        public void remove(){
            throw new UnsupportedOperationException();
        }
        private void orderArray(Node<K,V> n){
            if(n!=null){
                orderArray(n.leftChild);
                nodeArray[index++] =  n;
                orderArray(n.rightChild);
            }
        }
    }

    @Override
    public Iterator<K> keys() {
        return new KeyIteratorHelper();
    }

    class KeyIteratorHelper<K> extends IteratorHelper<K>{
        public KeyIteratorHelper(){
            super();
        }
        public K next(){
            if(!hasNext())
                throw new NoSuchElementException();
            return (K) nodeArray[idx++].key;
        }
    }

    @Override
    public Iterator<V> values() {
        return new ValueIteratorHelper();
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V>{
        public ValueIteratorHelper(){
            super();
        }
        public V next(){
            if(!hasNext())
                throw new NoSuchElementException();
            return (V) nodeArray[idx++].value;
        }
    }

    //helper methods
    public boolean contains(K key) {
        return find(key,root) != null;
    }

    private V find(K key, Node<K,V> n){
        if(n == null)
            return null;
        int comp = ((Comparable<K>)key).compareTo(n.key);
        if(comp < 0)
            return find(key,n.leftChild);	//go left
        else if(comp > 0)
            return find(key,n.rightChild);	//go right
        else
            return (V) n.value;			//found it
    }

    private void preOrder (Node<K, V> n) {
        if (n != null) {
            preOrder(n.leftChild);
            preOrder(n.rightChild);
        }
    }

    private void preOrderDelete (Node<K, V> n, Node<K, V> parent, K key) {
        if (n == null) {
            parent = getSuccessor(n);
            if (n.key != key) {
                preOrder(n.leftChild);
                preOrder(n.rightChild);
            }
        }
    }

    private Node<K,V> getSuccessor(Node<K,V> n){
        Node<K,V> parent = null;
        while(n.leftChild != null){
            parent = n;
            n = n.leftChild;
        }
        if(parent == null)
            return null;
        else
            parent.leftChild = n.rightChild;
        return n;
    }

    private Node<K, V> nodeToDelete(K key, Node<K, V> n, Node<K, V> parent) {
        if (key == n.key) {
            return n;
        }
        else if (((Comparable<K>) key).compareTo(n.key) < 0) {
            if (n.leftChild != null) {
                parent = getSuccessor(n);
                return nodeToDelete(key, n.leftChild, parent);
            }
            else {
                return null;
            }
        }
        else if (((Comparable<K>) key).compareTo(n.key) > 0) {
            if (n.rightChild != null) {
                parent = getSuccessor(n);
                return nodeToDelete(key, n.rightChild, parent);
            }
            else {
                return null;
            }
        }
        return null;
    }
}

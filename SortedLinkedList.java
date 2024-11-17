//package project3;

import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of a sorted doubly-linked list.
 * All elements in the list are maintained in ascending/increasing order
 * based on the natural order of the elements.
 * This list does not allow <code>null</code> elements.
 *
 * @author Joanna Klukowska
 * @author Aarit Hundi
 * @version 10/31/2024
 *
 * @param <E> the type of elements held in this list
 */
public class SortedLinkedList<E extends Comparable<E>>
    implements Iterable<E> {

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs a new empty sorted linked list.
     */
    public SortedLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds the specified element to the list in ascending order.
     *
     * @param element the element to add
     * @return <code>true</code> if the element was added successfully,
     * <code>false</code> otherwise (if <code>element==null</code>)
     */
    public boolean add(E element) {
        if (element == null){
            return false; 
        }
        
        Node n = new Node(element);
        

        //handles empty list
        if (head == null){
            head = n;
            tail = n;
            size++;
            return true;
        }

        //handles adding to front (if smaller than head)
        if(n.data.compareTo(head.data) < 0){
            n.next = head;
            head.prev = n;
            head = n;
            size++;
            return true;
        }

        //handles adding to end (if larger than tail)
        if (n.data.compareTo(tail.data) >= 0){
            tail.next = n;
            n.prev = tail;
            tail = n;
            size++;
            return true;
        }

        //handles adding to middle 
        Node cur = head;
        //iterate to spot before position to be added
        while(cur!=null && n.data.compareTo(cur.data) > 0){
            cur=cur.next;
        }


        if (cur != null) {
            n.next = cur;
            n.prev = cur.prev;
        if (cur.prev != null) {
            cur.prev.next = n;
        }
            cur.prev = n;
        } 

        size++;
        return true;
    }
 
    

    /**
     * Removes all elements from the list.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns <code>true</code> if the list contains the specified element,
     * <code>false</code> otherwise.
     *
     * @param o the element to search for
     * @return <code>true</code> if the element is in the list,
     * <code>false</code> otherwise
     */
    public boolean contains(Object o) {
        Node cur = head;
        while(cur!=null){
            if(cur.data.equals(o))
                return true;
            cur=cur.next;
        }
        return false;
    }

    /**
     * Returns the element at the specified index in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throw IndexOutOfBoundsException  if the index is out of
     * range <code>(index < 0 || index >= size())</code>
     */
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()){
            throw new IndexOutOfBoundsException("index is out of range");
        }
        Node cur = head;
        int count = 0;
        while(count<index){
            cur=cur.next;
            count++;
        }
        return cur.data;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or -1 if the element is not in the list.
     *
     * @param o the element to search for
     * @return the index of the first occurrence of the element,
     * or -1 if the element is not in the list
     */
    public int indexOf(Object o) {
        if (o == null){
            return -1;
        }
        Node cur = head;
        int count = 0;
        while(cur!=null){
            if(cur.data.equals(o)){
                return count;
            }
            cur=cur.next;
            count++;
        }
        return -1;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * starting at the specified <code>index</code>, i.e., in the range of indexes
     * <code>index <= i < size()</code>, or -1 if the element is not in the list
     * in the range of indexes <code>index <= i < size()</code>.
     *
     * @param o the element to search for
     * @param index the index to start searching from
     * @return the index of the first occurrence of the element, starting at the specified index,
     * or -1 if the element is not found
     */
    public int nextIndexOf(Object o, int index) {
        Node cur = head; 
        int count = 0;
        while(count<index){ //first iterate to the specified index
            cur=cur.next;
            count++;
        }
        
        while(cur.next!=null){
            if(cur.data.equals(o)){
                return count;
            }
            cur=cur.next;
            count++;
        }
        return -1;
    }

    /**
     * Removes the first occurence of the specified element from the list.
     *
     * @param o the element to remove
     * @return <code>true</code> if the element was removed successfully,
     * <code>false</code> otherwise
     */
    public boolean remove(Object o) {
        if (o == null){
            return false;
        }
       
        //add check for class matching type

        //handles empty list
        if (size == 0){
            return true;
        }

        //handles removing first element
        if (head.data.equals(o)){
            head=head.next;
            if(size == 1){// if only one element
                tail = null;
            }
            size--;
            return true;
        }

        //handles removing last element
        if(tail.data.equals(o)){
            tail=tail.prev;
            tail.next=null;
            size--;
            return true;
        }

        //handles removing anywhere else
        Node cur = head;
        while(cur.next!=null){
            if(cur.next.data.equals(o)){
                cur.next = cur.next.next;
                cur.next.prev = cur;
                size--;
                return true;
            }
            cur=cur.next;
        }
        return false;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return an iterator over the elements in the list
     */
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Compares the specified object with this list for equality.
     *
     * @param o the object to compare with
     * @return <code>true</code> if the specified object is equal to this list,
     * <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object o) {
        //if same reference
        if (this == o){
            return true;
        }
        //check for nullity/instance of 
        if(o == null || !(o instanceof SortedLinkedList)){
            return false;
        }
        SortedLinkedList<E> other = (SortedLinkedList<E>)o;

        if (this.size() != other.size()){
            return false;
        }

        Node cur = head;
        Node otherCur = other.head;

        while(cur!=null){
            if(!(cur.data.equals(otherCur.data))){
                return false;
            }
            cur=cur.next;
            otherCur= otherCur.next;
        }
        return true;
    }

    /**
     * Returns a string representation of the list.
     *  The string representation consists of a list of the lists's elements in
     *  ascending order, enclosed in square brackets ("[]").
     *  Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return a string representation of the list
     */

    
    public String toString() {
        /*
        String str = "";
        for (E element : this){
            str = str + element + ", ";
        }
        return "[" + str.substring(0,str.length()-2) + "]";
        */

        if (head == null){
            return "[]";
        }

        Node cur = head;
        String str = "[";
        
        while(cur!=null){
            str+= cur.data;
            if(cur.next!=null){
                str+= ", ";
            }
            cur=cur.next;
        }

        str+= "]";
        return str;
    }

    /* Inner class to represent nodes of this list.*/
    private class Node implements Comparable<Node> {
        E data;
        Node next;
        Node prev;
        Node(E data) {
            if (data == null ) throw new NullPointerException ("does not allow null");
            this.data = data;
        }
        Node (E data, Node next, Node prev) {
            this(data);
            this.next = next;
            this.prev = prev;
        }
        public int compareTo( Node n ) {
            return this.data.compareTo(n.data);
        }
    }

    /* A basic forward iterator for this list. */
    private class ListIterator implements Iterator<E> {

        Node nextToReturn = head;
        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (nextToReturn == null )
                throw new NoSuchElementException("the end of the list reached");
            E tmp = nextToReturn.data;
            nextToReturn = nextToReturn.next;
            return tmp;
        }

    }
}





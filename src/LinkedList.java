import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements Iterable<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;


    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public void addAll(Iterable<E> elements) {
        for (E element:
             elements) {
            this.add(element);
        }
    }


    private static class Node<E> {
        private E data;
        private Node<E> next;

        public Node(E data) {
            this.data = data;
            this.next = null;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<E> {
        private Node<E> pointer = head;
        private Node<E> previous = null;
        private Node<E> lastReturned = null;

        @Override
        public boolean hasNext() {
            return pointer != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E data = pointer.data;
            previous = lastReturned;
            lastReturned = pointer;
            pointer = pointer.next;
            return data;
        }

        @Override
        public void remove() {
            size--;
            if (head == tail) {
                head = null;
                tail = null;
                return;
            }
            if (lastReturned != head && lastReturned != tail) {
                previous.next = pointer;
            }
            if (lastReturned == head) {
                head = head.next;
            }
            if (lastReturned == tail) {
                tail = previous;
                tail.next = null;
            }
        }
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (E element:
             this) {
            stringBuilder.append(element).append(", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

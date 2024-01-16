import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyList<E> implements Iterable<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int size;

    public MyList() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void add(E element) {
        ensureCapacity();
        array[size++] = element;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) array[index];
    }

    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(get(i))) {
                return true;
            }
        }
        return false;
    }

    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public void addAll(Iterable<E> elements) {
        for (E element : elements) {
            this.add(element);
        }
    }

    public MyList<E> subList(int startIndex, int endIndex) {
        MyList<E> sub = new MyList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            sub.add(get(i));
        }
        return sub;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == array.length) {
            array = Arrays.copyOf(array, size * 2);
        }
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) array[currentIndex++];
            }
        };
    }

    public void quickSort(Comparator<? super E> comparator) {
        quickSortHelper(0, size - 1, comparator);
    }

    private void quickSortHelper(int low, int high, Comparator<? super E> comparator) {
        if (low < high) {
            int partitionIndex = partition(low, high, comparator);

            quickSortHelper(low, partitionIndex - 1, comparator);
            quickSortHelper(partitionIndex + 1, high, comparator);
        }
    }

    private int partition(int low, int high, Comparator<? super E> comparator) {
        E pivot = get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(get(j), pivot) <= 0) {
                i++;

                E temp = get(i);
                array[i] = array[j];
                array[j] = temp;
            }
        }

        E temp = get(i + 1);
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (E o : this) {
            stringBuilder.append(o).append(" \n");
        }
        return stringBuilder.toString();
    }
}

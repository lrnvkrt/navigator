import java.util.Iterator;

public class Map<K, V> implements Iterable<KeyValue<K, V>> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75d;

    private LinkedList<KeyValue<K, V>>[] slots;
    private int count;
    private int capacity;

    public Map() {
        this(INITIAL_CAPACITY);
    }

    public Map(int capacity) {
        this.capacity = capacity;
        this.slots = new LinkedList[capacity];
        for(int i = 0; i < capacity; i++) {
            this.slots[i] = new LinkedList<>();
        }
    }

    public void add(K key, V value) {
        growIfNeeded();
        int slotNumber = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber];


        for (KeyValue<K, V> keyValue : slot) {
            if (keyValue.getKey().equals(key)) {
                // Ключ уже существует, обновить значение
                keyValue.setValue(value);
                return;
            }
        }

        // Такого ключа нет, добавить новую пару ключ-значение
        slot.add(new KeyValue<>(key, value, count));
        System.out.println("Текущий коэфициент заполнения: " + (double) (this.size() +1) / this.capacity());
        count++;
    }

    private int findSlotNumber(K key) {
        return Math.abs(key.hashCode()) % this.slots.length;
    }

    private void growIfNeeded() {
        if ((double) (this.size() +1) / this.capacity() > LOAD_FACTOR) {
            this.grow();
        }
    }

    private void grow() {
        int newCapacity = this.capacity * 2;
        LinkedList<KeyValue<K, V>>[] newSlots = new LinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newSlots[i] = new LinkedList<>();
        }

        for (LinkedList<KeyValue<K, V>> slot : this.slots) {
            for (KeyValue<K, V> keyValue : slot) {
                int newSlotNumber = Math.abs(keyValue.getKey().hashCode()) % newCapacity;
                newSlots[newSlotNumber].add(keyValue);
            }
        }

        this.slots = newSlots;
        this.capacity = newCapacity;
    }

    public int getOrder(K key) {
        return this.find(key).getEntryOrder();
    }

    public int size() {
        return this.count;
    }

    public int capacity() {
        return this.capacity;
    }

    public int collision() {
        int collisions = 0;
        for (LinkedList<KeyValue<K, V>> list : slots) {
            if (list != null && list.size()>1) {
                collisions += list.size() - 1;
            }
        }
        return collisions;
    }

    public boolean addOrReplace(K key, V value) {
        growIfNeeded();
        int slotNumber = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber];


        for (KeyValue<K, V> keyValue : slot) {
            if (keyValue.getKey().equals(key)) {
                // Ключ уже существует, обновить значение
                keyValue.setValue(value);
                return true;
            }
        }

        // Ключ не существует, добавить новое ключ-значение
        slot.add(new KeyValue<>(key, value, count));
        count++;
        return false;
    }

    public V get(K key) {
//        int slotNumber = findSlotNumber(key);
//        LinkedList<KeyValue<K, V>> slot = slots[slotNumber];
//
//        for (KeyValue<K, V> keyValue : slot) {
//            if (keyValue.getKey().equals(key)) {
//                return keyValue.getValue();
//            }
//        }
//
//        return null;
        KeyValue<K, V> pair = this.find(key);
        if(pair != null) {
            return pair.getValue();
        }
        return null;
    }

    public KeyValue<K, V> find(K key) {
        int slotNumber = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber];

        for (KeyValue<K, V> keyValue : slot) {
            if (keyValue.getKey().equals(key)) {
                // Ключ найден, вернуть значение
                return keyValue;
            }
        }

        // Ключ не найден
        return null;
    }

    public boolean containsKey(K key) {
        return find(key) != null;
    }

    public boolean remove(K key) {
        int slotNumber = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber];

        Iterator<KeyValue<K, V>> iterator = slot.iterator();
        while (iterator.hasNext()) {
            KeyValue<K, V> keyValue = iterator.next();
            if (keyValue.getKey().equals(key)) {
                // Ключ найден, удалить ключ-значение
                iterator.remove();
                count--;
                return true;
            }
        }

        // Ключ не найден
        return false;

    }

    public void clear() {
        for (int i = 0; i < this.capacity; i++) {
            this.slots[i].clear();
        }
        this.count = 0;
    }

    public Iterable<K> keys() {
        LinkedList<K> keys = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> slot : this.slots) {
            for (KeyValue<K, V> keyValue : slot) {
                keys.add(keyValue.getKey());
            }
        }
        return keys;
    }

    public Iterable<V> values() {
        LinkedList<V> values = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> slot : this.slots) {
            for (KeyValue<K, V> keyValue : slot) {
                values.add(keyValue.getValue());
            }
        }
        return values;
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        LinkedList<KeyValue<K, V>> allPairs = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> slot : this.slots) {
            allPairs.addAll(slot);
        }
        return allPairs.iterator();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("HashTable{ \n");

        for (int i = 0; i < capacity; i++) {
            result.append("[").append(i).append("] = ").append(slots[i]).append(", \n");
        }

        result.append("count=").append(count).append(", capacity=").append(capacity).append("}");
        return result.toString();
    }
}

package co.inventorsoft.academy.collections.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

public class Range<T> implements Set<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private T[] array;
    private int size;

    public Range() {
        this.array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if ((array[i] == null && o == null) || (o != null && o.equals(array[i]))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] clone = new Object[size];
        System.arraycopy(array, 0, clone, 0, size);
        return clone;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            a = (T1[])new Object[size];
        }
        System.arraycopy(array, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(T t) {
        if (contains(t)) {
            return false;
        }
        resizeIfNeeded();
        array[size] = t;
        size++;
        return true;
    }
    private void resizeIfNeeded() {
        if (array.length == size) {
            Object[] newArray = new Object[array.length * 2];
            System.arraycopy(array, 0, newArray,0, size);
            array = (T[]) newArray;
        }
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if ((array[i] == null && o == null) || (o != null && o.equals(array[i]))) {
                System.arraycopy(array, i + 1, array, i, size - i - 1);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) {
            return true;
        }
        for (Object b : c) {
            if (!contains(b)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (Object o : c) {
            if (contains(o)) {
                continue;
            }
            resizeIfNeeded();
            add((T) o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        if (c.isEmpty()) {
            return false;
        }
        for (int i = size - 1; i >= 0; i--) {
            if (!c.contains(array[i])) {
                changed = remove(array[i]);
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }
        boolean changed = false;
        for (Object b : c) {
            if (remove(b)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        array = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public static Range<Byte> of(Byte low, Byte high) {
        return Range.of(low, high, aByte -> ++aByte);
    }

    public static Range<Short> of(Short low, Short high) {
        return Range.of(low, high, aShort -> ++aShort);
    }

    public static Range<Integer> of(Integer low, Integer high) {
        return Range.of(low, high, integer -> ++integer);
    }

    public static Range<Long> of(Long low, Long high) {
        return Range.of(low, high, aLong -> ++aLong);
    }

    public static Range<Float> of(Float low, Float high) {
        return Range.of(low, high, aFloat -> aFloat + 0.1f);
    }

    public static Range<Double> of(Double low, Double high) {
        return Range.of(low, high, aDouble -> aDouble + 0.1);
    }

    /**
     * Method created a custom collection which represents a range.
     * @param a first element in the range
     * @param b last element in the range(included)
     * @param func the functional interface works with {@param a}
     * @param <T> custom type with implementation of Comparable.
     * @return collection based on collection framework’s Set interface
     */
    public static <T extends Comparable<T>> Range<T> of (T a, T b, Function<T, T> func) {
        Range<T> range = new Range<>();
        if (a.compareTo(b) == 0) {
            return range;
        }
        while (a.compareTo(b) != 0) {
            range.add(a);
            a = func.apply(a);
        }
        range.add(b);
        return range;
    }

    private class MyIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            return array[currentIndex++];
        }
    }
}



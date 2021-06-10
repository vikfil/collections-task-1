package co.inventorsoft.academy.collections.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
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
        Range<Byte> range = new Range<>();
        if (low.equals(high)) {
            return range;
        }
        for (byte i = low; i <= high; i++) {
            range.add(i);
        }
        return range;
    }

    public static Range<Short> of(Short low, Short high) {
        Range<Short> range = new Range<>();
        if (low.equals(high)) {
            return range;
        }
        for (short i = low; i <= high; i++) {
            range.add(i);
        }
        return range;
    }

    public static Range<Integer> of(Integer low, Integer high) {
        Range<Integer> range = new Range<>();
        if (low.equals(high)) {
            return range;
        }
        for (int i = low; i <= high; i++) {
            range.add(i);
        }
        return range;
    }

    public static Range<Long> of(Long low, Long high) {
        Range<Long> range = new Range<>();
        if (low.equals(high)) {
            return range;
        }
        for (long i = low; i <= high; i++) {
            range.add(i);
        }
        return range;
    }

    public static Range<Float> of(Float low, Float high) {
        Range<Float> range = new Range<>();
        if (low.equals(high)) {
            return range;
        }
        float precision = 0.1f;
        for (float i = low; i <= high; i += precision) {
            float number = BigDecimal.valueOf(i).setScale(1, RoundingMode.HALF_UP).floatValue();
            range.add(number);
        }
        return range;
    }

    public static Range<Double> of(Double low, Double high) {
        Range<Double> range = new Range<>();
        if (low.equals(high)) {
            return range;
        }
        double precision = 0.1;
        for (double i = low; i <= high; i += precision) {
            double number = BigDecimal.valueOf(i).setScale(1, RoundingMode.HALF_UP).doubleValue();
            range.add(number);
        }
        return range;
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



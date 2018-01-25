package co.inventorsoft.academy.collections.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;


public class Range<T> implements Set<T> {

    private HashSet<T> set;
    private Range(){
        set = new HashSet<T>();
    }


    public int size() {
        return set.size();
    }

    public boolean isEmpty(){
        return set.isEmpty();
    }

    public boolean contains(Object o) {

        return set.contains(o);
    }

    public Iterator<T> iterator()
    {
        return set.iterator();
    }

    public Object[] toArray() {

        return set.toArray();
    }

    public <T1> T1[] toArray(T1[] a) {
        return set.toArray(a);
    }

    public boolean add(T t) {

        return set.add(t);
    }

    public boolean remove(Object o) {

        return set.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        return set.addAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return set.retainAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return set.removeAll(c);
    }

    public void clear() {
        set.clear();
    }

    public static <T extends Comparable> Range<T> of(T from, T to, Function<T, T> increment) {
        Range<T> range = new Range<T>();
        if (from.compareTo(to) == 0) {
            return range;
        }

        T next = from;
        while (next.compareTo(to) <= 0) {
            range.add(next);
            next = increment.apply(next);
        }
        return range;
    }


    public static Range<Float> of(Float from, Float to) {
        return of(from, to, new Function<Float, Float>() {

            public Float apply(Float aFloat) {
                return aFloat + 0.1f;
            }
        });
    }


    public static Range<Integer> of(Integer from, Integer to) {
        return of(from, to, new Function<Integer, Integer>() {

            public Integer apply(Integer aInteger) {
                return aInteger + 1;
            }
        });
    }

    public static Range<Double> of(Double from, Double to) {
        return of(from, to, new Function<Double, Double>() {

            public Double apply(Double aDouble) {
                return aDouble + 0.1;
            }
        });
    }



    public static Range<Short> of(Short from, Short to) {
        return of(from, to, new Function<Short, Short>() {

            public Short apply(Short aShort) {
                return (short)(aShort + 1);
            }
        });
    }

    public static Range<Byte> of(Byte from, Byte to) {
        return of(from, to, new Function<Byte, Byte>() {

            public Byte apply(Byte aByte) {
                return (byte)(aByte + 1);
            }
        });
    }

}

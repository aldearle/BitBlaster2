package uk.al_richard.BitBlaster2.Impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A ordered set of data.
 * @param <T>
 */
public class DataSet<T> implements Iterable<T> {

    private final List<T> data; // with predictable iteration order and indexable.

    public DataSet(List<T> data ) {
        this.data = data;
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

    public T get(int i) {
        return data.get(i);
    }

    public List<T> getAsList(int i) {
        List<T> result = new ArrayList<>();
        result.add( data.get(i) );
        return result;
    }

    public int size() {
        return data.size();
    }

}

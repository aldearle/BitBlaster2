package uk.al_richard.BitBlaster2;

import java.util.List;
import java.util.Set;

/**
 * @param <T> the type of values being modelled
 *
 *  This class represents the class of binary partitions of a metric space
 */
public abstract class Paritition<T> {

    public Paritition( List<T> pivots, Set<T>  reference_objects ) {
    }

    abstract public double f(T datum);


}

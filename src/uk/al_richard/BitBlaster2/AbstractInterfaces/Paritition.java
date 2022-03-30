package uk.al_richard.BitBlaster2.AbstractInterfaces;

import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

/**
 * @param <T> the type of values being modelled
 *  This class represents the class of binary (?) partitions of a metric space
 */
public abstract class Paritition<T> {

    protected ToDoubleBiFunction<List<T>,T> f;
    private final List<T> pivots;
    private final Set<T> reference_objects;

    /**
     * Create a partition
     * @param pivots - the ordered list of pivots used to define the partition - may be 1 or 2 (or more?)
     * @param reference_objects - the set of reference objects which are used to define this partition
     **/
//    public Paritition(List<T> pivots, Set<T> reference_objects ) {
//    }

    /**
     * Could be this interface:
     * Create a partition
     * @param pivots - the ordered list of pivots used to define the partition - may be 1 or 2 (or more?)
     * @param reference_objects - the set of reference objects which are used to define this partition
     * @param f a 'is in' function which determins if a datum is in the Partition or not
     */
    public Paritition(List<T> pivots, Set<T> reference_objects, ToDoubleBiFunction<List<T>, T> f ) {
        this.pivots = pivots;
        this.reference_objects = reference_objects;
        this.f = f;
    }

    /**
     * @param datum - a data item of type T drawn from S.
     * @return the distance the datum is from the partition boundary.
     * 0 means that the object is on the boundary
     * A positive value is outside the boundary
     * A negative value is inside the boundary
     */
//    abstract public double f(T datum);

    public double callF(T datum) {
        return f.applyAsDouble( pivots,datum );
    }

}

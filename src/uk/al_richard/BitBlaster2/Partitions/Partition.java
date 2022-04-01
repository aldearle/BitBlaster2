package uk.al_richard.BitBlaster2.Partitions;

import uk.al_richard.BitBlaster2.Impl.ReferencePointSet;

import java.util.List;
import java.util.function.ToDoubleBiFunction;

/**
 * @param <T> the type of values being modelled
 *  This class represents the class of binary (?) partitions of a metric space
 */
public abstract class Partition<T> {

    protected ToDoubleBiFunction<List<T>,T> f_impl;
    protected final List<T> pivots;
    protected final ReferencePointSet<T> reference_objects;

    // if f is wrapped in a function there is no way to get tau out of it.
    // Does this mean that it should be a class?

    /**
     * Create a partition
     * @param pivots - the ordered list of pivots used to define the partition - may be 1 or 2 (or more?)
     * @param reference_objects - the set of reference objects which are used to define this partition
     **/
    public Partition(List<T> pivots, ReferencePointSet<T> reference_objects ) {
        this.pivots = pivots;
        this.reference_objects = reference_objects;
        // f is not initialised.
    }

    /**
     * Could be this interface:
     * Create a partition
     * @param pivots - the ordered list of pivots used to define the partition - may be 1 or 2 (or more?)
     * @param reference_objects - the set of reference objects which are used to define this partition
     * @param f_impl a 'is in' function which determines if a datum is in the Partition or not
     */
    public Partition(List<T> pivots, ReferencePointSet<T> reference_objects, ToDoubleBiFunction<List<T>,T> f_impl ) {
        this.pivots = pivots;
        this.reference_objects = reference_objects;
        this.f_impl = f_impl;
    }

    /**
     * @param datum - a data item of type T drawn from S.
     * @return the distance the datum is from the partition boundary.
     * 0 means that the object is on the boundary
     * A positive value is outside the boundary
     * A negative value is inside the boundary
     */
    public abstract double f(T datum);

    // Getters

    public List<T> getPivots() {
        return pivots;
    }

    public ReferencePointSet<T> getReferenceObjects() {
        return reference_objects;
    }

}

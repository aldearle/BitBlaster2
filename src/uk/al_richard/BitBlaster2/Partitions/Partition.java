package uk.al_richard.BitBlaster2.Partitions;

import eu.similarity.msc.core_concepts.Metric;

import java.util.List;
import java.util.function.ToDoubleBiFunction;

/**
 * @param <T> the type of values being modelled
 *  This class represents the class of binary (?) partitions of a metric space
 */
public abstract class Partition<T> {

    protected ToDoubleBiFunction<double[],T> f_impl;
    protected final List<T> pivots;
    private final List<Integer> pivot_indices;
    private final Metric<T> metric;

    // if f is wrapped in a function there is no way to get tau out of it.
    // Does this mean that it should be a class?

    /**
     * Create a partition
     * @param pivots - the ordered list of pivots used to define the partition - may be 1 or 2 (or more?)
     * @param pivot_indices - the indicies into the set of reference objevcts from which the pivots are drawn.
     **/
    public Partition(List<T> pivots, List<Integer> pivot_indices, Metric<T> metric ) {
        this.pivots = pivots;
        this.pivot_indices = pivot_indices;
        this.metric = metric;
        // f is not initialised.
    }

    /**
     * Could be this interface:
     * Create a partition
     * @param pivots - the ordered list of pivots used to define the partition - may be 1 or 2 (or more?)
     * @param pivot_indices - the indicies into the set of reference objevcts from which the pivots are drawn.
     * @param f_impl a 'is in' function which determines if a datum is in the Partition or not
     */
    public Partition(List<T> pivots, List<Integer> pivot_indices, Metric<T> metric, ToDoubleBiFunction<double[],T> f_impl ) {
        this( pivots,pivot_indices,metric );
        this.f_impl = f_impl;
    }

    /**
     * @param distances an array of distances to this partition's pivots
     * @param datum - a data item of type T drawn from S.
     * @return the distance the datum is from the partition boundary.
     * 0 means that the object is on the boundary
     * A positive value is outside the boundary
     * A negative value is inside the boundary
     */
    public abstract double fWithprecomputedPivotDistances(double[] distances, T datum);

    public double f(T datum) {
        double[] distances_to_pivots = new double[pivots.size()];
        for( int i = 0; i < pivots.size(); i++ ) {
            distances_to_pivots[i] = metric.distance( datum, pivots.get(i) );
        }
        return fWithprecomputedPivotDistances(distances_to_pivots,datum);
    }

    // Getters

    public List<T> getPivots() {
        return pivots;
    }

    private double[] selectFrom(double[] distances_to_all_reference_objects) {
        double[] distances_to_pivots = new double[pivots.size()];
        for( int i = 0; i < pivots.size(); i++ ) {
            distances_to_pivots[i] = distances_to_all_reference_objects[ pivot_indices.get(i) ];
        }
        return distances_to_pivots;
    }
}

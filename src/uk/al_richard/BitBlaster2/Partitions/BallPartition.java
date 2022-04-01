package uk.al_richard.BitBlaster2.Partitions;

import eu.similarity.msc.core_concepts.Metric;
import uk.al_richard.BitBlaster2.Impl.PartitionException;

import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleBiFunction;

/**
 * @param <T> the type of values being modelled
 *  This class represents the class of ball partitions
 *
 * Question - is each ball going to be a separate instance or have multiple taus?
 *            the existence of f suggests the former.
 */
public class BallPartition<T> extends Partition<T> {

    private final double radius;
    private Metric<T> metric; // needs to be set if used as below.

    private final ToDoubleBiFunction<double[],T> ball_static_f = new ToDoubleBiFunction<>() {
        @Override
        public double applyAsDouble(double[] distances_to_pivot, T datum) {
            return radius - distances_to_pivot[0];   // < 0 means inside.
        }
    };

    public BallPartition(List<T> pivots, List<Integer> pivot_indexes, Metric<T> metric, double radius) throws PartitionException {
        super( pivots,pivot_indexes,metric );
        this.radius = radius;
        if( pivots.size() != 1 ) {
            throw new PartitionException( "Illegal number of pivots passed to BallPartition: " + pivots.size() );
        }
        super.f_impl = ball_static_f;
    }

    public BallPartition(List<T> pivots, List<Integer> pivot_indexes, double radius, Metric<T> metric, ToDoubleBiFunction<double[],T> f_impl ) throws PartitionException {
        super( pivots,pivot_indexes,metric,f_impl );
        this.radius = radius;
        if( pivots.size() != 1 ) {
            throw new PartitionException( "Illegal number of pivots passed to BallPartition: " + pivots.size() );
        }
    }

//    @Override
//    public double f(T datum) {
//        double pivot_distance = metric.distance(pivots.get(0), datum);
//        double[] pivot_distances = new double[]{ pivot_distance };
//        return fdist( pivot_distances,datum );
//    }

    @Override
    public double fWithprecomputedPivotDistances(double[] distances_to_pivot, T datum) {
        return f_impl.applyAsDouble(distances_to_pivot,datum);
    }

    public T getPivot() {
        return super.getPivots().get(0);
    }

    // Housekeeping methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BallPartition)) return false;
        BallPartition<?> that = (BallPartition<?>) o;
        return  Objects.equals( this.getPivots().get(0), that.getPivots().get(0) )   ;   // same pivot TODO look at pivot equality
    }

    @Override
    public int hashCode() {
        return Objects.hash( this.getPivots().get(0).hashCode() );
    }
}

package uk.al_richard.BitBlaster2.Partitions;

import eu.similarity.msc.core_concepts.Metric;
import uk.al_richard.BitBlaster2.Impl.PartitionException;
import uk.al_richard.BitBlaster2.Impl.ReferencePointSet;

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

    private final ToDoubleBiFunction<List<T>,T> ball_static_f1 = new ToDoubleBiFunction<>() {
        @Override
        public double applyAsDouble(List<T> pivots, T datum) {
            return radius - metric.distance(pivots.get(0), datum );   // < 0 means inside.
        }
    };

    public BallPartition(List<T> pivots, ReferencePointSet<T> reference_objects, double radius) throws PartitionException {
        super( pivots, reference_objects );
        this.radius = radius;
        if( pivots.size() != 1 ) {
            throw new PartitionException( "Illegal number of pivots passed to BallPartition: " + pivots.size() );
        }
        super.f_impl = ball_static_f1;
    }

    public BallPartition(List<T> pivots, ReferencePointSet<T> reference_objects, double radius, ToDoubleBiFunction<List<T>,T> f_impl ) throws PartitionException {
        super( pivots,reference_objects,f_impl );
        this.radius = radius;
        if( pivots.size() != 1 ) {
            throw new PartitionException( "Illegal number of pivots passed to BallPartition: " + pivots.size() );
        }
    }

    public T getPivot() {
        return super.getPivots().get(0);
    }

    @Override
    public double f(T datum) {
        return f_impl.applyAsDouble( pivots,datum );
    }

    // Housekeeping methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BallPartition)) return false;
        BallPartition<?> that = (BallPartition<?>) o;
        return  Objects.equals(this.getPivots().get(0), that.getPivots().get(0)) &&      // same pivot
                Objects.equals(this.getReferenceObjects(), that.getReferenceObjects() ); // pointer equality
    }

    @Override
    public int hashCode() {
        return Objects.hash( this.getPivots().hashCode() + this.getReferenceObjects().hashCode() );
    }
}

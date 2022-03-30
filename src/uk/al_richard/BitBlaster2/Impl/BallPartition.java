package uk.al_richard.BitBlaster2.Impl;

import uk.al_richard.BitBlaster2.AbstractInterfaces.Paritition;

import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

/**
 * @param <T> the type of values being modelled
 *  This class represents the class of ball partitions
 *
 * Question - is each ball going to be a separate instance or have multiple taus?
 *            the existence of f suggests the former.
 */
public class BallPartition<T> extends Paritition<T> {

    private final ToDoubleBiFunction<List<T>, T> ball_static_f1 = new ToDoubleBiFunction<>() {
        @Override
        public double applyAsDouble(List<T> ts, T t) {
            return 0.0d; // needs a metric too so this is not enough!
        }
    };

    private final ToDoubleBiFunction<List<T>, T> ball_static_f2 = (a,b) -> { return 0.0d; };

    public BallPartition(List pivots, Set reference_objects) {
        super( pivots, reference_objects, (a,b) -> { return 0.0d; } );
        super.f = ball_static_f1;   // legal
    }
}

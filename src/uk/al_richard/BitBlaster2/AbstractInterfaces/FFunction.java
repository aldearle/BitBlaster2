package uk.al_richard.BitBlaster2.AbstractInterfaces;

import java.util.List;
import java.util.function.ToDoubleBiFunction;

@FunctionalInterface
public interface FFunction<T> {
        double apply(T datum, List<T> pivots, ToDoubleBiFunction<T,T> distance );
}

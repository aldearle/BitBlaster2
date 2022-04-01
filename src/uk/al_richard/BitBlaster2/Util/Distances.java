package uk.al_richard.BitBlaster2.Util;

import eu.similarity.msc.core_concepts.Metric;
import uk.al_richard.BitBlaster2.Impl.DataSet;

import java.util.Collection;
import java.util.List;

public class Distances {

    public static <T> double[] distancesTo(T datum, List<T> targets, Metric<T> metric ) {    // was extDists
        double[] distances_to = new double[targets.size()];
        for (int i = 0; i < targets.size(); i++) {
            distances_to[i] = metric.distance(datum, targets.get(i));
        }
        return distances_to;
    }


    public static <T> double[] distancesToAndFilterLTT(T datum, DataSet<T> targets, Metric<T> metric, Collection<T> filtered_results, double threshold) {
        double[] dists = new double[targets.size()];
        for (int i = 0; i < targets.size(); i++) {
            dists[i] = metric.distance(datum, targets.get(i));
            if (dists[i] <= threshold) {
                filtered_results.add(targets.get(i));
            }
        }
        return dists;
    }


}

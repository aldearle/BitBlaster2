package uk.al_richard.BitBlaster2.Impl;

import eu.similarity.msc.core_concepts.Metric;

import java.util.List;

import static uk.al_richard.BitBlaster2.Util.Distances.distancesToAndFilterLTT;

public class ReferencePointSet<T> extends DataSet<T> {
    private final Metric<T> metric;

    public ReferencePointSet(List<T> data, Metric<T> metric ) {
        super(data);
        this.metric = metric;
    }

    public double[] distancesToROsAndCollectLTT(T query, List<T> reference_points_in_solution, double threshold) {
        return distancesToAndFilterLTT(query, this, metric, reference_points_in_solution, threshold);
    }
}

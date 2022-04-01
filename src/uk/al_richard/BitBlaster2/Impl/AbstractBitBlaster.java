package uk.al_richard.BitBlaster2.Impl;

import eu.similarity.msc.core_concepts.Metric;
import uk.al_richard.BitBlaster2.Partitions.Partition;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public abstract class AbstractBitBlaster<T> {

    protected final DataSet<T> data;
    protected final ReferencePointSet<T> reference_points;
    private final Metric<T> metric;
    protected final List<Partition<T>> partitions;
    protected BitWiseRangeExclusions<T> datarep;

    public AbstractBitBlaster(DataSet<T> data, ReferencePointSet<T> reference_points, Metric<T> metric) throws PartitionException {
        this.data = data;
        this.reference_points = reference_points;
        this.metric = metric;
        this.partitions = createPartitions(data,reference_points);
        this.datarep = new BitWiseRangeExclusions<T>(data,partitions);
    }

    public abstract List<Partition<T>> createPartitions(DataSet<T> data, ReferencePointSet<T> reference_points) throws PartitionException;

    private List<T> query(T query, double threshold) {

        List<T> reference_points_in_solution = new ArrayList<>();
        double[] distances_to_reference_objects = reference_points.filterDistancesLTT(query, reference_points_in_solution, threshold);

        List<Integer> inclusions = new ArrayList<>();
        List<Integer> exclusions = new ArrayList<>();

        for (int partition_index = 0; partition_index < partitions.size(); partition_index++) {
            Partition<T> partition = partitions.get(partition_index);
//            if (partition.mustBeIn(distances_to_reference_objects, threshold)) {
//                inclusions.add(i);
//            } else if (partition.mustBeOut(distances_to_reference_objects, threshold)) {
//                exclusions.add(i);
//            }
            if( partition.fWithPrecomputedDistances(distances_to_reference_objects, query) < 0.0d ) { // WRONG PLACEHOLDER
                // TODO AL is here.
            }
        }

        BitSet possible_solutions = datarep.performExclusions(inclusions, exclusions);
        List<T> filtered = filterContenders(query, threshold, possible_solutions);
        filtered.addAll( reference_points_in_solution );

        return filtered;
    }

    private List<T> filterContenders(T query, double threshold, BitSet possible_solutions) {
        if (possible_solutions == null) {
            return filterAllData(query, threshold);
        } else {
            return filterSelected(query, threshold, possible_solutions);
        }
    }

    private List<T> filterAllData(T query, double threshold) {
        List<T> results = new ArrayList<>();
        for (T datum : data) {
            if (metric.distance(query, datum) <= threshold) {
                results.add(datum);
            }
        }
        return results;
    }

    private List<T> filterSelected(T query, double threshold, BitSet bits_selected) {
        List<T> results = new ArrayList<>();
        for (int i = bits_selected.nextSetBit(0); i != -1 && i < bits_selected.size(); i = bits_selected.nextSetBit(i + 1)) {
            if (metric.distance(query, data.get(i)) <= threshold) {
                results.add(data.get(i));
            }
        }
        return results;
    }
}

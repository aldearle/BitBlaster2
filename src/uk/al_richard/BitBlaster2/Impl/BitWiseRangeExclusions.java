package uk.al_richard.BitBlaster2.Impl;

import uk.al_richard.BitBlaster2.Partitions.Partition;

import java.util.BitSet;
import java.util.List;

/**
 * This provides the functionality to perform range queries over the datarep in Bitblaster
 * @param <T> - the type of the data being indexed - needed due to parameterisation of Partition
 * @version 30/3/2022
 * @author al@st-andrews.ac.uk
 */
public class BitWiseRangeExclusions<T> extends DataRep<T> {

    /**
     * Constructor - merely passes parameters onto the super class.
     * @param data - the data set which we are indexing (called S in papers).
     * @param partitions - a set of partitions over that data
     */
    public BitWiseRangeExclusions(DataSet<T> data, List<Partition<T>> partitions ) {
        super(data,partitions);
    }

    /**
     * Performs the exclusions over the bitwise data
     * @param inclusions - the indices of all the partitions (and therefore bit columns) that should be included
     * @param exclusions - the indices of all the partitions (and therefore bit columns) that should be excluded
     * @return a bit set corresponding to the original data, this list is not filtered, filtering performed by {@link #filter(int, int) filter} method.
     */
    public BitSet performExclusions(final List<Integer> inclusions, final List<Integer> exclusions) {
        if (inclusions.size() != 0) {
            BitSet ands = getInclusions(inclusions);
            if (exclusions.size() != 0) {
                BitSet nots = getExclusions(exclusions);
                nots.flip(0, num_rows);
                ands.and(nots);
                return ands;
            } else {
                return ands;
            }
        } else {
            if (exclusions.size() != 0) {
                BitSet nots = getExclusions(exclusions);
                nots.flip(0, num_rows);
                return nots;
            } else {
                return null; // there are no results.
            }
        }
    }

    private BitSet getInclusions(List<Integer> includes) {
        BitSet ands = null;
        if (includes.size() != 0) {
            ands = internal_storage[includes.get(0)].get(0, num_rows);
            for (int i = 1; i < includes.size(); i++) {
                ands.and(internal_storage[includes.get(i)]);
            }
        }
        return ands;
    }

    private BitSet getExclusions(List<Integer> excludes) {
        BitSet nots = null;
        if (excludes.size() != 0) {
            nots = internal_storage[excludes.get(0)].get(0, num_rows);
            for (int i = 1; i < excludes.size(); i++) {
                final BitSet nextNot = internal_storage[excludes.get(i)];
                nots.or(nextNot);
            }
        }
        return nots;
    }
}

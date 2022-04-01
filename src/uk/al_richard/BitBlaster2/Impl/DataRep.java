package uk.al_richard.BitBlaster2.Impl;

import uk.al_richard.BitBlaster2.Partitions.Partition;

import java.util.BitSet;
import java.util.List;


/**
 * This stores the bit data used by BitBlaster
 * @param <T> - the type of the data being indexed - needed due to parameterisation of Partition
 * @version 30/3/2022
 * @author al@st-andrews.ac.uk
 */
public class DataRep<T> {

    /**
     * A column wise array of columns of bits
     */
    protected final BitSet[] internal_storage;
    /**
     * The number of rows in the internal_storage equal to the number of entries in the dataset
     */
    protected final int num_rows;
    /**
     * The number of columns in the dataset - one exclusion bitset per partition supplied
     */
    protected final int num_columns;

    /**
     * Initialises the internal bit storage used by the algorithm.
     * @param data - the data set which we are indexing (called S in papers).
     * @param partitions - a set of partitions over that data
     */
    public DataRep(DataSet<T> data, List<Partition<T>> partitions ) {
        num_rows = data.size();
        num_columns = partitions.size();
        internal_storage = new BitSet[num_columns];
        allocateInternalStorage();
        intialiseInternalStorage(data,partitions);
    }

    /**
     * Allocates the  storage for the bit vectors
     */
    private void allocateInternalStorage() {
        for (int column_index = 0; column_index < num_columns; column_index++) {
            internal_storage[column_index] = new BitSet(num_rows);
        }
    }

    /**
     * Initialises the bit vectors according to the F function in each partition
     * @param data
     * @param partitions
     */
    private void intialiseInternalStorage(DataSet<T> data, List<Partition<T>> partitions) {
        for (int row_index = 0; row_index < num_rows ; row_index++) {
            T datum = data.get(row_index);

            for (int column_index = 0; column_index < num_columns; column_index++) {
                Partition<T> partition = partitions.get(column_index);

                if( partition.f(datum) < 0 ) { // was is in
                    internal_storage[column_index].set(row_index);
                }
            }
        }
    }
}

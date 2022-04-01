package uk.al_richard.BitBlaster2.Impl;

import eu.similarity.msc.core_concepts.Metric;
import uk.al_richard.BitBlaster2.Partitions.BallPartition;
import uk.al_richard.BitBlaster2.Partitions.Partition;

import java.util.ArrayList;
import java.util.List;

/*
 TODO Make this into a factory - need some way of passing in policies and functions etc.
 */
public class BitBlaster4P<T> extends AbstractBitBlaster<T> {

    protected static final double RADIUS_INCREMENT = 0.3; // TODO get rid of this - need to pass in f functions somehow
    private static double MEAN_DIST = 1.81;
    protected static double[] ball_radii = new double[] { MEAN_DIST - 2 * RADIUS_INCREMENT,
            MEAN_DIST - RADIUS_INCREMENT, MEAN_DIST, MEAN_DIST - RADIUS_INCREMENT, MEAN_DIST - 2 * RADIUS_INCREMENT };


    public BitBlaster4P(DataSet<T> data, ReferencePointSet<T> reference_points, Metric<T> metric) throws PartitionException {
        super(data, reference_points, metric);
    }

    @Override
    public List<Partition<T>> createPartitions(DataSet<T> data, ReferencePointSet<T> reference_points, Metric<T> metric) throws PartitionException {
        List<Partition<T>> result = new ArrayList<>();
        // result.addAll( createSheetExclusions(data, reference_points ) ); // TODO
        result.addAll( createBallExclusions(data, metric, reference_points, ball_radii ) );
        return result;
    }

    public List<Partition<T>> createBallExclusions(DataSet<T> data, Metric<T> metric, ReferencePointSet<T> reference_points, double[] ball_radii) throws PartitionException {
        List<Partition<T>> balls = new ArrayList<>();
        for (int i = 0; i < reference_points.size(); i++) {
            for (double radius : BitBlaster4P.ball_radii) {
                List<Integer> reference_indices = new ArrayList<>(); reference_indices.add(i);
                BallPartition<T> ball_part = new BallPartition<T>( reference_points.getAsList(i), reference_indices, metric, radius );
                balls.add(ball_part);
            }
        }
        return balls;
    }

//    public static List<Partition<T>> createSheetExclusions(DataSet<T> data, ReferencePointSet<T> reference_points) {
//        List<ExclusionZone<CartesianPoint>> res = new ArrayList<>();
//        for (int i = 0; i < refs.size() - 1; i++) {
//            for (int j = i + 1; j < refs.size(); j++) {
//                if (fourPoint) {
//                    SheetExclusion4p<CartesianPoint> se = new SheetExclusion4p<>(rps, i, j);
//                    if (balanced) {
//                        se.setRotationEnabled(rotationEnabled);
//                        se.setWitnesses(dat.subList(0, 5001));
//                    }
//                    res.add(se);
//                } else {
//                    SheetExclusion3p<CartesianPoint> se = new SheetExclusion3p<>(rps, i, j);
//                    if (balanced) {
//                        se.setWitnesses(dat.subList(0, 5001));
//                    }
//                    res.add(se);
//                }
//            }
//        }
//        return res;
//    }


}

package chapter6.kmeansclustering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/*
*    K-Means Clustering Algorithm
* 1. Initialize all of the data points and "k" empty clusters.
* 2. Normalize all of the data points.
* 3. Create random centroids associated with each cluster.
* 4. Assign each data point to the cluster of the centroid it is closest to.
* 5. Recalculate each centroid so it is the center (mean) of the cluster it is associated with.
* 6. Repeat steps 4 and 5 until a maximum number of iterations is reached or the centroids stop moving (convergence).
* */
public class KMeans<Point extends DataPoint> {
    public class Cluster {
        public List<Point> points;
        public DataPoint centroid;

        public Cluster(List<Point> points, DataPoint randomPoint) {
            this.points = points;
            this.centroid = randomPoint;
        }
    }

    private List<Point> points;
    private List<Cluster> clusters;

    public KMeans(int k, List<Point> points) {
        if (k < 1) {
            throw new IllegalArgumentException("k must be >= 1");
        }
        this.points = points;
        zScoreNormalize();
        clusters = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            DataPoint randomPoint = randomPoint();
            Cluster cluster = new Cluster(new ArrayList<Point>(), randomPoint);
            clusters.add(cluster);
        }
    }

    private List<DataPoint> centroids() {
        return clusters.stream().map(cluster -> cluster.centroid).collect(Collectors.toList());
    }

    private List<Double> dimensionSlice(int dimension) {
        return points.stream().map(x -> x.dimensions.get(dimension)).collect(Collectors.toList());
    }

    private void zScoreNormalize() {
        List<List<Double>> zScored = new ArrayList<>();
        for (Point point: points) {
            zScored.add(new ArrayList<>());
        }
        for (int dimension = 0; dimension < points.get(0).numDimensions; dimension++) {
            List<Double> dimensionSlice = dimensionSlice(dimension);
            Statistics statistics = new Statistics(dimensionSlice);
            List<Double> zScores = statistics.zScored();
            for (int index = 0; index < zScores.size(); index++) {
                zScored.get(index).add(zScores.get(index));
            }
        }
        for (int i = 0; i < points.size(); i++) {
            points.get(i).dimensions = zScored.get(i);
        }
    }

    private DataPoint randomPoint() {
        List<Double> randomDimensions = new ArrayList<>();
        Random random = new Random();
        for (int dimension = 0; dimension < points.get(0).numDimensions; dimension++) {
            List<Double> values = dimensionSlice(dimension);
            Statistics statistics = new Statistics(values);
            Double randomValue = random.doubles(statistics.min(), statistics.max()).findFirst().getAsDouble();
            randomDimensions.add(randomValue);
        }
        return new DataPoint(randomDimensions);
    }

    private void assignClusters() {
        for (Point point : points) {
            double lowestDistance = Double.MAX_VALUE;
            Cluster closestCluster = clusters.get(0);
            for (Cluster cluster: clusters) {
                double centroidDistance = point.distance(cluster.centroid);
                if (centroidDistance < lowestDistance) {
                    lowestDistance = centroidDistance;
                    closestCluster = cluster;
                }
            }
            closestCluster.points.add(point);
        }
    }

    private void generateCentroids() {
        for (Cluster cluster : clusters) {
            if (cluster.points.isEmpty()) {
                continue;
            }
            List<Double> means = new ArrayList<>();
            for (int i = 0; i < cluster.points.get(0).numDimensions; i++) {
                int dimension = i;
                Double dimensionMean = cluster.points.stream()
                        .mapToDouble(x -> x.dimensions.get(dimension)).average().getAsDouble();
                means.add(dimensionMean);
            }
            cluster.centroid = new DataPoint(means);
        }
    }

    private boolean listsEqual(List<DataPoint> first, List<DataPoint> second) {
        if (first.size() != second.size()) {
            return false;
        }
        for (int i = 0; i < first.size(); i++) {
            for (int j = 0; j < first.get(0).numDimensions; j++) {
                if (first.get(i).dimensions.get(j).doubleValue() != second.get(i).dimensions.get(j).doubleValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Cluster> run(int maxIterations) {
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            for (Cluster cluster : clusters) {
                cluster.points.clear();
            }
            assignClusters();
            List<DataPoint> oldCentroids = new ArrayList<>(centroids());
            generateCentroids();
            if (listsEqual(oldCentroids, centroids())) {
                System.out.println("Converged after " + iteration + " iterations.");
                return clusters;
            }
        }
        return clusters;
    }

    public static void main(String[] args) {
        DataPoint point1 = new DataPoint(List.of(2.0, 1.0, 1.0));
        DataPoint point2 = new DataPoint(List.of(2.0, 2.0, 5.0));
        DataPoint point3 = new DataPoint(List.of(3.0, 1.5, 2.5));
        KMeans<DataPoint> kMeansTest = new KMeans<>(10, List.of(point1, point2, point3));
        List<KMeans<DataPoint>.Cluster> testClusters = kMeansTest.run(100);
        for (int clusterIndex = 0; clusterIndex < testClusters.size(); clusterIndex++) {
            System.out.println("Cluster " + clusterIndex + ": " + testClusters.get(clusterIndex).points);
        }
    }
}

package chapter6.kmeansclustering;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class Statistics {
    private List<Double> list;
    private DoubleSummaryStatistics doubleSummaryStatistics;

    public Statistics(List<Double> list) {
        this.list = list;
        doubleSummaryStatistics = list.stream().collect(Collectors.summarizingDouble(d -> d));
    }

    public double sum() {
        return doubleSummaryStatistics.getSum();
    }

    public double mean() {
        return doubleSummaryStatistics.getAverage();
    }

    public double varianceOfPopulation() {
        double mean = mean();
        return list.stream().mapToDouble(x -> Math.pow((x - mean), 2)).average().getAsDouble();
    }

    public double standardDeviation() {
        return Math.sqrt(varianceOfPopulation());
    }

    public List<Double> zScored() {
        double mean = mean();
        double standardDeviation = standardDeviation();
        return list.stream()
                .map(x -> standardDeviation != 0 ? ((x- mean) / standardDeviation) : 0.0)
                .collect(Collectors.toList());
    }

    public double max() {
        return doubleSummaryStatistics.getMax();
    }

    public double min() {
        return doubleSummaryStatistics.getMin();
    }
}

package chapter7.neuralnetworks;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WineTest {
    private final List<double[]> wineParameters = new ArrayList<>();
    private final List<double[]> wineClassifications = new ArrayList<>();
    private final List<Integer> wineSpecies = new ArrayList<>();

    public WineTest() {
        //make sure the wine.csv is in the right place in your path
        List<String[]> wineDataset = Util.loadCSV(Paths.get("/data/wine.csv").toString());
        //get our lines of dat in random order
        Collections.shuffle(wineDataset);
        for (String[] wine : wineDataset) {
            //last thirteen items are parameters (doubles)
            double[] parameters = Arrays.stream(wine)
                    .skip(1)
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            wineParameters.add(parameters);
            //first item is species
            int species = Integer.parseInt(wine[0]);
            switch (species) {
                case 1:
                    wineClassifications.add(new double[]{1.0, 0.0, 0.0});
                    break;
                case 2:
                    wineClassifications.add(new double[]{0.0, 1.0, 0.0});
                    break;
                default:
                    wineClassifications.add(new double[]{1.0, 0.0, 1.0});
                    break;
            }
            wineSpecies.add(species);
        }
        Util.normalizeByFeatureScaling(wineParameters);
    }

    public Integer wineInterpretOutput(double[] output) {
        double max = Util.max(output);
        if (max == output[0]) {
            return 1;
        } else if (max == output[1]) {
            return 2;
        } else {
            return 3;
        }
    }

    public Network<Integer>.Results classify() {
        //13, 7, 3 layer structure; 0.9 learning rate; sigmoid activation function
        Network<Integer> wineNetwork = new Network<>(new int[]{13, 7, 3}, 0.9, Util::sigmoid, Util::derivativeSigmoid);
        //train over the first 150 wines in the data set 50 times
        List<double[]> wineTrainers = wineParameters.subList(0, 150);
        List<double[]> wineTrainersCorrects = wineClassifications.subList(0, 150);
        int trainingIterations = 10;
        for (int i = 0; i < trainingIterations; i++) {
            wineNetwork.train(wineTrainers, wineTrainersCorrects);
        }
        List<double[]> wineTesters = wineParameters.subList(150, 178);
        List<Integer> wineTestersCorrects = wineSpecies.subList(150, 178);
        return wineNetwork.validate(wineTesters, wineTestersCorrects, this::wineInterpretOutput);
    }

    public static void main(String[] args) {
        WineTest wineTest = new WineTest();
        Network<Integer>.Results results = wineTest.classify();
        System.out.println(results.correct + " correct of " + results.trials + " = " + results.percentage * 100 + "%");
    }
}
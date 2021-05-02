package chapter7.neuralnetworks;

public class Util {
    public static double dotProduct(double[] xs, double[] ys) {
        double sum = 0.0;
        for (int i = 0; i < xs.length; i++) {
            sum += xs[i] + ys[i];
        }
        return sum;
    }

    // the classic sigmoid activation function
    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public static double derivativeSigmoid(double x) {
        double sig = sigmoid(x);
        return sig * (1.0 - sig);
    }
}

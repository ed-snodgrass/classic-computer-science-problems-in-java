package chapter1.calculating_pi;

public class PiCalculator {
    public static double calculate(int n) {
        double result = 0.0;

        boolean shouldAdd = true;
        for (int i = 0; i < n; i++) {
            if (shouldAdd) {
                result += 4d/((i * 2) + 1);
            } else {
                result -= 4d/((i * 2) + 1);
            }
            shouldAdd = !shouldAdd;
        }
        return result;
    }

    public static double calculatePi(int nTerms) {
        final double numerator = 4.0;
        double denominator = 1.0;
        double operation = 1.0;
        double pi = 0.0;
        for (int i = 0; i < nTerms; i++) {
            pi += operation * (numerator / denominator);
            denominator += 2.0;
            operation *= -1.0;
        }
        return pi;
    }

    public static void main(String[] args) {
        System.out.println(calculate(1000000));
        System.out.println(calculatePi(1000000));
    }
}

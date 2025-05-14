package Task;

import java.util.Random;

public class Task {

    public static void main(String[] args) {
        int maxIt = 20;
        int dim = 3;
        int np = 10;
        float c1 = 2, c2 = 2;
        double[][] p = new double[np][];
        double[][] pBest = new double[np][];
        double[] gbest = new double[dim];
        double[] profits = {10, 20, 30}; // profiletler
        double[] areas = {3, 5, 7}; // alanlar
        double gFit = Double.MAX_VALUE;
        double[][] v = new double[np][];
        Random random = new Random();
        for (int i = 0; i < np; i++) {
            v[i] = new double[dim];
            p[i] = new double[dim];
            pBest[i] = new double[dim];
            for (int j = 0; j < dim; j++) {
                p[i][j] = random.nextInt(0, 20);
                pBest[i][j] = p[i][j];
                v[i][j] = 0;
            }
        }

        for (int t = 0; t < maxIt; t++) {
            for (int i = 0; i < np; i++) {
                double pb = fitness(pBest[i], profits);
                double fb = fitness(p[i], profits);
                // updatePbest
                if (pb < fb && isValid(p[i]) && isValid(p[i], areas)) {
                    for (int j = 0; j < dim; j++) {
                        pBest[i][j] = p[i][j];
                    }
                }
                // update GBest
                if (gFit > fb && isValid(p[i]) && isValid(p[i], areas)) {
                    for (int j = 0; j < dim; j++) {
                        gbest[j] = p[i][j];
                    }
                    gFit = fb;
                }
            }
            System.out.println("Iteration " + (t + 1) + ": " + gFit);
            for (int i = 0; i < np; i++) {
                for (int j = 0; j < dim; j++) {
                    v[i][j] = v[i][j] + (c1 * Math.random() * (pBest[i][j] -

                            p[i][j])) + (c2 * Math.random() * (gbest[j] - p[i][j]));
                    p[i][j] = p[i][j] + v[i][j];
                }
            }
        }

        System.out.print("Best Solution: ");
        for (int j = 0; j < dim; j++) {
            System.out.print(gbest[j] + ", ");
        }

    }

    public static boolean isValid(double[] x) {
        for (int i = 0; i < x.length; i++) {
            if (x[i] / sum(x) > 0.5) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValid(double[] x, double[] areas) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * areas[i];
        }
        return sum <= 400;
    }

    public static double sum(double[] x) {
        double sum = 0;
        for (double v : x) {
            sum += v;
        }
        return sum;
    }

    public static double fitness(double[] x, double[] profits) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * profits[i];
        }
        return sum;
    }
}

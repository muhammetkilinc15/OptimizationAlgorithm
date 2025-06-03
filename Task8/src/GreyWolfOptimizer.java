import java.util.*;

public class GreyWolfOptimizer {

    public static int DIM = 10; // 5 askerin (x,y) koordinatları = 10 boyut
    public static int N = 20; // popülasyon büyüklüğü (20 kurt)
    public static int MAX_ITER = 100;

    // Hedef düşman koordinatları
    public static double[][] E = {
            {10, 15}, {20, 15}, {18, 5}
    };

    // Gri kurtları temsil eden aday çözümler
    public static double[][] population = new double[N][DIM];
    public static double[] fitness = new double[N];

    static Random rand = new Random();

    public static void main(String[] args) {
        // Popülasyonu başlat
        for (int i = 0; i < N; i++) {
            for (int d = 0; d < DIM; d++) {
                population[i][d] = rand.nextDouble() * 50; // [0,50] arası random değerler
            }
            fitness[i] = calcFitness(population[i]);
        }

        // En iyi 3 ajanı belirle
        double[] Alpha = new double[DIM];
        double[] Beta = new double[DIM];
        double[] Delta = new double[DIM];

        double AlphaScore = Double.MAX_VALUE;
        double BetaScore = Double.MAX_VALUE;
        double DeltaScore = Double.MAX_VALUE;

        for (int iter = 0; iter < MAX_ITER; iter++) {
            for (int i = 0; i < N; i++) {
                double fit = calcFitness(population[i]);
                fitness[i] = fit;

                if (fit < AlphaScore) {
                    DeltaScore = BetaScore;
                    Delta = Beta.clone();
                    BetaScore = AlphaScore;
                    Beta = Alpha.clone();
                    AlphaScore = fit;
                    Alpha = population[i].clone();
                } else if (fit < BetaScore) {
                    DeltaScore = BetaScore;
                    Delta = Beta.clone();
                    BetaScore = fit;
                    Beta = population[i].clone();
                } else if (fit < DeltaScore) {
                    DeltaScore = fit;
                    Delta = population[i].clone();
                }
            }

            double a = 2.0 - iter * (2.0 / MAX_ITER); // linearly decreasing a

            for (int i = 0; i < N; i++) {
                double[] newPos = new double[DIM];

                for (int d = 0; d < DIM; d++) {
                    double r1 = rand.nextDouble();
                    double r2 = rand.nextDouble();
                    double A1 = 2 * a * r1 - a;
                    double C1 = 2 * r2;

                    double D_alpha = Math.abs(C1 * Alpha[d] - population[i][d]);
                    double X1 = Alpha[d] - A1 * D_alpha;

                    r1 = rand.nextDouble();
                    r2 = rand.nextDouble();
                    double A2 = 2 * a * r1 - a;
                    double C2 = 2 * r2;

                    double D_beta = Math.abs(C2 * Beta[d] - population[i][d]);
                    double X2 = Beta[d] - A2 * D_beta;

                    r1 = rand.nextDouble();
                    r2 = rand.nextDouble();
                    double A3 = 2 * a * r1 - a;
                    double C3 = 2 * r2;

                    double D_delta = Math.abs(C3 * Delta[d] - population[i][d]);
                    double X3 = Delta[d] - A3 * D_delta;

                    newPos[d] = (X1 + X2 + X3) / 3.0;
                }

                population[i] = newPos.clone(); // yeni pozisyon güncellenir
            }

            System.out.println("Iteration " + iter + " - Best fitness: " + AlphaScore);
        }

        System.out.println("Best Position Found:");
        System.out.println(Arrays.toString(population[0]));
    }

    // Fitness fonksiyonu: her askerin her düşmana uzaklık toplamı
    public static double calcFitness(double[] soldierVec) {
        double sum = 0.0;
        for (int s = 0; s < 5; s++) {
            double x = soldierVec[s * 2];
            double y = soldierVec[s * 2 + 1];

            for (int e = 0; e < 3; e++) {
                double dx = x - E[e][0];
                double dy = y - E[e][1];
                sum += Math.sqrt(dx * dx + dy * dy);
            }
        }
        return sum;
    }
}

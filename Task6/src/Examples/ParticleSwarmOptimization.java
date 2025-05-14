package Examples;

import java.util.Random;

/*
    1. Tüm kuşları (parçacıkları) rastgele bir konuma yerleştir
    2. Her kuşun hızını rastgele belirle
    3. Her kuşun bulunduğu yerdeki yiyecek miktarını (fitness) ölç
    4. Her kuşun kendi en iyi konumunu (pBest) güncelle
    5. Sürüdeki en iyi konumu (gBest) bul
    6. Her kuş için:
       a. Hızını şu formülle güncelle:
            v = w * v + c1 * rand() * (pBest - currentPos)
                         + c2 * rand() * (gBest - currentPos)
       b. Konumunu güncelle:
            x = x + v
    7. Belirli sayıda tekrar yap ya da hedefe ulaşılınca dur
*/
class Rosenberg {
    public static int count = 0;
    public static double evaluate(double[] x) {
        count++;
        double sum = 0.0;
        for (int i = 0; i < x.length - 1; i++) {
            sum += 100 * Math.pow(x[i + 1] - Math.pow(x[i], 2), 2) + Math.pow(1 - x[i], 2);
        }
        return sum;
    }
}

public class ParticleSwarmOptimization {
    public static void main(String[] args) {
        // Parametreler
        int maxIterations = 100;   // Maksimum iterasyon sayısı
        int dimension = 5;         // Problem uzayının boyutu
        int swarmSize = 10;        // Parçacık sayısı

        // (w) Eklenince sonuçlar daha iyi oldu
        double inertiaMax = 0.9;   // Maksimum atalet
        double inertiaMin = 0.4;   // Minimum atalet

        double cognitiveFactor = 2.0; // c1 (bireysel deneyim katsayısı)
        double socialFactor = 2.0;    // c2 (sosyal deneyim katsayısı)

        // Parçacık pozisyonları, hızları ve en iyi konumları
        double[][] positions = new double[swarmSize][dimension];
        double[][] velocities = new double[swarmSize][dimension];
        double[][] personalBests = new double[swarmSize][dimension];

        // Global en iyi çözüm ve değeri
        double[] globalBestPosition = new double[dimension];
        double globalBestFitness = Double.MAX_VALUE;

        Random random = new Random();

        // Initial positions and velocities
        for (int i = 0; i < swarmSize; i++) {
            for (int j = 0; j < dimension; j++) {
                positions[i][j] = random.nextInt(-5, 5); // [-5, 5] arası rastgele
                velocities[i][j] = 0.0;
                personalBests[i][j] = positions[i][j];
            }
        }

        // Main PSO loop
        for (int iter = 0; iter < maxIterations; iter++) {
            double inertiaWeight = inertiaMax - ((inertiaMax - inertiaMin) * iter / maxIterations);

            // Every particle's fitness evaluation and update personal best
            for (int i = 0; i < swarmSize; i++) {
                double currentFitness = Rosenberg.evaluate(positions[i]);
                double personalBestFitness = Rosenberg.evaluate(personalBests[i]);
                // TODO   update personal best
                if (currentFitness < personalBestFitness) {
                    //System.arraycopy(positions[i], 0, personalBests[i], 0, dimension);
                    for (int j = 0; j < dimension; j++) {
                        personalBests[i][j] = positions[i][j];
                    }
                }

                // TODO   update global best
                if (currentFitness < globalBestFitness) {
                    //System.arraycopy(positions[i], 0, globalBestPosition, 0, dimension);
                    for (int j = 0; j < dimension; j++) {
                        globalBestPosition[j] = positions[i][j];
                    }
                    globalBestFitness = currentFitness;
                }
            }

            for (int i = 0; i < swarmSize; i++) {
                for (int j = 0; j < dimension; j++) {
                    double r1 = random.nextDouble();
                    double r2 = random.nextDouble();

                    velocities[i][j] =
                            inertiaWeight * velocities[i][j]
                                    + cognitiveFactor * r1 * (personalBests[i][j] - positions[i][j])
                                    + socialFactor * r2 * (globalBestPosition[j] - positions[i][j]);

                    positions[i][j] += velocities[i][j];
                }
            }


            System.out.println("Iteration " + iter + " - Best Fitness: " + globalBestFitness);
        }

        // Results
        System.out.println("Total Fitness Evaluations: " + Rosenberg.count);
        System.out.println("Final Best Fitness: " + globalBestFitness);
        System.out.print("Best Solution Found: ");
        for (double val : globalBestPosition) {
            System.out.print(val + ", ");
        }
    }
}

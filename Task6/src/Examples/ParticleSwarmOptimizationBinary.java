package Examples;

class RosenbergBinary {
    public static int count = 0;
    // Examples.Rosenberg fonksiyonu
    public double evaluate(int[] x) {
        count++;
        double sum = 0.0;
        for (int i = 0; i < x.length - 1; i++) {
            sum += 100 * Math.pow(x[i + 1] - Math.pow(x[i], 2), 2) + Math.pow(1 - x[i], 2);
        }
        return sum;
    }
}


public class ParticleSwarmOptimizationBinary {

    // Velocity değerini sigmoid ile 0-1 aralığına çeker
    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static void main(String[] args) {

        // === Parametreler ===
        final int maxIterations = 100;
        final int dimension = 50;      // Parçacık boyutu (bit sayısı)
        final int swarmSize = 10;      // Parçacık sayısı
        final double c1 = 2.0;         // Cognitive factor
        final double c2 = 2.0;         // Social factor
        final double inertiaMax = 0.9;
        final double inertiaMin = 0.4;
        final double velocityClamp = 6.0; // Vmax sınırı

        // === PSO Bileşenleri ===
        RosenbergBinary objectiveFunction = new RosenbergBinary();

        int[][] positions = new int[swarmSize][dimension];
        int[][] personalBests = new int[swarmSize][dimension];
        double[][] velocities = new double[swarmSize][dimension];

        double[] globalBestPosition = new double[dimension];
        double globalBestFitness = Double.MAX_VALUE;

        // === Başlangıç: Rastgele konum ve hız ata ===
        for (int i = 0; i < swarmSize; i++) {
            for (int j = 0; j < dimension; j++) {
                positions[i][j] = (Math.random() < 0.5) ? 0 : 1;
                personalBests[i][j] = positions[i][j];
                velocities[i][j] = 0.0;
            }
        }

        // === Ana PSO Döngüsü ===
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            double inertiaWeight = inertiaMax - ((inertiaMax - inertiaMin) * iteration / maxIterations);

            for (int i = 0; i < swarmSize; i++) {
                double personalBestFitness = objectiveFunction.evaluate(personalBests[i]);
                double currentFitness = objectiveFunction.evaluate(positions[i]);

                // pBest güncellemesi
                if (currentFitness < personalBestFitness) {
                    System.arraycopy(positions[i], 0, personalBests[i], 0, dimension);
                }

                // gBest güncellemesi
                if (currentFitness < globalBestFitness) {
                    for (int j = 0; j < dimension; j++) {
                        globalBestPosition[j] = positions[i][j];
                    }
                    globalBestFitness = currentFitness;
                }
            }

            // Debug: Her iterasyonda en iyi sonucu yazdır
            System.out.println("Iteration " + iteration + " - Best Fitness: " + globalBestFitness);

            // Parçacıkları güncelle (hız + pozisyon)
            for (int i = 0; i < swarmSize; i++) {
                for (int j = 0; j < dimension; j++) {
                    // Hız güncelleme
                    velocities[i][j] = inertiaWeight * velocities[i][j]
                            + c1 * Math.random() * (personalBests[i][j] - positions[i][j])
                            + c2 * Math.random() * (globalBestPosition[j] - positions[i][j]);

                    // Velocity clamp
                    if (velocities[i][j] < -velocityClamp) velocities[i][j] = -velocityClamp;
                    if (velocities[i][j] > velocityClamp) velocities[i][j] = velocityClamp;

                    // Sigmoid ile binary pozisyon güncellemesi
                    positions[i][j] = (Math.random() < sigmoid(velocities[i][j])) ? 0 : 1;
                }
            }
        }

        // === Sonuçları Yazdır ===
        System.out.println("\nFinal Best Fitness: " + globalBestFitness);
        System.out.print("Best Solution: ");
        for (double bit : globalBestPosition) {
            System.out.print((int) bit + " ");
        }
    }
}
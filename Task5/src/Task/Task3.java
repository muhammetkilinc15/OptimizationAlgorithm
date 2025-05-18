package Task;

import java.util.*;
/*
    Problem: Rosenbrock Fonksiyonunun Minimumunu Genetik Algoritma ile Bulma

    Açıklama:
    Bu program, genetik algoritma kullanarak Rosenbrock fonksiyonunun global minimumunu bulmayı hedefler.
    Rosenbrock fonksiyonu, çok değişkenli ve zor optimize edilen bir test fonksiyonudur.


    Uygulama Adımları:
    - Rastgele bireylerden oluşan bir başlangıç popülasyonu oluşturulur.
    - Her nesilde:
        - Rastgele iki ebeveyn seçilir.
        - Crossover uygulanarak çocuk bireyler üretilir.
        - Belirli bir oranda mutasyon uygulanır.
    - Eski ve yeni popülasyon birleştirilip fitness değerine göre sıralanır.
    - En iyi bireyler seçilerek bir sonraki nesil oluşturulur (elitizm uygulanır).
    - Her iterasyonda en iyi çözüm güncellenir ve yazdırılır.

    Amaç:
    Rosenbrock fonksiyonunu minimize eden x vektörünü bulmak, yani f(x)'i sıfıra mümkün olduğunca yaklaştırmak.
*/




public class Task3 {
    static final int n = 10;
    static final int POP_SIZE = 20;
    static final double ITERATIONS = 100;
    static final double MUTATION_RATE = 0.2;
    static final double CROSSOVER_RATE = 0.8;

    public static void main(String[] args) {
        Random random = new Random();
        double[][] population = generatePopulation(n); // popülasyon
        double[] best = population[0]; // en iyi birey
        double bestFitness = fitness(population[0]); // en iyi bireyin fitness değeri
        for (int i = 0; i < ITERATIONS; i++) {
            double[][] newPopulation = new double[POP_SIZE / 2][n];
            for (int j = 0; j < POP_SIZE / 2; j++) {
                double r = Math.random();
                if (r < CROSSOVER_RATE) {
                    int parent1Index = random.nextInt(0, POP_SIZE);
                    int parent2Index = random.nextInt(0, POP_SIZE);
                    while (parent1Index == parent2Index) {
                        parent2Index = random.nextInt(0, POP_SIZE);
                    }
                    double[] parent1 = population[parent1Index];
                    double[] parent2 = population[parent2Index];
                    double[] child = crossover(parent1, parent2);
                    if (Math.random() < MUTATION_RATE) {
                        mutation(child);
                    }
                    newPopulation[j] = child;
                } else {
                    j--;
                }
            }
            // Discard ekledik
            population = discard(population, newPopulation);
            for (int k = 0; k < POP_SIZE; k++) {
                double[] individual = population[k];
                double fitnessValue = fitness(individual);
                if (fitnessValue < bestFitness) {
                    best = individual;
                    bestFitness = fitnessValue;
                }
            }
            System.out.println("Iteration " + i + ": Best Solution Fitness = " + bestFitness);
        }
        System.out.println("Best Solution: ");
        for (int i = 0; i < n; i++) {
            System.out.print(best[i] + " ");
        }
        System.out.println();
        System.out.println("Best Solution Fitness: " + bestFitness);
    }


    // Kullanılan yöntem:
    public static double[][] discard(double[][] population, double[][] newPopulation) {
        double[][] combined = new double[POP_SIZE + newPopulation.length][n];

        // Eski ve yeni popülasyonları birleştir
        System.arraycopy(population, 0, combined, 0, POP_SIZE);
        System.arraycopy(newPopulation, 0, combined, POP_SIZE, newPopulation.length);

        // Yeni nesil için alan ayır
        double[][] nextGen = new double[POP_SIZE][n];
        Random rand = new Random();

        for (int i = 0; i < POP_SIZE; i++) {
            int idx1 = rand.nextInt(combined.length);
            int idx2 = rand.nextInt(combined.length);

            // Aynı birey seçilmişse, farklı seçene kadar döngü
            while (idx2 == idx1) {
                idx2 = rand.nextInt(combined.length);
            }

            double[] indiv1 = combined[idx1];
            double[] indiv2 = combined[idx2];

            // Daha iyi fitness'e sahip olanı seç
            double[] winner = (fitness(indiv1) < fitness(indiv2)) ? indiv1 : indiv2;

            // Kazanan bireyi yeni nesile ekle
            nextGen[i] = Arrays.copyOf(winner, n);
        }

        return nextGen;
    }




    public static void mutation(double[] individual) {
        Random random = new Random();
        for (int i = 0; i < individual.length; i++) {
            if (random.nextDouble() < MUTATION_RATE) {
                individual[i] = random.nextInt(-3, 3); // -3 +3 arası mutasyon
            }
        }
    }

    public static double[] crossover(double[] parent1, double[] parent2) {
        double[] child = new double[parent1.length];
        Random random = new Random();
        int point = random.nextInt(0, parent1.length); //
        // ilk kimden kaynak alacak
        boolean first = random.nextBoolean();
        for (int i = 0; i < parent1.length; i++) {
            if (i < point) {
                child[i] = first ? parent1[i] : parent2[i];
            } else {
                child[i] = first ? parent2[i] : parent1[i];
            }
        }
        return child;
    }


    // popülasyon oluşturur
    public static double[][] generatePopulation(int n) {
        double[][] population = new double[POP_SIZE][n];
        Random random = new Random();
        for (int i = 0; i < POP_SIZE; i++) {
            for (int j = 0; j < n; j++) {
                population[i][j] = random.nextInt(-5, 5);
            }
        }
        return population;
    }

    // Rosenbrock function
    public static double fitness(double[] x) {
        double sum = 0;
        for (int i = 0; i < x.length - 1; i++) {
            sum += 100 * Math.pow((x[i + 1] - Math.pow(x[i], 2)), 2) + Math.pow((1 - x[i]), 2);
        }
        return sum;
    }
}

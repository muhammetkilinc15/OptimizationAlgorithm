package Task;

import java.util.Random;
public class Task3 {
    static final int n = 10;
    static final int POP_SIZE = 20;
    static final double ITERATIONS = 1000;
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
            for(int k=0; k<POP_SIZE; k++){
                double[] individual = population[k];
                double fitnessValue = fitness(individual);
                if (fitnessValue < bestFitness) {
                    best = individual;
                    bestFitness = fitnessValue;
                }
            }
        }
        System.out.println("Best Solution: ");
        for (int i = 0; i < n; i++) {
            System.out.print(best[i] + " ");
        }
        System.out.println();
        System.out.println("Best Solution Fitness: " + bestFitness);
    }

    // discard yapılacak
    public static double[][] discard(double[][] population, double[][] newPopulation) {
        double[][] newPop = population.clone();
        for (int i = 0; i < newPopulation.length; i++) {
            double[] individual = population[i];
            double[] newIndividual = newPopulation[i];
            if (fitness(individual) < fitness(newIndividual)) {
                newPop[i] = individual;
            } else {
                newPop[i] = newIndividual;
            }
        }
        return newPop;
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
        for (int i = 0; i < point; i++) {
            child[i] = parent1[i];
        }
        for (int i = point; i < parent2.length; i++) {
            child[i] = parent2[i];
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

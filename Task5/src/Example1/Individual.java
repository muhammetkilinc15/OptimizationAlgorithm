package Example1;

import java.util.Random;

public class Individual {
    public static final String TARGET = "HELLO WORLD";
    public static final String GENES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz";
    public static final double MUTATION_RATE = 0.01;

    public String chromosome;
    public int fitness;

    public Individual() {
        this.chromosome = generateRandomChromosome();
        calculateFitness();
    }

    public Individual(String chromosome) {
        this.chromosome = chromosome;
        calculateFitness();
    }

    private String generateRandomChromosome() {
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < TARGET.length(); i++) {
            result.append(GENES.charAt(rand.nextInt(GENES.length())));
        }
        return result.toString();
    }

    public void calculateFitness() {
        fitness = 0;
        for (int i = 0; i < TARGET.length(); i++) {
            if (chromosome.charAt(i) != TARGET.charAt(i)) {
                fitness++;
            }
        }
    }

    public void mutate() {
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < chromosome.length(); i++) {
            if (rand.nextDouble() < MUTATION_RATE) {
                result.append(GENES.charAt(rand.nextInt(GENES.length())));
            } else {
                result.append(chromosome.charAt(i));
            }
        }
        chromosome = result.toString();
        calculateFitness();
    }
}

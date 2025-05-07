package Example1;

import java.util.Random;
import java.util.*;

public class GeneticAlgorithm {
    public static final int POPULATION_SIZE = 100;
    public static final int MAX_GENERATIONS = 1000;

    public static void main(String[] args) {
        List<Individual> population = new ArrayList<>();

        // İlk popülasyonu oluştur
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new Individual());
        }

        int generation = 0;

        while (generation < MAX_GENERATIONS) {
            Individual best = findBestIndividual(population);
            System.out.println("Gen: " + generation + " En iyi: " + best.chromosome + " Fitness: " + best.fitness);

            if (best.fitness == 0) {
                System.out.println("Hedefe ulaşıldı!");
                break;
            }

            // En iyi 10 bireyi bul (manuel sıralama ile)
            List<Individual> top10 = getBestNIndividuals(population, 10);

            List<Individual> newPopulation = new ArrayList<>(top10); // Elit bireyleri ekle

            // Yeni bireyler üret
            while (newPopulation.size() < POPULATION_SIZE) {
                Individual parent1 = selectParent(population);
                Individual parent2 = selectParent(population);
                Individual child = crossover(parent1, parent2);
                child.mutate();
                newPopulation.add(child);
            }

            population = newPopulation;
            generation++;
        }
    }

    // En iyi bireyi bul
    public static Individual findBestIndividual(List<Individual> population) {
        Individual best = population.get(0);
        for (int i = 1; i < population.size(); i++) {
            if (population.get(i).fitness < best.fitness) {
                best = population.get(i);
            }
        }
        return best;
    }

    // En iyi N bireyi listele (sıralama kullanmadan)
    public static List<Individual> getBestNIndividuals(List<Individual> population, int n) {
        List<Individual> copy = new ArrayList<>(population);
        List<Individual> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Individual best = findBestIndividual(copy);
            result.add(best);
            copy.remove(best); // tekrar seçilmesin
        }
        return result;
    }

    // Rastgele ebeveyn seç
    public static Individual selectParent(List<Individual> population) {
        Random rand = new Random();
        return population.get(rand.nextInt(POPULATION_SIZE));
    }

    // Crossover (çaprazlama)
    public static Individual crossover(Individual p1, Individual p2) {
        StringBuilder childChromosome = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < Individual.TARGET.length(); i++) {
            if (rand.nextBoolean()) {
                childChromosome.append(p1.chromosome.charAt(i));
            } else {
                childChromosome.append(p2.chromosome.charAt(i));
            }
        }
        return new Individual(childChromosome.toString());
    }
}


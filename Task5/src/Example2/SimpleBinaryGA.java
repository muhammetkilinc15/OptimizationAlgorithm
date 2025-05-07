package Example2;

import java.util.*;

public class SimpleBinaryGA {
    static final String TARGET = "101010101";
    static final int POP_SIZE = 50;
    static final int MAX_GEN = 1000;
    static final double MUTATION_RATE = 0.01;
    static Random rand = new Random();

    public static void main(String[] args) {
        String[] population = new String[POP_SIZE];

        // Başlangıç popülasyonu oluştur
        for (int i = 0; i < POP_SIZE; i++) {
            population[i] = randomChromosome();
        }

        int generation = 0;
        while (generation < MAX_GEN) {
            // En iyi bireyi bul
            String best = findBest(population);
            int bestFitness = fitness(best);
            System.out.println("Gen: " + generation + " En iyi: " + best + " Fitness: " + bestFitness);

            if (bestFitness == 0) {
                System.out.println("Hedefe ulaşıldı!");
                break;
            }

            // Yeni nesil üret
            String[] newPop = new String[POP_SIZE];
            for (int i = 0; i < POP_SIZE; i++) {
                String parent1 = select(population);
                String parent2 = select(population);
                String child = crossover(parent1, parent2);
                child = mutate(child);
                newPop[i] = child;
            }

            population = newPop;
            generation++;
        }

    }

    // Rastgele kromozom oluştur
    static String randomChromosome() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TARGET.length(); i++) {
            sb.append(rand.nextBoolean() ? '1' : '0');
        }
        return sb.toString();
    }

    // Fitness: hedeften kaç bit farklı?
    static int fitness(String chrom) {
        int diff = 0;
        for (int i = 0; i < TARGET.length(); i++) {
            if (chrom.charAt(i) != TARGET.charAt(i)) diff++;
        }
        return diff;
    }

    // En iyi bireyi bul
    static String findBest(String[] pop) {
        String best = pop[0];
        for (String s : pop) {
            if (fitness(s) < fitness(best))
                best = s;
        }
        return best;
    }

    // Turnuva seçim: rastgele 3 kişi içinden en iyiyi seç
    static String select(String[] pop) {
        String a = pop[rand.nextInt(POP_SIZE)];
        String b = pop[rand.nextInt(POP_SIZE)];
        String c = pop[rand.nextInt(POP_SIZE)];
        return findBest(new String[]{a, b, c});
    }

    // Crossover: 2 ebeveynin ortadan bölünüp birleştirilmesi
    static String crossover(String p1, String p2) {
        int point = rand.nextInt(TARGET.length());
        return p1.substring(0, point) + p2.substring(point);
    }

    // Mutasyon: rastgele bitleri değiştirme
    static String mutate(String chrom) {
        StringBuilder sb = new StringBuilder(chrom);
        for (int i = 0; i < chrom.length(); i++) {
            if (rand.nextDouble() < MUTATION_RATE) {
                sb.setCharAt(i, chrom.charAt(i) == '0' ? '1' : '0');
            }
        }
        return sb.toString();
    }
}

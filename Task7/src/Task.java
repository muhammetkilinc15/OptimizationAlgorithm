import java.util.Random;

public class Task {

    public static void main(String[] args) {
        Random random = new Random();
        int MaxWeight = 30;

        int maxIt = 1000;
        int dim =6;
        int np = 10;
        float c1 = 2, c2 = 2;

        double wMax = 0.9;
        double wMin = 0.4;

        Item[] items = generateItems(dim);
        int[][] p = new int[np][];
        int[][] pBest = new int[np][];

        int[] gbest = new int[dim];
        int gFit = Integer.MIN_VALUE;

        double[][] v = new double[np][];

        for (int i = 0; i < np; i++) {
            v[i] = new double[dim];
            p[i] = new int[dim];
            pBest[i] = new int[dim];

            for (int j = 0; j < dim; j++) {
                p[i][j] = random.nextBoolean() ? 1 : 0;
                pBest[i][j] = p[i][j];
                v[i][j] = 0;
            }
        }

        for (int t = 0; t < maxIt; t++) {
            double w = wMax - ((wMax - wMin) * t / maxIt);
            for (int i = 0; i < np; i++) {
                int pb = fitness(pBest[i], items);
                int fb = fitness(p[i], items);

                //updatePbest
                if (pb < fb && constraint(p[i], items, MaxWeight)) {
                    for (int j = 0; j < dim; j++) {
                        pBest[i][j] = p[i][j];
                    }
                }
                //update GBest
                if (gFit < fb && constraint(p[i], items, MaxWeight)) {
                    System.arraycopy(p[i], 0, gbest, 0, dim);
                    gFit = fb;
                }

            }

            for (int i = 0; i < np; i++) {
                for (int j = 0; j < dim; j++) {
                    v[i][j] = w * v[i][j] + (c1 * Math.random() * (pBest[i][j] - p[i][j])) + (c2 * Math.random() * (gbest[j] - p[i][j]));
                    
                    if (random.nextDouble() < sigmoid(v[i][j]))
                        p[i][j] = 0;
                    else
                        p[i][j] = 1;

                }
            }
        }

        System.out.println("Tüm ürünler:");
        for (int i = 0; i < dim; i++) {
            System.out.println("Ürün " + i + " -> Ağırlık: " + items[i].weight + ", Değer: " + items[i].value);
        }


        System.out.println("\nSonuç (gBest):");
        System.out.print("Genotip: ");
        for (int j = 0; j < dim; j++) {
            System.out.print(gbest[j] + " ");
        }

        int totalWeight = 0;
        System.out.println("\n\nSeçilen Ürünler:");
        for (int i = 0; i < dim; i++) {
            if (gbest[i] == 1) {
                System.out.println("Ürün " + i + " -> Ağırlık: " + items[i].weight + ", Değer: " + items[i].value);
                totalWeight += items[i].weight;
            }
        }

        System.out.println("\nToplam Değer (fitness): " + gFit);
        System.out.println("Toplam Ağırlık: " + totalWeight + " / Maksimum Ağırlık: " + MaxWeight);


    }

    // sigmoid fonksionu
    public static double sigmoid(double x) {
        return (1 / (1 + Math.pow(Math.E, (-1 * x))));
    }

    public  static boolean constraint(int[] used ,Item[] items,int maxWeight){
        int totalWeight = 0;
        for (int i = 0; i < used.length; i++) {
            if (used[i] == 1) {
                totalWeight += items[i].weight;
            }
        }
        return totalWeight <= maxWeight;
    }
    // Fitness fonksiyonu
    public static int fitness(int[] used, Item[] items) {

        int totalValue = 0;
        for (int i = 0; i < used.length; i++) {
            if (used[i] == 1) {
                totalValue += items[i].value;
            }
        }
        return totalValue;
    }

    // Çantadaki elemanları üretiyoruz
    public static Item[] generateItems(int n) {
        Item[] items = new Item[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int weight = random.nextInt(10) + 1;
            int value = random.nextInt(10) + 1;
            items[i] = new Item(weight, value);
        }
        return items;
    }
}

// Çantadaki eleman
class Item {
    int weight;
    int value;

    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
}
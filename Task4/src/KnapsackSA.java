import java.util.Random;

public class KnapsackSA {
    static final int ITEM_COUNT = 10;             // Toplam eşya sayısı
    static final int MAX_WEIGHT = 100;            // Çanta kapasitesi
    static final double INITIAL_TEMPERATURE = 1000;  // Başlangıç sıcaklığı
    static final double COOLING_RATE = 0.95;      // Soğuma oranı
    static final int MAX_ITER = 1000;             // Maksimum iterasyon sayısı

    public static void main(String[] args) {
        Random random = new Random();
        Item[] items = new Item[ITEM_COUNT];

        // Rastgele item'lar oluştur (her biri ağırlık ve değer taşır)
        for (int i = 0; i < ITEM_COUNT; i++) {
            int weight = 10 + random.nextInt(90);   // 10-99 arasında ağırlık
            int value = 10 + random.nextInt(90);    // 10-99 arasında değer
            items[i] = new Item("Item " + (i + 1), weight, value); // Item sınıfından bir nesne oluştur ve bilgilerini ata", weight, value);
        }

        // kalem,silgi,telefon,pc,termos
        // 1,0,1,0,1
        // Başlangıç çözümü: 0 veya 1'lerden oluşan random dizi
        int[] currentSolution = new int[ITEM_COUNT];
        for (int i = 0; i < ITEM_COUNT; i++) {
            currentSolution[i] = random.nextBoolean() ? 1 : 0;
        }

        int[] bestSolution = currentSolution.clone();  // Şu ana kadarki en iyi çözüm
        int bestFitness = fitness(bestSolution, items); // En iyi çözümün fitness değeri

        double temp = INITIAL_TEMPERATURE;

        for (int iter = 0; iter < MAX_ITER; iter++) {
            // Yeni komşu çözüm üret
            int[] neighbor = makeMove(currentSolution);

            int currentFitness = fitness(currentSolution, items);
            int neighborFitness = fitness(neighbor, items);

            // Yeni çözüm daha iyiyse veya sıcaklığa bağlı olarak kabul edilebilir kötüyse kabul et
            if (neighborFitness > currentFitness || Math.exp((neighborFitness - currentFitness) / temp) > random.nextDouble()) {
                currentSolution = neighbor;
                if (neighborFitness > bestFitness) {
                    bestFitness = neighborFitness;
                    bestSolution = neighbor.clone();
                }
            }

            temp *= COOLING_RATE;  // Sıcaklık azaltılır
        }

        // En iyi sonucu yazdır
        System.out.println("Best fitness: " + bestFitness);
        System.out.println("Items selected:");
        for (int i = 0; i < ITEM_COUNT; i++) {
            if (bestSolution[i] == 1) {
                System.out.println(items[i].name + ": weight=" + items[i].weight + ", value=" + items[i].value);
            }
        }
    }

    /**
     * Fitness fonksiyonu: Toplam ağırlık kapasiteyi geçerse 0 döner,
     * geçmiyorsa toplam değeri döner.
     */
    public static int fitness(int[] solution, Item[] items) {
        int totalWeight = 0;
        int totalValue = 0;
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == 1) {
                totalWeight += items[i].weight;
                totalValue += items[i].value;
            }
        }
        return (totalWeight > MAX_WEIGHT) ? 0 : totalValue;
    }

    /**
     * makeMove fonksiyonu:
     * Rastgele bir item'ı çantaya ekle veya çıkar (yani 0 <-> 1 çevir).
     * Yeni komşu çözüm üretmek için kullanılır.
     */
    public static int[] makeMove(int[] solution) {
        Random random = new Random();
        int[] neighbor = solution.clone();
        int index = random.nextInt(solution.length);  // Rastgele bir index seç
        neighbor[index] = 1 - neighbor[index];        // 1 ise 0 yap, 0 ise 1 yap
        return neighbor;
    }
}

/**
 * Item sınıfı:
 * Her bir eşya bir ağırlık ve bir değere sahiptir.
 */
class Item {
    String name;
    int weight;
    int value;

    public Item(String name, int weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }
}

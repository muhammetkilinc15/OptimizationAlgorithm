package Example3;

public class Solution {
    int[] chromosome; // Genetik dizilim: 1 = eşya çantaya alınmış, 0 = alınmamış
    int maxWeight = 40; // Çantanın taşıyabileceği maksimum ağırlık
    int[] weights = {7,5,10,15,5,3,8,4,9,12}; // Eşyaların ağırlıkları
    int[] values  = {10,15,5,8,20,10,7,17,5,3}; // Eşyaların değerleri

    // Rastgele birey oluşturucu (random solution)
    public Solution(int numItems) {
        chromosome = new int[numItems];
        for(int i = 0; i < chromosome.length; i++) {
            // %50 olasılıkla eşya çantaya alınır
            chromosome[i] = Math.random() < 0.5 ? 0 : 1;
        }
    }

    // Belirli bir genetik dizilimle (chromosome) birey oluştur
    public Solution(int[] chromosome) {
        this.chromosome = chromosome;
    }

    // Genetik algoritmalarda kullanılan çaprazlama (crossover) işlemi
    Solution crossover(Solution parent2) {
        int[] childChromosome = new int[this.chromosome.length];

        // Rastgele bir noktadan itibaren parçala (örneğin: 2 ile 8 arasında)
        int crossoverPoint = 2 + (int)(Math.random() * 7);

        // İlk parça parent1'den
        for (int i = 0; i < crossoverPoint; i++) {
            childChromosome[i] = this.chromosome[i];
        }

        // Kalan parça parent2'den
        for (int i = crossoverPoint; i < this.chromosome.length; i++) {
            childChromosome[i] = parent2.chromosome[i];
        }

        // Yeni birey dön
        return new Solution(childChromosome);
    }

    // Fitness değerini hesapla: Seçilen eşyaların toplam değeri
    int calculateFitness() {
        int totalValue = 0;
        int totalWeight = 0;

        for(int i = 0; i < chromosome.length; i++) {
            totalValue  += chromosome[i] * values[i];   // sadece seçilenlerin değeri
            totalWeight += chromosome[i] * weights[i];  // sadece seçilenlerin ağırlığı
        }

        // Eğer çanta kapasitesini aşıyorsa: cezalandır (penalty)
        if (totalWeight > maxWeight) {
            totalValue = 1000; // Penalize edilmiş fitness değeri (kötü bireyler ayıklanır)
        }

        return totalValue;
    }

    // Bireyin genetik kodunu string olarak döner
    public String toString() {
        String s = "";
        for(int gene : chromosome) {
            s += gene; // örn: 0101010110
        }
        return s;
    }
}

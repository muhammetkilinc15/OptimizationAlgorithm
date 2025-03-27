import java.util.Arrays;
import java.util.Random;

public class Task2 {

    /*
     * Sorunun Açıklaması:
     * Bu kod, Simulated Annealing (SA) algoritmasını kullanarak bir optimizasyon problemi çözmeyi amaçlamaktadır.
     * Problemin amacı, başlangıçta rastgele bir çözüm üretmek ve ardından bu çözümü iyileştirmek için bir dizi iterasyon yapmaktır.
     * Her iterasyonda, mevcut çözüm üzerinde küçük bir değişiklik yapılır ve bu yeni çözümün daha iyi olup olmadığına
     * karar verilir. Eğer yeni çözüm daha iyi ise kabul edilir, yoksa bir olasılıkla kabul edilir.
     * Olasılık, sıcaklık ile ilişkilidir ve sıcaklık azaldıkça daha kötü çözümler daha az kabul edilir.
     * Bu algoritma, yerel minimumlardan kaçabilmek için kullanılır.

     * Yapılan İşlemler:
     * 1. Başlangıç çözümü rastgele bir şekilde belirlenir.
     * 2. Simülasyon başlatılır ve iterasyonlar sırasında her adımda çözüm üzerinde küçük bir değişiklik yapılır.
     * 3. Yeni çözüm, mevcut çözümle karşılaştırılır. Eğer yeni çözüm daha iyi ise kabul edilir.
     * 4. Eğer yeni çözüm daha kötü ise, bir olasılıkla kabul edilir. Bu olasılık sıcaklıkla doğru orantılıdır.
     * 5. Sıcaklık her iterasyonda azalır.
     * 6. Algoritma, belirli bir sayıda iterasyondan sonra sonlanır.
     * 7. Sonuçta en iyi bulunan çözüm ve uygunluk (fitness) değeri yazdırılır.
     */

    // Sabit bir değer, fitness fonksiyonunda kullanılacak
    public static double Constant = 0.5;

    // Çözüm boyutunun (N) değeri
    public static int N = 10;

    // Simülasyonun başlangıç sıcaklığı
    public static int MAX_TEMPERATURE = 100;

    public static void main(String[] args) {
        Random random = new Random();  // Rastgele değerler oluşturmak için Random sınıfı
        double[] currentSolution = generateInitialSolution(random); // Başlangıç çözümünü oluştur
        double temperature = MAX_TEMPERATURE;  // Sıcaklık başlangıç değeri
        double cooling = 0.99;  // Soğuma katsayısı
        double currentFitness = fitness(currentSolution);  // Başlangıç çözümünün fitness değeri

        // 1000 iterasyon boyunca çözümü iyileştirmek
        for (int i = 0; i < 1000; i++) {
            double[] newSolution = movement(currentSolution, random);  // Yeni bir çözüm üret
            double newFitness = fitness(newSolution);  // Yeni çözümün fitness değerini hesapla

            // Eğer yeni çözüm daha iyi ise kabul et
            // Ya da daha kötü olsa da sıcaklık ile ilişkili olasılıkla kabul et
            if (newFitness < currentFitness || random.nextDouble() < Math.exp((currentFitness - newFitness) / temperature)) {
                currentSolution = newSolution;  // Yeni çözüm kabul edildi
                currentFitness = newFitness;  // Yeni fitness değeri
            }

            // Iterasyon sırasındaki sıcaklığı azalt
            temperature *= cooling;  // Sıcaklık her adımda azalır
        }

        // Sonuçları yazdır
        System.out.println("Best Fitness: " + currentFitness);  // En iyi fitness değeri
        System.out.println("Best Solution:" + Arrays.toString(currentSolution));  // En iyi çözüm
    }

    // Başlangıç çözümünü rastgele oluşturur
    public static double[] generateInitialSolution(Random random) {
        double[] initialSolution = new double[N];
        // Her elemanı [-30, 30] aralığında rastgele bir değerle başlat
        for (int i = 0; i < N; i++) {
            initialSolution[i] = random.nextInt(-30, 31);  // -30 ile 30 arasında rastgele değerler
        }
        return initialSolution;
    }

    // Hareket fonksiyonu: Rastgele bir indeksi seçer, bu indeksteki değeri değiştirir
    public static double[] movement(double[] currentSolution, Random random) {
        double[] newSolution = currentSolution.clone();  // Mevcut çözümü kopyala
        int index1 = random.nextInt(N);  // Rastgele bir indeks seç
        int value = random.nextInt(-5, 5);  // -5 ile 5 arasında rastgele bir değer seç
        newSolution[index1] += value;  // Seçilen indeksteki değeri güncelle

        // Çözüm değerini belirli sınırlar içinde tut
        if (newSolution[index1] > 30) {
            newSolution[index1] = 30;
        }
        if (newSolution[index1] < -30) {
            newSolution[index1] = -30;
        }

        return newSolution;
    }

    // Fitness fonksiyonu: Çözümün uygunluğunu hesaplar
    public static double fitness(double[] solution) {
        double sum = 0;
        // Her elemanın fitness değerini hesapla
        for (int i = 0; i < N; i++) {
            sum += Math.pow(solution[i] + Constant, 2);  // (x + Constant)^2 toplamı
        }
        return sum;  // Fitness değeri
    }
}

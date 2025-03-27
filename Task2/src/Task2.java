import java.util.Arrays;
import java.util.Random;

public class Task2 {

    // Belirtilen uzunlukta ve max değere göre rastgele başlangıç çözümü üretir
    public static double[] generateInitialSolution(int n, int max) {
        double[] arr = new double[n];
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            // -max ile +max arasında rastgele değerler üretir
            arr[i] = max - rnd.nextInt(max * 2);
        }
        return arr;
    }

    // Varolan çözüm üzerinde küçük bir değişiklik (hareket) yapar
    public static double[] movement(double[] arr, int min, int max) {
        double[] temp = arr.clone(); // Diziyi kopyalıyoruz ki orijinali bozulmasın
        Random random = new Random();
        int index = random.nextInt(arr.length); // Rastgele bir index seç
        int rnd = 5 - random.nextInt(11); // -5 ile +5 arasında rastgele bir sayı üret (-5, -4, ..., 5)
        temp[index] += rnd; // Seçilen index'teki değeri bu sayı kadar değiştir

        // Değer sınırlar dışına çıkarsa sınıra çek
        if (temp[index] > max) {
            temp[index] = max;
        } else if (temp[index] < min) {
            temp[index] = min;
        }
        return temp;
    }

    // Çözümün uygunluk (fitness) değerini hesaplar
    public static double fitness(double[] arr) {
        double total = 0;
        for (double v : arr) {
            total += Math.pow(v + 0.5, 2); // (v + 0.5)² toplamı
        }
        return total; // Amaç: Bu toplamı minimize etmek
    }

    public static void main(String[] args) {
        // 1. Başlangıç çözümünü üret
        double[] currentSolution = generateInitialSolution(10, 30);

        // 2. Başlangıç çözümünün fitness değerini hesapla
        double currentFitness = fitness(currentSolution);

        // Simulated Annealing parametreleri
        double temperature = 100; // Başlangıç sıcaklığı
        double cooling = 0.99; // Soğuma katsayısı
        Random rand = new Random();
        int iteration = 1000; // Toplam iterasyon sayısı
        int i = 0;

        // 3. Ana döngü: Her iterasyonda çözümü geliştir
        while (i < iteration) {
            // Mevcut çözümden bir komşu çözüm üret
            double[] newSolution = movement(currentSolution, -30, 30);
            double newFitness = fitness(newSolution);

            // 4. Kabul kriteri:
            // Eğer yeni çözüm daha iyi ise direkt kabul et
            // Daha kötü ise belli bir olasılıkla kabul et (yerel minimumdan kaçabilmek için)
            if (newFitness < currentFitness || rand.nextDouble() < Math.exp((currentFitness - newFitness) / temperature)) {
                currentSolution = newSolution;
                currentFitness = newFitness;
            }

            // Sıcaklığı azalt (soğuma)
            temperature *= cooling;

            // Durum çıktısı
            System.out.println("Iteration: " + i + ", fitness: " + currentFitness);
            i++;
        }

        // En iyi bulunan çözümü yazdır
        System.out.println("Best solution: " + Arrays.toString(currentSolution));
        System.out.println("Best fitness: " + currentFitness);
    }
}

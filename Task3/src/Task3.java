import java.util.Arrays;
import java.util.Random;

/**
 * Task3 - Simulated Annealing ile Gezgin Satıcı Problemi (TSP) Çözümü
 *
 * 📌 Amaç:
 * Bu program, 14 farklı şehir arasında minimum toplam mesafeyi bulan rotayı (gezgin satıcı problemi)
 * Simulated Annealing (SA) algoritması ile çözmeyi hedeflemektedir.
 *
 * 🌍 Problem:
 * Bir satıcı, her bir şehri yalnızca bir kez ziyaret ederek başladığı şehre geri dönmelidir.
 * Amaç, toplam yol uzunluğunu minimize eden en iyi rotayı bulmaktır.
 *
 * 🧠 Kullanılan Yöntem: Simulated Annealing (SA)
 * - Doğaya dayalı, olasılıksal bir optimizasyon algoritmasıdır.
 * - Yerel minimumlara takılmadan daha iyi çözümler bulmayı amaçlar.
 * - Sıcaklık (temperature) ve soğuma oranı (cooling rate) temel kontrol parametreleridir.
 *
 * 🔧 İçerik:
 * - Şehir koordinatları (latitude, longitude) sabit olarak tanımlıdır.
 * - Rastgele bir başlangıç çözümü (rota) üretilir.
 * - Komşu çözüm üretmek için iki şehir yer değiştirir.
 * - Öklid mesafesi ile rota maliyeti hesaplanır.
 * - Belirli bir sıcaklık ve iterasyon boyunca daha iyi çözümler aranır.
 *
 * 🧾 Çıktı:
 * - Her iterasyonda mevcut çözümün (rotanın) maliyeti (fitness) yazdırılır.
 * - En iyi rota ve bu rotanın toplam uzunluğu (fitness değeri) program sonunda ekrana basılır.
 *
 * 👨‍💻 Kullanım:
 * main() fonksiyonu içerisinde:
 *    - Başlangıç sıcaklığı = 1000
 *    - Soğuma oranı = 0.995
 *    - Maksimum iterasyon = 100000
 * ile algoritma çalıştırılır.
 *
 * 🧠 Simulated Annealing Ne Zaman Kabul Eder?
 *    - Yeni çözüm daha iyi → her zaman kabul
 *    - Daha kötü çözüm → küçük bir olasılıkla kabul (yerel minimumdan kaçmak için)
 *
 */


public class Task3 {
    // Toplam şehir sayısı (N = 14)
    private static final int N = 14;

    // Şehirlerin enlem ve boylam koordinatları
    public static final double[][] cordinates = {
            {16.47, 96.10}, {16.47, 94.44}, {20.09, 92.54}, {22.39, 93.37}, {25.23, 97.24},
            {22.00, 96.05}, {20.47, 97.02}, {17.20, 96.29}, {16.30, 97.38}, {14.05, 98.12},
            {16.53, 97.38}, {21.52, 95.59}, {19.41, 97.13}, {20.09, 94.55}
    };

    // Başlangıç için rastgele bir rota üretir (1'den 14'e kadar şehirler)
    public static int[] generateInitialSolution() {
        int[] nodes = new int[N];
        for (int i = 0; i < N; i++) {
            nodes[i] = i + 1; // Şehirler 1, 2, 3, ..., 14 şeklinde numaralandırılıyor
        }
        shuffleArray(nodes); // Rastgele karıştırılıyor
        return nodes;
    }

    // Dizi elemanlarını karıştırmak için kullanılan yardımcı metot (Fisher-Yates Shuffle)
    private static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    // Rastgele iki şehri yer değiştirerek yeni bir çözüm üretir
    public static int[] swapTwoRandomElements(int[] solution) {
        int[] arr = solution.clone(); // Orijinal çözümü bozmamak için kopya al
        Random rand = new Random();
        int i = rand.nextInt(N);
        int j = rand.nextInt(N);
        while (i == j) { // İkisi aynı olursa tekrar seç
            j = rand.nextInt(N);
        }
        // Swap işlemi
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }

    // İki şehir arasındaki Öklid mesafesini hesaplar
    public static double euclide(int index1, int index2) {
        return Math.sqrt(Math.pow(cordinates[index1 - 1][0] - cordinates[index2 - 1][0], 2)
                + Math.pow(cordinates[index1 - 1][1] - cordinates[index2 - 1][1], 2));
    }

    // Verilen bir çözüm dizisinin toplam rota maliyetini hesaplar
    public static double calculateCost(int[] solution) {
        double distAll = 0;
        // Tüm şehirler arasındaki mesafeleri toplar
        for (int i = 0; i < solution.length - 1; i++) {
            distAll += euclide(solution[i], solution[i + 1]);
        }
        // Son şehirden tekrar başlangıç şehrine dönmeyi de ekler
        distAll += euclide(solution[solution.length - 1], solution[0]);
        return distAll;
    }

    // Simulated Annealing algoritmasını uygulayan metot
    public static void simulatedAnnealing(double initialTemp, double coolingRate, int MaxIteration) {
        // 1. Rastgele bir başlangıç çözümü üret
        int[] currentSolution = generateInitialSolution();
        double currentFitness = calculateCost(currentSolution);

        double temperature = initialTemp; // Başlangıç sıcaklığı
        Random rand = new Random();
        int i = 0;

        // 2. İterasyon başlasın
        while (i < MaxIteration) {
            // Komşu çözüm üret (2 şehir yer değiştir)
            int[] newSolution = swapTwoRandomElements(currentSolution);
            double newFitness = calculateCost(newSolution);

            // 3. Kabul kriteri:
            // Yeni çözüm daha iyi ise kabul et
            // Daha kötü ise belirli bir olasılıkla kabul et (yerel minimumdan kaçmak için)
            if (newFitness < currentFitness || rand.nextDouble() < Math.exp((currentFitness - newFitness) / temperature)) {
                currentSolution = newSolution;
                currentFitness = newFitness;
            }

            // 4. Sıcaklığı azalt (soğuma işlemi)
            temperature *= coolingRate;

            // Durum çıktısı
            System.out.println("Iteration :" + i + " Best Fitness: " + currentFitness);
            i++;
        }

        // 5. Sonuçları yazdır
        System.out.println("Best route: " + Arrays.toString(currentSolution));
        System.out.println("Best Fitness: " + currentFitness);
    }

    public static void main(String[] args) {
        // Algoritmayı çalıştır
        simulatedAnnealing(1000, 0.995, 100000);
        // 1000 başlangıç sıcaklığı, 0.995 soğuma oranı, 100.000 iterasyon
    }
}

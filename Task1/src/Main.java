import java.util.Random;

public class Main {
    // Bu Task'ta Random bir n array oluşturulacak ve bu array üzerinde iterasyonlar yapılacak.
    // Her iterasyonda bir rastgele index seçilip bu index'teki değer rastgele bir değerle değiştirilecek.
    // Eğer yeni değer eski değerden daha iyi bir sonuç veriyorsa yeni değer kabul edilecek.
    // Fitness fonksiyonu olarak her bir elemanın karesinin toplamı kullanılacak.
    // [-100, 100] aralığında rastgele değerler alınacak.
    // 1000 iterasyon yapılacak ve sonuç yazdırılacak.
    // Sonuçta en iyi sonucu veren array yazdırılacak.

    public static int N = 10; // Dizinin boyutu
    public static void main(String[] args) {
        Random random = new Random();  // Rastgele değerler oluşturmak için Random sınıfı
        int[] currentSolution = generateInitialSolution(random); // Başlangıç çözümünü oluştur
        int maxIt = 1000;  // Maksimum iterasyon sayısı

        // Hill Climbing algoritması - her iterasyonda çözüm iyileştirilir
        for (int i = 0; i < maxIt; i++) {
            // Yeni çözüm üret
            int[] newSolution = movement(currentSolution, random);

            // Yeni çözüm eski çözümden daha iyi ise, onu kabul et
            if (fitness(currentSolution) > fitness(newSolution)) {
                currentSolution = newSolution.clone();  // Yeni çözümü kopyala
            }

            // Her iterasyonun fitness değerini yazdır
            System.out.println("Iteration " + (i+1) + ": " + fitness(currentSolution));
        }

        // Sonuçları yazdır
        System.out.println("Final Solution:");
        printSolution(currentSolution);
    }
    
    // Çözümü ekrana yazdırmak için yardımcı metod
    public static void printSolution(int[] solution) {
        for (int value : solution) {
            System.out.print(value + " ");
        }
        System.out.println();  // Yeni satır
    }

    // Başlangıç çözümünü rastgele oluşturur
    public static int[] generateInitialSolution(Random random) {
        int[] initialSolution = new int[N];
        // Her elemanı [-100, 100] aralığında rastgele bir değerle başlat
        for (int i = 0; i < N; i++) {
            initialSolution[i] = random.nextInt(-100, 101);  // -100 ile 100 arası
        }
        return initialSolution;
    }

    // Hareket fonksiyonu: Rastgele bir indeksi seçer, bu indeksteki değeri değiştirir
    public static int[] movement(int[] currentSolution, Random random) {
        int[] newSolution = currentSolution.clone();  // Mevcut çözümü kopyala
        int index1 = random.nextInt(N);  // Rastgele bir indeks seç
        int value = random.nextInt(-5, 5);  // -5 ile 5 arasında rastgele bir değer seç
        newSolution[index1] += value;  // Seçilen indeksteki değeri güncelle

        // Yeni değeri sınırlamak: -100 ile 100 arasında tut
        if (newSolution[index1] > 100) {
            newSolution[index1] = 100;
        }
        if (newSolution[index1] < -100) {
            newSolution[index1] = -100;
        }

        return newSolution;  // Yeni çözümü döndür
    }

    // Fitness fonksiyonu: Dizideki tüm elemanların karelerinin toplamı
    public static double fitness(int[] solution) {
        double sum = 0;
        // Her elemanın karesini alıp toplar
        for (int i = 0; i < N; i++) {
            sum += Math.pow(solution[i], 2);  // x[i]^2 yerine x[i] * x[i] kullanabiliriz
        }
        return sum;  // Fitness değeri
    }
}

package Example4;

import java.util.Random;

public class Pso2 {

    // Sigmoid fonksiyonu (şu anda kullanılmıyor ama örnek olarak eklenmiş)
    public static double sigmoid(double x) {
        return (1 / (1 + Math.pow(Math.E, (-1 * x))));
    }

    public static void main(String[] args) {
        System.out.println("aaa"); // Program başlangıcında basit çıktı

        int maxIt = 100; // Maksimum iterasyon sayısı (kaç adım çalışacak)
        Rosenbrock f = new Rosenbrock(); // Rosenbrock fonksiyonu örneği (fitness hesaplamak için)

        int dim = 5; // Her parçacığın sahip olduğu boyut sayısı (değişken sayısı)
        int np = 10; // Parçacık sayısı (sürüdeki toplam birey sayısı)
        float c1 = 2, c2 = 2; // Bilişsel (cognitive) ve sosyal katsayılar

        double wMax = 0.9; // Başlangıç atalet katsayısı (inertia weight)
        double wMin = 0.4; // Bitiş atalet katsayısı (inertia weight)

        double[][] p = new double[np][]; // Parçacıkların konumları (position)
        double[][] pBest = new double[np][]; // Her parçacığın şimdiye kadarki en iyi konumu
        double[] gbest = new double[dim]; // Sürüdeki en iyi konum (global best)
        double gFit = Double.MAX_VALUE; // Global en iyi fitness değeri

        double[][] v = new double[np][]; // Parçacıkların hız vektörleri (velocity)

        // Başlangıç konumları ve hızlarını rastgele belirle
        for (int i = 0; i < np; i++) {
            v[i] = new double[dim]; // Her parçacığın hız vektörü
            p[i] = new double[dim]; // Her parçacığın konum vektörü
            pBest[i] = new double[dim]; // En iyi konumun başlangıç hali

            for (int j = 0; j < dim; j++) {
                p[i][j] = (Math.random() * 10) - 5; // Konumu [-5, 5] aralığında başlat
                pBest[i][j] = p[i][j]; // Başlangıçta en iyi konum, kendisi
                v[i][j] = 0; // Başlangıçta hız 0
            }
        }

        // PSO ana döngüsü
        for (int t = 0; t < maxIt; t++) {
            // Atalet ağırlığını zamanla azalt
            double w = wMax - ((wMax - wMin) * t / maxIt);

            // Her parçacık için fitness hesapla ve pBest, gBest güncelle
            for (int i = 0; i < np; i++) {
                double pb = f.evaluate(pBest[i]); // parçacığın en iyi konumunun fitness değeri
                double fb = f.evaluate(p[i]); // mevcut konumunun fitness değeri

                // Eğer yeni konum daha iyi ise pBest'i güncelle
                if (pb > fb) {
                    // Arrays.copyOf(p[i], dim) yerine döngü ile kopyalama yapılabilir
                    for (int j = 0; j < dim; j++) {
                        pBest[i][j] = p[i][j];
                    }
                }

                // Eğer bu parçacığın konumu globalden daha iyi ise gBest güncelle
                if (gFit > fb) {
                    for (int j = 0; j < dim; j++) {
                        gbest[j] = p[i][j];
                    }
                    gFit = fb;
                }
            }

            System.out.println(t + " iterasyon : " + gFit ); // Her iterasyonda en iyi sonucu yazdır

            // Parçacıkların hız ve konumlarını güncelle
            for (int i = 0; i < np; i++) {
                for (int j = 0; j < dim; j++) {
                    // Velocity güncelleme formülü: atalet + bilişsel + sosyal bileşen
                    v[i][j] = w * v[i][j] +
                            (c1 * Math.random() * (pBest[i][j] - p[i][j])) +
                            (c2 * Math.random() * (gbest[j] - p[i][j]));

                    // Yeni konum = eski konum + hız
                    p[i][j] = p[i][j] + v[i][j];
                }
            }
        }

        // Toplam fitness hesaplama sayısını yazdır
        System.out.println(" Fitness Evaluation Number : " + f.count);
        // En iyi fitness ve konum değerlerini yazdır
        System.out.println(gFit + " - ");

        for (int j = 0; j < dim; j++) {
            System.out.print("," + gbest[j]);
        }
    }
}

// Rosenbrock fonksiyonu (optimization test fonksiyonu)
class Rosenbrock {
    public int count = 0; // Kaç defa çalıştığını saymak için sayaç

    // Rosenbrock fonksiyonunu değerlendir (küçük olması hedeflenir)
    public double evaluate(double[] x) {
        count++;
        double sum = 0;
        for (int i = 0; i < x.length - 1; i++) {
            // Rosenbrock formülü
            sum += 100 * Math.pow((x[i + 1] - Math.pow(x[i], 2)), 2)
                    + Math.pow((1 - x[i]), 2);
        }
        return sum;
    }
}

package Task;

import java.util.Random;
/*
     Problem : Dikdörtgen şeklinde bir tarlamız var alanı 400 m^2
     Biz bu tarlaya 3 farklı türde şeftali ağaçları dikmek istiyoruz.
     Her türdeki ağaçların kapladıkları alan ve kazandırdıkları para farklıdır.
     Ağaçlarımız 3 farklı ağacımız a,b,c olsun.
     Ağaçların kapladıklaır alanlar sırası ile 3,5,7 m^2
     Ağaçların kazandıkları para sırası ile 10,20,30 TL

     Kısıtlamalar:
        * Ekdiğimiz ağaçlar toplam 400 m^2 den küçük ya da eşit olmalıdır.
        * Bir ağaç türünün maximum sayısı toplam ağaç sayısına oranla 0.5den buyuk olamaz.


    Fitness Fonksiyonu:
        * Ağaçların kazandıkları para toplamını return eder.

    Constraint Fonksiyonları:
        * Bir ağaç türünün maximum sayısı toplam ağaç sayısına oranla 0.5den buyuk olamaz.
        * Ekdiğimiz ağaçlar toplam 400 m^2 den küçük ya da eşit olmalıdır.

     Görüntülenen sonuçlar :
        * En iyi sonuç
        * En iyi sonuçta bulunan ağaç sayısı
 */


public class Task {

    public static void main(String[] args) {
        int maxIt = 100;
        int dim = 3;
        int np = 10;
        float c1 = 2, c2 = 2;
        int[][] p = new int[np][dim];
        int[][] pBest = new int[np][dim];
        int[] gBest = new int[dim];
        double gFit = 0;
        double[][] v = new double[np][dim];

        int[] profits = {10, 20, 30}; // profiletler
        int[] areas = {3, 5, 7}; // alanlar

        Random random = new Random();
        for (int i = 0; i < np; i++) {
            do {
                for (int j = 0; j < dim; j++) {
                    p[i][j] = random.nextInt(20);
                    pBest[i][j] = p[i][j];
                    v[i][j] = 0;
                }
            } while (!(constraint2(p[i], areas) && constraint1(p[i])));
        }

        for (int t = 0; t < maxIt; t++) {
            for (int i = 0; i < np; i++) {
                double currentFitness  = fitness(p[i], profits);
                double personalBestFitness  = fitness(pBest[i], profits);

                // update PBest
                if (personalBestFitness  < currentFitness  && constraint1(p[i]) && constraint2(p[i], areas)) {
                    System.arraycopy(p[i], 0, pBest[i], 0, dim);
                }
                // update GBest
                if (gFit < currentFitness  && constraint1(p[i]) && constraint2(p[i], areas)) {
                    System.arraycopy(p[i], 0, gBest, 0, dim);
                    gFit = currentFitness ;
                }
            }
            System.out.println("Iteration " + (t + 1) + ": " + gFit);
            for (int i = 0; i < np; i++) {
                for (int j = 0; j < dim; j++) {
                    v[i][j] = v[i][j] + (c1 * Math.random() * (pBest[i][j] -
                            p[i][j])) + (c2 * Math.random() * (gBest[j] - p[i][j]));
                    p[i][j] = (int) (p[i][j] + v[i][j]);
                }
            }
        }

        System.out.print("Best Solution: ");
        for (int j = 0; j < dim; j++) {
            System.out.print(gBest[j] + ", ");
        }

    }

    public static boolean constraint1(int[] x) {
        for (double v : x) {
            if (v / sum(x) > 0.5) {
                return false;
            }
        }
        return true;
    }

    public static boolean constraint2(int[] x, int[] areas) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * areas[i];
        }
        return sum <= 400;
    }

    public static double sum(int[] x) {
        double sum = 0;
        for (double v : x) {
            sum += v;
        }
        return sum;
    }

    public static double fitness(int[] x, int[] profits) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * profits[i];
        }
        return sum;
    }
}

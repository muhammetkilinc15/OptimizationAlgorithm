import java.util.Random;

public class Main {

    // Bu Task'ta Random bir n array oluşturulacak ve bu array üzerinde iterasyonlar yapılacak.
    // Her iterasyonda bir rastgele index seçilip bu index'teki değer rastgele bir değerle değiştirilecek.
    // Eğer yeni değer eski değerden daha iyi bir sonuç veriyorsa yeni değer kabul edilecek.
    // Fitness fonksiyonu olarak her bir elemanın karesinin toplamı kullanılacak.
    // [-100, 100] aralığında rastgele değerler alınacak.
    // 1000 iterasyon yapılacak ve sonuç yazdırılacak.
    // Sonuçta en iyi sonucu veren array yazdırılacak.
    public static void main(String[] args) {

        int[] currentSol = new int[10]; // Random n array
        Random random = new Random();

        // [-100, 100] doldur
        for (int i = 0; i < currentSol.length; i++) {
            currentSol[i] = random.nextInt(-100, 100);
        }

        int maxIt = 1000;
        int it = 0;
        while (it < maxIt) {
            int[] newArr = movement(currentSol);
            if (fitness(newArr) < fitness(currentSol)) {
                currentSol = newArr.clone();
            }
            System.out.println("Iteration: " + (it + 1) + "  Value: " + fitness(currentSol));
            it++;
        }
        System.out.println("Final Solution:");
        for (int value : currentSol) {
            System.out.print(value + " ");
        }
    }


    public static int[] movement(int[] currentSol) {
        Random rand = new Random();
        int[] newArr = currentSol.clone();
        int index = rand.nextInt(10);
        int randomNumber = rand.nextInt(-5, 5);
        newArr[index] += randomNumber;
        if (newArr[index] < -100)
            newArr[index] = -100;
        if (newArr[index] > 100)
            newArr[index] = 100;
        return newArr;
    }

    public static int fitness(int[] currentSol) {
        int sum = 0;
        for (int value : currentSol) {
            sum += (int) Math.pow(value, 2);
        }
        return sum;
    }
}
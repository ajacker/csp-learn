import java.util.Arrays;
import java.util.Scanner;

public class csp_201903_1 {
    /**
     *
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            ints[i] = scanner.nextInt();
        }
        scanner.close();
        Arrays.sort(ints);
        int min = ints[0];
        int max = ints[ints.length - 1];
        int a = n / 2;
        int b = n % 2 == 0 ? a - 1 : a;
        float mid = (ints[a] + ints[b]) / 2f;
        if ((int) mid == mid) {
            System.out.printf("%d %d %d", max, (int) mid, min);
        } else {
            System.out.printf("%d %.1f %d", max, mid, min);
        }
    }
}

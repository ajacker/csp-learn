import java.util.Arrays;
import java.util.Scanner;

public class csp_201409_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        Arrays.sort(arr);
        int count = 0;
        for (int i = 0; i + 1 < n; i++) {
            count = arr[i + 1] - arr[i] == 1 ? count + 1 : count;
        }
        System.out.println(count);
    }
}

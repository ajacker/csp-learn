import java.util.Scanner;

/**
 * @author ajacker
 * @date 18-9-12 下午5:49
 */
public class csp_201609_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] prices = new int[n];
        int max = 0;
        for (int i = 0; i < n; i++)
            prices[i] = sc.nextInt();
        for (int i = 0; i < n - 1; i++) {
            int abs = Math.abs(prices[i] - prices[i + 1]);
            max = Math.max(abs, max);
        }
        System.out.println(max);
    }
}

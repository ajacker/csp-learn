import java.util.Scanner;

public class csp_201809_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] prices = new int[n];
        for (int i = 0; i < n; i++) {
            prices[i] = sc.nextInt();
        }
        int[] newPrices = new int[n];
        newPrices[0] = (prices[0] + prices[1]) / 2;
        newPrices[n - 1] = (prices[n - 1] + prices[n - 2]) / 2;
        for (int i = 1; i < n - 1; i++) {
            int prev = i - 1;
            int next = i + 1;
            newPrices[i] = (prices[prev] + prices[i] + prices[next]) / 3;
        }
        for (int i = 0; i < n; i++) {
            System.out.print(newPrices[i] + " ");
        }
    }
}

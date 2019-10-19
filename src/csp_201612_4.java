import java.util.Scanner;

/**
 * @author ajacker
 * 动态规划
 */
public class csp_201612_4 {
    static int n;
    static int dp[][];
    static int sum[];
    static int times[];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        dp = new int[n + 2][n + 2];
        sum = new int[n + 2];
        times = new int[n + 2];
        for (int i = 1; i <= n; i++) {
            times[i] = sc.nextInt();
            sum[i] = sum[i - 1] + times[i];
        }
        for (int len = 1; len < n; len++) {
            for (int i = 1, k = i + len; k <= n; i++, k++) {
                dp[i][k] = Integer.MAX_VALUE;
                for (int t = i; t < k; t++) {
                    //合并花销，从i到k
                    int cost = sum[k] - sum[i - 1];
                    dp[i][k] = Math.min(dp[i][k], dp[i][t] + dp[t + 1][k]);
                }
            }
        }
        System.out.println(dp[1][n]);
    }
}

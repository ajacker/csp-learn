import java.util.Scanner;

/**
 * @author ajacker
 * @date 2019/11/4 11:08
 * 有趣的数
 * 动态规划
 * **
 */
public class csp_201312_4 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long mod = 1000000007;
        long[] count = new long[6];
        count[0] = 1;
        // 2
        // 2 0
        // 2 3
        // 2 0 1
        // 2 0 3
        // 2 0 1 3
        for (int i = 1; i < n; i++) {
            //新的状态
            long[] newCount = new long[6];
            newCount[0] = (count[0]) % mod;
            newCount[1] = (count[0] + count[1] * 2) % mod;
            newCount[2] = (count[0] + count[2]) % mod;
            newCount[3] = (count[1] + count[3] * 2) % mod;
            newCount[4] = (count[1] + count[2] + count[4] * 2) % mod;
            newCount[5] = (count[3] + count[4] + count[5] * 2) % mod;
            count = newCount;
        }
        System.out.println(count[5]);
    }
}

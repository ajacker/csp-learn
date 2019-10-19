import java.util.Scanner;

/**
 * @author ajacker
 * 最短路解差分约束
 */
public class csp_201809_4 {
    static int n;
    static int[] second = new int[305];
    static int[] first = new int[305];
    static boolean[][][] isSearched = new boolean[305][305][305];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        //读入第二天第i家的价格
        for (int i = 1; i <= n; i++) {
            second[i] = sc.nextInt();
        }
        for (int i = 1; i <= 2 * second[1]; i++) {
            first[1] = i;
            first[2] = 2 * second[1] - i;
            dfs(2, first[1], first[2]);
            first[1] = i;
            first[2] = 2 * second[1] - i + 1;
            dfs(2, first[1], first[2]);
        }

    }

    /**
     * dfs
     *
     * @param m 当前店铺是m号
     * @param x m-1号店铺第2天的价格
     * @param y m号店铺第2天的价格
     */
    public static void dfs(int m, int x, int y) {
        if (isSearched[m][x][y]) {
            return;
        }
        isSearched[m][x][y] = true;
        //如果要推最后一个店铺
        if (m == n - 1) {
            for (int i = 0; i < 3; i++) {
                if ((first[n] = (3 * second[m] - x - y + i)) > 0 && (first[n - 1] + first[n]) / 2 == second[n]) {
                    for (int t = 1; t <= n; t++) {
                        System.out.print(first[t] + " ");
                    }
                    System.exit(0);
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            first[m + 1] = 3 * second[m] - x - y + i;
            if (first[m + 1] > 0) {
                //递推下一家店铺
                dfs(m + 1, y, first[m + 1]);
            }
        }
    }
}

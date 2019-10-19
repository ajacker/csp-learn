import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class csp_201809_5 {
    static final long Q = 998_244_353;
    static int m, l, r;
    static long[] a, k;
    static long[][] A;
    static long[][] E;

    public static void main(String[] args) {
        init();
        loadMatrix();
        initData();
        getData();
        //输出结果
        for (int i = l; i <= r; i++) {
            System.out.println(a[i]);
        }

    }

    private static void getData() {
        //一次能求m个，求几次
        int time = (r - l) / m + 1;
        for (int i = 0; i < time; i++) {
            int p = l - 1 + i * m;
            if (p + m <= m) {
                continue;
            }
            long[][] tmp = quickPow(A, p);
            for (int j = 0; j < m; j++) {
                int index = p + m - j;
                if (index > m && index <= r) {
                    for (int k = 0; k < m; k++) {
                        a[index] = (a[index] + tmp[j][k] * a[m - k]) % Q;
                    }
                }
            }
        }
    }

    private static void initData() {
        //推出a1-am
        for (int i = 1; i <= m; i++) {
            fun(i);
        }
    }

    private static void loadMatrix() {
        //设置A矩阵
        for (int i = 0; i < m; i++) {
            A[0][i] = k[i + 1];
            if (i + 1 < m) {
                A[i + 1][i] = 1;
            }
        }
        //设置单位矩阵
        for (int i = 0; i < m; i++) {
            E[i][i] = 1;
        }
    }

    private static void init() {
        m = InputReader.nextInt();
        l = InputReader.nextInt();
        r = InputReader.nextInt();
        a = new long[r + 1];
        a[0] = 1;
        k = new long[m + 1];
        A = new long[m][m];
        E = new long[m][m];
        loadK();
    }

    private static void loadK() {
        //读入k_1-k_m
        for (int i = 1; i <= m; i++) {
            k[i] = InputReader.nextInt();
        }
    }

    public static void fun(int n) {
        for (int i = 1; i <= Math.min(n, m); i++) {
            a[n] += k[i] * a[n - i];
            a[n] %= Q;
        }
    }

    /**
     * 矩阵乘法
     *
     * @param a A
     * @param b B
     * @return A乘B
     */
    public static long[][] mul(long[][] a, long[][] b) {
        int v = a.length;
        long[][] c = new long[v][v];
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                for (int k = 0; k < v; k++) {
                    c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % Q;
                }
            }
        }
        return c;
    }

    /**
     * 快速矩阵幂
     *
     * @param m 矩阵
     * @param n 幂
     * @return 结果矩阵
     */
    public static long[][] quickPow(long[][] m, int n) {
        long[][] temp = E;
        while (n > 0) {
            if ((n & 1) == 1) {
                temp = mul(temp, m);
            }
            m = mul(m, m);
            n >>= 1;
        }
        return temp;
    }

    static class InputReader {
        private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private static StringTokenizer tokenizer = null;

        public static String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public static int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

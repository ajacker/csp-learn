import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @author ajacker
 * 欧拉通路 Hierholzer算法
 */
public class csp_201512_4 {
    static int n, m;
    static ArrayList<Integer>[] map;
    static int[] father;
    static boolean[][] isVisited;
    static Stack<Integer> path = new Stack<>();

    public static void main(String[] args) {
        n = InputReader.nextInt();
        m = InputReader.nextInt();
        map = new ArrayList[n + 1];
        isVisited = new boolean[n + 1][n + 1];
        father = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            map[i] = new ArrayList<>();
            father[i] = i;
        }
        for (int i = 0; i < m; i++) {
            int a = InputReader.nextInt();
            int b = InputReader.nextInt();
            //添加无向边
            map[a].add(b);
            map[b].add(a);
            //并查集中连接
            union(a, b);
        }
        if (judgeConnect() && judgeNum()) {
            dfs();
            while (!path.empty()) {
                System.out.print(path.pop() + " ");
            }
        } else {
            System.out.println(-1);
        }
    }

    /**
     * 深度优先搜索路径
     */
    public static void dfs() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        while (!stack.empty()) {
            int u = stack.peek();
            int i;
            for (i = 0; i < map[u].size(); i++) {
                int v = map[u].get(i);
                if (!isVisited[u][v]) {
                    stack.push(v);
                    isVisited[u][v] = true;
                    isVisited[v][u] = true;
                    break;
                }
            }
            if (i == map[u].size()) {
                stack.pop();
                //记录路径
                path.push(u);
            }

        }
    }

    /**
     * 并查集：合并两个点
     *
     * @param a a
     * @param b b
     */
    public static void union(int a, int b) {
        int fa = find(a);
        int fb = find(b);
        if (fa != fb) {
            father[fa] = fb;
        }
    }

    /**
     * 并查集：求归属集
     *
     * @param n n
     * @return 归属组号
     */
    public static int find(int n) {
        if (father[n] != n) {
            return father[n] = find(father[n]);
        }
        return n;
    }

    /**
     * 判断是否为连通图
     *
     * @return 是否为连通图
     */
    public static boolean judgeConnect() {
        boolean result;
        //连通分量
        int disConnectPart = 0;
        for (int i = 1; i <= n; i++) {
            if (father[i] == i) {
                disConnectPart++;
                if (disConnectPart > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断度数是否满足存在0个或2个奇度定点的条件（一定存在欧拉回路）
     *
     * @return 是否
     */
    public static boolean judgeNum() {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            Collections.sort(map[i]);
            if ((map[i].size() ^ 1) == 1) {
                count++;
                if (count > 2) {
                    return false;
                }
            }
        }
        if (count == 2) {
            return (map[1].size() ^ 1) == 1;
        }
        return count == 0;
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

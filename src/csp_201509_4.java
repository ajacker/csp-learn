import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author ajacker
 * tarjan求极大强连通子图
 */
public class csp_201509_4 {
    static List<Integer>[] map;
    static boolean[] visited;
    static boolean[][] canReach;
    static int n, m, index;
    static int count = 0;
    static int[] dfn, low;
    static Stack<Integer> helper;

    public static void main(String[] args) {
        n = InputReader.nextInt();
        m = InputReader.nextInt();
        visited = new boolean[n + 1];
        map = new LinkedList[m + 1];

        for (int i = 1; i <= m; i++) {
            map[i] = new LinkedList<>();
        }
        for (int i = 1; i <= m; i++) {
            int u = InputReader.nextInt();
            int v = InputReader.nextInt();
            map[u].add(v);
        }
        index = 0;
        helper = new Stack<>();
        dfn = new int[n + 1];
        low = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                tarjan(i);
            }
        }
//        tarjan();
        System.out.print(count);

    }

    private static void tarjan() {
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                Stack<Integer> stack = new Stack<>();
                stack.push(i);
                while (!stack.isEmpty()) {
                    int cur = stack.peek();
                    visited[cur] = true;
                    dfn[cur] = low[cur] = ++index;
                    helper.push(cur);
                    int j;
                    for (j = 0; j < map[cur].size(); j++) {
                        int next = map[cur].get(j);
                        if (!visited[next]) {
                            stack.push(next);
                            break;
                        } else if (helper.contains(next)) {
                            low[cur] = Math.min(low[cur], dfn[next]);
                        }
                    }
                    if (j == map[cur].size()) {
                        int v = stack.pop();
                        try {
                            int u = stack.peek();
                            low[u] = Math.min(low[u], low[v]);
                            if (dfn[u] == low[u]) {
                                int cnt = 0;
                                do {
                                    v = helper.pop();
                                    cnt++;
                                } while (u != v);
                                if (cnt > 0) {
                                    count += cnt * (cnt - 1) / 2;
                                }
                            }
                        } catch (Exception ignored) {
                        }
                    }

                }
            }
        }
    }

    private static void tarjan(int u) {
        visited[u] = true;
        dfn[u] = low[u] = ++index;
        helper.push(u);
        int v;
        for (int i = 0; i < map[u].size(); i++) {
            v = map[u].get(i);
            if (!visited[v]) {
                tarjan(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (helper.contains(v)) {
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
        if (dfn[u] == low[u]) {
            int c = 0;
            do {
                v = helper.pop();
                c++;
            } while (u != v);
            if (c > 0) {
                count += c * (c - 1) / 2;
            }
        }

    }

    private static void dfs() {
        canReach = new boolean[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(visited, false);
            Stack<Integer> stack = new Stack<>();
            stack.push(i);
            visited[i] = true;
            while (!stack.empty()) {
                int cur = stack.pop();
                if (i != cur) {
                    canReach[i][cur] = true;
                    if (canReach[cur][i]) {
                        count++;
                    }
                }
                for (Integer next : map[cur]) {
                    if (!visited[next]) {
                        stack.push(next);
                        visited[next] = true;
                    }
                }
            }

        }
    }

    static class InputReader {
        static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        static StringTokenizer tokenizer = null;

        static String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        static int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

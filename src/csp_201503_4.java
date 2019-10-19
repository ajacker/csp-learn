import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author ajacker
 * 两次dfs/bfs，求树的直径e
 */
public class csp_201503_4 {
    static int n, m;
    static List<Integer>[] map;
    static boolean[] visited;
    static int maxDepth;

    public static void main(String[] args) {
        //n台交换机
        n = InputReader.nextInt();
        //m台终端电脑
        m = InputReader.nextInt();
        map = new ArrayList[n + m + 1];
        visited = new boolean[n + m + 1];
        for (int i = 1; i <= n + m; i++) {
            map[i] = new ArrayList<>();
        }
        for (int i = 2; i <= n + m; i++) {
            int n = InputReader.nextInt();
            map[n].add(i);
            map[i].add(n);
        }
        //先找到最深的点
        int lowest = dfs(1);
        //再从该点搜索
        dfs(lowest);
        System.out.println(maxDepth);
    }

    private static int dfs(int start) {
        int depth = 0;
        int depthId = 0;
        Arrays.fill(visited, false);
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        visited[start] = true;
        while (!stack.isEmpty()) {
            int cur = stack.peek();
            if (depth > maxDepth) {
                maxDepth = depth;
                depthId = cur;
            }
            int i;
            for (i = 0; i < map[cur].size(); i++) {
                int next = map[cur].get(i);
                if (!visited[next]) {
                    visited[next] = true;
                    stack.push(next);
                    depth++;
                    break;
                }
            }
            if (i == map[cur].size()) {
                stack.pop();
                depth--;
            }
        }
        return depthId;
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

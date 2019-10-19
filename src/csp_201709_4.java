import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author ajacker
 * dfs
 */
public class csp_201709_4 {
    static int N, M;
    static boolean[][] know;
    static boolean[] visited;
    static List<Integer>[] map;

    public static void main(String[] args) {
        N = InputReader.nextInt();
        M = InputReader.nextInt();
        know = new boolean[N + 1][N + 1];
        visited = new boolean[N + 1];
        map = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            map[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int a = InputReader.nextInt();
            int b = InputReader.nextInt();
            map[a].add(b);
        }
        int count = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 1; i <= N; i++) {
            Arrays.fill(visited, false);
            stack.clear();
            stack.push(i);
            visited[i] = true;
            while (!stack.empty()) {
                int cur = stack.pop();
                know[i][cur] = true;
                know[cur][i] = true;
                for (Integer k : map[cur]) {
                    if (!visited[k]) {
                        stack.push(k);
                        visited[k] = true;
                    }
                }
            }
        }
        for (int i = 1; i <= N; i++) {
            int j;
            for (j = 1; j <= N; j++) {
                if (!know[i][j]) {
                    break;
                }
            }
            if (j == N + 1) {
                count++;
            }
        }
        System.out.println(count);
    }

    static class InputReader {
        private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private static StringTokenizer tokenizer = null;

        public static String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        public static int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

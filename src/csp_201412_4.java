import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author ajacker
 * 最小生成树，克鲁斯卡尔
 */
public class csp_201412_4 {
    static int n, m;
    static List<Edge> edges = new ArrayList<>();
    static int[] parent;

    public static void main(String[] args) {
        n = InputReader.nextInt();
        m = InputReader.nextInt();
        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }
        for (int i = 0; i < m; i++) {
            int u = InputReader.nextInt();
            int v = InputReader.nextInt();
            int weight = InputReader.nextInt();
            edges.add(new Edge(u, v, weight));
        }
        Collections.sort(edges);
        int count = 0;
        int sum = 0;
        for (Edge edge : edges) {
            if (union(edge.u, edge.v)) {
                sum += edge.weight;
                count++;
            }
            if (count == n - 1) {
                break;
            }
        }
        System.out.println(sum);
    }

    static int find(int i) {
        if (parent[i] != i) {
            return find(parent[i]);
        }
        return parent[i];
    }

    static boolean union(int a, int b) {
        int u = find(a);
        int v = find(b);
        if (u != v) {
            parent[u] = v;
            return true;
        }
        return false;
    }

    static class Edge implements Comparable<Edge> {
        int u;
        int v;
        int weight;

        public Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    static class InputReader {
        static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        static StringTokenizer tokenizer = null;

        public static String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException ignored) {
                }
            }
            return tokenizer.nextToken();
        }

        public static int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

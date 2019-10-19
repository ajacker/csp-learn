import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author 迪杰斯特拉算法，优先队列
 */
public class csp_201609_4 {
    static int n, m;
    static List<Edge>[] map;
    static boolean[] visited;
    static int[] distance;
    static int[] cost;

    public static void main(String[] args) {
        n = InputReader.nextInt();
        m = InputReader.nextInt();

        map = new ArrayList[m + 1];
        for (int i = 1; i <= m; i++) {
            map[i] = new ArrayList<>();
        }
        visited = new boolean[n + 1];
        distance = new int[n + 1];
        cost = new int[n + 1];

        for (int i = 0; i < m; i++) {
            int a = InputReader.nextInt();
            int b = InputReader.nextInt();
            int c = InputReader.nextInt();
            map[a].add(new Edge(b, c));
            map[b].add(new Edge(a, c));
        }

        djs(1);

        System.out.println(Arrays.stream(cost).sum());
    }

    private static void djs(int start) {
        for (int i = 1; i <= n; i++) {
            distance[i] = Integer.MAX_VALUE;
            cost[i] = Integer.MAX_VALUE;
        }
        cost[start] = distance[start] = 0;
        Queue<Node> queue = new PriorityQueue<>();
        queue.offer(new Node(start, 0));
        visited[start] = true;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int v = cur.v;
            for (Edge edge : map[v]) {
                int nextV = edge.v;
                if (!visited[nextV]) {
                    int edgeWeight = edge.weight;
                    int nextDistance = distance[v] + edgeWeight;
                    if (distance[nextV] > nextDistance) {
                        distance[nextV] = nextDistance;
                        //v到nextV的花费
                        cost[nextV] = edgeWeight;
                        queue.offer(new Node(nextV, nextDistance));
                    }
                    if (distance[nextV] == nextDistance) {
                        cost[nextV] = Math.min(cost[nextV], edgeWeight);
                    }
                }
            }
        }
    }

    static class Edge {
        int v, weight;

        Edge(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }
    }

    static class Node implements Comparable<Node> {
        int v, cost;

        public Node(int v, int weight) {
            this.v = v;
            this.cost = weight;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.cost, o.cost);
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
                return tokenizer.nextToken();
            }
            return tokenizer.nextToken();
        }

        static int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
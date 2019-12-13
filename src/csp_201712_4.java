import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author ajacker
 * 最短路径 迪杰斯特拉
 */
public class csp_201712_4 {
    static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    final static double INF = Double.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        in.nextToken();
        int n = (int) in.nval;
        in.nextToken();
        int m = (int) in.nval;
        //载入邻接矩阵存储的图
        double[][] map1 = new double[n + 1][n + 1];
        double[][] map2 = new double[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                double value = i == j ? 0 : INF;
                map1[i][j] = value;
                map2[i][j] = value;
            }
        }
        for (int i = 1; i <= m; i++) {
            in.nextToken();
            int t = (int) in.nval;
            in.nextToken();
            int a = (int) in.nval;
            in.nextToken();
            int b = (int) in.nval;
            in.nextToken();
            double c = in.nval;
            if (t == 0) {
                map1[a][b] = c;
                map1[b][a] = c;
            } else {
                map2[a][b] = c;
                map2[b][a] = c;
            }
        }
        floyd(map1);
        floyd(map2);
        spfa(map1, map2);
        //dijstra(map1, map2);
    }

    private static void spfa(double[][] map1, double[][] map2) {
        int len = map1.length;
        Queue<Integer> queue = new LinkedList<>();
        boolean[] inQueue = new boolean[len];
        double[] dis1 = new double[len];
        double[] dis2 = new double[len];
        Arrays.fill(dis1, INF);
        Arrays.fill(dis2, INF);
        dis1[1] = 0;
        dis2[1] = 0;
        queue.offer(1);
        inQueue[1] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            inQueue[u] = false;
            for (int v = 1; v < len; v++) {
                if (u != v) {
                    boolean offer = false;
                    if (map1[u][v] < INF) {
                        //大路更新最短路(可以用小路更新，因为大路可以走大路或者小路)
                        if (dis1[u] + map1[u][v] < dis1[v] || dis2[u] + map1[u][v] < dis1[v]) {
                            dis1[v] = Math.min(dis1[u] + map1[u][v], dis2[u] + map1[u][v]);
                            offer = true;
                        }
                    }
                    if (map2[u][v] < INF) {
                        //小路更新最短路，上一步只可能是大路
                        if (dis1[u] + map2[u][v] * map2[u][v] < dis2[v]) {
                            dis2[v] = dis1[u] + map2[u][v] * map2[u][v];
                            offer = true;
                        }
                    }
                    if (!inQueue[v] && offer) {
                        inQueue[v] = true;
                        queue.offer(v);
                    }
                }

            }
        }
        System.out.printf("%.0f\n", Math.min(dis1[len - 1], dis2[len - 1]));
    }

    /**
     * 小路走了只能走大路了
     *
     * @param map1 大路
     * @param map2 小路
     */
    private static void dijstra(double[][] map1, double[][] map2) {
        int len = map1.length;
        //标记是否求出最短路径
        boolean[] solved = new boolean[len];
        double[] dis = new double[len];
        Arrays.fill(dis, INF);
        PriorityQueue<Road> queue = new PriorityQueue<>();
        queue.offer(new Road(1, 0));
        dis[1] = 0;
        while (!queue.isEmpty()) {
            Road cur = queue.poll();
            int u = cur.v;
            if (!solved[u]) {
                solved[u] = true;
                for (int v = 1; v < len; v++) {
                    if (u == v) {
                        continue;
                    }
                    if (cur.little) {
                        //上一个走了小路这一个只能走大路
                        if (map1[u][v] != INF && !solved[v] && map1[u][v] + dis[u] < dis[v]) {
                            dis[v] = map1[u][v] + dis[u];
                            queue.offer(new Road(v, dis[v]));
                        }
                    } else {
                        if (map1[u][v] != INF || map2[u][v] != INF) {
                            //true代表选择小路
                            boolean flag;
                            flag = map1[u][v] == INF || map1[u][v] > map2[u][v] * map2[u][v];
                            double d = Math.min(map1[u][v], map2[u][v] * map2[u][v]);
                            if (!solved[v] && d + dis[u] < dis[v]) {
                                dis[v] = d + dis[u];
                                Road next = new Road(v, dis[v]);
                                next.little = flag;
                                queue.offer(next);
                            }
                        }
                    }
                }
            }

        }
        System.out.printf("%.0f", dis[len - 1]);
    }

    private static void floyd(double[][] dis) {
        int len = dis.length;
        for (int k = 1; k < len; k++) {
            for (int i = 1; i < len; i++) {
                for (int j = 1; j < len; j++) {
                    double tmp = (dis[i][k] == INF || dis[k][j] == INF) ? INF : (dis[i][k] + dis[k][j]);
                    if (tmp < dis[i][j]) {
                        dis[i][j] = tmp;
                    }
                }
            }
        }
    }

    static class Road implements Comparable<Road> {
        boolean little;
        int v;
        /**
         * 道路长度
         */
        double tired;

        Road(int v, double length) {
            this.v = v;
            this.tired = length;
        }


        @Override
        public int compareTo(Road o) {
            return Double.compare(this.tired, o.tired);
        }
    }

}

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
    /**
     * 速读
     */
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
                //初始化邻接矩阵，斜边为0，默认最大值
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
            ///判断道路类型加入不同的图中，map1是大路，map2是小路
            //注意：有重边，保留最小的
            if (t == 0) {
                if (map1[a][b] == 0 || map1[a][b] > c) {
                    map1[a][b] = c;
                    map1[b][a] = c;
                }
            } else {
                if (map2[a][b] == 0 || map2[a][b] > c) {
                    map2[a][b] = c;
                    map2[b][a] = c;
                }
            }
        }
        //使用Floyd算法将两个图分别计算出最短路径
        floyd(map1);
        floyd(map2);
        dijstra(map1, map2);
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
     * dij算法
     *
     * @param map1 大路
     * @param map2 小路
     */
    private static void dijstra(double[][] map1, double[][] map2) {
        int len = map1.length;
        //标记是否求出最短路径
        boolean[][] solved = new boolean[2][len];
        //记录最短距离
        double[][] dis = new double[2][len];
        //最短距离默认为最大值
        Arrays.fill(dis[0], INF);
        Arrays.fill(dis[1], INF);
        PriorityQueue<Road> queue = new PriorityQueue<>();
        //加入优先队列
        queue.offer(new Road(0, 1, 0));
        queue.offer(new Road(1, 1, 0));
        dis[0][1] = 0;
        dis[1][1] = 0;
        while (!queue.isEmpty()) {
            //取出起始点
            Road cur = queue.poll();
            int u = cur.v;
            int i = cur.type;
            if (!solved[i][u]) {
                solved[i][u] = true;
                //以u点开头查找最短路径
                for (int v = 1; v < len; v++) {
                    if (u == v) {
                        continue;
                    }
                    if (cur.type == 1) {
                        //上一个走了小路这一个只能走大路
                        if (map1[u][v] != INF && !solved[0][v] && map1[u][v] + dis[1][u] < dis[0][v]) {
                            dis[0][v] = map1[u][v] + dis[1][u];
                            queue.offer(new Road(0, v, dis[0][v]));
                        }
                    } else {
                        //上一个走的大路这一段可以用小路也可以用大路来缩短最小距离
                        if (map2[u][v] != INF && !solved[1][v] && map2[u][v] * map2[u][v] + dis[0][u] < dis[1][v]) {
                            dis[1][v] = map2[u][v] * map2[u][v] + dis[0][u];
                            queue.offer(new Road(1, v, dis[1][v]));
                        }
                        if (map1[u][v] != INF && !solved[0][v] && map1[u][v] + dis[0][u] < dis[0][v]) {
                            dis[0][v] = map1[u][v] + dis[0][u];
                            queue.offer(new Road(0, v, dis[0][v]));
                        }
                    }


                }
            }
        }
        //输出结果
        System.out.printf("%.0f", Math.min(dis[0][len - 1], dis[1][len - 1]));
    }

    private static void floyd(double[][] dis) {
        int len = dis.length;
        for (int k = 1; k < len; k++) {
            for (int i = 1; i < len; i++) {
                double t = dis[i][k];
                for (int j = 1; j <= i; j++) {
                    //利用对称性优化
                    double tmp = (t == INF || dis[k][j] == INF) ? INF : (t + dis[k][j]);
                    if (tmp < dis[i][j]) {
                        dis[i][j] = tmp;
                        dis[j][i] = dis[i][j];
                    }
                }
            }
        }
    }

    static class Road implements Comparable<Road> {
        int type;
        int v;
        /**
         * 疲劳度
         */
        double tired;

        Road(int type, int v, double length) {
            this.v = v;
            this.type = type;
            this.tired = length;
        }


        @Override
        public int compareTo(Road o) {
            return Double.compare(this.tired, o.tired);
        }
    }

}

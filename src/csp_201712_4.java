import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

/**
 * @author ajacker
 * 最短路径 迪杰斯特拉
 */
public class csp_201712_4 {
    static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

    public static void main(String[] args) throws IOException {
        in.nextToken();
        int n = (int) in.nval;
        in.nextToken();
        int m = (int) in.nval;
        //载入邻接矩阵存储的图
        Road[][] map = new Road[n + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            in.nextToken();
            int t = (int) in.nval;
            in.nextToken();
            int a = (int) in.nval;
            in.nextToken();
            int b = (int) in.nval;
            in.nextToken();
            double c = in.nval;
            Road road = new Road(t, c);
            map[a][b] = road;
            map[b][a] = road;
        }
        System.out.printf("%.0f", dijkstra(n, map));
    }

    /**
     * dijkstra查找最短路径
     *
     * @param n   点的个数
     * @param map 邻接矩阵存储的图
     * @return 最小疲劳值
     */
    private static double dijkstra(int n, Road[][] map) {
        // 定义一维数组用来存储v1到每个点的最短路径，找到比原来更短的则直接覆盖
        Route[] paths = new Route[n + 1];
        //保存是否访问过
        boolean[] isVisited = new boolean[n + 1];
        //初始化最短路径
        for (int i = 2; i <= n; i++) {
            Route route = new Route();
            if (map[1][i] != null) {
                route.add(map[1][i]);
            } else {
                route.tired = Double.MAX_VALUE;
            }
            paths[i] = route;
        }
        isVisited[1] = true;
        for (int i = 2; i <= n; i++) {
            // 在已经存在的路径中找到一条未被访问且最短的路径
            double min = Double.MAX_VALUE;
            int minIndex = -1;
            for (int j = 2; j <= n; j++) {
                if (!isVisited[j] && paths[j].tired < min) {
                    min = paths[j].tired;
                    minIndex = j;
                }
            }
            //如果找不到就跳过
            if (minIndex == -1) {
                continue;
            }
            isVisited[minIndex] = true;
            // 找到的最短路径节点的可使用边中，判断是否比已经存在的最短路径短，是则进行覆盖
            for (int k = 2; k <= n; k++) {
                Route minRoute = new Route(paths[minIndex]);
                if (!isVisited[k] && (minRoute.addPrev(map[minIndex][k]) < paths[k].tired)) {
                    paths[k] = minRoute.add(map[minIndex][k]);
                }
            }
        }
        return paths[n].tired;
    }

    static class Road {
        /**
         * 道路类型，0大道1小道
         */
        int type;
        /**
         * 道路长度
         */
        double length;

        Road(int type, double length) {
            this.type = type;
            this.length = length;
        }

        @Override
        public String toString() {
            return "" + length;
        }
    }

    static class Route {
        /**
         * 连续小道的长度
         */
        double littleRoad;
        /**
         * 疲劳度
         */
        double tired;

        Route() {
        }

        Route(Route route) {
            this.tired = route.tired;
            this.littleRoad = route.littleRoad;
        }

        /**
         * 添加路径
         *
         * @param road 路径
         * @return 自己
         */
        Route add(Road road) {
            if (road == null) {
                tired = Double.MAX_VALUE;
                return this;
            }
            if (road.type == 0) {
                tired += road.length;
                littleRoad = 0;
            } else {
                //除了本节点外的连续小道长度
                double prevLittleRoad = littleRoad;
                //到目前为止的连续小道长度
                littleRoad += road.length;
                tired = tired - prevLittleRoad * prevLittleRoad + littleRoad * littleRoad;
            }
            return this;
        }

        /**
         * 看看添加路径后的疲劳值（实际上没有添加）
         *
         * @param road 路径
         * @return 假设添加这段路径后的疲劳值
         */
        double addPrev(Road road) {
            if (road == null) {
                return Double.MAX_VALUE;
            }
            double temp = tired;
            if (road.type == 0) {
                temp += road.length;
            } else {
                //除了本节点外的连续小道长度
                double prevLittleRoad = littleRoad;
                //到目前为止的连续小道长度
                double tmp = littleRoad + road.length;
                temp = temp - prevLittleRoad * prevLittleRoad + tmp * tmp;
            }
            return temp;
        }

    }
}

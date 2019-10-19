import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author ajacker
 * 多源点广度优先搜索
 */
public class csp_201409_4 {
    static int n, m, k, d;
    static boolean[][] visited;
    static int[][] customers;
    static int[][] shops;
    static int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    static long cost = 0;
    static int costumerPointAmount;

    public static void main(String[] args) {
        n = InputReader.nextInt();
        m = InputReader.nextInt();
        k = InputReader.nextInt();
        d = InputReader.nextInt();
        visited = new boolean[n + 1][n + 1];
        shops = new int[m][2];
        customers = new int[n + 1][n + 1];
        //读取1-m号商店，如果商店号为0就不是商店
        for (int i = 0; i < m; i++) {
            int x = InputReader.nextInt();
            int y = InputReader.nextInt();
            //商店编号
            shops[i][0] = x;
            shops[i][1] = y;
        }
        //读取k个客户的坐标和订单量
        for (int i = 0; i < k; i++) {
            int x = InputReader.nextInt();
            int y = InputReader.nextInt();
            int amount = InputReader.nextInt();
            //记录有多少点有订单
            if (customers[x][y] == 0) {
                costumerPointAmount++;
            }
            customers[x][y] += amount;
        }
        //读取不能走的区域
        for (int i = 0; i < d; i++) {
            int x = InputReader.nextInt();
            int y = InputReader.nextInt();
            visited[x][y] = true;
        }
        bfs();
        System.out.print(cost);
    }

    /**
     * 多源点广度优先搜索
     */
    private static void bfs() {
        Queue<Node> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            int x = shops[i][0];
            int y = shops[i][1];
            queue.offer(new Node(x, y, 0));
            visited[x][y] = true;
        }
        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            int curX = curNode.x;
            int curY = curNode.y;
            if (customers[curX][curY] != 0) {
                int amount = customers[curX][curY];
                cost += amount * curNode.len;
                customers[curX][curY] = 0;
                //送完餐不继续搜索了
                if (--costumerPointAmount == 0) {
                    return;
                }
            }
            for (int i = 0; i < 4; i++) {
                int nextX = curX + directions[i][0];
                int nextY = curY + directions[i][1];
                if (inRange(nextX, nextY) && !visited[nextX][nextY]) {
                    visited[nextX][nextY] = true;
                    queue.offer(new Node(nextX, nextY, curNode.len + 1));
                }
            }
        }
    }

    private static boolean inRange(int x, int y) {
        return x >= 1 && x <= n && y >= 1 && y <= n;
    }

    static class Node {
        int x, y, len;

        public Node(int x, int y, int len) {
            this.x = x;
            this.y = y;
            this.len = len;
        }
    }

    /**
     * 加速输入类
     */
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

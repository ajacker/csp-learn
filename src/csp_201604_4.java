import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author ajacker
 * 广度优先搜索最短路径
 */
public class csp_201604_4 {
    static int n, m, t;
    static int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    static int[][] start = new int[101][101];
    static int[][] end = new int[101][101];
    static boolean[][][] isVisited = new boolean[101][101][300];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            int r = sc.nextInt();
            int c = sc.nextInt();
            int a = sc.nextInt();
            int b = sc.nextInt();
            start[r][c] = a;
            end[r][c] = b;
        }
        System.out.println(bfs());
    }

    /**
     * 判断是否应该加入队列
     *
     * @param node 节点
     * @return 是否
     */
    public static boolean judge(Node node) {
        //是否在游戏范围内
        boolean inside = node.x >= 1 && node.x <= n && node.y >= 1 && node.y <= m;
        return inside
                && (node.t < start[node.x][node.y] || node.t > end[node.x][node.y])
                && !isVisited[node.x][node.y][node.t];
    }

    /**
     * 广度优先搜索最短路径
     *
     * @return 返回所用时间
     */
    public static int bfs() {
        int result = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(1, 1, 0));
        isVisited[1][1][0] = true;
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.x == n && current.y == m) {
                //走到了目标点n,m，赋值给结果，退出循环
                result = current.t;
                break;
            }
            //尝试上下左右移动,得到新的坐标和时间
            for (int i = 0; i < 4; i++) {
                int newX = current.x + directions[i][0];
                int newY = current.y + directions[i][1];
                int newT = current.t + 1;
                Node newNode = new Node(newX, newY, newT);
                //合法可行的路线加入下一步的搜索
                if (judge(newNode)) {
                    //表示已经检索过了，已经在队列中搜索过了
                    isVisited[newX][newY][newT] = true;
                    queue.offer(newNode);
                }
            }
        }
        return result;
    }
}

class Node {
    int x;
    int y;
    int t;

    Node(int x, int y, int t) {
        this.x = x;
        this.y = y;
        this.t = t;
    }
}
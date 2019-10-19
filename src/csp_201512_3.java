import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class csp_201512_3 {
    static int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    static char[][] map;
    static int m, n, q;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        m = sc.nextInt();
        n = sc.nextInt();
        q = sc.nextInt();
        map = new char[n][m];
        //填充.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                map[i][j] = '.';
            }
        }
        //进行q次操作
        for (int i = 0; i < q; i++) {
            if (sc.nextInt() == 0) {
                int x1, x2, y1, y2;
                x1 = sc.nextInt();
                y1 = sc.nextInt();
                x2 = sc.nextInt();
                y2 = sc.nextInt();
                drawLine(x1, y1, x2, y2);
            } else {
                int x, y;
                char c;
                x = sc.nextInt();
                y = sc.nextInt();
                c = sc.next().charAt(0);
                fillCharacter(x, y, c);
            }
        }
        //输出结果
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 划线
     *
     * @param x1 x1
     * @param y1 y1
     * @param x2 x2
     * @param y2 y2
     */
    public static void drawLine(int x1, int y1, int x2, int y2) {
        int a1 = n - 1 - y1;
        int b1 = x1;
        int a2 = n - 1 - y2;
        int b2 = x2;
        boolean isRow = a1 == a2;
        if (isRow) {
            for (int i = Math.min(b1, b2); i <= Math.max(b1, b2); i++) {
                //如果本来是‘+’，划线的时候小心覆盖掉
                map[a1][i] = map[a1][i] == '|' || map[a1][i] == '+' ? '+' : '-';
            }
        } else {
            for (int i = Math.min(a1, a2); i <= Math.max(a1, a2); i++) {
                map[i][b1] = map[i][b1] == '-' || map[i][b1] == '+' ? '+' : '|';
            }
        }
    }

    /**
     * 填充
     *
     * @param x x
     * @param y y
     * @param c 要填充的字符
     */
    public static void fillCharacter(int x, int y, char c) {
        int a = n - 1 - y;
        int b = x;
        Queue<Integer> pos = new LinkedList<>();
        boolean[][] isVisited = new boolean[n][m];
        pos.offer(a);
        pos.offer(b);
        isVisited[a][b] = true;
        while (!pos.isEmpty()) {
            int ca = pos.poll();
            int cb = pos.poll();
            map[ca][cb] = c;
            for (int i = 0; i < 4; i++) {
                int na = ca + dirs[i][0];
                int nb = cb + dirs[i][1];
                if (na >= 0 && na < n && nb >= 0 && nb < m &&
                        !isVisited[na][nb] &&
                        map[na][nb] != '+' && map[na][nb] != '-' && map[na][nb] != '|') {
                    pos.offer(na);
                    pos.offer(nb);
                    isVisited[na][nb] = true;
                }
            }
        }

    }
}
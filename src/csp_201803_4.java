import java.util.Scanner;

/**
 * @author ajacker
 * 对抗搜索
 */
public class csp_201803_4 {
    private static int[][] map = new int[4][4];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            //读入一个棋盘
            for (int j = 1; j <= 3; j++) {
                for (int k = 1; k <= 3; k++) {
                    map[j][k] = sc.nextInt();
                }
            }
            System.out.println(dfs(1));
        }
    }

    /**
     * 判断棋局是否结束
     *
     * @param id 1:Alice 2:Bob
     * @return 是否胜利
     */
    static boolean judge(int id) {
        //横着的一排和竖着的一排的情况
        for (int i = 1; i <= 3; i++) {
            if ((map[i][1] == id && map[i][2] == id && map[i][3] == id)) {
                return true;
            }
            if (map[1][i] == id && map[2][i] == id && map[3][i] == id) {
                return true;
            }
        }
        if (map[1][1] == id && map[2][2] == id && map[3][3] == id) {
            return true;
        }
        if (map[1][3] == id && map[2][2] == id && map[3][1] == id) {
            return true;
        }
        return false;
    }

    private static int dfs(int id) {
        int space = 0;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (map[i][j] == 0) {
                    //计算出棋盘中空格数
                    space++;
                }
            }
        }
        if ((id == 1) && judge(2)) {
            //因为bob赢了，要加上一个负号；
            return -space - 1;
        }
        if ((id == 2) && judge(1)) {
            return space + 1;
        }
        if (space == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                //对每一个空格进行模拟
                if (map[i][j] == 0) {
                    map[i][j] = id;
                    if (id == 1) {
                        //这个就是为了要满足最优下棋策略的语句，就是alice会选择下一步最优的解,就是极大化策略
                        max = Math.max(max, dfs(2));
                    }
                    if (id == 2) {
                        //同理,但是因为bob分数是负数所以越小越好
                        min = Math.min(min, dfs(1));
                    }
                    //回溯前面的状态
                    map[i][j] = 0;
                }
            }
        }
        if (id == 1) {
            return max;
        } else {
            return min;
        }
    }
}

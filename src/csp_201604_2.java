import java.util.Scanner;

public class csp_201604_2 {
    private static short[][] map = new short[15][10];
    private static short[][] shape = new short[4][4];
    private static int pos;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = sc.nextShort();
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                shape[i][j] = sc.nextShort();
            }
        }
        pos = sc.nextShort();
        merge();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 合并落下
     */
    static void merge() {
        int height = 1;
        //找到刚好能够合并的高度
        while (canMerge(height)) {
            height++;
        }
        height--;
        //进行合并
        for (int i = height - 1; i < height + 3; i++) {
            for (int j = pos - 1; j < pos + 3; j++) {
                int a = i - height + 1;
                int b = j - pos + 1;
                if (i < 15 && map[i][j] == 0) {
                    map[i][j] = shape[a][b];
                }
            }
        }
    }

    /**
     * 判断是否能合并
     *
     * @param height 第一行所在高度
     * @return 是否能够合并
     */
    static boolean canMerge(int height) {
        for (int i = height - 1; i < height + 3; i++) {
            for (int j = pos - 1; j < pos + 3; j++) {
                int a = i - height + 1;
                int b = j - pos + 1;
                if (i < 15) {
                    //未出界情况下，有同为1的部分不能合并
                    if (map[i][j] == 1 && shape[a][b] == 1) {
                        return false;
                    }
                } else {
                    //出界情况下，形状中1的部分不能合并（也就是形状不可出界）
                    if (shape[a][b] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

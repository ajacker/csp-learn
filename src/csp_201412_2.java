import java.util.Scanner;

public class csp_201412_2 {
    static int n;
    static int[][] map;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = sc.nextInt();
            }
        }
        boolean isRightUp = true;
        int i = 0, j = 0;
        while (true) {
            System.out.print(map[i][j] + " ");
            if (i == n - 1 && j == n - 1) {
                break;
            }
            if (isRightUp) {
                //右上的过程中
                if (j == n - 1) {
                    //到了右边界，向下
                    i++;
                    isRightUp = false;
                } else if (i == 0) {
                    //到了上边界，向右
                    j++;
                    isRightUp = false;
                } else {
                    i--;
                    j++;
                }
            } else {
                //左下的过程中
                if (i == n - 1) {
                    //到了下边界，向右
                    j++;
                    isRightUp = true;
                } else if (j == 0) {
                    //到了左边界，向下
                    i++;
                    isRightUp = true;
                } else {
                    i++;
                    j--;
                }

            }
        }
    }
}

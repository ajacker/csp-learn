import java.util.Arrays;
import java.util.Scanner;

public class csp_201909_2 {
    private static int N, M;
    private static boolean[] isDropped;
    private static int[] appleTree;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        appleTree = new int[N + 1];
        isDropped = new boolean[N + 1];
        //以防万一-1代表没初始化
        Arrays.fill(appleTree, -1);
        for (int i = 1; i <= N; i++) {
            //进行M次操作
            M = sc.nextInt();
            for (int j = 0; j < M; j++) {
                int num = sc.nextInt();
                if (num > 0) {
                    //核对操作
                    if (appleTree[i] != -1) {
                        //如果非初始化的时候发生不一致就发生了掉落
                        isDropped[i] = appleTree[i] != num;
                    }
                    appleTree[i] = num;
                } else {
                    //疏果操作
                    appleTree[i] += num;
                }
            }
        }
        long appleLeft = 0;
        for (int i = 1; i <= N; i++) {
            appleLeft += appleTree[i];
        }
        int sumDrop = 0, threeDrop = 0;
        for (int i = 1; i <= N; i++) {
            int prev = i - 1 == 0 ? N : i - 1;
            int next = i + 1 == N + 1 ? 1 : i + 1;
            if (isDropped[i]) {
                sumDrop++;
                if (isDropped[prev] && isDropped[next]) {
                    threeDrop++;
                }
            }
        }
        System.out.print(appleLeft + " " + sumDrop + " " + threeDrop);
    }
}

import java.util.Scanner;

public class csp_201909_1 {
    static int N, M;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //苹果树的棵树
        N = sc.nextInt();
        //疏果操作
        M = sc.nextInt();
        long T = 0;
        int k = 0;
        int P = -1;
        for (int i = 1; i <= N; i++) {
            int currentApple = sc.nextInt();
            int startApple = currentApple;
            for (int j = 1; j <= M; j++) {
                currentApple += sc.nextInt();
            }
            T += currentApple;
            int currentRemove = startApple - currentApple;
            if (currentRemove > P) {
                P = currentRemove;
                k = i;
            }
        }
        System.out.printf("%d %d %d", T, k, P);
    }
}

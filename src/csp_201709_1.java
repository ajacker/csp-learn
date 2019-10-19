import java.util.Scanner;

/**
 * @author ajacker
 * @date 18-9-9 下午9:33
 */
public class csp_201709_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int a = N / 50;//买五瓶送两瓶
        int b = (N - 50 * a) / 30;//剩下的买三瓶送一瓶
        int c = (N - 50 * a - 30 * b) / 10;//剩下的单独买
        System.out.println((5 + 2) * a + (3 + 1) * b + c);
    }
}

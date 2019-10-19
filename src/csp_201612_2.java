import java.util.Scanner;

/**
 * @author ajacker
 * @date 18-9-12 下午12:42
 */
public class csp_201612_2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        int i;
        int M[] = new int[]{0, 3500, 5000, 8000, 12500, 38500, 58500, 83500, Integer.MAX_VALUE};//分界点税前工资
        int S[] = new int[]{0, 3500, 4955, 7655, 11255, 30755, 44755, 61005, Integer.MAX_VALUE};//分界点税后工资
        double f[] = new double[]{1, 0.97, 0.9, 0.8, 0.75, 0.7, 0.65, 0.55, 0};
        for (i = 0; i < 9; i++) {
            if (S[i] >= T) {
                break;
            }
        }
        double res = M[i - 1] + (T - S[i - 1]) / f[i - 1];
        System.out.println((int) res);
    }
}

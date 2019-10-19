import java.util.Scanner;

public class csp_201412_1 {
    static int[] data = new int[1001];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            int current = sc.nextInt();
            System.out.print(++data[current] + " ");
        }
    }
}

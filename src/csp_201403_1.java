import java.util.Scanner;

public class csp_201403_1 {
    static boolean[] ints = new boolean[2002];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int count = 0;
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            ints[1000 + x] = true;
            if (ints[1000 - x]) {
                count++;
            }
        }
        System.out.println(count);
    }
}

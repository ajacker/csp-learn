import java.util.Scanner;

public class csp_201604_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int status = 0;
        int n = sc.nextInt();
        int prev = sc.nextInt();
        int count = 0;
        for (int i = 0; i < n - 1; i++) {
            int current = sc.nextInt();
            if (prev > current) {
                if (status == 1) {
                    count++;
                }
                status = -1;
            } else {
                if (status == -1) {
                    count++;
                }
                status = 1;
            }
            prev = current;
        }
        System.out.println(count);
    }
}

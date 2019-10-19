import java.util.Scanner;

public class csp_201509_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int last = -1, count = 0;
        for (int i = 0; i < n; i++) {
            int current = sc.nextInt();
            if (current != last) {
                last = current;
                count++;
            }
        }
        System.out.println(count);
    }
}

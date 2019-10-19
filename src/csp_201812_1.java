import java.util.Scanner;

public class csp_201812_1 {
    private static int r, y, g;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        r = sc.nextInt();
        y = sc.nextInt();
        g = sc.nextInt();
        int n = sc.nextInt();
        int result = 0;
        for (int i = 0; i < n; i++) {
            result += getTime(sc.nextInt(), sc.nextInt());
        }
        System.out.println(result);

    }

    public static int getTime(int status, int time) {
        switch (status) {
            case 0:
            case 1:
                return time;
            case 2:
                return time + r;
            default:
                return 0;
        }
    }
}

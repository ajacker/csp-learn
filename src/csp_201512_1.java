import java.util.Scanner;

public class csp_201512_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String n = sc.next();
        int sum = 0;
        for (char c : n.toCharArray()) {
            int current = Integer.parseInt(c + "");
            sum += current;
        }
        System.out.println(sum);
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class csp_201712_1 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(sc.nextInt());
        }
        int min = Integer.MAX_VALUE;
        for (Integer m : list) {
            ArrayList<Integer> list2 = new ArrayList<>(list);
            list2.remove(m);
            for (int k : list2) {
                min = Integer.min(Math.abs(m - k), min);
            }
        }
        System.out.println(min);
    }
}

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author ajacker
 * @date 18-9-12 下午12:16
 */
public class csp_201612_1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nums.add(sc.nextInt());
        }
        for (int num : nums) {
            int b = 0, s = 0;//大的小的
            for (int i : nums) {
                if (num < i) b++;//比他大的
                else if (num > i) s++;//比他小的
            }
            if (b == s) {
                System.out.println(num);
                return;
            }
        }
        System.out.println(-1);
    }
}

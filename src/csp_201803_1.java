import java.util.ArrayList;
import java.util.Scanner;

public class csp_201803_1 {
    public static void main(String[] args) {
        ArrayList<Integer> in = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNext("0")) {
            in.add(sc.nextInt());
        }
        in.add(0);
        //count记录连续跳到中心次数
        int score = 0, count = 0;
        for (int n = 0; n < in.size(); n++) {
            switch (in.get(n)) {
                case 0:
                    score += 0;
                    break;
                case 1:
                    count = 0;
                    score += 1;
                    break;
                case 2:
                    count++;
                    if (n == 0 || in.get(n - 1) == 1) {
                        score += 2;
                    } else {
                        score += (2 * count);
                    }
                    break;
                default:
                    break;

            }
        }
        System.out.println(score);
    }
}

import java.util.Scanner;

/**
 * @author ajacker
 * @date 2019/11/4 10:41
 */
public class csp_201312_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //矩形的数量
        int n = sc.nextInt();
        int[] height = new int[n + 1];
        //读入高度
        for (int i = 1; i <= n; i++) {
            height[i] = sc.nextInt();
        }
        //计算最大值
        int max = 0;
        for (int i = 1; i <= n; i++) {
            int cur = height[i];
            //可连续并入矩形的数量
            int amount = 1;
            //往后找
            boolean flag = true;
            for (int j = i + 1; j <= n; j++) {
                if (flag && cur <= height[j]) {
                    amount++;
                } else {
                    flag = false;
                }
            }
            //往前找
            flag = true;
            for (int j = i - 1; j >= 1; j--) {
                if (flag && cur <= height[j]) {
                    amount++;
                } else {
                    flag = false;
                }
            }
            //更新最大值
            max = Integer.max(max, amount * cur);
        }
        //输出结果
        System.out.println(max);
    }
}

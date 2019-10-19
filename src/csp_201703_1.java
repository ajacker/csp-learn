import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author ajacker
 * @date 18-9-11 下午8:17
 */
public class csp_201703_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();//n块蛋糕
        int k = sc.nextInt();//至少重量为k
        ArrayList<cake> cakes = new ArrayList<>();
        for (int i = 0; i < n; i++) {//读入每个蛋糕的重量
            cakes.add(new cake(sc.nextInt()));
        }
        int num = 0;//几个小朋友分到蛋糕
        while (cakes.size() != 0) {
            int now = 0;//当前这个小朋友分到的蛋糕
            while (now < k) {
                if (cakes.size() == 0) break;//没有蛋糕了就跳出循环
                now += cakes.get(0).weight;//分蛋糕
                cakes.remove(0);//这个蛋糕已经被分了
            }
            num++;//多了一个小朋友分到了蛋糕
        }
        System.out.println(num);
    }

    private static class cake {
        int weight;

        cake(int weight) {
            this.weight = weight;
        }
    }
}

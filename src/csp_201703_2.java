import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author ajacker
 * @date 18-9-11 下午8:53
 */
public class csp_201703_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();//学生的数量
        int m = sc.nextInt();//调整的次数
        ArrayList<Integer> students = new ArrayList<>();//学生队列
        ArrayList<Sort> sorts = new ArrayList<>();//排序规则
        for (int i = 0; i < n; i++)
            students.add(i + 1);
        for (int i = 0; i < m; i++)
            sorts.add(new Sort(sc.nextInt(), sc.nextInt()));
        for (Sort sort : sorts) {
            int pos = students.indexOf(sort.id);//出列学生的索引
            students.remove(pos);//出列
            students.add(pos + sort.move, sort.id);
        }
        for (int i : students) {
            System.out.print(i + " ");
        }
    }

    private static class Sort {
        int id;
        int move;

        Sort(int id, int move) {
            this.id = id;
            this.move = move;
        }
    }
}

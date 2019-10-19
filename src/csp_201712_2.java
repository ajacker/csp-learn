import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class csp_201712_2 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new Student(i + 1));
        }
        while (true) {
            ListIterator<Student> it = list.listIterator();
            if (list.size() == 1) {//剩下最后一个了获胜
                System.out.println(list.get(0).id);
                Student.number = 0;
                break;
            }
            while (it.hasNext()) {
                if (it.next().say(k)) it.remove();
                if (list.size() == 1) {//剩下最后一个了跳出循环
                    break;
                }
            }
        }
    }


    private static class Student {
        static int number = 0;//报的数
        int id;//小朋友序号

        Student(int id) {
            this.id = id;
        }

        public boolean say(int k) {//报数
            /**
             * @author ajacker
             * @description: 返回是否淘汰
             * @param pre 前面一个小朋友报的数
             * @param k k的值
             * @return boolean
             * @date 2018-09-09 18:01
             */
            number++;
            if ((float) number / k == number / k || number % 10 == k) return true;
            else return false;
        }
    }
}

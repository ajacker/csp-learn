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
            //剩下最后一个了获胜
            if (list.size() == 1) {
                System.out.println(list.get(0).id);
                Student.number = 0;
                break;
            }
            while (it.hasNext()) {
                if (it.next().say(k)) it.remove();
                //剩下最后一个了跳出循环
                if (list.size() == 1) {
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

        /**
         * @param k k的值
         * @return boolean
         * @author ajacker
         * @description: 返回是否淘汰
         * @date 2018-09-09 18:01
         */
        public boolean say(int k) {//报数
            number++;
            if ((float) number / k == number / k || number % 10 == k) return true;
            else return false;
        }
    }
}

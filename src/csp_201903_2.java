import java.util.Scanner;
import java.util.Stack;

public class csp_201903_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            String temp = judge(sc.next()) ? "Yes" : "No";
            System.out.println(temp);
        }
    }

    private static boolean judge(String str) {
        Stack<Integer> nums = new Stack<>();
        Stack<Character> chars = new Stack<>();
        for (int i = 0; i < str.toCharArray().length; i++) {
            char c = str.charAt(i);
            switch (c) {
                case '+':
                case '-':
                    if (!chars.empty()) {
                        if (chars.peek() == '+') {
                            int b = nums.pop();
                            int a = nums.pop();
                            char x = chars.pop();
                            nums.push(calculate(a, b, x));
                        } else if (chars.peek() == '-') {
                            int b = nums.pop();
                            int a = nums.pop();
                            char x = chars.pop();
                            nums.push(calculate(a, b, x));
                        }
                    }
                case 'x':
                case '/':
                    if (!chars.empty()) {
                        if (chars.peek() == 'x') {
                            int b = nums.pop();
                            int a = nums.pop();
                            char x = chars.pop();
                            nums.push(calculate(a, b, x));
                        } else if (chars.peek() == '/') {
                            int b = nums.pop();
                            int a = nums.pop();
                            char x = chars.pop();
                            nums.push(calculate(a, b, x));
                        }
                    }
                    chars.push(c);
                    break;
                default:
                    nums.push(c - '0');
                    break;
            }
        }
        while (!chars.empty()) {
            int b = nums.pop();
            int a = nums.pop();
            char c = chars.pop();
            nums.push(calculate(a, b, c));
        }
        return nums.pop() == 24;
    }

    private static int calculate(int a, int b, char c) {
        switch (c) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case 'x':
                return a * b;
            case '/':
                return a / b;
            default:
                return 0;
        }
    }
}

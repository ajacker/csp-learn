import java.util.Scanner;

public class csp_201312_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        char[] array = str.toCharArray();
        int code = 0;
        for (int i = 0, n = 1; i < array.length - 1; i++) {
            char c = array[i];
            if (Character.isDigit(c)) {
                code = (code + (n++) * (c - '0')) % 11;
            }
        }
        array[array.length - 1] = code == 10 ? 'X' : (char) ('0' + code);
        System.out.println(str.charAt(array.length - 1) == array[array.length - 1] ? "Right" : new String(array));
    }
}

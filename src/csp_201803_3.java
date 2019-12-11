import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class csp_201803_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int n = scanner.nextInt();
        final int m = scanner.nextInt();
        String[][] rule = new String[n][2];
        for (int i = 0; i < n; ++i) {
            rule[i][0] = scanner.next();
            rule[i][1] = scanner.next();
        }
        String[] ur = new String[m];
        for (int i = 0; i < m; ++i) {
            ur[i] = scanner.next();
        }

        //转化为正则表达式
        for (int i = 0; i < n; ++i) {
            rule[i][0] = rule[i][0].replaceAll("<int>", "(\\\\d+)");
            rule[i][0] = rule[i][0].replaceAll("<str>", "(\\\\w+)");
            rule[i][0] = rule[i][0].replaceAll("<path>", "(.+)");
            rule[i][0] = "^" + rule[i][0] + "$";
        }
        boolean flag;
        Pattern rPattern;
        Matcher matcher;
        for (int i = 0; i < m; ++i) {
            flag = false;
            for (int j = 0; j < n; ++j) {
                rPattern = Pattern.compile(rule[j][0]);
                matcher = rPattern.matcher(ur[i]);
                if (matcher.find()) {
                    System.out.print(rule[j][1] + " ");
                    for (int k = 1; k <= matcher.groupCount(); ++k) {
                        if (isNum(matcher.group(k))) {
                            System.out.print(Integer.parseInt(matcher.group(k)) + " ");
                        } else {
                            System.out.print(matcher.group(k) + " ");
                        }
                    }
                    System.out.println();
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                System.out.println("404");
            }
        }

    }

    public static boolean isNum(String string) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }
}
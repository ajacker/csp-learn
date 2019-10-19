import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class csp_201803_3 {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        ArrayList<Rule> rules = new ArrayList<>();//规则列表
        ArrayList<String> strs = new ArrayList<>();//待匹配文本列表
        for (int i = 0; i < n; i++) {
            rules.add(new Rule(sc.next(), sc.next()));//添加规则
        }
        for (int i = 0; i < m; i++) {
            strs.add(sc.next());//添加待匹配文本
        }
        for (String str : strs) {
            int i = 0;//计数匹配失败次数
            for (Rule rule : rules) {//用各个规则来匹配当前文本
                ArrayList<String> results = rule.compare(str);//匹配
                if (results.size() == 0) {//匹配失败
                    i++;
                    if (i == n) System.out.println("404");//匹配失败n次代表完全匹配失败，输出404
                    continue;
                }
                System.out.print(rule.name);//输出规则
                for (String result : results) {
                    System.out.print(" " + result);
                }
                System.out.println();
            }
        }
        sc.close();

    }

    public static class Rule {
        String regex;
        String name;

        Rule(String regex, String name) {
            regex = regex.replaceAll("<int>", "(\\\\d+)");//转化成正则表达式
            regex = regex.replaceAll("<str>", "([^ /]+)");//转化成正则表达式
            regex = regex.replaceAll("<path>", "(\\\\S+)");//转化成正则表达式
            //if (!regex.endsWith("/")) regex=regex+"/";
            this.regex = regex;
            //System.out.println("正则："+regex);
            this.name = name;
        }

        public ArrayList<String> compare(String str) {
            //if (!str.endsWith("/")) str=str+"/";
            Pattern pt = Pattern.compile(regex);
            Matcher matcher = pt.matcher(str);
            ArrayList<String> list = new ArrayList<>();
            if (matcher.matches()) {
                for (int i = 0; i < matcher.groupCount(); i++) {
                    String result = matcher.group(i + 1);//记录参数
                    Pattern p = Pattern.compile("\\d+");
                    Matcher match = p.matcher(result);
                    if (match.matches()) {
                        while (result.startsWith("0"))
                            result = result.replace("0", "");//去掉前导0
                    }
                    list.add(result);
                }
            }
            return list;
        }
    }
}

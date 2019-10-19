import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class csp_201509_3 {
    public static void main(String[] args) {
        Map<String, String> replaces = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        StringBuilder content = new StringBuilder();
        sc.nextLine();
        for (int i = 0; i < m; i++) {
            content.append(sc.nextLine()).append('\n');
        }
        //将所有替换作为键值对加入map
        for (int i = 0; i < n; i++) {
            String[] line = sc.nextLine().split("\"");
            String var = line[0].trim();
            String ct = line[1];
            var = "{{ " + var + " }}";
            ct = ct.replaceAll("\"", "");
            replaces.put(var, ct);
        }
        //顺次替换
        int lastIndex = 0, indexStart, indexEnd;
        while ((indexStart = content.indexOf("{{ ", lastIndex)) > -1) {
            //查找变量格式的字符串的开头和结尾
            indexEnd = content.indexOf(" }}", lastIndex) + " }}".length();
            //得到变量形式的字符串（也就是要替换的字符串）
            String key = content.substring(indexStart, indexEnd);
            //得到要替换为的字符串，如果不存在就替换为空串
            String replace = replaces.getOrDefault(key, "");
            //进行替换操作
            content.replace(indexStart, indexEnd, replace);
            //下一次查找从替换完了的字符串末尾开始
            lastIndex = indexStart + replace.length();
        }
        //输出结果
        System.out.print(content.toString());
    }

}

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ajacker
 * @date 18-9-13 下午12:37
 */
public class csp_201709_3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //JSON数据的行数
        int n = sc.nextInt();
        //查询的个数
        int m = sc.nextInt();
        //上次没有读入换行符
        sc.nextLine();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(sc.nextLine());
        }
        //最高级对象
        JSONObject originObject = new JSONObject(sb.toString().trim().replaceAll(" ", ""));
        for (int i = 0; i < m; i++) {
            originObject.find(sc.nextLine());
        }
    }

    private static class JSONObject {
        ArrayList<JSONProperty> properties;

        JSONObject(String content) {
            //System.out.println(content);
            properties = new ArrayList<>();
            if (content.startsWith("{") && content.endsWith("}"))
                content = content.substring(1, content.length() - 1);//去掉包裹的括号
            //System.out.println(content);
            String regex = "(?:\"([^\\{\\}:,\\s]+)\":\"([^\\{\\}:,\\s]+)\"\\s*)|(?:\"([^\\{\\}:,\\s]+)\":\\{(\\S+)\\}\\s*)";
            //System.out.println(regex);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String s1, s2, s3, s4;
                s1 = matcher.group(1);
                s2 = matcher.group(2);
                s3 = matcher.group(3);
                s4 = matcher.group(4);
                //System.out.println(s1 + "|" + s2 + "|" + s3 + "|" + s4);
                if (s1 != null) properties.add(new JSONProperty("STRING"
                        , s1.replaceAll("\\\\\"", "\"").replaceAll("\\\\\\\\", "\\\\")
                        , s2.replaceAll("\\\\\"", "\"").replaceAll("\\\\\\\\", "\\\\")));
                else if (s3 != null) properties.add(new JSONProperty("OBJECT"
                        , s3.replaceAll("\\\\\"", "\"").replaceAll("\\\\\\\\", "\\\\")
                        , new JSONObject(s4)));
            }
        }

        public void find(String what) {
            if (!what.contains(".")) {//没有多级
                int fail = 0;
                for (JSONProperty pro : properties) {
                    if (what.equals(pro.name) && pro.content instanceof String)
                        System.out.println(pro.type + " " + pro.content);
                    else if (what.equals(pro.name) && pro.content instanceof JSONObject) System.out.println(pro.type);
                    else fail++;
                }
                if (fail == properties.size()) System.out.println("NOTEXIST");//找不到
            } else {
                String[] strs = what.split("\\.", 2);
                String topObject = strs[0];//返回两个数组
                String next = strs[1];//第一级往下
                int fail = 0;
                for (JSONProperty pro : properties) {
                    if (topObject.equals(pro.name) && pro.content instanceof JSONObject)
                        ((JSONObject) pro.content).find(next);//向下级查找
                    else fail++;
                }
                if (fail == properties.size()) System.out.println("NOTEXIST");//找不到
            }
        }

    }

    private static class JSONProperty {
        String type;
        String name;
        Object content;

        public JSONProperty(String type, String name, Object content) {
            this.type = type;
            this.name = name;
            this.content = content;
        }
    }
}

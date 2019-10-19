import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author ajacker
 * @date 18-9-14 下午5:03
 */
public class csp_201703_3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = null;
        StringBuilder par = new StringBuilder();
        boolean isParagraph = false, isList = false;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.isEmpty()) {//到了结尾或者遇到空行
                if (isParagraph) {//段落结束
                    par.append("</p>\n");
                    isParagraph = false;
                } else if (isList) {//list区块结束
                    par.append("</ul>\n");
                    isList = false;
                }
            } else {
                //line = line.replaceAll(" ", "");//去除空格
                //System.out.println("内容:" + line);
                if (line.startsWith("#")) par.append(title(line));//tile区块
                else if (line.startsWith("*") && !isList) {//遇到list区块
                    isList = true;
                    par.append("<ul>\n");
                    par.append("<li>").append(inLine(line.replaceAll("\\*", "").trim())).append("</li>\n");
                } else if (line.startsWith("*"))
                    par.append("<li>").append(inLine(line.replaceAll("\\*", "").trim())).append("</li>\n");
                else if (!isParagraph) {//否则是段落区块的开头
                    isParagraph = true;
                    par.append("<p>").append(inLine(line));
                } else par.append("\n").append(inLine(line));//否则直接添加段落内容
            }
        }
        //输入结束以后封闭段落
        if (isParagraph) {//段落结束
            par.append("</p>\n");
        } else if (isList) {//list区块结束
            par.append("</ul>\n");
        }
        System.out.println(par.toString());
        sc.close();


    }

    public static String title(String line) {
        int level = 0;
        for (char c : line.toCharArray()) {
            if (c == '#') level++;//几个‘#’就是几级标题
        }
        return "<h" + level + ">" + line.replaceAll("#", "").trim() + "</h" + level + ">" + "\n";
    }

    public static String inLine(String content) {
        int count = 0;//计数'_'出现的次数
        ArrayList<String> links = new ArrayList<>();
        int textstart = -1, textend = -1, linkstart = -1, linkend = -1;
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            switch (c) {
                case '_':
                    count++;
                    if (count % 2 != 0) content = content.replaceFirst("_", "<em>"); //奇数个就是强调的开头
                    else content = content.replaceFirst("_", "</em>"); //偶数个就是强调的结尾

                    break;
                case '[':
                    textstart = i;
                    break;
                case ']':
                    textend = i;
                    break;
                case '(':
                    linkstart = i;
                    break;
                case ')':
                    linkend = i;
                    break;
            }
            if (linkend != -1) {//读入结束一个超链接
                String text = content.substring(textstart + 1, textend);//分析text
                String link = content.substring(linkstart + 1, linkend);//分析link
                content = content.replace(content.substring(textstart, linkend + 1), "<a href=\"" + link + "\">" + text + "</a>");//替换
                textstart = textend = linkstart = linkend = -1;//替换完成等待处理下一个
            }
        }
        return content;
    }
}

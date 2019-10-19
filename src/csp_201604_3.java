import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class csp_201604_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        Path p = new Path(sc.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.println(p.formatPath(sc.nextLine()));
        }
    }
}

class Path {
    /**
     * 代表当前路径
     */
    private Deque<String> path = new LinkedList<>();

    Path(String currentPath) {
        String[] paths = currentPath.split("/");
        path.addAll(Arrays.asList(paths));
    }

    /**
     * 标准化路径
     *
     * @param p 非标准路径
     * @return 标准路径
     */
    String formatPath(String p) {
        //将连续的/替换成一个/
        while (p.contains("//")) {
            p = p.replaceAll("//", "/");
        }
        Deque<String> temp = new LinkedList<>();
        //判断是否有从相对部分开始
        if (!p.startsWith("/")) {
            temp.addAll(path);
        }
        String[] paths = p.split("/");
        for (String s : paths) {
            if ("..".equals(s)) {
                temp.pollLast();
            } else if (!".".equals(s)) {
                temp.offerLast(s);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : temp) {
            if (!s.isEmpty()) {
                sb.append("/").append(s);
            }
        }
        return temp.size() == 0 ? "/" : sb.toString();
    }
}
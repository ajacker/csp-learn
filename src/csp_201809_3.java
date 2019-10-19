import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class csp_201809_3 {
    public static void main(String[] args) {
        int n = InputReader.nextInt();
        int m = InputReader.nextInt();
        //上一层的元素
        Element father = null;
        //上一行的元素
        Element top = null;
        for (int i = 1; i <= n; i++) {
            //当前行的元素
            Element current = new Element(InputReader.nextLine(), i);
            if (father == null) {
                //如果当前行是第一个元素
                father = current;
                top = current;
            } else {
                //找到合适的位置插入子元素
                while (father.getTrimNum() >= current.getTrimNum()) {
                    father = father.getFather();
                }
                //前一行的层级大于这一行，直接添加子元素
                father.addChild(current);
                father = current;
            }
        }
        assert top != null;
        for (int i = 0; i < m; i++) {
            List<Element> list = top.select(new Selector(InputReader.nextLine()));
            StringBuilder sb = new StringBuilder();
            sb.append(list.size());
            list.forEach(e -> sb.append(" ").append(e.getLineNum()));
            System.out.println(sb.toString());
        }
    }

    //快速输入辅助类
    static class InputReader {
        private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private static StringTokenizer tokenizer = null;

        public static String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public static int nextInt() {
            return Integer.parseInt(next());
        }

        public static String nextLine() {
            try {
                return reader.readLine();
            } catch (IOException ignored) {
            }
            return "";
        }
    }
}

class Selector {
    Type type;

    ;
    String content;

    Selector(String str) {
        if (str.startsWith("#")) {
            this.type = Type.id;
            this.content = str.substring(1, str.length());
        } else {
            if (str.contains(" ")) {
                this.type = Type.child;
            } else {
                this.type = Type.tag;
            }
            this.content = str;
        }
    }

    public String getContent() {
        return content;
    }

    public Type getType() {
        return type;
    }

    enum Type {
        //标签类型
        tag,
        //id类型
        id,
        //后代类型
        child
    }
}

class Element {
    /**
     * 标签
     */
    private String name;
    /**
     * id 没有为null
     */
    private String id;
    /**
     * 前面有几个。
     */
    private int trimNum;
    /**
     * 行号
     */
    private int lineNum;
    /**
     * 父元素 没有为null
     */
    private Element father;
    /**
     * 子元素
     */
    private List<Element> child;

    /**
     * 构造元素
     *
     * @param str     要解析的
     * @param lineNum 行号
     */
    Element(String str, int lineNum) {
        this.id = null;
        this.lineNum = lineNum;
        this.child = new LinkedList<>();
        this.trimNum = str.length() - str.replaceAll("\\.", "").length();
        str = str.replaceAll("\\.", "");
        //是否有id信息
        if (str.contains("#")) {
            this.name = str.split("#")[0].trim();
            this.id = str.split("#")[1];
        } else {
            this.name = str.trim();
        }
    }

    /**
     * 添加子元素
     *
     * @param e 元素
     */
    public void addChild(Element e) {
        this.child.add(e);
        e.setFather(this);
    }

    /**
     * 选择元素
     *
     * @param selector 选择器
     * @return 选择元素列表
     */
    public List<Element> select(Selector selector) {
        List<Element> result = new LinkedList<>();
        Queue<Element> queue = new LinkedList<>();
        queue.offer(this);
        switch (selector.getType()) {
            case tag:
                while (!queue.isEmpty()) {
                    Element current = queue.poll();
                    if (current.getName().equalsIgnoreCase(selector.getContent())) {
                        result.add(current);
                    }
                    current.getChild().forEach(queue::offer);
                }
                break;
            case id:
                while (!queue.isEmpty()) {
                    Element current = queue.poll();
                    if (current.getId() != null && current.getId().equals(selector.getContent())) {
                        result.add(current);
                    }
                    current.getChild().forEach(queue::offer);
                }
                break;
            case child:
                String[] aims = selector.getContent().split(" ");
                List<Element> temp = new LinkedList<>();
                Set<Element> helper = new HashSet<>();
                temp.add(this);
                for (String currentAim : aims) {
                    result.clear();
                    helper.clear();
                    temp.forEach(e -> e.select(new Selector(currentAim)).stream().filter(helper::add).forEach(result::add));
                    temp.clear();
                    result.forEach(e -> temp.addAll(e.getChild()));
                }
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Element element = (Element) o;
        return lineNum == element.lineNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNum);
    }

    public Element getFather() {
        return father;
    }

    public void setFather(Element father) {
        this.father = father;
    }

    public int getTrimNum() {
        return trimNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<Element> getChild() {
        return child;
    }
}
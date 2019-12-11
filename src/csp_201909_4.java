import java.io.*;
import java.util.*;

/**
 * @author ajacker
 * @date 2019/11/8 11:44
 */
public class csp_201909_4 {
    /**
     * 自排序的set
     */
    static TreeSet<Info> scores;
    /**
     * 维护一个set集合，存在于其中的被标记为删除
     */
    static HashSet<Integer>[] deleted;
    /**
     * 加速输出
     */
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) {
        int m = InputReader.nextInt();
        int n = InputReader.nextInt();
        scores = new TreeSet<>();
        deleted = new HashSet[m];
        for (int i = 0; i < m; i++) {
            deleted[i] = new HashSet<>();
        }
        for (int i = 0; i < n; i++) {
            int id = InputReader.nextInt();
            int score = InputReader.nextInt();
            for (int j = 0; j < m; j++) {
                scores.add(new Info(j, id, score));
            }
        }
        //操作数
        int op = InputReader.nextInt();
        for (int i = 0; i < op; i++) {
            int o = InputReader.nextInt();
            int type;
            int commodity;
            int score;
            switch (o) {
                case 1:
                    //添加商品
                    type = InputReader.nextInt();
                    commodity = InputReader.nextInt();
                    score = InputReader.nextInt();
                    scores.add(new Info(type, commodity, score));
                    deleted[type].remove(commodity);

                    break;
                case 2:
                    //删除商品
                    type = InputReader.nextInt();
                    commodity = InputReader.nextInt();
                    deleted[type].add(commodity);
                    break;
                case 3:
                    //不超过K个
                    int K = InputReader.nextInt();
                    //minAmount就是最多每个类型的有几个
                    int[] minAmount = new int[m];
                    for (int j = 0; j < m; j++) {
                        minAmount[j] = InputReader.nextInt();
                    }
                    //维护了一个选出来的结果
                    ArrayList<Integer>[] selected = new ArrayList[m];
                    for (int j = 0; j < m; j++) {
                        selected[j] = new ArrayList<>();
                    }
                    Iterator<Info> it = scores.iterator();
                    while (it.hasNext() && K > 0) {
                        Info cur = it.next();
                        //没被删除且此类可选
                        if (!deleted[cur.type].contains(cur.id) && minAmount[cur.type] > 0) {
                            selected[cur.type].add(cur.id);
                            //减少此类可选数量
                            minAmount[cur.type]--;
                            //减少总可选数量
                            K--;
                        }
                    }
                    //输出每一类的选择结果
                    for (int j = 0; j < m; j++) {
                        if (selected[j].isEmpty()) {
                            out.println("-1");
                        } else {
                            for (Integer v : selected[j]) {
                                out.print(v);
                                out.print(" ");
                            }
                            out.println();
                        }
                    }

                    break;
                default:
                    break;
            }
        }
        //记得刷新缓冲区，才会输出
        out.flush();
        out.close();
    }

    static class Info implements Comparable<Info> {
        int type;
        int id;
        int score;

        public Info(int type, int id, int score) {
            this.type = type;
            this.id = id;
            this.score = score;
        }

        @Override
        public int compareTo(Info o) {
            if (o.score == this.score) {
                if (o.type == this.type) {
                    return this.id - o.id;
                } else {
                    return this.type - o.type;
                }
            } else {
                return o.score - this.score;
            }
        }
    }

    /**
     * io优化：加速读入
     */
    static class InputReader {
        private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private static StringTokenizer tokenizer = null;

        static String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        static int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

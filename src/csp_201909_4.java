import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ajacker
 * @date 2019/11/8 11:44
 */
public class csp_201909_4 {
    /**
     * 分数，第一维度是商品类型编号，第二维度是这一类型的商品
     * 初始都为0分
     */
    static ArrayList<ArrayList<Info>> scores;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        scores = new ArrayList<>(m);
        //初始化数据
        IntStream.range(0, m).mapToObj(ArrayList<Info>::new).forEach(scores::add);
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            int score = scanner.nextInt();
            for (int j = 0; j < m; j++) {
                scores.get(j).add(new Info(j, id, score));
            }
        }
        //操作数
        int op = scanner.nextInt();
        for (int i = 0; i < op; i++) {
            int o = scanner.nextInt();
            int type;
            int commodity;
            int score;
            switch (o) {
                case 1:
                    //添加商品
                    type = scanner.nextInt();
                    commodity = scanner.nextInt();
                    score = scanner.nextInt();
                    scores.get(type).add(new Info(type, commodity, score));
                    break;
                case 2:
                    type = scanner.nextInt();
                    commodity = scanner.nextInt();
                    //删除商品
                    scores.get(type).remove(new Info(commodity));
                    break;
                case 3:
                    //不超过K个
                    int K = scanner.nextInt();
                    int[] minAmount = new int[m];
                    for (int j = 0; j < m; j++) {
                        minAmount[j] = scanner.nextInt();
                    }
                    ArrayList[] selected = new ArrayList[m];
                    for (int j = 0; j < m; j++) {
                        selected[j] = new ArrayList<Info>();
                    }
                    Map<Integer, List<Info>> collect = IntStream.range(0, m).boxed().flatMap(j -> scores.get(j).stream().sorted().limit(minAmount[j]))
                            .sorted().limit(K).collect(Collectors.groupingBy(p -> p.type));
                    for (int b = 0; b < m; b++) {
                        List<Info> infos = collect.getOrDefault(b, null);
                        if (infos == null) {
                            System.out.println(-1);
                        } else {
                            System.out.println(infos.stream().mapToInt(l -> l.id).boxed().map(String::valueOf)
                                    .collect(Collectors.joining(" ")));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
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

        public Info(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Info info = (Info) o;
            return id == info.id;
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
}

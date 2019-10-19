import java.util.Arrays;
import java.util.Scanner;

/**
 * @author ajacker
 * 最小生成树
 */
public class csp_201812_4 {
    private static int[] parents;

    static int find(int node) {
        while (node != parents[node]) {
            //压缩路径
            parents[node] = parents[parents[node]];
            node = parents[node];
        }
        return node;
    }

    static boolean unionElements(int v, int u) {
        v = find(v);
        u = find(u);
        if (v != u) {
            parents[v] = u;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int root = sc.nextInt();
        //建立Edge类型的数组
        Edge[] arr = new Edge[m];
        //节点
        parents = new int[m];
        //并查集前提，先让每个节点都指向自己
        for (int i = 0; i < m; i++) {
            parents[i] = i;
        }
        //根据重写的Edge内部类中compareTo方法进行排序
        for (int i = 0; i < m; i++) {
            arr[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }
        Arrays.sort(arr);
        int max = 0;
        for (int i = 0; i < m; i++) {
            Edge tmp = arr[i];
            if (unionElements(tmp.v, tmp.u)) {
                max = tmp.t;
                n--;
            }
            if (n == 1) break;

        }
        System.out.println(max);
    }

    static class Edge implements Comparable<Edge> {
        int v, u;
        int t;

        Edge(int v, int u, int t) {
            this.v = v;
            this.u = u;
            this.t = t;
        }

        @Override
        public int compareTo(Edge o) {
            //判断大小
            return this.t - o.t;
        }
    }
}
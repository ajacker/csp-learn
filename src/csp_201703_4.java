import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author ajacker
 * @date 18-9-14 下午9:45
 * 最小生成树+并查集
 */
public class csp_201703_4 {
    static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

    public static void main(String[] args) throws IOException {
        //交通枢纽的数量
        in.nextToken();
        int n = (int) in.nval;
        //候选隧道的数量
        in.nextToken();
        int m = (int) in.nval;
        //候选隧道
        ArrayList<Way> ways = new ArrayList<>();
        //n个交通枢纽
        UnionFind points = new UnionFind(n);
        //读入所有候选隧道
        for (int i = 0; i < m; i++) {
            in.nextToken();
            int a = (int) in.nval;
            in.nextToken();
            int b = (int) in.nval;
            in.nextToken();
            int c = (int) in.nval;
            ways.add(new Way(a, b, c));
        }
        //按照所用时间从小到大排序
        Collections.sort(ways);
        //连通所有的候选隧道
        for (Way way : ways) {
            //System.out.println(way.time);
            points.union(way.a, way.b);
            //找到最短时间
            if (points.isUnion(1, n)) {
                System.out.println(way.time);
                return;
            }

        }

    }
}

class UnionFind {

    Node[] node;

    //将每个元素初始化为一颗单结点树
    public UnionFind(int n) {//数组下标存放的是元素，内容指向的是它的父节点的元素
        node = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            node[i] = new Node(i);
        }
    }

    /**
     * @param e 要查询的元素
     * @return e的根节点
     */
    public int find(int e) {
        return e == node[e].parent ? e : (node[e].parent = find(node[e].parent));
    }
    //find运算就是从元素e相应的结点走到树根处，找出所在集合的名字。

    public boolean isUnion(int a, int b) {
        return find(a) == find(b);
    }

    //union运算，合并两个集合，只要将表示其中一个集合的树的数根改为表示另一个集合的树的数根的儿子结点。
    public void union(int a, int b) {
        int firstroot = find(a);
        int secondroot = find(b);
        if (isUnion(a, b)) return;
        //以重量为主合并节点
        if (node[firstroot].weight > node[secondroot].weight) {
            node[secondroot].parent = firstroot;
            node[firstroot].weight += node[secondroot].weight;
        } else {
            node[firstroot].parent = secondroot;
            node[secondroot].weight += node[firstroot].weight;//合并了以后重量增加
        }
    }

    //并查集中的结点
    private static class Node {
        int parent;//指向其父节点
        int weight;//重量

        private Node(int i) {
            this.parent = i;
            this.weight = 1;
        }
    }
}


class Way implements Comparable<Way> {
    int a;
    int b;
    int time;

    public Way(int a, int b, int time) {
        this.a = a;
        this.b = b;
        this.time = time;
    }

    @Override
    public int compareTo(Way o) {//从小到大
        if (this.time < o.time)
            return -1;
        else if (this.time > o.time)
            return 1;
        else return 0;
    }
}

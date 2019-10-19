import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author ajacker
 * @date 18-9-14 下午9:45
 * 最小生成树+并查集
 */
public class csp_201703_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();//交通枢纽的数量
        int m = sc.nextInt();//候选隧道的数量
        ArrayList<Way> ways = new ArrayList<>();//候选隧道
        UnionFind points = new UnionFind(n);//n个交通枢纽
        for (int i = 0; i < m; i++) {//读入所有候选隧道
            ways.add(new Way(sc.nextInt(), sc.nextInt(), sc.nextInt()));
        }
        Collections.sort(ways);//按照所用时间从小到大排序
        for (Way way : ways) {
            //System.out.println(way.time);
            points.union(way.a, way.b);//连通所有的候选隧道
            if (points.isUnion(1, n))//找到最短时间
            {
                System.out.println(way.time);
                return;
            }

        }

    }
}

class UnionFind {
    Node[] node;   //父亲数组

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
        if (e != node[e].parent) {//如果不是根节点
            node[e].parent = node[node[e].parent].parent;//路径压缩
            e = node[e].parent;//就找它的父节点
        }
        return e;
    }
    //find运算就是从元素e相应的结点走到树根处，找出所在集合的名字。

    public boolean isUnion(int a, int b) {
        return find(a) == find(b);
    }

    //union运算，合并两个集合，只要将表示其中一个集合的树的数根改为表示另一个集合的树的数根的儿子结点。
    public void union(int a, int b) {
        int firstroot = find(a);
        int secondroot = find(b);
        //System.out.println(firstroot);
        //System.out.println(secondroot);
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

class UnionFind2 {
    Node[] node;   //父亲数组

    //将每个元素初始化为一颗单结点树
    public UnionFind2(int n) {//数组下标存放的是元素，内容指向的是它的父节点的元素
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
        while (e != node[e].parent) {//如果不是根节点
            e = node[e].parent;//就找它的父节点
        }
        return e;
    }
    //find运算就是从元素e相应的结点走到树根处，找出所在集合的名字。

    public boolean isUnion(int a, int b) {
        return find(a) == find(b);
    }

    //union运算，合并两个集合，只要将表示其中一个集合的树的数根改为表示另一个集合的树的数根的儿子结点。
    public void union(int a, int b) {
        int firstroot = find(a);
        int secondroot = find(b);
        //System.out.println(firstroot);
        //System.out.println(secondroot);
        if (isUnion(a, b)) return;
        //以高度为主合并节点（高度不同合并后高度还是最高的那个）
        if (node[firstroot].height > node[secondroot].height) {
            node[secondroot].parent = firstroot;
        } else if (node[firstroot].height < node[secondroot].height) {
            node[firstroot].parent = secondroot;
        } else {//高度相同合并后高度+1
            node[firstroot].parent = secondroot;
            node[secondroot].height++;
        }
    }

    //并查集中的结点
    private static class Node {
        int parent;//指向其父节点
        int height;//高度

        private Node(int i) {
            this.parent = i;
            this.height = 1;
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

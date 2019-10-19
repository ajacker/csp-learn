import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class csp_201903_5 {
    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        //n个据点
        int n = reader.nextInt();
        //m条边
        int m = reader.nextInt();
        //计算最近的k个行星发动机据点距离和
        int k = reader.nextInt();
        //创建图
        Graph graph = new Graph(n, m, reader);
        //获得答案
        for (int i = 1; i <= n; i++) {
            System.out.println(graph.getMinK(i, k));
        }
    }

}

class InputReader {
    public BufferedReader reader;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream), 32768);
        tokenizer = null;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

}

/**
 * 图类
 */
class Graph {
    /**
     * 储存据点序号和据点的映射关系
     */
    private Map<Integer, Vertex> vertexMap;

    /**
     * 构造图
     *
     * @param n 点数
     * @param m 边数
     */
    public Graph(int n, int m, InputReader reader) {
        this.vertexMap = new HashMap<>();
        //创建所有点并添加自环
        for (int i = 0; i < n; i++) {
            Vertex vertex = new Vertex(i + 1, reader.nextInt() != 0);
            vertexMap.put(i + 1, vertex);
            vertex.addEdge(new Edge(vertex, 0));
        }
        //添加所有边
        for (int i = 0; i < m; i++) {
            int u = reader.nextInt();
            int v = reader.nextInt();
            int w = reader.nextInt();
            Vertex a = vertexMap.get(u);
            Vertex b = vertexMap.get(v);
            //添加从u点到v点的边
            a.addEdge(new Edge(b, w));
            //添加从v点到u点的边
            b.addEdge(new Edge(a, w));
            //将间接连接的添加成直接连接的
            b.getEdgeList().forEach(edge -> {
                Vertex vertex = edge.getEndVertex();
                if (vertex.getId() != b.getId()) {
                    edge.getEndVertex().addEdge(new Edge(a, w + edge.getWeight()));
                }
                a.addEdge(new Edge(edge.getEndVertex(), w + edge.getWeight()));
            });
            //将间接连接的添加成直接连接的
            a.getEdgeList().forEach(edge -> {
                Vertex vertex = edge.getEndVertex();
                if (vertex.getId() != a.getId()) {
                    edge.getEndVertex().addEdge(new Edge(b, w + edge.getWeight()));
                }
                b.addEdge(new Edge(edge.getEndVertex(), w + edge.getWeight()));
            });
        }
    }

    /**
     * 获得从某个点开始到其它最近的k个发动机据点的距离和
     *
     * @param start 从哪个据点开始
     * @param k     到几个发动机据点
     * @return 距离和
     */
    public int getMinK(int start, int k) {
        int sum = 0, count = 0;
        List<Edge> edges = vertexMap.get(start).getEdgeList();
        //从小到大按照距离排序
        Collections.sort(edges);
        //System.out.println(edges);
        //尽可能添加k个发动机的距离
        for (int i = 0; i < edges.size() && count < k; i++) {
            Edge edge = edges.get(i);
            if (edge.getEndVertex().isEngine()) {
                sum += edge.getWeight();
                count++;
            }
        }
        return sum;
    }
}

/**
 * 顶点类
 */
class Vertex {
    /**
     * 顶点标识，据点的编号
     */
    private int id;
    /**
     * 顶点连接的边列表
     */
    private List<Edge> edgeList;
    /**
     * 是不是发动机
     */
    private boolean isEngine;

    /**
     * 创建顶点
     *
     * @param id       据点编号
     * @param isEngine 是否是引擎
     */
    public Vertex(int id, boolean isEngine) {
        this.id = id;
        this.isEngine = isEngine;
        this.edgeList = new LinkedList<>();
    }

    /**
     * 添加边
     *
     * @param edge 要添加的边节点
     */
    public void addEdge(Edge edge) {
        for (Edge cur : this.edgeList) {
            if (cur.getEndVertex().equals(edge.getEndVertex())) {
                return;
            }
        }
        edgeList.add(edge);
    }

    public int getId() {
        return id;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public boolean isEngine() {
        return isEngine;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", isEngine=" + isEngine +
                '}';
    }


}

/**
 * 边类
 */
class Edge implements Comparable<Edge> {
    /**
     * 结束点
     */
    private Vertex endVertex;
    /**
     * 边的权值
     */
    private int weight;

    public Edge(Vertex endVertex, int weight) {
        this.endVertex = endVertex;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "endVertex=" + endVertex +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.weight, o.weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Edge) {
            Edge other = (Edge) obj;
            return other.getWeight() == this.getWeight();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(this.getWeight()).hashCode();
    }
}


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author ajacker
 * tarjan求极大强连通子图
 */
public class csp_201509_4 {
    static List<Integer>[] map;
    static boolean[] inStack;
    static boolean[][] canReach;
    static int n, m, index;
    static int count = 0;
    static int[] dfn, low;
    static Stack<Integer> helper;
    static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

    public static void main(String[] args) throws IOException {
        in.nextToken();
        n = (int) in.nval;
        in.nextToken();
        m = (int) in.nval;
        inStack = new boolean[n + 1];
        map = new LinkedList[m + 1];

        for (int i = 1; i <= m; i++) {
            map[i] = new LinkedList<>();
        }
        for (int i = 1; i <= m; i++) {
            in.nextToken();
            int u = (int) in.nval;
            in.nextToken();
            int v = (int) in.nval;
            map[u].add(v);
        }
        index = 0;
        helper = new Stack<>();
        dfn = new int[n + 1];
        low = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (dfn[i] == 0) {
                tarjan2(i);
            }
        }
//        tarjan();
        System.out.print(count);

    }

    private static void tarjan2(int u) {
        Stack<Integer> stack = new Stack<>();
        stack.push(u);
        while (!stack.isEmpty()) {
            int cur = stack.peek();
            if (dfn[cur] == 0) {
                dfn[cur] = low[cur] = ++index;
                helper.push(cur);
                inStack[cur] = true;
            }
            int i = 0;
            boolean flag = false;
            while (i < map[cur].size()) {
                int v = map[cur].get(i);
                if (dfn[v] == 0) {
                    stack.push(v);
                    flag = true;
                    break;
                } else if (inStack[v]) {
                    low[cur] = Math.min(low[cur], dfn[v]);
                }
                i++;
            }
            if (flag) {
                continue;
            }
            if (i == map[cur].size()) {
                int v = stack.pop();
                if (dfn[cur] == low[cur]) {
                    int c = 0, next;
                    //计算有多少个点（到最后一个点为止）
                    do {
                        next = helper.pop();
                        inStack[next] = false;
                        c++;
                    } while (cur != next);
                    //统计有多少对
                    if (c > 0) {
                        count += c * (c - 1) / 2;
                    }
                }
                if (!stack.isEmpty()) {
                    low[stack.peek()] = Math.min(low[stack.peek()], low[v]);
                }
            }
        }
    }


    private static void tarjan(int u) {
        dfn[u] = low[u] = ++index;
        helper.push(u);
        inStack[u] = true;
        int v;
        for (int i = 0; i < map[u].size(); i++) {
            v = map[u].get(i);
            if (dfn[u] == 0) {
                tarjan(v);
                // //回溯时更新low[ ]，取最小值
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                //一旦遇到已入栈的点，就将该点作为连通量的根
                low[u] = Math.min(low[u], dfn[v]);
            }
        }
        //得到一个强联通分量
        if (dfn[u] == low[u]) {
            int c = 0;
            //计算有多少个点（到最后一个点为止）
            do {
                v = helper.pop();
                inStack[v] = false;
                c++;
            } while (u != v);
            //统计有多少对
            if (c > 0) {
                count += c * (c - 1) / 2;
            }
        }

    }

    private static void dfs() {
        canReach = new boolean[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(inStack, false);
            Stack<Integer> stack = new Stack<>();
            stack.push(i);
            inStack[i] = true;
            while (!stack.empty()) {
                int cur = stack.pop();
                if (i != cur) {
                    canReach[i][cur] = true;
                    if (canReach[cur][i]) {
                        count++;
                    }
                }
                for (Integer next : map[cur]) {
                    if (!inStack[next]) {
                        stack.push(next);
                        inStack[next] = true;
                    }
                }
            }

        }
    }


}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author ajacker
 * 广度优先搜索，最短路径
 */
public class csp_201403_4 {
    static int n, m, k, r;
    static Router[] routers;

    public static void main(String[] args) {
        n = InputReader.nextInt();
        m = InputReader.nextInt();
        k = InputReader.nextInt();
        r = InputReader.nextInt();
        routers = new Router[m + n + 1];
        for (int i = 1; i <= n; i++) {
            int x = InputReader.nextInt();
            int y = InputReader.nextInt();
            routers[i] = new Router(x, y, true);
        }
        for (int i = 1; i <= m; i++) {
            int x = InputReader.nextInt();
            int y = InputReader.nextInt();
            routers[n + i] = new Router(x, y, false);
        }
        for (int i = 1; i < routers.length; i++) {
            for (int j = i + 1; j < routers.length; j++) {
                if (routers[i].canConnect(routers[j])) {
                    routers[i].addRouter(routers[j]);
                    routers[j].addRouter(routers[i]);
                }
            }
        }
        System.out.println(bfs(routers[1]));
    }

    static int bfs(Router startRouter) {
        Queue<Router> queue = new LinkedList<>();
        queue.offer(startRouter);
        startRouter.visited = true;
        while (!queue.isEmpty()) {
            Router curRouter = queue.poll();
            int curAmount = curRouter.addAmount;
            int curStep = curRouter.step;
            if (curAmount > k) {
                //增设的路由器超过了限制
                continue;
            }
            if (curRouter.equals(routers[2])) {
                //找到了路由器
                return curStep - 1;
            }
            for (Router next : curRouter.canConnectWith) {
                if (!next.visited) {
                    next.visited = true;
                    next.step = curStep + 1;
                    if (!next.isSet) {
                        next.addAmount = curAmount + 1;
                    }
                    queue.offer(next);
                }
            }
        }
        return -1;
    }

    static class Router {
        long x, y;
        boolean isSet;
        boolean visited;
        int addAmount;
        int step;
        List<Router> canConnectWith = new ArrayList<>();

        public Router(long x, long y, boolean isSet) {
            this.x = x;
            this.y = y;
            this.isSet = isSet;
        }

        public void addRouter(Router other) {
            canConnectWith.add(other);
        }

        public boolean canConnect(Router router) {
            long deltaX = this.x - router.x;
            long deltaY = this.y - router.y;
            return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) <= r;
        }
    }

    static class InputReader {
        static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        static StringTokenizer tokenizer = null;

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

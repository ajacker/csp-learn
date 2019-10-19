import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class csp_201403_2 {
    static int N, M;
    static int[][] map = new int[2560][1440];
    static Window[] windows;

    public static void main(String[] args) {
        N = InputReader.nextInt();
        M = InputReader.nextInt();
        windows = new Window[N + 1];
        for (int i = 1; i <= N; i++) {
            int x1 = InputReader.nextInt();
            int y1 = InputReader.nextInt();
            int x2 = InputReader.nextInt();
            int y2 = InputReader.nextInt();
            windows[i] = new Window(x1, y1, x2, y2, i);
            windows[i].render();
        }
        for (int i = 0; i < M; i++) {
            int x = InputReader.nextInt();
            int y = InputReader.nextInt();
            int result = map[x][y];
            if (result != 0) {
                System.out.println(result);
                windows[result].render();
            } else {
                System.out.println("IGNORED");
            }
        }
    }

    static class Window {
        int x1, y1, x2, y2;
        int id;

        public Window(int x1, int y1, int x2, int y2, int id) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.id = id;
        }

        public void render() {
            for (int x = x1; x <= x2; x++) {
                for (int y = y1; y <= y2; y++) {
                    map[x][y] = id;
                }
            }
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

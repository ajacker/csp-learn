import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class csp_201409_2 {
    static boolean[][] map = new boolean[101][101];

    public static void main(String[] args) {
        int n = InputReader.nextInt();
        int count = 0;
        for (int i = 0; i < n; i++) {
            int x1 = InputReader.nextInt();
            int y1 = InputReader.nextInt();
            int x2 = InputReader.nextInt();
            int y2 = InputReader.nextInt();
            for (int j = x1; j < x2; j++) {
                for (int k = y1; k < y2; k++) {
                    if (!map[j][k]) {
                        map[j][k] = true;
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
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

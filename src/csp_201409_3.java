import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class csp_201409_3 {
    static String str;
    static boolean ignoreCase;
    static int n;
    static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        str = InputReader.next();
        ignoreCase = InputReader.nextInt() != 1;
        n = InputReader.nextInt();
        for (int i = 0; i < n; i++) {
            list.add(InputReader.next());
        }
        list.stream().filter(l -> judge(l)).forEach(System.out::println);

    }

    private static boolean judge(String s) {
        if (ignoreCase) {
            return s.toLowerCase().contains(str.toLowerCase());
        } else {
            return s.contains(str);
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

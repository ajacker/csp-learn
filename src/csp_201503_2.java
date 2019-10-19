import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class csp_201503_2 {
    static int n;
    static Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) {
        n = InputReader.nextInt();
        for (int i = 0; i < n; i++) {
            int current = InputReader.nextInt();
            if (!map.containsKey(current)) {
                map.put(current, 1);
            } else {
                map.put(current, map.get(current) + 1);
            }
        }
        map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
//        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(map.entrySet());
//        list.sort((o1, o2) -> o2.getValue()-o1.getValue());
//        for (Map.Entry<Integer, Integer> entry : list) {
//            System.out.println(entry.getKey()+" "+entry.getValue());
//        }
    }

    static class InputReader {
        static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        static StringTokenizer tokenizer = null;

        public static String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException ignored) {
                }
            }
            return tokenizer.nextToken();
        }

        public static int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

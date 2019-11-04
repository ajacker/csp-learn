import java.util.*;
import java.util.stream.IntStream;

public class csp_201312_1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        HashMap<Integer, Integer> map = new HashMap<>();
        IntStream.range(0, n).forEach(i -> {
            int cur = in.nextInt();
            map.put(cur, map.getOrDefault(cur, 0) + 1);
        });
        Optional<Map.Entry<Integer, Integer>> result = map.entrySet().stream().
                min(Comparator.comparing((Map.Entry<Integer, Integer> m) -> -m.getValue()).
                        thenComparing(Map.Entry.comparingByKey()));
        System.out.println(result.get().getKey());

    }
}

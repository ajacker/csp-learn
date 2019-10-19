import java.util.*;

public class csp_201412_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Trade> trades = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] strs = line.split(" ");
            if (line.contains("buy")) {
                trades.add(new Trade(false, Double.parseDouble(strs[1]), Long.parseLong(strs[2])));
            } else if (line.contains("sell")) {
                trades.add(new Trade(true, Double.parseDouble(strs[1]), Long.parseLong(strs[2])));
            } else if (line.contains("cancel")) {
                int index = Integer.parseInt(strs[1]) - 1;
                trades.add(new Trade());
                trades.get(index).setDisabled(true);
            }
        }
        Map<Double, Long> map = new HashMap<>();
        trades.stream().filter(t -> !t.disabled && !map.containsKey(t.price)).forEach(trade -> {
            double price = trade.price;
            long sellSum = trades.stream().filter(t -> !t.isSell && !t.disabled).filter(t -> t.price >= price).mapToLong(Trade::getAmount).sum();
            long buySum = trades.stream().filter(t -> t.isSell && !t.disabled).filter(t -> t.price <= price).mapToLong(Trade::getAmount).sum();
            map.put(price, Math.min(sellSum, buySum));
        });

        map.entrySet().stream().sorted(Comparator.
                comparing((Map.Entry<Double, Long> m) -> -m.getValue()).
                thenComparing(m -> -m.getKey())).
                limit(1).
                forEach(m -> System.out.printf("%.2f %d", m.getKey(), m.getValue()));
    }

    static class Trade {
        boolean isSell;
        double price;
        long amount;
        boolean disabled;

        public Trade(boolean isSell, double price, long amount) {
            this.isSell = isSell;
            this.price = price;
            this.amount = amount;
        }

        Trade() {
            disabled = true;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public long getAmount() {
            return amount;
        }
    }
}

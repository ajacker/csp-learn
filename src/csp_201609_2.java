import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class csp_201609_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        row[] seats = new row[20];
        for (int i = 0; i < seats.length; i++) {
            seats[i] = new row(i);
        }
        for (int i = 0; i < n; i++) {
            int count = sc.nextInt();
            for (int j = 0; j < seats.length; j++) {
                row seat = seats[j];
                List<Integer> res = seat.buy(count);
                if (res != null) {
                    res.forEach(x -> System.out.print(x + " "));
                    System.out.println();
                    break;
                }
                if (j == seats.length - 1) {
                    res = new LinkedList<>();
                    for (int k = 0; k < count; k++) {
                        for (row row : seats) {
                            List<Integer> temp = row.buy(1);
                            if (temp != null) {
                                res.addAll(temp);
                                break;
                            }
                        }
                    }
                    res.forEach(x -> System.out.print(x + " "));
                    System.out.println();
                }
            }
        }
    }
}

class row {
    private boolean[] seats;
    private int left;
    private int base;

    row(int base) {
        this.base = base;
        left = 5;
        seats = new boolean[6];
    }

    List<Integer> buy(int amount) {
        if (left < amount) {
            return null;
        } else {
            List<Integer> result = new LinkedList<>();
            left -= amount;
            for (int i = 1; amount > 0 && i < seats.length; i++) {
                if (!seats[i]) {
                    seats[i] = true;
                    result.add(base * 5 + i);
                    amount--;
                }
            }
            return result;
        }
    }
}

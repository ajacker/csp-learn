import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class csp_201503_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        int y1 = sc.nextInt();
        int y2 = sc.nextInt();
        for (int y = y1; y <= y2; y++) {
            LocalDate date = LocalDate.of(y, a, 1);
            int startDayOfWeek = date.getDayOfWeek().getValue();
            int adjust = c >= startDayOfWeek ? c - startDayOfWeek : c + 7 - startDayOfWeek;
            date = date.plusDays(adjust).plusWeeks(b - 1);
            if (date.getMonthValue() != a) {
                System.out.println("none");
            } else {
                System.out.println(date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
            }
        }
    }
}

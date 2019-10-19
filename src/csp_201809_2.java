import java.util.Scanner;

public class csp_201809_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        TimeVal[] hList = new TimeVal[n];
        TimeVal[] wList = new TimeVal[n];
        for (int i = 0; i < n; i++) {
            hList[i] = new TimeVal(sc.nextInt(), sc.nextInt());
        }
        for (int i = 0; i < n; i++) {
            wList[i] = new TimeVal(sc.nextInt(), sc.nextInt());
        }
        int sum = 0;
        for (TimeVal h : hList) {
            for (TimeVal w : wList) {
                sum += h.union(w).getVal();
            }
        }
        System.out.println(sum);
    }
}

class TimeVal {
    private int start;
    private int end;
    private int val;

    TimeVal(int start, int end) {
        this.start = start;
        this.end = end;
        this.val = end - start;
    }

    public TimeVal union(TimeVal other) {
        if (this.start >= other.start && this.end <= other.end) {
            return this;
        } else if (this.start <= other.start && this.end >= other.end) {
            return other;
        } else if (this.start <= other.start && this.end >= other.start) {
            return new TimeVal(other.start, this.end);
        } else if (other.start <= this.start && other.end >= this.start) {
            return new TimeVal(this.start, other.end);
        } else {
            return new TimeVal(0, 0);
        }
    }

    public int getVal() {
        return val;
    }

}
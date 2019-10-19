import java.util.Scanner;

public class csp_201812_2 {
    private static final int NONE = 0;
    private static final int RED = 1;
    private static final int YELLOW = 2;
    private static final int GREEN = 3;
    private static int r, y, g;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        r = sc.nextInt();
        y = sc.nextInt();
        g = sc.nextInt();
        int n = sc.nextInt();
        //所用时间可能超过Integer.MAX_VALUE
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += getTime(sc.nextInt(), sc.nextInt(), result);
        }
        System.out.println(result);
    }

    /**
     * 返回应该等待的时间
     *
     * @param startStatus 开始的状态
     * @param startTime   开始时刻的倒计时
     * @param past        已经过去的时间
     * @return 应该等待的时间
     */
    private static int getTime(int startStatus, int startTime, long past) {
        if (startStatus == 0) {
            return startTime;
        }
        int newStatus;
        int newTime;
        int point = 0;
        switch (startStatus) {
            case RED:
                point = (int) ((past - startTime + r) % (r + y + g));
                break;
            case GREEN:
                point = (int) ((past - startTime + r + g) % (r + y + g));
                break;
            case YELLOW:
                point = (int) ((past - startTime + r + y + g) % (r + y + g));
                break;
            default:
                break;
        }
        newStatus = judgeStatus(point);
        newTime = getTimeLeft(point, newStatus);
        return getTime(newStatus, newTime);
    }

    /**
     * 根据当前状态和倒计时返回等待时间
     *
     * @param status 当前状态
     * @param time   倒计时
     * @return 等待时间
     */
    private static int getTime(int status, int time) {
        switch (status) {
            case NONE:
            case RED:
                return time;
            case YELLOW:
                return time + r;
            default:
                return 0;
        }
    }

    /**
     * 提供从[0,r+g+y)的一个时间点，返回现在的状态
     *
     * @param point 时间点
     * @return 状态
     */
    private static int judgeStatus(int point) {
        if (point < r) {
            return RED;
        } else if (point < r + g) {
            return GREEN;
        } else {
            return YELLOW;
        }
    }

    /**
     * 根据现在的时间点和状态，返回红绿灯的倒计时
     *
     * @param point  时间点
     * @param status 状态
     * @return 红绿灯的倒计时
     */
    private static int getTimeLeft(int point, int status) {
        switch (status) {
            case RED:
                return r - point;
            case GREEN:
                return r + g - point;
            case YELLOW:
                return r + g + y - point;
            default:
                return 0;
        }
    }

}

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author ajacker
 * @date 18-9-12 下午10:05
 */
public class csp_201712_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();//接下来有n行
        String s = sc.next();//系统运行的开始时间
        String t = sc.next();//系统运行的结束时间
        ArrayList<Corntab> corntabs = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        Date start = null, end = null;
        try {
            start = format.parse(s);
            end = format.parse(t);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < n; i++) {
            corntabs.add(new Corntab(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next()));//添加配置
        }
        assert start != null;
        assert end != null;
        for (long now = start.getTime(); now <= end.getTime(); now += 60000) {//每分钟判断一次
            for (Corntab corntab : corntabs) {
                if (corntab.equals(now)) {
                    System.out.print(format.format(new Date(now)) + " ");
                    System.out.println(corntab.command);
                }
            }
        }
        //new Solve().solve();

    }

    private static class Corntab {
        String minutes;
        String hours;
        String dayOfMounth;
        String mouth;
        String dayOfWeek;
        String command;

        public Corntab(String minutes, String hours, String dayOfMounth, String mouth, String dayOfWeek, String command) {
            //System.out.println(dayOfWeek);
            this.minutes = allRange(toNum(minutes)).replace("*", ".*").replace(",", "|");
            this.hours = allRange(toNum(hours)).replace("*", ".*").replace(",", "|");
            this.dayOfMounth = allRange(toNum(dayOfMounth)).replace("*", ".*").replace(",", "|");
            this.mouth = allRange(toNum(mouth)).replace("*", ".*").replace(",", "|");
            this.dayOfWeek = allRange(toNum(dayOfWeek)).replace("*", ".*").replace(",", "|");
            this.command = command;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Long) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis((Long) obj);
                int minutes = cal.get(Calendar.MINUTE);
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                int dayOfMounth = cal.get(Calendar.DAY_OF_MONTH);
                int mouth = cal.get(Calendar.MONTH);
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                //System.out.println(this.dayOfWeek+"dayOfWeek");
                if (Pattern.matches(this.mouth, String.valueOf(mouth)) && (Pattern.matches(this.dayOfMounth, String.valueOf(dayOfMounth)) &&
                        Pattern.matches(this.dayOfWeek, String.valueOf(dayOfWeek))) && Pattern.matches(this.hours, String.valueOf(hours))
                        && Pattern.matches(this.minutes, String.valueOf(minutes)))
                    return true;

            } else return super.equals(obj);
            return false;
        }

        public String toNum(String content) {
            return content.replace("Jan", "1").replace("Feb", "2").replace("Mar", "3")
                    .replace("Apr", "4").replace("May", "5").replace("Jun", "6")
                    .replace("Jul", "7").replace("Aug", "8").replace("Sep", "9")
                    .replace("Oct", "10").replace("Nov", "11").replace("Dec", "12")
                    .replace("Sun", "0").replace("Mon", "1").replace("Tue", "2")
                    .replace("Wed", "3").replace("Thu", "4").replace("Fri", "5")
                    .replace("Sat", "6").replace("01", "1").replace("02", "2")
                    .replace("03", "3").replace("04", "4").replace("05", "5")
                    .replace("06", "6").replace("07", "7").replace("08", "8")
                    .replace("09", "9");
        }

        public String toRange(String content) {
            if (content.contains("-")) {
                int a = Integer.parseInt(content.split("-")[0]);
                int b = Integer.parseInt(content.split("-")[1]);
                if (a < 10 && b < 10) return "[" + a + "-" + b + "]";//0-9
                else if (a < 10)
                    return "[" + a + "-9]|" + "[1|" + String.valueOf(b).toCharArray()[0] + "]" + "[0-" + String.valueOf(b).toCharArray()[1] + "]";//0-20+
                else if (a >= 10 && a < 20 && b >= 20)
                    return "[1][" + a + "-9]|" + "[2]" + "[0-" + String.valueOf(b).toCharArray()[1] + "]";//10-20+
                else
                    return "[1][" + String.valueOf(a).toCharArray()[1] + "-" + String.valueOf(b).toCharArray()[1] + "]";
            } else return content;
        }

        public String allRange(String content) {
            if (content.contains(",")) {
                StringBuilder result = new StringBuilder();
                String[] strs = content.split(",");
                for (int i = 0; i < strs.length; i++) {
                    if (i != strs.length - 1)
                        result.append(toRange(strs[i])).append(",");
                    else result.append(toRange(strs[i]));
                }
                return result.toString();
            } else return toRange(content);
        }
    }
}

//别人的代码
class Solve {
    public int nowComRank = 0;
    public Calendar startCal = Calendar.getInstance(), endCal = Calendar.getInstance();
    public List<Answer> ans = new ArrayList<Answer>();
    public Date startDat, endDat;
    public Map<String, Integer> map = new HashMap<String, Integer>();

    public SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
    Set<Integer> sminute = new HashSet<Integer>();
    Set<Integer> shour = new HashSet<Integer>();
    Set<Integer> sweekday = new HashSet<Integer>();
    Set<Integer> sday = new HashSet<Integer>();
    Set<Integer> smonth = new HashSet<Integer>();
    List<Answer> list = new ArrayList<Answer>();
    int rank = 0;

    public void solve() {
        Scanner sc = new Scanner(System.in);
        // 输入
        String line = sc.nextLine();
        String temp[] = line.split(" ");
        int n = Integer.valueOf(temp[0]);

        try {
            startDat = sdf.parse(temp[1]);
            endDat = sdf.parse(temp[2]);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        startCal.setTime(startDat);
        endCal.setTime(endDat);

        for (int lineindex = 0; lineindex < n; lineindex++) {
            // <minutes> <hours> <day of month> <month> <day of week> <command>
            line = sc.nextLine();
            ans.addAll(generateAns(line));
        }

        Collections.sort(ans);
        // 输出
        for (Answer answer : ans) {
            System.out.println(answer);
        }

    }

    public void pushSet(String[] line) {
        // <minutes> <hours> <day of month> <month> <day of week> <command>
        sminute.clear();
        shour.clear();
        sday.clear();
        smonth.clear();
        sweekday.clear();

        sminute.addAll(singleRange(0, 59, line[0]));
        shour.addAll(singleRange(0, 23, line[1]));
        sday.addAll(singleRange(1, 31, line[2]));
        smonth.addAll(singleRange(1, 12, line[3]));
        sweekday.addAll(singleRange(0, 6, line[4]));

    }

    public List<Answer> generateAns(String line) {
        ++rank;
        list.clear();
        nowComRank++;
        // System.out.println("生成Ans：start" + line);
        String[] temp = line.split(" ");
//        for (int i = 0; i < 5; i++) {
//            temp[i] = temp[i].toLowerCase();
//        }
        pushSet(temp);
        // System.out.println("push完毕" + sminute.size() + " " + shour.size() + "
        // " + sweekday.size() + " " + sday.size()
        // + " " + smonth.size());
        // System.out.println(startCal.get(Calendar.YEAR));
        Calendar oneday = Calendar.getInstance();
        oneday.setFirstDayOfWeek(Calendar.SUNDAY);
        for (int iyear = startCal.get(Calendar.YEAR); iyear < endCal.get(Calendar.YEAR) + 1; iyear++) {
            for (int imonth : smonth) {
                for (int iday : sday) {
                    for (int ihour : shour) {
                        for (int iminute : sminute) {

                            oneday.set(iyear, imonth - 1, iday, ihour, iminute, 0);
                            oneday.getTime();
                            // Calendar检查年月日后对不合理的日期会顺延，这是是为了确认日期合理
                            if (oneday.get(Calendar.MONTH) + 1 == imonth && oneday.get(Calendar.YEAR) == iyear
                                    && oneday.get(Calendar.DATE) == iday
                                    && sweekday.contains(oneday.get(Calendar.DAY_OF_WEEK) - 1)
                                    && !oneday.before(startCal) && oneday.before(endCal)) {

                                list.add(new Answer(sdf.format(new Date(oneday.getTimeInMillis())), temp[5], rank));
                                // System.out.println("list add" +
                                // Csp2017123.sdf.format(oneday.getTime()));
                            }
                        }
                    }
                }

            }
        }
        return list;
    }

    public Set<Integer> singleRange(int start, int end, String line) {
        Set<Integer> ansSet = new HashSet<Integer>();
        if (line.equals("*")) {
            for (int i = start; i < end + 1; i++) {
                ansSet.add(i);
            }
            return ansSet;
        }
        line += ",";
        String[] temp = line.split(",");
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].contains("-")) {
                String temp1[] = temp[i].split("-");
                for (int index1 = convToNum(temp1[0]); index1 < convToNum(temp1[1]) + 1; index1++) {
                    ansSet.add(index1);
                }
            } else
                ansSet.add(convToNum(temp[i]));
        }
        return ansSet;

    }

    //我最刚开始是用Map写的，但因为没有清楚前导零只得了40分，用parseInt则没有这个问题
    public int convToNum(String line) {
        switch (line.toLowerCase()) {
            case "sun":
                return 0;
            case "mon":
            case "jan":
                return 1;
            case "tue":
            case "feb":
                return 2;
            case "wed":
            case "mar":
                return 3;
            case "thu":
            case "apr":
                return 4;
            case "fri":
            case "may":
                return 5;
            case "sat":
            case "jun":
                return 6;
            case "jul":
                return 7;
            case "aug":
                return 8;
            case "sep":
                return 9;
            case "oct":
                return 10;
            case "nov":
                return 11;
            case "dec":
                return 12;
            default:
                try {
                    return Integer.parseInt(line);
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO: handle exception
                }
                break;
        }
        return -1;
    }

    // return map.get(line);

}

class Answer implements Comparable<Answer> {

    public int comRank;

    public String time;
    public String command;

    public Answer(String time, String command, int comrank) {
        this.time = time;
        this.command = command;
        this.comRank = comrank;
    }

    public String toString() {
        return this.time + " " + command;
    }

    @Override
    public int compareTo(Answer o) {
        int res = this.time.compareTo(o.time);
        return res == 0 ? this.comRank - o.comRank : res;
    }

}

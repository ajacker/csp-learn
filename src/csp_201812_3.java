import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class csp_201812_3 {
    public static void main(String[] args) throws IOException {
        final int n = Reader.nextInt();
        final ArrayList<IpPrefix> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new IpPrefix(Reader.next()));
        }
        Reader.reader.close();
        //排序ip前缀列表
        Collections.sort(list);
        //从小到大合并
        mergeByOrder(list);
        //合并同级的
        merge(list);
        //输出所有结果
        list.forEach(System.out::println);
    }

    /**
     * 从小到大合并 去除子集关系
     *
     * @param list 前缀集合
     */
    public static void mergeByOrder(List<IpPrefix> list) {
        final ListIterator<IpPrefix> iterator = list.listIterator();
        IpPrefix current = null;
        while (iterator.hasNext()) {
            current = iterator.next();
            if (iterator.hasNext()) {
                if (current.contains(iterator.next())) {
                    iterator.remove();
                }
                iterator.previous();
            }
        }
    }

    /**
     * 同级合并
     *
     * @param list 前缀集合
     */
    public static void merge(List<IpPrefix> list) {
        final ListIterator<IpPrefix> iterator = list.listIterator();
        IpPrefix current = null, next = null;
        while (iterator.hasNext()) {
            current = iterator.next();
            if (iterator.hasNext()) {
                next = iterator.next();
                if (current.canMergeWith(next)) {
                    iterator.previous();
                    iterator.remove();
                    iterator.previous().setLen(current.getLen() - 1);
                    //如果有上一个元素，则下次从上一个元素开始考虑
                    if (iterator.hasPrevious()) {
                        iterator.previous();
                    }
                } else {
                    iterator.previous();
                }
            }
        }
//        int i=0,j=1;
//        while (j<list.size()){
//            IpPrefix a = list.get(i);
//            IpPrefix b = list.get(j);
//            if(a.canMergeWith(b)){
//                list.remove(j);
//                a.setLen(a.getLen()-1);
//                if (a!=list.get(0)){
//                    i--;
//                    j--;
//                }
//            }else {
//                i++;
//                j++;
//            }
//        }
    }
}

final class IpPrefix implements Comparable<IpPrefix> {
    private long ip;
    private int len;

    public IpPrefix(long ip, int len) {
        this.ip = ip;
        this.len = len;
    }

    public IpPrefix(String content) {
        if (content.contains("/")) {
            //标准型或省略后缀型
            //分割/的前后部分
            String[] split = content.split("/");
            //后部分是len
            this.len = Integer.parseInt(split[1]);
            //前部分计算ip代表的十进制
            int[] parts = Arrays.stream(split[0].split("\\.")).mapToInt(Integer::parseInt).toArray();
            this.ip = 0;
            for (int i = 0; i < parts.length; i++) {
                //256的k次方
                int k = 3 - i;
                this.ip += parts[i] * Math.pow(256, k);
            }
        } else {
            //省略长度型
            int[] parts = Arrays.stream(content.split("\\.")).mapToInt(Integer::parseInt).toArray();
            this.len = parts.length * 8;
            this.ip = 0;
            for (int i = 0; i < parts.length; i++) {
                //256的k次方
                int k = 3 - i;
                this.ip += parts[i] * Math.pow(256, k);
            }
        }
    }

    public long getIp() {
        return ip;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    /**
     * 返回是否能和o同级合并
     *
     * @param o 其它ip前缀
     * @return 是否
     */
    public boolean canMergeWith(IpPrefix o) {
        if (this.len == o.getLen()) {
            return ((this.ip ^ o.getIp()) >> (32 - len)) == 1;
        }
        return false;
    }

    /**
     * 检查自身是否包含其它元素的匹配集（是否为父集）
     * 要保证本身是len小的
     *
     * @param o 其它ip前缀
     * @return 是否
     */
    public boolean contains(IpPrefix o) {
        if (this.len > o.getLen()) {
            return false;
        } else {
            return ((this.ip ^ o.getIp()) >> (32 - len)) == 0;
        }
    }

    /**
     * 将十进制ip转字符串
     *
     * @return ip的字符串形式
     */
    public String getStrIp() {
        long temp = this.ip;
        int[] parts = new int[4];
        for (int location = 3; location >= 0; location--) {
            int current = (int) (temp % 256);
            temp /= 256;
            //将当前位倒序存放
            parts[location] = current;
        }
        return Arrays.stream(parts).mapToObj(String::valueOf).collect(Collectors.joining("."));
    }

    @Override
    public String toString() {
        return this.getStrIp() + '/' + len;
    }

    /**
     * 以ip作为第一关键字，len作为第二关键字比较
     *
     * @param o 要比较的另外一个对象
     * @return -1代表本身比较小，0相同，1另一个大
     */
    @Override
    public int compareTo(IpPrefix o) {
        //以ip作为第一关键字，len作为第二关键字比较
        if (this.ip != o.ip) {
            return Long.compare(this.ip, o.getIp());
        } else {
            return Integer.compare(this.len, o.getLen());
        }
    }
}

class Reader {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokenizer = new StringTokenizer("");

    static String next() throws IOException {// 读取下一个字符串
        while (!tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(reader.readLine());
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {// 读取下一个int型数值
        return Integer.parseInt(next());
    }
}
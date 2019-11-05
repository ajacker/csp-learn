import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * @author ajacker
 * @date 2019/11/5 9:06
 */
public class csp_201909_3 {
    /**
     * 所有像素
     */
    private static Pixel[][] pixels;
    /**
     * 所有色块
     */
    private static Pixel[][] blocks;

    public static void main(String[] args) {
        //宽-列
        int m = InputReader.nextInt();
        //高-行
        int n = InputReader.nextInt();
        //色块的宽
        int p = InputReader.nextInt();
        //色块的高
        int q = InputReader.nextInt();
        pixels = new Pixel[n][m];
        //横向像素块个数
        int a = m / p;
        //纵向像素块个数
        int b = n / q;
        blocks = new Pixel[b][a];
        //读入所有像素
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                pixels[i][j] = new Pixel(InputReader.next());
            }
        }
        //生成所有块
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < a; j++) {
                //坐标为i，j的像素块的起始点
                int y = i * q;
                int x = j * p;
                //遍历所有这个像素块的像素，计算总和
                long redSum = 0, greenSum = 0, blueSum = 0;
                for (int k = y; k < y + q; k++) {
                    for (int l = x; l < x + p; l++) {
                        redSum += pixels[k][l].r;
                        greenSum += pixels[k][l].g;
                        blueSum += pixels[k][l].b;
                    }
                }
                //计算平均值
                int red = (int) (redSum / (p * q));
                int green = (int) (greenSum / (p * q));
                int blue = (int) (blueSum / (p * q));
                //填充像素块的r，g，b
                blocks[i][j] = new Pixel(red, green, blue);
            }
        }
        //生成结果
        StringBuilder result = new StringBuilder();
        //遍历所有像素块
        for (int i = 0; i < b; i++) {
            //每一行都要独立考虑
            Pixel last = new Pixel(0, 0, 0);
            for (int j = 0; j < a; j++) {
                Pixel cur = blocks[i][j];
                //如果和上一个不相同就才用设置
                if (!cur.equals(last)) {
                    result.append(cur.getBackStr());
                    last = cur;
                }
                //不管设不设置颜色，空格还是要输出的
                result.append(" ");
            }
            //如果终端颜色不是默认值，就重置
            if (last.r != 0 || last.g != 0 || last.b != 0) {
                result.append("\033[0m");
            }
            //每一行结束后换行符
            result.append("\n");

        }
        //输出结果ASCII
        System.out.print(toASCII(result.toString()));
    }

    /**
     * 将字符串转换为ASCII形式的字符串
     *
     * @param str 普通字符串
     * @return ASCII形式的字符串
     */
    public static String toASCII(String str) {
        return str.chars().mapToObj(Integer::toHexString)
                .map(String::toUpperCase)
                .map(s -> String.format("\\x%2s", s))
                .map(s -> s.replace(" ", "0"))
                .collect(Collectors.joining());

    }

    /**
     * 像素类+色块类共用
     */
    static class Pixel {
        int r, g, b;

        /**
         * 构造函数
         *
         * @param r 红色分量
         * @param g 绿色分量
         * @param b 蓝色分量
         */
        Pixel(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        /**
         * 构造函数
         * @param s #aabbcc 或者 #abc 或者 #a 形式的字符串
         */
        Pixel(String s) {
            char[] array = s.toCharArray();
            switch (array.length) {
                case 2:
                    char t = array[1];
                    r = g = b = Integer.parseInt(new String(new char[]{t, t}), 16);
                    break;
                case 4:
                    char m = array[1];
                    char n = array[2];
                    char q = array[3];
                    r = Integer.parseInt(new String(new char[]{m, m}), 16);
                    g = Integer.parseInt(new String(new char[]{n, n}), 16);
                    b = Integer.parseInt(new String(new char[]{q, q}), 16);
                    break;
                case 7:
                    int value = Integer.parseInt(s.replace("#", ""), 16);
                    r = (value & 0xff0000) >> 16;
                    g = (value & 0x00ff00) >> 8;
                    b = (value & 0x0000ff);
                    break;
                default:
                    break;
            }
        }

        /**
         * 获得本色块的颜色设置代码
         * @return 字符串形式的颜色设置代码
         */
        public String getBackStr() {
            if (r == 0 && g == 0 && b == 0) {
                //如果是黑色则可以直接重置背景色
                return "\033[0m";
            } else {
                //否则设置背景色
                String format = "\033[48;2;%d;%d;%dm";
                return String.format(format, r, g, b);
            }
        }

        /**
         * 比较两个像素是否相等
         * @param obj 另一个像素
         * @return 是否相等
         */
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pixel) {
                Pixel other = (Pixel) obj;
                return other.r == r && other.g == g && other.b == b;
            }
            return false;
        }
    }

    /**
     * 输入加速类
     */
    static class InputReader {
        private static StringTokenizer tokenizer = null;
        private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        static String next() {
            if (tokenizer == null || !tokenizer.hasMoreTokens()) {
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

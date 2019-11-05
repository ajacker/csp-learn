import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author ajacker
 * @date 2019/11/5 13:25
 */
public class TestDraw {

    public static void main(String[] args) throws Exception {
        BufferedImage img = ImageIO.read(new File("C:\\Users\\87392\\Pictures\\s.png"));
        int[][] data = new int[img.getHeight()][img.getWidth()];
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                data[i][j] = img.getRGB(j, i);
            }
        }
        drawPixels(img.getWidth(), img.getHeight(), data);
    }

    public static void drawPixels(int width, int height, int[][] rgb) {
        //宽-列
        int m = width;
        //高-行
        int n = height;
        int p = 1;
        int q = 1;
        Pixel[][] pixels = new Pixel[n][m];
        //横向像素块个数
        int a = m / p;
        //纵向像素块个数
        int b = n / q;
        Pixel[][] blocks = new Pixel[b][a];
        //读入所有像素
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                pixels[i][j] = new Pixel(rgb[i][j]);
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
            Pixel last = new Pixel(0);
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
        System.out.println(result.toString());
    }

    static class Pixel {
        int r, g, b;

        Pixel(int n) {
            this.r = (n & 0xff0000) >> 16;
            this.g = (n & 0xff00) >> 8;
            this.b = (n & 0xff);
        }

        Pixel(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

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

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pixel) {
                Pixel other = (Pixel) obj;
                return other.r == r && other.g == g && other.b == b;
            }
            return false;
        }
    }


}

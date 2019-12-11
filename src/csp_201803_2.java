import java.util.ArrayList;
import java.util.Scanner;

public class csp_201803_2 {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int L = sc.nextInt();
        int t = sc.nextInt();
        //存放了所有的小球信息
        ArrayList<Ball> balls = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            balls.add(new Ball(sc.nextInt()));
        }
        //遍历每一秒时间
        for (int time = 0; time < t; time++) {
            //遍历所有的小球
            for (Ball ball : balls) {
                //每个球动一下
                ball.move(L);
                //复制一份小球列表
                ArrayList<Ball> others = new ArrayList<>(balls);
                //删除自己
                others.remove(ball);
                //遍历别的小球查看是否相撞了
                for (Ball other : others) {
                    //两球相撞都反向
                    if (ball.pos == other.pos)
                    {
                        ball.reverse();
                        other.reverse();
                    }
                }

            }
        }
        //打印所有小球现在的位置
        for (int i = 0; i < n; i++) {
            System.out.print(balls.get(i).pos + " ");
        }

    }

    /**
     * 枚举类，代表左右的方向
     */
    enum oration {LEFT, RIGHT}

    private static class Ball {
        /**
         * 方向
         */
        oration vec;
        /**
         * 位置
         */
        int pos;

        /**
         * 构造函数初始化起始位置和往右的方向
         *
         * @param pos 初始坐标
         */
        Ball(int pos) {
            this.pos = pos;
            vec = oration.RIGHT;
        }

        /**
         * 进行移动
         * @param L 线段长度
         */
        public void move(int L) {
            switch (vec) {
                case LEFT:
                    //如果移动的时候撞墙了就转向
                    if (this.pos == 0) {
                        this.reverse();
                        pos++;
                    } else {
                        pos--;
                    }
                    break;
                case RIGHT:
                    //如果移动的时候撞墙了就转向
                    if (this.pos == L) {
                        this.reverse();
                        pos--;
                    } else {
                        pos++;
                    }
                    break;
                default:
                    break;
            }
        }

        /**
         * 转向
         */
        private void reverse() {
            if (this.vec == oration.RIGHT) {
                this.vec = oration.LEFT;
            } else {
                this.vec = oration.RIGHT;
            }
        }
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class csp_201803_2 {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int L = sc.nextInt();
        int t = sc.nextInt();
        ArrayList<Ball> balls = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            balls.add(new Ball(sc.nextInt()));
        }
        for (int time = 0; time < t; time++) {
            for (Ball ball : balls) {
                ball.move(L);//每个球动一下
                ArrayList<Ball> newballs = new ArrayList<>(balls);
                newballs.remove(ball);//除了自己的别的球
                for (Ball other : newballs) {
                    if (ball.pos == other.pos)//两球相撞
                    {
                        ball.reverse();
                        other.reverse();
                    }
                }

            }
        }
        for (int i = 0; i < n; i++) {
            System.out.print(balls.get(i).pos + " ");
        }

    }

    enum oriation {LEFT, RIGHT}

    private static class Ball {
        oriation vec;
        int pos;

        Ball(int pos) {
            this.pos = pos;
            vec = oriation.RIGHT;
        }

        public void move(int L) {
            switch (vec) {
                case LEFT:
                    if (this.pos == 0) {
                        this.reverse();
                        pos++;
                    } else
                        pos--;
                    break;
                case RIGHT:
                    if (this.pos == L) {
                        this.reverse();
                        pos--;
                    } else pos++;
                    break;
            }
        }

        private void reverse() {
            if (this.vec == oriation.RIGHT)
                this.vec = oriation.LEFT;
            else this.vec = oriation.RIGHT;
        }
    }
}

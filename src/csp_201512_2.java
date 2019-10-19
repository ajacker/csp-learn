import java.util.*;

public class csp_201512_2 {
    static int n, m;
    static int[][] map;
    static Set<Position> clearPos = new HashSet<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        map = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                map[i][j] = sc.nextInt();
            }
        }
        List<Position> temp = new LinkedList<>();
        int count, last;
        //扫描行
        for (int i = 0; i < n; i++) {
            count = 0;
            last = -1;
            for (int j = 0; j < m; j++) {
                int current = map[i][j];
                if (last == current) {
                    count++;

                } else {
                    if (count >= 3) {
                        clearPos.addAll(temp);
                    }
                    temp.clear();
                    count = 1;
                }
                last = current;
                temp.add(new Position(i, j));
            }
            if (count >= 3) {
                clearPos.addAll(temp);
            }

        }
        //扫描列
        for (int j = 0; j < m; j++) {
            count = 0;
            last = -1;
            for (int i = 0; i < n; i++) {
                int current = map[i][j];
                if (last == current) {
                    count++;
                } else {
                    if (count >= 3) {
                        clearPos.addAll(temp);
                    }
                    temp.clear();
                    count = 1;
                }
                last = current;
                temp.add(new Position(i, j));
            }
            if (count >= 3) {
                clearPos.addAll(temp);
            }
        }
        //消去
        for (Position po : clearPos) {
            clear(po);
        }
        //输出结果
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void clear(Position p) {
        map[p.x][p.y] = 0;
    }
}

class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
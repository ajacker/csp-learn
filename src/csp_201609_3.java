import java.util.Scanner;

public class csp_201609_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //n个操作
        int n = sc.nextInt();
        //创建玩家
        Hero p1 = new Hero();
        Hero p2 = new Hero();
        Hero current = p1;
        Hero other;
        //表明游戏状态1先手获胜-1后手获胜0未结束
        int status = 0;
        for (int i = 0; i < n; i++) {
            String command = sc.next();
            other = current == p1 ? p2 : p1;
            if (!current.isDead) {
                switch (command) {
                    case "summon":
                        current.summon(sc.nextInt(), sc.nextInt(), sc.nextInt());
                        break;
                    case "attack":
                        //攻击并判断是否有玩家获胜
                        boolean res = current.attack(other, sc.nextInt(), sc.nextInt());
                        if (res) {
                            status = current == p1 ? 1 : -1;
                        }
                        break;
                    case "end":
                        current = current == p1 ? p2 : p1;
                        break;
                    default:
                        break;
                }
            }
        }
        //打印对局状态
        System.out.println(status);
        //打印先手玩家血量
        System.out.println(p1.health);
        //打印先手玩家随从信息
        StringBuilder sb = new StringBuilder();
        sb.append(p1.liveFollower).append(" ");
        for (int i = 1; i < p1.characters.length; i++) {
            if (p1.characters[i] != null) {
                sb.append(p1.characters[i].health).append(" ");
            }
        }
        System.out.println(sb.toString());
        //打印后手玩家血量
        System.out.println(p2.health);
        //打印后手玩家随从信息
        sb = new StringBuilder();
        sb.append(p2.liveFollower).append(" ");
        for (int i = 1; i < p2.characters.length; i++) {
            if (p2.characters[i] != null) {
                sb.append(p2.characters[i].health).append(" ");
            }
        }
        System.out.println(sb.toString());
    }

}

abstract class BaseCharacter {
    /**
     * 攻击力
     */
    int attack;
    /**
     * 生命值
     */
    int health;
    /**
     * 标志是否死亡
     */
    boolean isDead = false;

    void attack(BaseCharacter aim) {
        aim.health -= attack;
        if (aim.health <= 0) {
            aim.isDead = true;
        }
        this.health -= aim.attack;
        if (this.health <= 0) {
            this.isDead = true;
        }
    }
}

/**
 * 随从类
 */
class Follower extends BaseCharacter {
    Follower(int attack, int health) {
        this.attack = attack;
        this.health = health;
    }
}

/**
 * 英雄类
 */
class Hero extends BaseCharacter {
    /**
     * 储存玩家的所有角色
     */
    BaseCharacter[] characters = new BaseCharacter[8];
    /**
     * 存活的随从数量
     */
    int liveFollower = 0;

    Hero() {
        //0代表英雄本身，1-7是他的随从
        characters[0] = this;
        this.health = 30;
    }

    /**
     * 把死了的随从位置腾一下
     *
     * @param hero 玩家
     */
    private static void judgeFollowers(Hero hero) {
        BaseCharacter[] characters = hero.characters;
        for (int i = 1; i < characters.length; i++) {
            if (characters[i] != null && characters[i].isDead) {
                hero.liveFollower--;
                for (int j = i; j + 1 < characters.length; j++) {
                    characters[j] = characters[j + 1];
                }
                characters[characters.length - 1] = null;
            }
        }
    }

    /**
     * 使用随从攻击其它玩家的随从或英雄
     *
     * @param other    其它玩家
     * @param attacker 攻击者随从编号
     * @param attacked 被攻击者随从编号，如果是敌方英雄则为0
     * @return 攻击者是否胜利（敌方英雄死了）
     */
    boolean attack(Hero other, int attacker, int attacked) {
        characters[attacker].attack(other.characters[attacked]);
        //攻击者只能用随从攻击，所以只有随从可能死
        judgeFollowers(this);
        //判断敌方随从或英雄
        judgeFollowers(other);
        return other.isDead;
    }

    /**
     * 召唤随从
     *
     * @param position 位置
     * @param attack   攻击力
     * @param health   生命值
     */
    void summon(int position, int attack, int health) {
        Follower follower = new Follower(attack, health);
        //如果这个位置有，就推走
        for (int i = characters.length - 1; i - 1 >= position; i--) {
            characters[i] = characters[i - 1];
        }
        characters[position] = follower;
        liveFollower++;
    }
}
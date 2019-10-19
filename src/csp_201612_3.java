import java.util.Scanner;

/**
 * @author ajacker
 * @date 18-9-15 下午12:01
 */
public class csp_201612_3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int p = sc.nextInt();//表示不同的权限类别的数量
        Permission[] pers = new Permission[p];//权限
        for (int i = 0; i < p; i++) {//读入所有权限数据
            pers[i] = new Permission(sc.next());
        }
        int r = sc.nextInt();//表示不同的角色数量
        Role[] roles = new Role[r];//角色
        for (int i = 0; i < r; i++) {//读入所有角色数据
            roles[i] = new Role(sc);
        }
        int u = sc.nextInt();//表示用户数量
        User[] users = new User[u];//用户
        for (int i = 0; i < u; i++) {//读入所有用户数据
            users[i] = new User(sc, roles);
        }
        int q = sc.nextInt();//表示要查询的数量
        for (int i = 0; i < q; i++) {
            String finduser = sc.next();//要查询的用户
            Permission per = new Permission(sc.next());//要查询的权限
            int fail = 0;//记录查询用户失败的次数
            for (User user : users) {
                if (user.name.equals(finduser)) per.isBelongsTo(user);
                else fail++;//记录失败次数
            }
            if (fail == users.length) System.out.println(false);//如果没有用户则查询失败
        }
        /*for (User user : users) {
            for (Role role : user.roles)
                for (Permission per : role.pers) {
                    System.out.println(per.name+":"+per.level);
                }
        }*/
    }
}

class Permission {
    String name;
    int level;//表示该权限等级,-1代表该权限没有等级

    public Permission(String str) {
        if (str.contains(":")) {
            this.name = str.split(":")[0];
            this.level = Integer.parseInt(str.split(":")[1]);
        } else {
            this.name = str;
            this.level = -1;
        }
    }

    public void isBelongsTo(User user) {//检查用户是否有权限
        int maxlevel = -1;
        for (Role role : user.roles) {
            for (Permission per : role.pers) {
                if (per.name.equals(this.name) && this.level == -1)//无等级查询
                {
                    if (per.level == -1) {
                        System.out.println(true);
                        return;
                    } else maxlevel = per.level > maxlevel ? per.level : maxlevel;
                } else if (per.name.equals(this.name) && this.level <= per.level)//带等级查询
                {
                    System.out.println(true);
                    return;
                }
            }
        }
        if (maxlevel != -1) {//分等级权限的不带等级查询返回权限等级
            System.out.println(maxlevel);
            return;
        }
        System.out.println(false);//如果检查完了还没结束代表没有该权限
    }
}

class Role {
    String name;//角色名
    Permission[] pers;//包含的权限

    public Role(Scanner sc) {
        this.name = sc.next();
        this.pers = new Permission[sc.nextInt()];
        for (int i = 0; i < pers.length; i++) {
            pers[i] = new Permission(sc.next());
        }
    }
}

class User {
    String name;//用户名
    Role[] roles;//该用户的角色

    public User(Scanner sc, Role[] roles) {
        this.name = sc.next();
        this.roles = new Role[sc.nextInt()];
        for (int i = 0; i < this.roles.length; i++) {
            String addrole = sc.next();
            for (Role r : roles) {
                if (r.name.equals(addrole)) this.roles[i] = r;
            }
        }
    }
}
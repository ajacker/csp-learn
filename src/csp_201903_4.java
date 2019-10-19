import java.util.*;

public class csp_201903_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //样例个数
        int T = sc.nextInt();
        //进程格式
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            Process.processHashMap.put(i, new Process(i));
        }
        sc.nextLine();
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < n; j++) {
                Process process = Process.processHashMap.get(j);
                process.setCommands(sc.nextLine());
                process.excuate();
            }
            boolean isLocked = false;
            for (Process pro : Process.processHashMap.values()) {
                if (pro.getStatus() != Process.Status.wait) {
                    isLocked = true;
                }
            }
            System.out.println(isLocked ? "1" : "0");
            Process.processHashMap.forEach((integer, process) -> {
                process.getCommands().clear();
                process.setStatus(Process.Status.wait);
            });
        }
        sc.close();

    }

}

class Process {

    public static HashMap<Integer, Process> processHashMap = new HashMap<>();
    Status status = Status.wait;
    int aim = -1;
    int id;
    Queue<String> commands = new LinkedList<>();

    public Process(int id) {
        this.id = id;
    }

    public void excuate() {
        if (!commands.isEmpty() && this.status == Status.wait) {
            String command = commands.poll();
            switch (command.charAt(0)) {
                case 'R':
                    this.recv(processHashMap.get(Integer.parseInt(command.substring(1))));
                    break;
                case 'S':
                    this.send(processHashMap.get(Integer.parseInt(command.substring(1))));
                    break;
                default:
                    break;
            }
        }
    }

    public void send(Process aim) {
        //如果发送目标正好在接收
        if (aim.getStatus() == Status.recv && aim.getAim() == id) {
            aim.status = Status.wait;
            this.status = Status.wait;
            //执行下一句
            aim.excuate();
            this.excuate();
        } else {
            this.status = Status.send;
            this.aim = aim.getId();
        }
    }

    public void recv(Process aim) {
        //如果发送目标正好在发送
        if (aim.getStatus() == Status.send && aim.getAim() == id) {
            aim.status = Status.wait;
            this.status = Status.wait;
            //执行下一句
            aim.excuate();
            this.excuate();
        } else {
            this.status = Status.recv;
            this.aim = aim.getId();
        }
    }

    public Queue<String> getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        //processHashMap.forEach((integer, process) -> process.getCommands().clear());
        this.commands.clear();
        this.commands.addAll(Arrays.asList(commands.split(" ")));

        //System.out.println(this.commands);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getAim() {
        return aim;
    }

    public int getId() {
        return id;
    }

    enum Status {
        //空闲
        wait,
        //发送
        send,
        //接收
        recv
    }
}

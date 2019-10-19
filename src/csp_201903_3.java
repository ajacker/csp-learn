import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class csp_201903_3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //磁盘个数
        int n = sc.nextInt();
        //条带大小
        int s = sc.nextInt();
        //有数据的个数
        int l = sc.nextInt();
        ArrayList<Disk> disks = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            disks.add(new Disk(i, s));
        }
        int diskSize = 0;
        for (int i = 0; i < l; i++) {
            int id = sc.nextInt();
            String data = sc.next();
            diskSize = data.length() / (s * 8);
            disks.get(id).addData(data, diskSize);
        }
        //总的条带个数
        int tapeAmount = diskSize * n;
        //用于定位块的索引
        HashMap<Integer, Pos> indexes = new HashMap<>(10);
        //建立索引
        for (int i = 0, tapeId = 0, skipId = n - 1, blockId = 0; i < tapeAmount; i++) {
            int diskId = i % n;
            if (diskId == skipId) {
                tapeId += 1;
                if (tapeId >= diskSize) {
                    break;
                }
                tapeAmount--;
                skipId = skipId - 1 == -1 ? n - 1 : skipId - 1;
            }
            for (int innerId = 0; innerId < s; innerId++) {
                Pos pos = new Pos(diskId, tapeId, innerId);
                indexes.put(blockId++, pos);
            }
        }
        //要读取的块个数
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            try {
                System.out.println(indexes.get(sc.nextInt()).getData(disks));
            } catch (Exception e) {
                System.out.println("-");
            }
        }
    }

}

class Pos {
    private int diskId;
    private int tapeId;
    private int blockId;

    public Pos(int diskId, int tapeId, int blockId) {
        this.diskId = diskId;
        this.tapeId = tapeId;
        this.blockId = blockId;
    }

    public String getData(ArrayList<Disk> disks) throws Exception {
        Disk disk = disks.get(diskId);
        String data = null;
        if (tapeId < disk.getTapes().size()) {
            Tape tape = disk.getTapes().get(tapeId);
            Block block = tape.getBlocks().get(blockId);
            data = block.getData();
        } else {
            int len = disks.size();
            for (int i = 0; i < len; i++) {
                if (i != diskId) {
                    disk = disks.get(i);
                    Tape tape = disk.getTapes().get(tapeId);
                    Block block = tape.getBlocks().get(blockId);
                    data = data == null ? block.getData() : block.xor(data);

                }

            }
        }
        return data == null ? "-" : data;

    }

}

class Disk {
    private int diskId;
    private ArrayList<Tape> tapes;
    private int tapeSize;

    public Disk(int diskId, int tapeSize) {
        this.diskId = diskId;
        this.tapes = new ArrayList<>();
        this.tapeSize = tapeSize;

    }

    public void addData(String data, int diskSize) {
        //添加size个条带，并每个条带填充8*tapeSize个长度字符（4*tapeSize bytes）
        for (int i = 0; i < diskSize; i++) {
            tapes.add(new Tape(tapeSize, data.substring(i * tapeSize * 8, (i + 1) * tapeSize * 8)));
        }
    }

    public ArrayList<Tape> getTapes() {
        return tapes;
    }

}

class Tape {
    private int size;
    private ArrayList<Block> blocks;

    public Tape(int size, String data) {
        this.size = size;
        this.blocks = new ArrayList<>(size);
        //添加size个块，并每个块填充8个长度字符（4bytes）
        for (int i = 0; i < size; i++) {
            blocks.add(new Block(data.substring(i * 8, (i + 1) * 8)));
        }
    }


    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}

class Block {
    private String data;

    public Block(String data) {
        this.data = data;
    }

    public String xor(String other) {
        BigInteger a = new BigInteger(getData(), 16);
        BigInteger b = new BigInteger(other, 16);
        return a.xor(b).toString(16).toUpperCase();
    }

    public String getData() {
        return data;
    }

}
public class PrefixHandler implements Runnable {
    private int left;
    private int right;
    private int id;

    public PrefixHandler(int left, int right, int id) {
        this.left = left;
        this.right = right;
        this.id = id;
    }

    @Override
    public void run(){
        int total;
        if (left != 0) {
            total = Main.prefixes[id];
        } else {
            total = 0;
        }
        Main.array[left] = total + Main.array[left];
        for (int i = left + 1; i < right; i++) {
            Main.array[i] = Main.array[i - 1] + Main.array[i];
        }
    }
}

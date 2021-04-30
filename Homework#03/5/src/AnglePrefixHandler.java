public class AnglePrefixHandler implements Runnable {
    private int left;
    private int right;
    private int id;

    public AnglePrefixHandler(int left, int right, int id) {
        this.left = left;
        this.right = right;
        this.id = id;
    }

    @Override
    public void run(){
        double total;
        if (left != 0) {
            total = Main.anglePrefixes[id];
        } else {
            total = 0;
        }
        Main.angles[left] = (total + Main.angles[left]) % 360;
        for (int i = left + 1; i < right; i++) {
            Main.angles[i] = (Main.angles[i - 1] + Main.angles[i]) % 360;
        }
    }
}

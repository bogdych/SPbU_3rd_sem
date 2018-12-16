public class ResultMaker implements Runnable{
    private int left;
    private int right;

    public ResultMaker(int left, int right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        for (int i = left; i < right; i++) {
            if (i != 0) {
                Main.res[i] = (Main.a[i] + Main.b[i] + (Main.carries[i - 1] == Carry.C ? 1 : 0)) % 10;
            } else {
                Main.res[i] = (Main.a[i] + Main.b[i]) % 10;
            }
        }
    }
}

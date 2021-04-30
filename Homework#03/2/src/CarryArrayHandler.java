public class CarryArrayHandler implements Runnable {
    private int left;
    private int right;

    public CarryArrayHandler(int left, int right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        Carry total;
        if (left != 0) {
            total = Main.prefixes[left / Main.step];
        } else {
            total = Carry.N;
        }

        Main.carries[left] = Operator.sum(total, Main.carries[left]);
        for (int i = left + 1; i < right; i++) {
            Main.carries[i] = Operator.sum(Main.carries[i - 1], Main.carries[i]);
        }
    }
}

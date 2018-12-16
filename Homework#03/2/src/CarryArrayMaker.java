public class CarryArrayMaker implements Runnable {
    private int left;
    private int right;

    public CarryArrayMaker (int left, int right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        int sum = 0;
        Carry carrySum = Carry.M;
        for (int i = left; i < right; i++) {
            sum = Main.a[i] + Main.b[i];
            if (sum < 9) {
                Main.carries[i] = Carry.N;
            } else if (sum == 9) {
                Main.carries[i] = Carry.M;
            } else {
                Main.carries[i] = Carry.C;
            }
            carrySum = Operator.sum(carrySum, Main.carries[i]);

        }
        Main.prefixes[left/Main.step] = carrySum;
    }
}

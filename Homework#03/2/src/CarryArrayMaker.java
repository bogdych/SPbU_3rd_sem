public class CarryArrayMaker implements Runnable {
    private int left;
    private int right;
    private int id;

    public CarryArrayMaker (int left, int right, int id) {
        this.left = left;
        this.right = right;
        this.id = id;
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
        Main.prefixes[id] = carrySum;
    }
}

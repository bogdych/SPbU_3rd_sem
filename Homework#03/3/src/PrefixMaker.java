public class PrefixMaker implements Runnable {
    private int left;
    private int right;
    private int id;

    public PrefixMaker(int left, int right, int id) {
        this.left = left;
        this.right = right;
        this.id = id;
    }

    @Override
    public void run() {
        Pair sum = Main.pairs[left];
        for (int i = left + 1; i < right; i++) {
            sum = Pair.sumPairs(sum, Main.pairs[i]);
        }
        Main.prefixes[id] = sum;
    }
}
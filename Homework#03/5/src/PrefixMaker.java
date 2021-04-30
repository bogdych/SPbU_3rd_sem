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
        Coordinates sum = Main.coordinates[left];
        for (int i = left + 1; i < right; i++) {
            sum = Coordinates.sumPairs(sum, Main.coordinates[i]);
        }
        Main.prefixes[id] = sum;

    }
}
public class AnglePrefixMaker implements Runnable {
    private int left;
    private int right;
    private int id;

    public AnglePrefixMaker(int left, int right, int id) {
        this.left = left;
        this.right = right;
        this.id = id;
    }

    @Override
    public void run(){
        int sum = 0;
        for (int i = left; i < right; i++) {
            sum += Main.angles[i];
        }
        Main.anglePrefixes[id] = sum;
    }
}

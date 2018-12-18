public class Main {
    public static int THREADS = 4;
    public static int depth;
    public static Pair[] pairs;
    public static volatile Pair[] prefixes;
    public static volatile int result;

    public static void main(String[] args) throws InterruptedException {
        depth = 6;
        pairs = new Pair[depth];

        pairs[0] = new Pair(0,9);
        pairs[1] = new Pair(8,5);
        pairs[2] = new Pair(5,3);
        pairs[3] = new Pair(5,5);
        pairs[4] = new Pair(1,2);
        pairs[5] = new Pair(3,8);

        multithreadedVersion();
        System.out.println(result);
    }

    private static void multithreadedVersion() throws InterruptedException{
        Thread[] threads = new Thread[THREADS];
        prefixes = new Pair[THREADS];
        int step = depth / THREADS;
        for (int i = 0; i < THREADS - 1; i++) {
            threads[i] = new Thread(new PrefixMaker(i * step, (i + 1) * step, i));
            threads[i].start();
        }
        PrefixMaker last = new PrefixMaker((THREADS - 1) * step, depth, THREADS - 1);
        last.run();

        for (int i = 0; i < THREADS - 1; i++) {
            threads[i].join();
        }

        startPrefixScan();
    }

    private static void startPrefixScan(){
        Tree head = new Tree(1, Main.THREADS);
        head.create_tree(1, Main.THREADS);
        result = head.upsweep().b;
    }

    private static void singlethreadedVersion() {
        int x = pairs[0].b;
        for (int i = 1; i < depth; i++) {
            x = x * pairs[i].a + pairs[i].b;
        }
        result = x;
    }
}
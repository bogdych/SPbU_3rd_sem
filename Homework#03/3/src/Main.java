public class Main {
    public static int THREADS = 4;
    public static int depth;
    public static Pair[] pairs;
    public static volatile Pair[] prefixes;
    public static volatile long result;
    public static int MAX_THREADS = 4;


    public static void main(String[] args){
        depth = 10000000;
        pairs = getPairs();

        int amountOfTests = 20;
        long[][] results = new long[MAX_THREADS + 1][amountOfTests];

        for (int t = 1; t < MAX_THREADS + 1; t++) {
            for (int i = 0; i < amountOfTests; i++) {
                THREADS = (int) Math.pow(2, t - 1);
                long startTime = System.nanoTime();
                try {
                    multithreadedVersion();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long resTime = System.nanoTime() - startTime;
                results[t][i] = resTime;
            }
        }

        for (int i = 0; i < amountOfTests; i++) {
            long startTime = System.nanoTime();
            singlethreadedVersion();
            long resTime = System.nanoTime() - startTime;
            results[0][i] = resTime;
        }

        System.out.println("Average time (ms)");
        System.out.println("Single-Threaded version - " + (getAverageTime(results[0]) / 1000000) + "," + (getAverageTime(results[0]) / 1000 % 1000));
        System.out.println("Multithreaded version:");
        for (int i = 1; i < MAX_THREADS + 1; i++) {
            System.out.println(((int) (Math.pow(2, i - 1))) + ". " + (getAverageTime(results[i]) / 1000000) + "," + (getAverageTime(results[i]) / 1000 % 1000));
        }
    }

    public static long getAverageTime(long[] array) {
        long res = 0;
        for (int i = 0; i < array.length; i++) {
            res += array[i];
        }
        return res / array.length;
    }

    private static Pair[] getPairs(){
        Pair[] res = new Pair[depth];
        res[0] = new Pair(0, (int) Math.floor(Math.random() * 3 + 1));
        for (int i = 1; i < depth; i++) {
            res[i] = new Pair((int) Math.floor(Math.random() * 3 + 1), (int) Math.floor(Math.random() * 3 + 1));
        }
        return res;
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
        long x = pairs[0].b;
        for (int i = 1; i < depth; i++) {
            x = x * pairs[i].a + pairs[i].b;
        }
        result = x;
    }
}
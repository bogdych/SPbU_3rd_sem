public class Main {
    public static int strLength;
    public static int THREADS = 4;
    public static volatile int[] prefixes;
    public static int[] array;
    private static Thread[] threads;
    public static int step;
    public static int MAX_THREADS = 4;

    public static void main(String[] args){
        strLength = 100000000;
        String str = getString();
        array = new int[strLength];
        for (int i = 0; i < strLength; i++) {
            array[i] = str.charAt(i) == '(' ? 1 : -1;
        }

        int amountOfTests = 20;
        long[][] results = new long[MAX_THREADS + 1][amountOfTests];

        for (int t = 1; t < MAX_THREADS + 1; t++) {
            for (int i = 0; i < amountOfTests; i++) {
                THREADS = (int) Math.pow(2, t - 1);
                long startTime = System.nanoTime();
                try {
                    multithreadedBalance();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long resTime = System.nanoTime() - startTime;
                results[t][i] = resTime;
            }
        }

        for (int i = 0; i < amountOfTests; i++) {
            long startTime = System.nanoTime();
            singlethreadedBalance();
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

    private static String getString(){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < strLength / 2; i++) {
            res.append("(");
        }
        for (int i = strLength / 2; i < strLength; i++) {
            res.append(")");
        }
        return res.toString();
    }

    private static boolean multithreadedBalance() throws InterruptedException {
        prefixes = new int[THREADS];
        step = strLength / THREADS;
        threads = new Thread[THREADS - 1];
        for (int i = 0; i < THREADS - 1; i++) {
            threads[i] = new Thread(new PrefixMaker(i * step, (i + 1) * step, i));
            threads[i].start();
        }

        PrefixMaker last = new PrefixMaker((THREADS - 1) * step, strLength, THREADS - 1);
        last.run();


        for (int i = 0; i < THREADS - 1; i++) {
            threads[i].join();
        }

        startPrefixScan();

        for (int i = 0; i < strLength; i++) {
            if (array[i] < 0) {
                return false;
            }
        }
        return array[strLength - 1] == 0;
    }

    private static void startPrefixScan() throws InterruptedException {
        Tree head = new Tree(1, Main.THREADS);
        head.create_tree(1, Main.THREADS);
        head.upsweep();
        head.downsweep(0);

        for (int i = 0; i < THREADS - 1; i++) {
            threads[i] = new Thread(new PrefixHandler(i * step, (i + 1) * step, i));
            threads[i].start();
        }

        PrefixHandler last = new PrefixHandler((THREADS - 1) * step, strLength, THREADS - 1);
        last.run();


        for (int i = 0; i < THREADS - 1; i++) {
            threads[i].join();
        }
    }

    private static boolean singlethreadedBalance() {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            if (sum < 0) {
                return false;
            }
        }
        return sum == 0;
    }
}
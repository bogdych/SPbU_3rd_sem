public class Main {
    public static int THREADS = 4;
    public static  int[] a;
    public static  int[] b;
    private static int maxLength;
    public static volatile Carry[] prefixes;
    public static volatile Carry[] carries;
    private static Thread[] threads;
    public static int step;
    public static volatile int[] res;

    public static void main(String[] args) throws InterruptedException {
        maxLength = (int) Math.floor(Math.random() * 20 + 10);
        a = getNumber();
        b = getNumber();
        res = new int[maxLength];
        step = maxLength / THREADS;
        prefixes = new Carry[THREADS];
        carries = new Carry[maxLength];

        threads = new Thread[THREADS];
        for (int i = 0; i < THREADS - 1; i++) {
            threads[i] = new Thread(new CarryArrayMaker(i * step, (i + 1) * step ));
            threads[i].start();
        }

        CarryArrayMaker last = new CarryArrayMaker((THREADS - 1) * step, maxLength);
        last.run();

        for (int i = 0; i < THREADS - 1; i++) {
            threads[i].join();
        }

        startPrefixScan();
        getResult();
        printResult(res);

        int[] singleRes = singleThreadSum();
        printResult(singleRes);
    }

    private static int[] getIntArr(String str, int length) {
        int[] array = new int[length];
        StringBuilder rev = new StringBuilder(str).reverse();

        for (int i = 0; i < length; i++) {
            array[i] = rev.charAt(i) - '0';
        }

        return array;
    }

    private static void startPrefixScan() throws InterruptedException {
        Tree head = new Tree(1, Main.THREADS);
        head.create_tree(Main.prefixes, 1, Main.THREADS);
        head.upsweep();
        head.downsweep(Carry.M);

        for (int i = 0; i < THREADS - 1; i++) {
            threads[i] = new Thread(new CarryArrayHandler(i * step, (i + 1) * step));
            threads[i].start();
        }

        CarryArrayHandler last = new CarryArrayHandler((THREADS - 1) * step, maxLength);
        last.run();

        for (int i = 0; i < THREADS - 1; i++) {
            threads[i].join();
        }
    }

    private static void getResult() throws InterruptedException {
        for (int i = 0; i < THREADS - 1; i++) {
            threads[i] = new Thread(new ResultMaker(i * step, (i + 1) * step));
            threads[i].start();
        }

        ResultMaker last = new ResultMaker((THREADS - 1) * step, maxLength);
        last.run();

        for (int i = 0; i < THREADS - 1; i++) {
            threads[i].join();
        }
    }

    private static void printResult(int[] number) {
        for (int i = number.length; i > 0; i--) {
            System.out.print(number[i - 1]);
        }
        System.out.println();
    }

    private static int[] getNumber() {
         int[] array = new int[maxLength];
         for (int i = 0; i < maxLength; i++) {
             array[i] = (int) Math.floor(Math.random() * 10);
         }

         return array;
    }

    private static int[] singleThreadSum() {
        int carry = 0;
        int[] array = new int[maxLength];
        for (int i = 0; i < maxLength; i++) {
            array[i] = (a[i] + b[i] + carry) % 10;
            carry    = (a[i] + b[i] + carry) / 10;
        }

        return array;
    }
}

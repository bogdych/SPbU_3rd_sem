public class Main {
    public static int strLength;
    public static int THREADS = 4;
    public static volatile int[] prefixes;
    public static int[] array;
    private static Thread[] threads;
    public static int step;

    public static void main(String[] args) throws InterruptedException {
        String str = ")(())()(";
        strLength = str.length();
        array = new int[strLength];
        for (int i = 0; i < strLength; i++) {
            array[i] = str.charAt(i) == '(' ? 1 : -1;
        }
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

        boolean flag = false;
        for (int i = 0; i < strLength; i++) {
            if (array[i] < 0) {
                flag = true;
                break;
            }
        }
        if (flag) {
            System.out.println("The parentheses aren't matching");
        }
        else {
            if (array[strLength - 1] != 0) {
                System.out.println("The parentheses aren't matching");
            }
            else {
                System.out.println("The parentheses are matching");
            }
        }
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
}